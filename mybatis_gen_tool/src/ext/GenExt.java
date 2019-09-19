package ext;
import org.mybatis.generator.api.ShellRunner;
import java.util.List;
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
 *     Author Email:   misterkey952@gmail.com     Copyright (C) <2019>  <Century>
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
public class GenExt {
    public static int FILE_ERR=1;
    public static int SUCCESS=0;
    public static int EXCEPTION_ERR=2;
    public static String ROOTDIR;
    private static final Object lock=new Object();

    public static int run(String rootDir,String path,ExeInfo exeInfo){
        synchronized (lock){
            ROOTDIR=rootDir;
            List<String> warns= null;
            if(path.endsWith(".xml")){
                try {
                    warns= ShellRunner.main(new String[]{
                            ShellRunner.CONFIG_FILE,
                            path,
                            ShellRunner.OVERWRITE
                    });
                }catch (Exception e) {
                    exeInfo.appendException(e);
                    return EXCEPTION_ERR;
                }
                if(warns!=null){
                    for(String s:warns){
                        exeInfo.appendString(s);
                    }
                }
            }else{
                return FILE_ERR;
            }
            return SUCCESS;
        }
    }
}
