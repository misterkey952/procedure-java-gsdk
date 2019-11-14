package century.gsdk.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class LogAssisant {
    private static final Logger std= LoggerFactory.getLogger(LogAssisant.class);
    public static void listLogger(Class clazz){
        try{
            Field[] fields=clazz.getDeclaredFields();
            for(Field field:fields){
                Object o=field.get(null);
                Method method=o.getClass().getMethod("getName");
                std.info("{} has a logger named {}",clazz.getSimpleName(),method.invoke(o).toString());
            }
        }catch(Exception e){
            std.error("listLogger",e);
        }
    }
}
