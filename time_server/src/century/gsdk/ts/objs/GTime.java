package century.gsdk.ts.objs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class GTime implements Runnable{
    private static final Logger logger= LoggerFactory.getLogger("GTIME");
    private int timeID;
    private long timeUnit=250;
    private long startTimestamp;
    private long timeCount;
    private TimeEventHandle timeEventHandle;
    private boolean onoff;
    public GTime(int timeID) {
        this.timeID = timeID;
        timeEventHandle=new TimeEventHandle();
    }

    @Override
    public void run() {
        try{
            while(onoff){
                Thread.sleep(timeUnit);
                timeCount++;
                timeEventHandle.addTimeCount(timeCount);
            }
            saveTimeData();
        }catch (Exception e){
            logger.error("["+timeID+"] run exception",e);
        }
    }

    public void start(){
        onoff=true;
        timeEventHandle.start();
        TimeThreadTool.getInstance().execute(this);
    }

    public void shutdown(){
        onoff=false;
        timeEventHandle.shutdown();
    }

    private void saveTimeData(){
    }

    private void loadTimeData(){

    }

    public TimeEventHandle getTimeEventHandle() {
        return timeEventHandle;
    }
}
