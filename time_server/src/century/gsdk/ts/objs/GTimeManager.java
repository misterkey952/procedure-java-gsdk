package century.gsdk.ts.objs;

import java.util.HashMap;
import java.util.Map;

public class GTimeManager {
    private Map<Integer,GTime> timeMap=new HashMap<>();
    private static final GTimeManager instance=new GTimeManager();
    private GTimeManager(){}
    public static GTimeManager getInstance(){
        return instance;
    }

    public GTime getGTime(int timeID){
        return timeMap.get(timeID);
    }

    public GTime createGTime(int timeID){
        GTime time=new GTime(timeID);
        timeMap.put(timeID,time);
        return time;
    }

}
