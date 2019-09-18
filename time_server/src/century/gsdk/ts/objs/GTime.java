package century.gsdk.ts.objs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GTime implements Runnable{
    private static final Logger logger= LoggerFactory.getLogger("GTIME");
    private static final GTime instance=new GTime();
    private static final long FRAME_RATE=50;
    private static final long TIME_TICK=1000;
    private long lastTimeStamp;
    private long currTimeStamp;
    private long timeCount;
    private long tickCount;
    private boolean onoff;
    private GTime() {
        lastTimeStamp=System.currentTimeMillis();
        currTimeStamp=lastTimeStamp;
    }

    public static GTime getInstance(){
        return instance;
    }

    @Override
    public void run() {
        try{
            while(onoff){
                Thread.sleep(FRAME_RATE);
                currTimeStamp=System.currentTimeMillis();
                tickCount=(currTimeStamp-lastTimeStamp)/TIME_TICK;
                for(int i=0;i<tickCount;i++){
                    timeCount++;
                    TimeEventHandle.getInstance().addTimeCount(timeCount);
                    lastTimeStamp+=TIME_TICK;
                }
            }

        }catch (Exception e){
            logger.error("run exception",e);
        }
    }

    public void start(){
        onoff=true;
        TimeThreadTool.getInstance().execute(this);
    }

    public void shutdown(){
        onoff=false;
    }

}
