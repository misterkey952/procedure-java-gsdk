package century.gsdk.net.core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
