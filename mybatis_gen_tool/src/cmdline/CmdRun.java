package cmdline;

import ext.ExeInfo;
import ext.GenExt;

import java.io.File;

public class CmdRun {
    public static void main(String[] args){
        String cur=System.getProperty("user.dir");
        ExeInfo exeInfo=new ExeInfo();
        GenExt.run(cur,cur+ File.separator+"test/generator/stage.xml",exeInfo);
    }
}
