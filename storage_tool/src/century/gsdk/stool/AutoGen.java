package century.gsdk.stool;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

import java.io.File;

public class AutoGen {
    public static void main(String[] args){
        String rootPath=System.getProperty("user.dir");
        Element rootElement= XMLTool.getRootElement(rootPath+ File.separator+"storage/cfg/table_create.xml");
        DBConn conn=new DBConn(rootElement.element("conn"));
    }
}
