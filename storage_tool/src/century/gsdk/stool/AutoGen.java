package century.gsdk.stool;

import century.gsdk.docker.GameDocker;
import century.gsdk.stool.objs.StorageToolApplication;

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
public class AutoGen {
    public static void main(String[] args){
        String rootPath=System.getProperty("user.dir");
        String dir=rootPath+ File.separator+"storage_tool";
        GameDocker docker=new GameDocker(StorageToolApplication.getInstance());
        docker.start(dir,"StorageTool");
        StorageToolApplication.getInstance().buildDataBaseAndTable();
    }
}