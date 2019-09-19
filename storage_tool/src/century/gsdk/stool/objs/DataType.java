package century.gsdk.stool.objs;

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
public enum DataType {
    BYTE("byte","byte","tinyint"),
    SHORT("short","short","smallint"),
    INT("int","int","int"),
    LONG("long","long","bigint"),
    FLOAT("float","float","float"),
    DOUBLE("double","double","double"),
    BOOL("bool","boolean","bool"),
    STRING("string","String","varchar"),
    LSTRING("lstring","String","text"),
    BLOB("blob","byte[]","blob"),
    JSON("json","String","text"),
    DATETIME("datetime","java.sql.Timestamp.Timestamp","datetime");
    private String typeName;
    private String javaType;
    private String mysqlType;

    DataType(String typeName, String javaType, String mysqlType) {
        this.typeName = typeName;
        this.javaType = javaType;
        this.mysqlType = mysqlType;
    }

    private static Map<String, DataType> id2type;
    static {
        id2type = new HashMap<String, DataType>();
        for (DataType type : DataType.values()) {
            id2type.put(type.getTypeName(), type);
        }
    }

    public String getTypeName() {
        return typeName;
    }

    public static DataType getDataType(String name){
        return id2type.get(name);
    }

    public String getJavaType() {
        return javaType;
    }

    public String getMysqlType() {
        return mysqlType;
    }
}
