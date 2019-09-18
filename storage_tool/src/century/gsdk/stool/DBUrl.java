package century.gsdk.stool;

public class DBUrl {
    private String ip;
    private String proto="jdbc";
    private String dbType="mysql";
    private String character="UTF-8";


    public DBUrl(String ip) {
        this.ip = ip;
    }

    public DBUrl(String ip, String proto, String dbType, String character) {
        this.ip = ip;
        this.proto = proto;
        this.dbType = dbType;
        this.character = character;
    }

    public String genDBURL(String dbname){
        return proto+":"+dbType+"://"+ip+"/"+dbname+"?useUnicode=true&amp;characterEncoding="+character;
    }

}
