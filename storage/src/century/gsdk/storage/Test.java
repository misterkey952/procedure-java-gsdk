package century.gsdk.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
