package century.gsdk.storage.core;

import java.util.HashMap;
import java.util.Map;
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
public enum DataBaseType {
    MYSQL("mysql","mysql");
    private String typeName;
    private String dataBaseTypeName;

    DataBaseType(String typeName, String dataBaseTypeName) {
        this.typeName = typeName;
        this.dataBaseTypeName = dataBaseTypeName;
    }

    private static Map<String, DataBaseType> id2type;
    static {
        id2type = new HashMap<String, DataBaseType>();
        for (DataBaseType type : DataBaseType.values()) {
            id2type.put(type.getTypeName(), type);
        }
    }

    public String getTypeName() {
        return typeName;
    }

    public static DataBaseType getDataType(String name){
        return id2type.get(name);
    }

    public String getDataBaseTypeName() {
        return dataBaseTypeName;
    }
}
