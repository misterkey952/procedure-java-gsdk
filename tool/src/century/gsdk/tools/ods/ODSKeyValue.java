package century.gsdk.tools.ods;

import century.gsdk.tools.str.StringTool;
import org.dom4j.Element;

import java.sql.Timestamp;
import java.util.Date;

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
public class ODSKeyValue {
    private String key;
    private String value;


    public void encodeElement(Element superElement){
        Element element=superElement.addElement("field");
        element.addAttribute("key",key);
        element.addAttribute("value",value);
    }

    public ODSKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public Object convert(Class clazz){
        if(clazz==Byte.class||clazz==byte.class||clazz==Short.class||clazz==short.class||clazz==int.class||clazz==Integer.class||clazz==long.class||clazz==Long.class){
            if(value.contains(".")){
                value=value.substring(0,value.indexOf("."));
            }
        }

        return StringTool.convert(value,clazz);
    }

    public Object keyValueOf(Class clazz){
        if(clazz==Byte.class||clazz==byte.class){
            return convert(clazz);
        }else if(clazz==Integer.class||clazz==int.class){
            return convert(clazz);
        }else if(clazz==Short.class||clazz==short.class){
            return convert(clazz);
        }else if(clazz==Long.class||clazz==long.class){
            return convert(clazz);
        }else if(clazz==Float.class||clazz==float.class){
            return convert(clazz);
        }else if(clazz==Double.class||clazz==double.class){
            return convert(clazz);
        }else if(clazz==String.class){
            return convert(clazz);
        }
        return null;
    }
}
