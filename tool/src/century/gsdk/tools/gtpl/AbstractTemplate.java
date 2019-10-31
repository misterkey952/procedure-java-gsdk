package century.gsdk.tools.gtpl;

import century.gsdk.tools.ods.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) <2019>  <Century>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * <p>
 * Author's Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public abstract class AbstractTemplate {

    private static final Logger logger= LoggerFactory.getLogger(AbstractTemplate.class);

    private String directory;
    private String name_ods;
    private String name_xml;

    public AbstractTemplate(String directory, String name_ods, String name_xml) {
        this.directory = directory;
        this.name_ods = name_ods;
        this.name_xml = name_xml;
    }

    private void filterRelation(TemplateRelation templateRelation,ODSFile odsFile){
        if(!templateRelation.astemplate()){
            return;
        }
        Class clazz=templateRelation.clazz();
        TemplateClass templateClass= (TemplateClass) clazz.getAnnotation(TemplateClass.class);
        ODSSheet sheet=new ODSSheet(clazz.getName(),templateClass.name());
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            TemplateField templateField=field.getAnnotation(TemplateField.class);
            sheet.addHead(field.getName(),templateField.name(),templateField.des());
        }
        odsFile.addSheet(sheet);
    }

    public void outputStruct(){
        ODSFile odsFile=new MSODSFile(name_ods,directory);
        Class clazz=this.getClass();
        Field[] fieldArr=clazz.getDeclaredFields();
        for(Field field:fieldArr){
            boolean accessible=field.isAccessible();
            field.setAccessible(true);
            TemplateRelation templateRelation=field.getAnnotation(TemplateRelation.class);
            if(templateRelation!=null){
                filterRelation(templateRelation,odsFile);
            }
            field.setAccessible(accessible);
        }
        odsFile.out();
    }


    private void adapterFieldValue(Field field,ODSKeyValue keyValue,Object object) throws IllegalAccessException {
        if(field.getType()==Byte.class||field.getType()==byte.class){
            field.setByte(object,keyValue.byteValue());
        }else if(field.getType()==Short.class||field.getType()==short.class){
            field.setShort(object,keyValue.shortValue());
        }else if(field.getType()==Integer.class||field.getType()==int.class){
            field.setInt(object,keyValue.intValue());
        }else if(field.getType()==Long.class||field.getType()==long.class){
            field.setLong(object,keyValue.longValue());
        }else if(field.getType()==Float.class||field.getType()==float.class){
            field.setFloat(object,keyValue.floatValue());
        }else if(field.getType()==Double.class||field.getType()==double.class){
            field.setDouble(object,keyValue.doubleValue());
        }else if(field.getType()==Boolean.class||field.getType()==boolean.class){
            field.setBoolean(object,keyValue.booleanValue());
        }else if(field.getType()== Timestamp.class){
            field.set(object,keyValue.convertTimestamp());
        }else if(field.getType()== Date.class){
            field.set(object,keyValue.convertDate());
        }else if(field.getType()== String.class){
            field.set(object,keyValue.getValue());
        }

    }

    private void filterInput(TemplateRelation templateRelation,ODSFile odsFile,Class2ObjectManager comanager) throws Exception{
        if(!templateRelation.astemplate()){
            return;
        }
        TemplateClass templateClass= (TemplateClass) templateRelation.clazz().getAnnotation(TemplateClass.class);
        ODSSheet sheet=odsFile.getSheet(templateRelation.clazz().getName());
        Class2Object class2Object=comanager.getClass2Object(templateRelation.clazz());
        Object object;
        for(ODSRecord record:sheet.getRecordList()){
            object=templateRelation.clazz().newInstance();
            class2Object.addObject(object);
            Field[] fields=templateRelation.clazz().getDeclaredFields();
            for(Field field:fields){
                boolean accessible=field.isAccessible();
                field.setAccessible(true);
                ODSKeyValue keyValue=record.getKeyValue(field.getName());
                adapterFieldValue(field,keyValue,object);
                field.setAccessible(accessible);
            }
        }
        System.out.println("ddd");
    }

    public void inputFromODS() throws Exception {
        ODSFile odsFile=new MSODSFile(name_ods,directory);
        odsFile.in();
        Class2ObjectManager comanager=new Class2ObjectManager();
        Class clazz=this.getClass();
        Field[] fieldArr=clazz.getDeclaredFields();
        for(Field field:fieldArr){
            boolean accessible=field.isAccessible();
            field.setAccessible(true);
            TemplateRelation templateRelation=field.getAnnotation(TemplateRelation.class);
            if(templateRelation!=null){
                filterInput(templateRelation,odsFile,comanager);
            }
            field.setAccessible(accessible);
        }
    }

}
