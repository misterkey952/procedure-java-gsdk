package century.gsdk.stool;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

public class DBConn {
    private DBUrl url;
    private String user;
    private String passwd;
    private String driver;

    public DBConn(Element element) {
        url=new DBUrl(XMLTool.getStrTextValue(element,"host"));
        user=XMLTool.getStrTextValue(element,"user");
        passwd=XMLTool.getStrTextValue(element,"passwd");
        driver=XMLTool.getStrTextValue(element,"driver");
    }

    public DBUrl getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getDriver() {
        return driver;
    }
}
