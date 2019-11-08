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


    Map<Object,Object> getKeyMap(String keyField) throws IllegalAccessException, NoSuchFieldException {
        Map<Object,Object> map=fieldMap.get(keyField);
        if(map==null){
            map=new HashMap<>();
            fieldMap.put(keyField,map);
            Field field=clazz.getDeclaredField(keyField);
            boolean ai=field.isAccessible();
            field.setAccessible(true);
            for(Object obj:objectList){
                Object key=field.get(obj);
                if(!map.containsKey(key)){
                    map.put(key,obj);
                }
            }
            field.setAccessible(ai);
        }

        return map;
    }

    Map<Object,List<Object>> getGroupMap(String groupField) throws NoSuchFieldException, IllegalAccessException {
        Map<Object,List<Object>> objectListMap=fieldListMap.get(groupField);
        if(objectListMap==null){
            objectListMap=new HashMap<>();
            fieldListMap.put(groupField,objectListMap);
            Field field=clazz.getDeclaredField(groupField);
            boolean ai=field.isAccessible();
            field.setAccessible(true);
            for(Object obj:objectList){
                Object key=field.get(obj);
                List<Object> list=objectListMap.get(key);
                if(list==null){
                    list=new ArrayList<>();
                    objectListMap.put(key,list);
                }
                list.add(obj);
            }
            field.setAccessible(ai);
        }

        return objectListMap;
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
