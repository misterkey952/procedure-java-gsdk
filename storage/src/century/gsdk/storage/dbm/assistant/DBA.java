package century.gsdk.storage.dbm.assistant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented //文档  
@Retention(RetentionPolicy.RUNTIME) //在运行时可以获取  
@Inherited //子类会继承  
@Target({ ElementType.TYPE, ElementType.METHOD,ElementType.FIELD }) //作用到类，方法，接口上等  
public @interface DBA {

	public String Table() default "";

	public String Field() default "";

	public int Length() default 0;

	public boolean Key() default false;

	public boolean Increment() default false;
	
}
