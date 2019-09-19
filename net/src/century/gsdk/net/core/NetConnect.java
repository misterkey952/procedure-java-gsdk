package century.gsdk.net.core;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *     Copyright (C) <2019>  <Century>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public abstract class NetConnect {
    private static final Logger logger= LoggerFactory.getLogger("NetConnect");
    private String name;
    private String ip;
    private int port;
    private Map<Integer,NetMessageHandle> handleMap;
    public NetConnect(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        handleMap=new HashMap<>();
    }

    public void close(){
        onClose();
        onUserClose();
    }

    public abstract void start();
    public abstract void onClose();
    public abstract void onUserClose();
    public abstract void registerSession(NetSession session);
    public abstract NetSession session(Object param);

    public void registerHandle(int msgType,NetMessageHandle handle){
        handleMap.put(msgType,handle);
    }


    public void loadMessageHandle(Element element){
        List<Element> elements= XMLTool.getElementsByTag("handle",element);
        int type;
        NetMessageHandle msgHandle;
        for(Element ele:elements){
            type=XMLTool.getIntAttrValue(ele,"type");
            try {
                if(ele.attributeValue("handle")!=null){
                    msgHandle= (NetMessageHandle) Class.forName(XMLTool.getStrAttrValue(ele,"handle")).newInstance();
                    registerHandle(type,msgHandle);
                }
            } catch (Exception e) {
                error("loadMessageHandle err",e);
            }
        }
    }

    public NetMessageHandle getMessageHandle(int msgType){
        return handleMap.get(msgType);
    }

    public void info(String msg, Object... info){
        logger.info("[{}][{}:{}]-->"+msg,name,ip,port,info);
    }

    public void debug(String msg,Object... info){
        logger.debug("[{}][{}:{}]-->"+msg,name,ip,port,info);
    }

    public void warn(String msg,Object... info){
        logger.warn("[{}][{}:{}]-->"+msg,name,ip,port,info);
    }

    public void error(String msg,Throwable e){
        logger.info("["+name+"][{"+ip+"}:{"+port+"}]-->"+msg,e);
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
