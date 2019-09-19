package century.gsdk.net.core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
public abstract class NetSession {
    private static final Logger logger= LoggerFactory.getLogger("NetSession");
    private Map<String,Object> attribute=new ConcurrentHashMap<>();
    private SessionCloseHook closeHook;

    public abstract void sendMsg(Object msg);
    public abstract void syncSendMsg(Object msg);
    public abstract String localIP();
    public abstract int localPort();
    public abstract String remoteIP();
    public abstract int remotePort();
    public void close(){

        if(closeHook!=null){
            closeHook.hook();
        }
        onClose();
    }

    protected void initCloseHook(SessionCloseHook closeHook){
        this.closeHook=closeHook;
        closeHook.init(this);
    }
    public abstract void onClose();
    public void attribute(String key,Object value){
        attribute.put(key,value);
    }


    public <T>T attribute(String key){
        return (T) attribute.get(key);
    }
    public void info(String msg, Object... info){
        logger.info("[LOC={}:{}@REM={}:{}]-->"+msg,localIP(),localPort(),remoteIP(),remotePort(),info);
    }

    public void debug(String msg,Object... info){
        logger.debug("[LOC={}:{}@REM={}:{}]-->"+msg,localIP(),localPort(),remoteIP(),remotePort(),info);
    }

    public void warn(String msg,Object... info){
        logger.warn("[LOC={}:{}@REM={}:{}]-->"+msg,localIP(),localPort(),remoteIP(),remotePort(),info);
    }

    public void error(String msg,Throwable e){
        logger.error("[LOC="+localIP()+":"+localPort()+"@REM="+remoteIP()+":"+remotePort()+"]-->"+msg,e);
    }
}
