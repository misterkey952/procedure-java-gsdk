package century.gsdk.tools.gtpl;

import century.gsdk.tools.classic.IEnum;
import century.gsdk.tools.classic.TypeAssistant;
import century.gsdk.tools.ods.ODSFile;
import century.gsdk.tools.ods.ODSKeyValue;
import century.gsdk.tools.ods.ODSRecord;
import century.gsdk.tools.ods.ODSSheet;
import century.gsdk.tools.str.StringTool;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    Class2ObjectManager comanager=new Class2ObjectManager();
    ODSCoder(String name, String directory) {
        this.name = name;
        this.directory = directory;
    }


    Class2Object readAsClass2Object(Class clazz) throws Exception {
        Class2Object class2Object=comanager.getClass2Object(clazz);
        if(class2Object==null){
            ODSSheet sheet=getSheet(clazz);
            class2Object=comanager.createClass2Object(clazz);
            for(ODSRecord record:sheet.getRecordList()){
                class2Object.addObject(record2Object(record,clazz));
            }
        }
        return class2Object;
    }


    void input(Field dataField,Object object) throws Exception {
        Class dataClass= TypeAssistant.getFieldClass(dataField);
        Template template= (Template) dataClass.getAnnotation(Template.class);
        if(template==null){
            return;
        }

        Template templateField=dataField.getAnnotation(Template.class);
        if(templateField==null){
            return;
        }

        boolean accessible=dataField.isAccessible();
        dataField.setAccessible(true);
        if(!comanager.isRead(dataClass)){
            readAsClass2Object(dataClass);
        }
        Class2Object class2Object=comanager.getClass2Object(dataClass);
        if(Map.class.isAssignableFrom(dataField.getType())){
            if(List.class.isAssignableFrom(TypeAssistant.getValueTypeInMap(dataField))){
                dataField.set(object,class2Object.getGroupMap(templateField.key()));
            }else{
                dataField.set(object,class2Object.getKeyMap(templateField.key()));
            }
        }else if(List.class.isAssignableFrom(dataField.getType())){
            dataField.set(object,class2Object.getObjectList());
        }
        dataField.setAccessible(accessible);
    }

    void output(Field dataField,Object object) throws Exception {
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


    void readCompositeType(Field field,ODSKeyValue keyValue,Object object) throws Exception{
        Template template=field.getAnnotation(Template.class);
        if(template==null){
            return;
        }
        Class dataClazz=TypeAssistant.getFieldClass(field);
        Class2Object class2Object=readAsClass2Object(dataClazz);


        Field refField=dataClazz.getDeclaredField(template.ref());
        String[] keyStrArr= (String[]) keyValue.convert(String[].class);

        if(Map.class.isAssignableFrom(field.getType())){
            Field keyField=dataClazz.getDeclaredField(template.key());
            boolean keyAcc=keyField.isAccessible();
            keyField.setAccessible(true);
            Class valueType=TypeAssistant.getValueTypeInMap(field);
            if(List.class.isAssignableFrom(valueType)){
                Map<Object,List<Object>> map=class2Object.getGroupMap(template.ref());
                List<Object> tmpList=new ArrayList<>();
                for(String refKey:keyStrArr){
                    Object kk=StringTool.convert(refKey,refField.getType());
                    tmpList.addAll(map.get(kk));
                }

                Map<Object,List<Object>> objMap=new HashMap<>();
                for(Object objj:tmpList){
                    Object objKk=keyField.get(objj);
                    List<Object> ll=objMap.get(objKk);
                    if(ll==null){
                        ll=new ArrayList<>();
                        objMap.put(objKk,ll);
                    }
                    ll.add(objj);
                }
                field.set(object,objMap);
            }else{
                Map<Object,List<Object>> map=class2Object.getGroupMap(template.ref());
                List<Object> tmpList=new ArrayList<>();
                for(String refKey:keyStrArr){
                    Object kk=StringTool.convert(refKey,refField.getType());
                    tmpList.addAll(map.get(kk));
                }

                Map<Object,Object> objMap=new HashMap<>();
                for(Object objj:tmpList){
                    Object objKk=keyField.get(objj);
                    if(!objMap.containsKey(objKk)){
                        objMap.put(objKk,objj);
                    }
                }
                field.set(object,objMap);
            }

            keyField.setAccessible(keyAcc);
        }else if(List.class.isAssignableFrom(field.getType())){
            Map<Object,List<Object>> map=class2Object.getGroupMap(template.ref());
            List<Object> tmpList=new ArrayList<>();
            for(String refKey:keyStrArr){
                Object kk=StringTool.convert(refKey,refField.getType());
                tmpList.addAll(map.get(kk));
            }
            field.set(object,tmpList);
        }else{
            Map<Object,Object> map=class2Object.getKeyMap(template.ref());
            Object oomb=map.get(keyValue.keyValueOf(refField.getType()));
            field.set(object,oomb);
        }
    }

    Object record2Object(ODSRecord odsRecord,Class clazz) throws Exception {
        Field[] fields=clazz.getDeclaredFields();
        Object object=clazz.newInstance();
        for(Field field:fields){
            boolean accessible=field.isAccessible();
            field.setAccessible(true);
            ODSKeyValue keyValue=odsRecord.getKeyValue(field.getName());
            if(keyValue==null){
                continue;
            }
            if(StringTool.supportType(field.getType())){
                field.set(object,keyValue.convert(field.getType()));
            }else{
                readCompositeType(field,keyValue,object);
            }
            field.setAccessible(accessible);
        }
        return object;
    }
    void convertRecord(Object obj,ODSSheet sheet) throws Exception {
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
                        if(i<refSet.size()){
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
                        if(i<refSet.size()){
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

    void genSheetItera(Class rootClass) throws Exception {
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


    ODSSheet genSheet(Class clazz) throws Exception{
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
            if(template==null){
                continue;
            }

            StringBuilder desBuild=new StringBuilder();
            if(!StringTool.SPACE.equals(template.des())){
                desBuild.append(template.des());
            }

            Class ienumClass=null;
            if(IEnum.class.isAssignableFrom(field.getType())){
                ienumClass=field.getType();
            }else if(IEnum[].class.isAssignableFrom(field.getType())){
                ienumClass=Class.forName(field.getType().getTypeName().substring(0,field.getType().getTypeName().length()-2));
            }


            if(ienumClass!=null){
                desBuild.append("\r\n[").append(ienumClass.getSimpleName());
                Method method=ienumClass.getDeclaredMethod("values");
                IEnum[] enums= (IEnum[]) method.invoke(null);
                for(IEnum enu:enums){
                    desBuild.append("\r\n").append(enu.value()).append(":").append(enu.des());
                }
                desBuild.append("]");
            }

            Class refClass=TypeAssistant.getFieldClass(field);
            Template refTemplate= (Template) refClass.getAnnotation(Template.class);
            if(refTemplate!=null){
                desBuild.append("\r\nplease ref to the field ["+template.ref()+"] in the sheet ["+refTemplate.name()+"]");
            }
            sheet.addHead(field.getName(),template.name(),desBuild.toString());
        }
        return sheet;
    }


    ODSSheet getSheet(Class clazz) throws Exception {
        ODSSheet sheet=odsFile.getSheet(clazz.getName());
        if(sheet==null){
            sheet=genSheet(clazz);
        }
        return sheet;
    }

    public ODSFile getOdsFile() {
        return odsFile;
    }

    protected abstract void decode(Object object)throws Exception;
    protected abstract void encode(Object object)throws Exception;
}
