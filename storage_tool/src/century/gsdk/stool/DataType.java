package century.gsdk.stool;

import java.util.HashMap;
import java.util.Map;

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
