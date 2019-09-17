package century.gsdk.docker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class GameApplication {

    private static final Logger logger=LoggerFactory.getLogger("GameApplication");
    private String appRootPath;
    private String resRootPath;
    private String cfgRootPath;
    private String appName;
    protected void init(String appRootPath,String appName) {
        this.appRootPath = appRootPath;
        this.resRootPath = appRootPath+ File.separator+"res";
        this.cfgRootPath = cfgRootPath+File.separator+"cfg";
        this.appName=appName;
        initialize();
    }

    public abstract void initialize();



    public void error(String msg,Throwable e){
        logger.error("[{}]"+msg,appName,e);
    }

    public void debug(String msg,Object... vars){
        logger.debug("[{}]"+msg,appName,vars);
    }

    public void warn(String msg,Object... vars){
        logger.warn("[{}]"+msg,appName,vars);
    }

    public void info(String msg,Object... vars){
        logger.info("[{}]"+msg,appName,vars);
    }

    public String getAppRootPath() {
        return appRootPath;
    }

    public String getResRootPath() {
        return resRootPath;
    }

    public String getCfgRootPath() {
        return cfgRootPath;
    }
}
