package assist.buildtable;

import century.gsdk.storage.core.DataType;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
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
public class ColumnEntity {
    private String name;
    private DataType dataType;
    private int length;
    private String defaultValue;
    private boolean incr;
    private String keyType;
    public ColumnEntity(Element element) {
        name= XMLTool.getStrAttrValue(element,"name");
        dataType=DataType.getDataType(XMLTool.getStrAttrValue(element,"type"));
        length=XMLTool.getIntAttrValue(element,"length");
        defaultValue=XMLTool.getStrAttrValue(element,"defaultValue");
        incr=XMLTool.getBoolAttrValue(element,"incre");
        keyType=XMLTool.getStrAttrValue(element,"keyType");
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public int getLength() {
        return length;
    }

    public String getDefaultValue() {
        return defaultValue;
    }


    public boolean isIncr() {
        return incr;
    }

    public String getKeyType() {
        return keyType;
    }
}
