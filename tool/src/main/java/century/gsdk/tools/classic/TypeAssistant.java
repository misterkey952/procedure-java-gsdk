package century.gsdk.tools.classic;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
 * Author's Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class TypeAssistant {
    public static Class getFieldClass(Field field){
        if(Map.class.isAssignableFrom(field.getType())){
            Type type=field.getGenericType();
            if(type instanceof ParameterizedTypeImpl){
                ParameterizedTypeImpl ppt= (ParameterizedTypeImpl) type;
                Type subType=ppt.getActualTypeArguments()[1];
                if(subType instanceof ParameterizedTypeImpl){
                    ppt= (ParameterizedTypeImpl) subType;
                    return (Class) ppt.getActualTypeArguments()[0];
                }else{
                    return (Class) subType;
                }
            }else{
                return field.getType();
            }
        }else if(List.class.isAssignableFrom(field.getType())){
            ParameterizedTypeImpl ppt= (ParameterizedTypeImpl) field.getGenericType();
            return (Class) ppt.getActualTypeArguments()[0];
        }else{
            return field.getType();
        }
    }


    public static Class getKeyTypeInMap(Field field){
        if(Map.class.isAssignableFrom(field.getType())){
            Type type=field.getGenericType();
            if(type instanceof ParameterizedTypeImpl){
                ParameterizedTypeImpl ppt= (ParameterizedTypeImpl) type;
                Type subType=ppt.getActualTypeArguments()[0];
                if(subType instanceof ParameterizedTypeImpl){
                    ppt= (ParameterizedTypeImpl) subType;
                    return ppt.getRawType();
                }else{
                    return (Class) subType;
                }
            }
        }
        return null;
    }

    public static Class getValueTypeInMap(Field field){
        if(Map.class.isAssignableFrom(field.getType())){
            Type type=field.getGenericType();
            if(type instanceof ParameterizedTypeImpl){
                ParameterizedTypeImpl ppt= (ParameterizedTypeImpl) type;
                Type subType=ppt.getActualTypeArguments()[1];
                if(subType instanceof ParameterizedTypeImpl){
                    ppt= (ParameterizedTypeImpl) subType;
                    return ppt.getRawType();
                }else{
                    return (Class) subType;
                }
            }
        }
        return null;
    }


    private static void filterSuperClass(Class clazz,Class annClazz,List<Field> list){
        Class superClass=clazz.getSuperclass();
        if(superClass!=null){
            filterSuperClass(superClass,annClazz,list);
        }
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            if(field.getAnnotation(annClazz)!=null){
                list.add(field);
            }
        }
    }

    public static List<Field> getFieldByAnnotation(Class clazz,Class annClazz){
        List<Field> list=new ArrayList<>();
        Class superClass=clazz.getSuperclass();
        if(superClass!=null){
            filterSuperClass(superClass,annClazz,list);
        }

        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            if(field.getAnnotation(annClazz)!=null){
                list.add(field);
            }
        }
        return list;
    }

}
