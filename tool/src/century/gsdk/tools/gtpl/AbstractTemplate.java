package century.gsdk.tools.gtpl;

import century.gsdk.tools.ods.*;
import century.gsdk.tools.str.StringTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void saveStructODS(){
        ODSFile odsFile=new MSODSFile(name_ods,directory);
        Class[] structClasses=assemble();
        for(Class clazz:structClasses){
            TemplateClass templateClass= (TemplateClass) clazz.getAnnotation(TemplateClass.class);
            ODSSheet sheet=new ODSSheet(clazz.getName(),templateClass.name());
            Field[] fields=clazz.getDeclaredFields();
            for(Field field:fields){
                TemplateField templateField=field.getAnnotation(TemplateField.class);
                sheet.addHead(field.getName(),templateField.name(),templateField.des());
            }
            odsFile.addSheet(sheet);
        }
        odsFile.out();
    }


    private void adapterFieldValue(Field field,ODSKeyValue keyValue,Object object,Class2ObjectManager comanager,ODSFile odsFile) throws Exception {
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
        }else if(List.class.isAssignableFrom(field.getType())){
            adapterList(field,keyValue,object,comanager,odsFile);
        }else if(Map.class.isAssignableFrom(field.getType())){
            adapterMap(field,keyValue,object,comanager,odsFile);
        }else{
            Class objClazz=field.getType();
            if(!comanager.isRead(objClazz)){
                readClass(comanager,objClazz,odsFile);
            }
            TemplateField templateField=field.getAnnotation(TemplateField.class);
            Class2Object class2Object=comanager.getClass2Object(objClazz);
            Object obj=class2Object.getRefObject(templateField.ref(),keyValue);
            field.set(object,obj);
        }
    }


    private Object objKey(Field ref,String v) throws Exception {
        if(ref.getType()==byte.class||ref.getType()==Byte.class){
            return StringTool.convertByte(v);
        }else if(ref.getType()==int.class||ref.getType()==Integer.class){
            return StringTool.convertInt(v);
        }else if(ref.getType()==short.class||ref.getType()==Short.class){
            return StringTool.convertShort(v);
        }else if(ref.getType()==long.class||ref.getType()==Long.class){
            return StringTool.convertLong(v);
        }else if(ref.getType()==float.class||ref.getType()==Float.class){
            return StringTool.convertFloat(v);
        }else if(ref.getType()==double.class||ref.getType()==Double.class){
            return StringTool.convertDouble(v);
        }else if(ref.getType()==String.class){
            return StringTool.convertString(v);
        }
        throw new Exception("The ref key must be basic type,include byte short int long float double string.");
    }

    private Object objKey(String ref,String v) throws Exception {
        if("byte".equals(ref)){
            return StringTool.convertByte(v);
        }else if("int".equals(ref)||StringTool.SPACE.equals(ref)){
            return StringTool.convertInt(v);
        }else if("short".equals(ref)){
            return StringTool.convertShort(v);
        }else if("long".equals(ref)){
            return StringTool.convertLong(v);
        }else if("float".equals(ref)){
            return StringTool.convertFloat(v);
        }else if("double".equals(ref)){
            return StringTool.convertDouble(v);
        }else if("string".equals(ref)){
            return StringTool.convertString(v);
        }
        throw new Exception("The ref key must be basic type,include byte short int long float double string.");
    }

    private void adapterMap(Field field,ODSKeyValue keyValue,Object object,Class2ObjectManager comanager,ODSFile odsFile) throws Exception {
        TemplateField templateField=field.getAnnotation(TemplateField.class);
        if(templateField.refClass()==Byte.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertByte(kav[1]));
            }
        }else if(templateField.refClass()==Short.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertShort(kav[1]));
            }
        }else if(templateField.refClass()==Integer.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertInt(kav[1]));
            }
        }else if(templateField.refClass()==Long.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertLong(kav[1]));
            }
        }else if(templateField.refClass()==Float.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertFloat(kav[1]));
            }
        }else if(templateField.refClass()==Double.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertDate(kav[1]));
            }
        }else if(templateField.refClass()==String.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertString(kav[1]));
            }
        }else if(templateField.refClass()==Date.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertDate(kav[1]));
            }
        }else if(templateField.refClass()==Timestamp.class){
            Map map=new HashMap();
            field.set(object,map);
            String[] kvstr=StringTool.convertStringArr(keyValue.getValue(),";");
            for(String kvv:kvstr){
                String[] kav=StringTool.convertStringArr(kvv,",");
                map.put(objKey(templateField.ref(),kav[0]),StringTool.convertTimestamp(kav[1]));
            }
        }else{
            if(!comanager.isRead(templateField.refClass())){
                readClass(comanager,templateField.refClass(),odsFile);
            }
            String ref=templateField.ref();
            Class2Object class2Object=comanager.getClass2Object(templateField.refClass());
            Map map=class2Object.getFieldMap(ref);

            if(StringTool.SPACE.equals(keyValue.getValue())){
                field.set(object,map);
            }else{
                Map submap=new HashMap();
                String[] kkvs=StringTool.convertStringArr(keyValue.getValue(),";");
                for(String kk:kkvs){
                    Object obj=objKey(class2Object.getKeyField(ref),kk);
                    submap.put(obj,map.get(obj));
                }
                field.set(object,submap);
            }
        }
    }

    private void adapterList(Field field,ODSKeyValue keyValue,Object object,Class2ObjectManager comanager,ODSFile odsFile) throws Exception {
        TemplateField templateField=field.getAnnotation(TemplateField.class);
        if(templateField.refClass()==Byte.class){
            List<Byte> list=new ArrayList<>();
            field.set(object,list);
            byte[] varr= StringTool.convertByteArr(keyValue.getValue(),";");
            for(byte v:varr){
                list.add(v);
            }
        }else if(templateField.refClass()==Short.class){
            List<Short> list=new ArrayList<>();
            field.set(object,list);
            short[] varr= StringTool.convertShortArr(keyValue.getValue(),";");
            for(short v:varr){
                list.add(v);
            }
        }else if(templateField.refClass()==Integer.class){
            List<Integer> list=new ArrayList<>();
            field.set(object,list);
            int[] varr= StringTool.convertIntArr(keyValue.getValue(),";");
            for(int v:varr){
                list.add(v);
            }
        }else if(templateField.refClass()==Long.class){
            List<Long> list=new ArrayList<>();
            field.set(object,list);
            long[] varr= StringTool.convertLongArr(keyValue.getValue(),";");
            for(long v:varr){
                list.add(v);
            }
        }else if(templateField.refClass()==Float.class){
            List<Float> list=new ArrayList<>();
            field.set(object,list);
            float[] varr= StringTool.convertFloatArr(keyValue.getValue(),";");
            for(float v:varr){
                list.add(v);
            }
        }else if(templateField.refClass()==Double.class){
            List<Double> list=new ArrayList<>();
            field.set(object,list);
            double[] varr= StringTool.convertDoubleArr(keyValue.getValue(),";");
            for(double v:varr){
                list.add(v);
            }
        }else if(templateField.refClass()==String.class){
            List<String> list=new ArrayList<>();
            field.set(object,list);
            String[] varr= StringTool.convertStringArr(keyValue.getValue(),";");
            list.addAll(Arrays.asList(varr));
        }else if(templateField.refClass()==Date.class){
            List<Date> list=new ArrayList<>();
            field.set(object,list);
            String[] varr= StringTool.convertStringArr(keyValue.getValue(),";");
            for(String vd:varr){
                list.add(StringTool.convertDate(vd));
            }
        }else if(templateField.refClass()==Timestamp.class){
            List<Timestamp> list=new ArrayList<>();
            field.set(object,list);
            String[] varr= StringTool.convertStringArr(keyValue.getValue(),";");
            for(String vd:varr){
                list.add(StringTool.convertTimestamp(vd));
            }
        }else{
            if(!comanager.isRead(templateField.refClass())){
                readClass(comanager,templateField.refClass(),odsFile);
            }
            String ref=templateField.ref();
            Class2Object class2Object=comanager.getClass2Object(templateField.refClass());
            if(StringTool.SPACE.equals(ref)){
                List list=class2Object.getObjectList();
                field.set(object,list);
            }else{
                if(keyValue.getValue().indexOf(";")<=0){
                    List list=class2Object.getRefObjectList(ref,keyValue);
                    field.set(object,list);
                }else{
                    Map map=class2Object.getFieldMap(ref);
                    List list=new ArrayList();
                    String[] kkvs=StringTool.convertStringArr(keyValue.getValue(),";");
                    for(String kk:kkvs){
                        Object obj=objKey(class2Object.getKeyField(ref),kk);
                        list.add(map.get(obj));
                    }
                    field.set(object,list);
                }

            }

        }
    }

    protected abstract Class[] assemble();
    private void readClass(Class2ObjectManager comanager,Class clazz,ODSFile odsFile) throws Exception{
        Class2Object class2Object=comanager.createClass2Object(clazz);
        ODSSheet sheet=odsFile.getSheet(clazz.getName());
        Field[] fieldList=clazz.getDeclaredFields();
        Object object;
        for(ODSRecord record:sheet.getRecordList()){
            object=clazz.newInstance();
            class2Object.addObject(object);
            for(Field field:fieldList){
                boolean accessible=field.isAccessible();
                field.setAccessible(true);
                adapterFieldValue(field,record.getKeyValue(field.getName()),object,comanager,odsFile);
                field.setAccessible(accessible);

            }
        }
    }

    public void readFromODS() throws Exception {
        ODSFile odsFile=new MSODSFile(name_ods,directory);
        odsFile.in();
        Class[] structClasses=assemble();
        Class2ObjectManager comanager=new Class2ObjectManager();
        for(Class clazz:structClasses){
            if(comanager.isRead(clazz)){
                continue;
            }
            readClass(comanager,clazz,odsFile);
        }

        Class rootClass=this.getClass();
        Field[] rootFields=rootClass.getDeclaredFields();
        for(Field field:rootFields){
            TemplateAssemble templateField=field.getAnnotation(TemplateAssemble.class);
            if(templateField!=null){
                Class2Object class2Object=comanager.getClass2Object(templateField.clazz());
                if(Map.class.isAssignableFrom(field.getType())){
                    if(templateField.islist()){
                        Map lmap=class2Object.getFieldList(templateField.key());
                        field.set(this,lmap);
                    }else{
                        Map map=class2Object.getFieldMap(templateField.key());
                        field.set(this,map);
                    }
                }else if(List.class.isAssignableFrom(field.getType())){
                    List list=class2Object.getObjectList();
                    field.set(this,list);
                }
            }
        }
    }

}
