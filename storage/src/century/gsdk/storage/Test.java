package century.gsdk.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
public class Test {

	public static void main(String[] args) {
		Map<String,String> map=new ConcurrentHashMap<String,String>();
		String kkk="keysssffffffffffsssssd";
		long start=System.currentTimeMillis();
		for(int i=1;i<=50000000;i++){
			map.put(kkk+i,"valueabcaaaaaaaaaaa"+i);
		}
		
		System.out.println("init time="+(System.currentTimeMillis()-start)+"ms");
		long st=System.currentTimeMillis();
		System.out.println(map.get(kkk+5000000));
		//map.put("slkjdflksjdlkfjsldk","valueabcaaaaaaaaaasssa");
		System.out.println("search time="+(System.currentTimeMillis()-st)+"ms");
		
	}

}
