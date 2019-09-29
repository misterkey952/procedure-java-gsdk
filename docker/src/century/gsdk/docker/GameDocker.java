package century.gsdk.docker;

import org.apache.log4j.xml.DOMConfigurator;

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
public class GameDocker {
    private GameApplication application;
    private String curPath;
    private String appPath;
    private String resPath;
    private String cfgPath;
    private String datPath;
    private static final GameDocker gameDocker=new GameDocker();
    private GameDocker(){
        curPath=System.getProperty("user.dir");
        appPath=curPath.replace(File.separator+"bin","");
        resPath=appPath+File.separator+"res";
        cfgPath=appPath+File.separator+"cfg";
        datPath=appPath+File.separator+"dat";
    }
    public static GameDocker getInstance(){
        return gameDocker;
    }

    public void start(GameApplication application){
        this.application=application;
        DOMConfigurator.configure(getCfgPath()+ File.separator +"log4j.xml");
        this.application.initialize();
    }


    public void resetPath(String curPath, String appPath, String resPath, String cfgPath, String datPath) {
        this.curPath = curPath;
        this.appPath = appPath;
        this.resPath = resPath;
        this.cfgPath = cfgPath;
        this.datPath = datPath;
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

    public GameApplication getApplication() {
        return application;
    }


}
