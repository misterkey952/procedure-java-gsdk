package century.gsdk.storage.test;

public class AccountStruct {
    private String id;

    private String opertor_id;

    private String login_name;

    public AccountStruct() {
        
    }

    public AccountStruct(String id, String opertor_id, String login_name) {
        this.id=id;
        this.opertor_id=opertor_id;
        this.login_name=login_name;
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
}