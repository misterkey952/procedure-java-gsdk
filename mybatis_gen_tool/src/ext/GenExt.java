package ext;
import org.mybatis.generator.api.ShellRunner;
import java.util.List;
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
