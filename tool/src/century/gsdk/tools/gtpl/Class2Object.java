package century.gsdk.tools.gtpl;

import century.gsdk.tools.ods.ODSKeyValue;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Author' Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
class Class2Object {
    private Class clazz;
    private List<Object> objectList;
    private Map<String,Map<Object,Object>> fieldMap;
    private Map<String,Map<Object,List<Object>>> fieldListMap;
    Class2Object(Class clazz) {
        this.clazz = clazz;
        objectList=new ArrayList<>();
        fieldMap=new HashMap<>();
        fieldListMap=new HashMap<>();
    }


    boolean checkKeyType(Field field){
        if(
                field.getType()==byte.class||
                        field.getType()==Byte.class||
                        field.getType()==short.class||
                        field.getType()==Short.class||
                        field.getType()==Integer.class||
                        field.getType()==int.class||
                        field.getType()==long.class||
                        field.getType()==Long.class||
                        field.getType()==float.class||
                        field.getType()==Float.class||
                        field.getType()==double.class||
                        field.getType()==Double.class||
                        field.getType()==String.class
        ){
            return true;
        }
        return false;
    }

    List<Object> getRefObjectList(String key,ODSKeyValue keyValue) throws Exception {
        Map<Object,List<Object>> map=getFieldList(key);
        Field field=clazz.getDeclaredField(key);
        if(field.getType()==byte.class||field.getType()==Byte.class){
            return map.get(keyValue.byteValue());
        }else if(field.getType()==short.class||field.getType()==Short.class){
            return map.get(keyValue.shortValue());
        }else if(field.getType()==int.class||field.getType()==Integer.class){
            return map.get(keyValue.intValue());
        }else if(field.getType()==long.class||field.getType()==Long.class){
            return map.get(keyValue.longValue());
        }else if(field.getType()==float.class||field.getType()==Float.class){
            return map.get(keyValue.floatValue());
        }else if(field.getType()==double.class||field.getType()==Double.class){
            return map.get(keyValue.doubleValue());
        }else if(field.getType()==String.class){
            return map.get(keyValue.getValue());
        }
        throw new Exception("there is no object ");
    }


    Object getRefObject(String key,ODSKeyValue keyValue) throws Exception {
        Map<Object,Object> map=getFieldMap(key);
        Field field=clazz.getDeclaredField(key);
        if(field.getType()==byte.class||field.getType()==Byte.class){
            return map.get(keyValue.byteValue());
        }else if(field.getType()==short.class||field.getType()==Short.class){
            return map.get(keyValue.shortValue());
        }else if(field.getType()==int.class||field.getType()==Integer.class){
            return map.get(keyValue.intValue());
        }else if(field.getType()==long.class||field.getType()==Long.class){
            return map.get(keyValue.longValue());
        }else if(field.getType()==float.class||field.getType()==Float.class){
            return map.get(keyValue.floatValue());
        }else if(field.getType()==double.class||field.getType()==Double.class){
            return map.get(keyValue.doubleValue());
        }else if(field.getType()==String.class){
            return map.get(keyValue.getValue());
        }
        throw new Exception("there is no object ");
    }

    Map<Object,List<Object>> getFieldList(String fkey) throws Exception{
        Field field=clazz.getDeclaredField(fkey);
        if(!checkKeyType(field)){
            throw new Exception("The ref key must be basic type,include byte short int long float double string.");
        }
        boolean accessimble=field.isAccessible();
        field.setAccessible(true);
        Map<Object,List<Object>> listMap=fieldListMap.get(fkey);
        if(listMap==null){
            listMap=new HashMap<>();
            fieldListMap.put(fkey,listMap);
            for(Object o:objectList){
                Object key=field.get(o);
                List<Object> objList = listMap.computeIfAbsent(key, k -> new ArrayList<>());
                objList.add(o);
            }
        }

        field.setAccessible(accessimble);
        return listMap;
    }


    Field getKeyField(String ref) throws NoSuchFieldException {
        return clazz.getDeclaredField(ref);
    }

    Map<Object,Object> getFieldMap(String fkey) throws Exception {
        Field field=clazz.getDeclaredField(fkey);
        if(!checkKeyType(field)){
            throw new Exception("The ref key must be basic type,include byte short int long float double string.");
        }
        boolean accessimble=field.isAccessible();
        field.setAccessible(true);
        Map<Object,Object> map=fieldMap.get(fkey);
        if(map==null){
            map=new HashMap<>();
            fieldMap.put(fkey,map);
            for(Object o:objectList){
                Object key=field.get(o);
                map.put(key,o);
            }
        }

        field.setAccessible(accessimble);
        return map;
    }

    List<Object> getObjectList(){
        return objectList;
    }

    void addObject(Object object){
        if(objectList.contains(object)){
            return;
        }
        objectList.add(object);
    }
}
