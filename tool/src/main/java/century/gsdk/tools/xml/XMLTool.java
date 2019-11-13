package century.gsdk.tools.xml;

import century.gsdk.tools.ToolLogger;
import century.gsdk.tools.classic.TypeAssistant;
import century.gsdk.tools.gtpl.Template;
import century.gsdk.tools.str.StringTool;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;
/**
 *     Copyright (C) <2019>  <Century>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class XMLTool {
    public static Element getRootElement(File file){
        FileInputStream fileInputStream=null;
        try{
            fileInputStream=new FileInputStream(file);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(fileInputStream);
            Element rootElement = doc.getRootElement();
            return rootElement;
        }catch (Exception e){
            ToolLogger.XMLTool.error("getRootElement err",e);
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    ToolLogger.XMLTool.error("getRootElement close err",e);
                }
            }
        }
        return null;
    }
    public static Element getRootElement(String path){
        return getRootElement(new File(path));
    }


    public static List<Element> getElementsByTag(String tag,Element element){
        if(element==null){
            return new ArrayList<>();
        }
        if(element.elements(tag)!=null){
            return element.elements(tag);
        }else{
            return new ArrayList<>();
        }
    }


    public static <T> List<T> adapterText2List(Element ele, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list=new ArrayList<>();
        List<Element> elist=ele.elements();
        for(Element element:elist){
            list.add(adapterText2Object(element,clazz));
        }
        return list;
    }

    public static <K,V> Map<K,V> adapterText2Map(Element ele, Class<V> clazz, String keyName) throws Exception{
        Map<K,V> map=new HashMap<>();
        List<Element> elist=ele.elements();
        V v;
        Field keyField=null;
        K k;
        Boolean oldass=null;
        for(Element element:elist){
            try {
                oldass=null;
                v=adapterText2Object(element,clazz);
                keyField=clazz.getDeclaredField(keyName);
                oldass=keyField.isAccessible();
                keyField.setAccessible(true);
                k=(K) keyField.get(v);
                map.put(k,v);
            } catch (Exception e) {
                throw e;
            }finally {
                if(keyField!=null&&oldass!=null){
                    keyField.setAccessible(oldass);
                }
            }
        }
        return map;
    }


    public static <T>List<T> adapterAttr2List(Element ele, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list=new ArrayList<>();
        List<Element> elist=ele.elements();
        for(Element element:elist){
            list.add(adapterAttr2Object(element,clazz));
        }
        return list;
    }

    public static <K,V>Map<K,V> adapterAttr2Map(Element ele, Class<V> clazz, String keyName) throws Exception{
        Map<K,V> map=new HashMap<>();
        List<Element> elist=ele.elements();
        V v;
        Field keyField=null;
        K k;
        Boolean oldass=null;
        for(Element element:elist){
            try {
                oldass=null;
                v=adapterAttr2Object(element,clazz);
                keyField=clazz.getDeclaredField(keyName);
                oldass=keyField.isAccessible();
                keyField.setAccessible(true);
                k=(K) keyField.get(v);
                map.put(k,v);
            } catch (Exception e) {
                throw e;
            }finally {
                if(keyField!=null&&oldass!=null){
                    keyField.setAccessible(oldass);
                }
            }
        }
        return map;
    }

    public static <T>T adapterText2Object(Element e, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T t=clazz.newInstance();
        List<Field> fields= TypeAssistant.getFieldByAnnotation(clazz, Template.class);
        for(Field field:fields){
            boolean oldass=field.isAccessible();
            field.setAccessible(true);
            if(field.getType()==Integer.class||field.getType()==int.class){
                field.setInt(t,getIntTextValue(e,field.getName()));
            }else if(field.getType()==Short.class||field.getType()==short.class){
                field.setShort(t,getShortTextValue(e,field.getName()));
            }else if(field.getType()==Byte.class||field.getType()==byte.class){
                field.setByte(t,getByteTextValue(e,field.getName()));
            }else if(field.getType()==Long.class||field.getType()==long.class){
                field.setLong(t,getLongTextValue(e,field.getName()));
            }else if(field.getType()==Float.class||field.getType()==float.class){
                field.setFloat(t,getFloatTextValue(e,field.getName()));
            }else if(field.getType()==Double.class||field.getType()==double.class){
                field.setDouble(t,getDoubleTextValue(e,field.getName()));
            }else if(field.getType()==String.class){
                field.set(t,getStrTextValue(e,field.getName()));
            }else if(field.getType()== Date.class){
                field.set(t,getDateTextValue(e,field.getName()));
            }else if(field.getType()== Timestamp.class){
                field.set(t,getTimestampTextValue(e,field.getName()));
            }else if(field.getType()==Boolean.class||field.getType()==boolean.class){
                field.setBoolean(t,getBoolTextValue(e,field.getName()));
            }
            field.setAccessible(oldass);
        }
        return t;
    }


    public static <T>T adapterAttr2Object(Element e, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T t=clazz.newInstance();
        List<Field> fields=TypeAssistant.getFieldByAnnotation(clazz,Template.class);
        for(Field field:fields){
            boolean oldass=field.isAccessible();
            field.setAccessible(true);
            if(field.getType()==Integer.class||field.getType()==int.class){
                field.setInt(t,getIntAttrValue(e,field.getName()));
            }else if(field.getType()==Short.class||field.getType()==short.class){
                field.setShort(t,getShortAttrValue(e,field.getName()));
            }else if(field.getType()==Byte.class||field.getType()==byte.class){
                field.setByte(t,getByteAttrValue(e,field.getName()));
            }else if(field.getType()==Long.class||field.getType()==long.class){
                field.setLong(t,getLongAttrValue(e,field.getName()));
            }else if(field.getType()==Float.class||field.getType()==float.class){
                field.setFloat(t,getFloatAttrValue(e,field.getName()));
            }else if(field.getType()==Double.class||field.getType()==double.class){
                field.setDouble(t,getDoubleAttrValue(e,field.getName()));
            }else if(field.getType()==String.class){
                field.set(t,getStrAttrValue(e,field.getName()));
            }else if(field.getType()==Date.class){
                field.set(t,getDateAttrValue(e,field.getName()));
            }else if(field.getType()== Timestamp.class){
                field.set(t,getTimestampAttrValue(e,field.getName()));
            }else if(field.getType()==Boolean.class||field.getType()==boolean.class){
                field.setBoolean(t,getBoolAttrValue(e,field.getName()));
            }
            field.setAccessible(oldass);
        }
        return t;
    }


    public static int getIntTextValue(Element e){
        return StringTool.convertInt(e.getText());
    }
    public static byte getByteTextValue(Element e){
        return StringTool.convertByte(e.getText());
    }
    public static short getShortTextValue(Element e){
        return StringTool.convertShort(e.getText());
    }
    public static long getLongTextValue(Element e){
        return StringTool.convertLong(e.getText());
    }

    public static float getFloatTextValue(Element e){
        return StringTool.convertFloat(e.getText());
    }

    public static double getDoubleTextValue(Element e){
        return StringTool.convertDouble(e.getText());
    }
    public static boolean getBoolTextValue(Element e){
        return StringTool.convertBoolean(e.getText());
    }

    public static String getStrTextValue(Element e){
        return StringTool.convertString(e.getText());
    }

    public static Date getDateTextValue(Element e){
        return StringTool.convertDate(e.getText());
    }

    public static Timestamp getTimestampTextValue(Element e){
        return StringTool.convertTimestamp(e.getText());
    }



    public static int getIntTextValue(Element e, String key){
        return StringTool.convertInt(e.elementText(key));
    }
    public static byte getByteTextValue(Element e, String key){
        return StringTool.convertByte(e.elementText(key));
    }
    public static short getShortTextValue(Element e, String key){
        return StringTool.convertShort(e.elementText(key));
    }
    public static long getLongTextValue(Element e, String key){
        return StringTool.convertLong(e.elementText(key));
    }

    public static float getFloatTextValue(Element e, String key){
        return StringTool.convertFloat(e.elementText(key));
    }

    public static double getDoubleTextValue(Element e, String key){
        return StringTool.convertDouble(e.elementText(key));
    }
    public static boolean getBoolTextValue(Element e, String key){
        return StringTool.convertBoolean(e.elementText(key));
    }

    public static String getStrTextValue(Element e, String key){
        return StringTool.convertString(e.elementText(key));
    }

    public static Date getDateTextValue(Element e, String key){
        return StringTool.convertDate(e.elementText(key));
    }

    public static Timestamp getTimestampTextValue(Element e, String key){
        return StringTool.convertTimestamp(e.elementText(key));
    }


    public static int getIntAttrValue(Element e, String key){
        return StringTool.convertInt(e.attributeValue(key));
    }
    public static byte getByteAttrValue(Element e, String key){
        return StringTool.convertByte(e.attributeValue(key));
    }
    public static short getShortAttrValue(Element e, String key){
        return StringTool.convertShort(e.attributeValue(key));
    }
    public static long getLongAttrValue(Element e, String key){
        return StringTool.convertLong(e.attributeValue(key));
    }

    public static float getFloatAttrValue(Element e, String key){
        return StringTool.convertFloat(e.attributeValue(key));
    }

    public static double getDoubleAttrValue(Element e, String key){
        return StringTool.convertDouble(e.attributeValue(key));
    }
    public static boolean getBoolAttrValue(Element e, String key){
        return StringTool.convertBoolean(e.attributeValue(key));
    }

    public static String getStrAttrValue(Element e, String key){
        return StringTool.convertString(e.attributeValue(key));
    }

    public static Date getDateAttrValue(Element e, String key){
        return StringTool.convertDate(e.attributeValue(key));
    }

    public static Timestamp getTimestampAttrValue(Element e, String key){
        return StringTool.convertTimestamp(e.attributeValue(key));
    }


    public static Object convertByType(String type,String value){
        if("int".equals(type)){
            return StringTool.convertInt(value);
        }else if("byte".equals(type)){
            return StringTool.convertByte(value);
        }else if("short".equals(type)){
            return StringTool.convertShort(value);
        }else if("long".equals(type)){
            return StringTool.convertLong(value);
        }else if("float".equals(type)){
            return StringTool.convertFloat(value);
        }else if("double".equals(type)){
            return StringTool.convertDouble(value);
        }else if("boolean".equals(type)){
            return StringTool.convertBoolean(value);
        }
        return value;
    }


}
