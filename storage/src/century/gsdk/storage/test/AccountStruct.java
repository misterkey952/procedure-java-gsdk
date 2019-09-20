package century.gsdk.storage.test;

public class AccountStruct {
    private String id;

    private String opertor_id;

    private String login_name;

    private java.sql.Timestamp create_time;

    private String create_ip;

    private String lastlogin_ip;

    private long lastlogin_date;

    private java.sql.Timestamp lastlogout_date;

    private int gold;

    private int online;

    private int platform;

    private byte ostype;

    private int subplatform;

    private String last_idfa;

    public AccountStruct() {
        
    }

    public AccountStruct(String id, String opertor_id, String login_name, java.sql.Timestamp create_time, String create_ip, String lastlogin_ip, long lastlogin_date, java.sql.Timestamp lastlogout_date, int gold, int online, int platform, byte ostype, int subplatform, String last_idfa) {
        this.id=id;
        this.opertor_id=opertor_id;
        this.login_name=login_name;
        this.create_time=create_time;
        this.create_ip=create_ip;
        this.lastlogin_ip=lastlogin_ip;
        this.lastlogin_date=lastlogin_date;
        this.lastlogout_date=lastlogout_date;
        this.gold=gold;
        this.online=online;
        this.platform=platform;
        this.ostype=ostype;
        this.subplatform=subplatform;
        this.last_idfa=last_idfa;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getOpertor_id() {
        return this.opertor_id;
    }

    public void setOpertor_id(String opertor_id) {
        this.opertor_id=opertor_id;
    }

    public String getLogin_name() {
        return this.login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name=login_name;
    }

    public java.sql.Timestamp getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(java.sql.Timestamp create_time) {
        this.create_time=create_time;
    }

    public String getCreate_ip() {
        return this.create_ip;
    }

    public void setCreate_ip(String create_ip) {
        this.create_ip=create_ip;
    }

    public String getLastlogin_ip() {
        return this.lastlogin_ip;
    }

    public void setLastlogin_ip(String lastlogin_ip) {
        this.lastlogin_ip=lastlogin_ip;
    }

    public long getLastlogin_date() {
        return this.lastlogin_date;
    }

    public void setLastlogin_date(long lastlogin_date) {
        this.lastlogin_date=lastlogin_date;
    }

    public java.sql.Timestamp getLastlogout_date() {
        return this.lastlogout_date;
    }

    public void setLastlogout_date(java.sql.Timestamp lastlogout_date) {
        this.lastlogout_date=lastlogout_date;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold=gold;
    }

    public int getOnline() {
        return this.online;
    }

    public void setOnline(int online) {
        this.online=online;
    }

    public int getPlatform() {
        return this.platform;
    }

    public void setPlatform(int platform) {
        this.platform=platform;
    }

    public byte getOstype() {
        return this.ostype;
    }

    public void setOstype(byte ostype) {
        this.ostype=ostype;
    }

    public int getSubplatform() {
        return this.subplatform;
    }

    public void setSubplatform(int subplatform) {
        this.subplatform=subplatform;
    }

    public String getLast_idfa() {
        return this.last_idfa;
    }

    public void setLast_idfa(String last_idfa) {
        this.last_idfa=last_idfa;
    }
}