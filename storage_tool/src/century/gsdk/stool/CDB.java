package century.gsdk.stool;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class CDB {
    private String dbName;
    private int split;
    private List<CTable> tables;

    public CDB(Element element) {
        tables=new ArrayList<>();
        dbName= XMLTool.getStrAttrValue(element,"name");
        split=XMLTool.getIntAttrValue(element,"split");
        List<Element> elements=element.elements();
        for(Element e:elements){
            tables.add(new CTable(e));
        }
    }
}
