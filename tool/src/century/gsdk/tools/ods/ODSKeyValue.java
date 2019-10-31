package century.gsdk.tools.ods;

import century.gsdk.tools.str.StringTool;

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

    public byte byteValue(){
        if(value.contains(".")){
            return StringTool.convertByte(value.substring(0,value.indexOf(".")));
        }
        return StringTool.convertByte(value);
    }
    public short shortValue(){
        if(value.contains(".")){
            return StringTool.convertShort(value.substring(0,value.indexOf(".")));
        }
        return StringTool.convertShort(value);
    }
    public int intValue(){
        if(value.contains(".")){
            return StringTool.convertInt(value.substring(0,value.indexOf(".")));
        }
        return StringTool.convertInt(value);
    }

    public long longValue(){
        if(value.contains(".")){
            return StringTool.convertLong(value.substring(0,value.indexOf(".")));
        }
        return StringTool.convertLong(value);
    }

    public float floatValue(){
        return StringTool.convertFloat(value);
    }

    public double doubleValue(){
        return StringTool.convertDouble(value);
    }

    public Timestamp convertTimestamp(){
        return StringTool.convertTimestamp(value);
    }
    public Date convertDate(){
        return StringTool.convertDate(value);
    }

    public boolean convertBoolean(){
        return StringTool.convertBoolean(value);
    }
}
