package century.gsdk.stool;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

public class Column {
    private String name;
    private DataType dataType;
    private int length;
    private String defaultValue;
    private boolean pk;
    private boolean incr;
    private String des;
    public Column(Element element) {
        name= XMLTool.getStrAttrValue(element,"name");
        dataType=DataType.getDataType(XMLTool.getStrAttrValue(element,"type"));
        length=XMLTool.getIntAttrValue(element,"length");
        defaultValue=XMLTool.getStrAttrValue(element,"defaultValue");
        pk=XMLTool.getBoolAttrValue(element,"pk");
        incr=XMLTool.getBoolAttrValue(element,"incre");
        des=XMLTool.getStrAttrValue(element,"des");
    }
}
