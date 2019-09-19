package century.gsdk.docker;

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
    private String appRootPath;
    private String resRootPath;
    private String cfgRootPath;
    private String datRootPath;
    private String appName;
    protected void init(String appRootPath,String appName) {
        this.appRootPath = appRootPath;
        this.resRootPath = appRootPath+ File.separator+"res";
        this.cfgRootPath = appRootPath+File.separator+"cfg";
        this.datRootPath= appRootPath+File.separator+"dat";
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
