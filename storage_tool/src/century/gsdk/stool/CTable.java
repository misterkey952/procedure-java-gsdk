package century.gsdk.stool;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class CTable {
    private String name;
    private int split;
    private String character;
    private List<Column> columns;

    public CTable(Element element) {
        columns=new ArrayList<>();
        name= XMLTool.getStrAttrValue(element,"name");
        split=XMLTool.getIntAttrValue(element,"split");
        character=XMLTool.getStrAttrValue(element,"character");
        List<Element> list=element.elements();
        for(Element e:list){
            columns.add(new Column(e));
        }
    }
}
