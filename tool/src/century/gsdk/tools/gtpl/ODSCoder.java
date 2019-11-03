package century.gsdk.tools.gtpl;

import century.gsdk.tools.classic.TypeAssistant;
import century.gsdk.tools.ods.ODSFile;
import century.gsdk.tools.ods.ODSKeyValue;
import century.gsdk.tools.ods.ODSRecord;
import century.gsdk.tools.ods.ODSSheet;
import century.gsdk.tools.str.StringTool;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;

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
public abstract class ODSCoder{
    String name;
    String directory;
    ODSFile odsFile;
    Set<Object> records=new HashSet<>();
    ODSCoder(String name, String directory) {
        this.name = name;
        this.directory = directory;
    }


    void output(Field dataField,Object object) throws IllegalAccessException, NoSuchFieldException {
        Class dataClass= TypeAssistant.getFieldClass(dataField);
        Template template= (Template) dataClass.getAnnotation(Template.class);
        if(template==null){
            return;
        }
        boolean accessible=dataField.isAccessible();
        dataField.setAccessible(true);

        ODSSheet sheet=getSheet(dataClass);

        Object dataObject=dataField.get(object);

        if(dataObject==null){
            dataField.setAccessible(accessible);
            return;
        }

        if(Map.class.isAssignableFrom(dataObject.getClass())){
            Map<Object,Object> objectObjectMap= (Map<Object, Object>) dataObject;
            for(Object objm:objectObjectMap.values()){
                if(List.class.isAssignableFrom(objm.getClass())){
                    List<Object> objlist= (List<Object>) objm;
                    for(Object o:objlist){
                        convertRecord(o,sheet);
                    }
                }else{
                    convertRecord(objm,sheet);
                }
            }
        }else if(List.class.isAssignableFrom(dataObject.getClass())){
            List<Object> objects= (List<Object>) dataObject;
            for(Object oo:objects){
                convertRecord(oo,sheet);
            }
        }else{
            convertRecord(dataObject,sheet);
        }

        dataField.setAccessible(accessible);
    }

    void convertRecord(Object obj,ODSSheet sheet) throws IllegalAccessException, NoSuchFieldException {
        if(records.contains(obj)){
            return;
        }
        ODSRecord record=new ODSRecord();
        sheet.addRecord(record);
        records.add(obj);
        Field[] fields=obj.getClass().getDeclaredFields();
        for(Field field:fields){
            boolean accessible=field.isAccessible();
            field.setAccessible(true);
            if(StringTool.supportType(field.getType())){
                record.addKV(field.getName(), StringTool.valueOf(field.get(obj)));
            }else{
                output(field,obj);
                Template template=field.getAnnotation(Template.class);
                if(Map.class.isAssignableFrom(field.getType())){
                    Map<Object,Object> map= (Map<Object, Object>) field.get(obj);
                    StringBuilder stb=new StringBuilder();
                    List<Object> list=new ArrayList<>();
                    for(Object mobj:map.values()){
                        if(List.class.isAssignableFrom(mobj.getClass())){
                            list.addAll((Collection<?>) mobj);
                        }else{
                            list.add(mobj);
                        }
                    }


                    Set<Object> refSet=new HashSet<>();
                    for(Object refObj:list){
                        Field refField=refObj.getClass().getDeclaredField(template.ref());
                        boolean ai=refField.isAccessible();
                        refField.setAccessible(true);
                        Object refff=refField.get(refObj);
                        if(!refSet.contains(refff)){
                            refSet.add(refff);
                        }
                        refField.setAccessible(ai);
                    }

                    int i=0;
                    for(Object key:refSet){
                        i++;
                        stb.append(StringTool.valueOf(key));
                        if(i<map.size()){
                            stb.append(StringTool.DEFAULT_SPLIT_OF_ARR);
                        }
                    }
                    record.addKV(field.getName(),stb.toString());
                }else if(List.class.isAssignableFrom(field.getType())){
                    StringBuilder stb=new StringBuilder();
                    List<Object> list= (List<Object>) field.get(obj);
                    Set<Object> refSet=new HashSet<>();
                    for(Object refObj:list){
                        Field refField=refObj.getClass().getDeclaredField(template.ref());
                        boolean ai=refField.isAccessible();
                        refField.setAccessible(true);
                        Object refff=refField.get(refObj);
                        if(!refSet.contains(refff)){
                            refSet.add(refff);
                        }
                        refField.setAccessible(ai);
                    }

                    int i=0;
                    for(Object key:refSet){
                        i++;
                        stb.append(StringTool.valueOf(key));
                        if(i<list.size()){
                            stb.append(StringTool.DEFAULT_SPLIT_OF_ARR);
                        }
                    }
                    record.addKV(field.getName(),stb.toString());
                }else{
                    Object refObj=field.get(obj);
                    Field refField=refObj.getClass().getDeclaredField(template.ref());
                    boolean ai=refField.isAccessible();
                    refField.setAccessible(true);
                    Object refKey=refField.get(refObj);
                    record.addKV(field.getName(),StringTool.valueOf(refKey));
                    refField.setAccessible(ai);
                }
            }
            field.setAccessible(accessible);
        }
    }


    boolean contain(Object object){
        return records.contains(object);
    }

    void genSheetItera(Class rootClass){
        Field[] fields=rootClass.getDeclaredFields();
        for(Field field:fields){
            Class clazz=TypeAssistant.getFieldClass(field);
            Template template= (Template) clazz.getAnnotation(Template.class);
            if(template!=null){
                genSheet(clazz);
                genSheetItera(clazz);
            }

        }
    }


    ODSSheet genSheet(Class clazz){
        Template template= (Template) clazz.getAnnotation(Template.class);
        if(template==null){
            return null;
        }

        ODSSheet sheet=odsFile.getSheet(clazz.getName());

        if(sheet!=null){
            return sheet;
        }

        sheet=new ODSSheet(clazz.getName(),template.name());
        odsFile.addSheet(sheet);
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            template=field.getAnnotation(Template.class);
            Class refClass=TypeAssistant.getFieldClass(field);
            Template refTemplate= (Template) refClass.getAnnotation(Template.class);
            if(refTemplate!=null){
                StringBuilder desBuild=new StringBuilder();
                desBuild.append("please ref to the field ["+template.ref()+"] in the sheet ["+refTemplate.name()+"]");
                sheet.addHead(field.getName(),template.name(),desBuild.toString());
            }else{
                sheet.addHead(field.getName(),template.name(),template.des());
            }
        }
        return sheet;
    }


    ODSSheet getSheet(Class clazz){
        ODSSheet sheet=odsFile.getSheet(clazz.getName());
        if(sheet==null){
            sheet=genSheet(clazz);
        }
        return sheet;
    }

    protected abstract void decode(Object object)throws Exception;
    protected abstract void encode(Object object)throws Exception;
}
