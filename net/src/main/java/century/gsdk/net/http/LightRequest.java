package century.gsdk.net.http;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:Century,Write on 2019/11/7
 * Description:
 */
public class LightRequest {
    private String uri;
    private Map<String,String> paramMap;
    private ByteBuf binaryData;

    public LightRequest(String uri,ByteBuf binaryData) {
        this.binaryData=binaryData;
        this.uri = uri.substring(0,uri.indexOf("?"));
        String pp=uri.substring(uri.indexOf("?")+1);
        String[] pps=pp.split("&");
        this.paramMap = new HashMap<>();
        for(String pv:pps){
            String[] am=pv.split("=");
            addParam(am[0],am[1]);
        }
    }


    public void addParam(String key, String value){
        paramMap.put(key,value);
    }

    public String getParam(String key){
        return paramMap.get(key);
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public ByteBuf getBinaryData() {
        return binaryData;
    }
}
