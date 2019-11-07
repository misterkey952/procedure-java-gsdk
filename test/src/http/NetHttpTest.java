package http;

import century.gsdk.tools.http.XHttpTool;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:Century,Write on 2019/11/7
 * Description:
 */
public class NetHttpTest {
    public static void main(String[] args){
        HttpApplication.getInstance().start();
        Map<String,String > map=new HashMap<>();
        map.put("fff1","2222");
        map.put("fff2","2222");
        map.put("fff3","2222");
        System.out.println(XHttpTool.post("http://127.0.0.1:8080/search/sdf?om=3&jj=7&mm=8","alsdjflkadjsfkljasdklf"));
    }
}
