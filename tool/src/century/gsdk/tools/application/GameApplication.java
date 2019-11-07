package century.gsdk.tools.application;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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
public abstract class GameApplication {
    private static final Logger logger=LoggerFactory.getLogger("GameApplication");
    private String appName;
    private String curPath;
    private String appPath;
    private String resPath;
    private String cfgPath;
    private String datPath;
    public GameApplication() {
        this.appName = this.getClass().getSimpleName();
        curPath=System.getProperty("user.dir");
        appPath=curPath.replace(File.separator+"bin","");
        resPath=appPath+File.separator+"res";
        cfgPath=appPath+File.separator+"cfg";
        datPath=appPath+File.separator+"dat";
    }


    public void start(){
        DOMConfigurator.configure(getCfgPath()+ File.separator +"log4j.xml");
        initialize();
    }

    protected abstract void initialize();

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


    public String getCurPath() {
        return curPath;
    }

    public String getAppPath() {
        return appPath;
    }

    public String getResPath() {
        return resPath;
    }

    public String getCfgPath() {
        return cfgPath;
    }

    public String getDatPath() {
        return datPath;
    }
}
