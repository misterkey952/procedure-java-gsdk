package century.gsdk.storage.dbm.assistant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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
