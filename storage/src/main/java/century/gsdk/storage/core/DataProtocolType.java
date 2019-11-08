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
public enum DataProtocolType {
    JDBC("jdbc","jdbc");
    private String typeName;
    private String protoName;

    DataProtocolType(String typeName, String protoName) {
        this.typeName = typeName;
        this.protoName = protoName;
    }

    private static Map<String, DataProtocolType> id2type;
    static {
        id2type = new HashMap<String, DataProtocolType>();
        for (DataProtocolType type : DataProtocolType.values()) {
            id2type.put(type.getTypeName(), type);
        }
    }

    public String getTypeName() {
        return typeName;
    }

    public static DataProtocolType getDataType(String name){
        return id2type.get(name);
    }

    public String getProtoName() {
        return protoName;
    }
}
