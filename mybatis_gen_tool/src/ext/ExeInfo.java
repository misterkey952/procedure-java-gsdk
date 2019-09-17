package ext;


import org.mybatis.generator.exception.XMLParserException;

import java.util.List;

public class ExeInfo {
    private StringBuffer stb=new StringBuffer();
    public void appendException(Exception e){
        stb.append(XTools.exceptionStack(e)).append("\r\n");
        if(e instanceof XMLParserException){
            XMLParserException em= (XMLParserException) e;
            List<String> errlist=em.getErrors();
            for(String es:errlist){
                stb.append(es).append("\r\n");
            }
        }

    }

    public void appendString(String st){
        stb.append(st).append("\r\n");
    }

    public String toString(){
        return stb.toString();
    }
}
