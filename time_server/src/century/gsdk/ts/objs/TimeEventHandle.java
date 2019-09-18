package century.gsdk.ts.objs;

import century.gsdk.net.core.NetSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;

public class TimeEventHandle implements Runnable{
    private static final Logger logger= LoggerFactory.getLogger("TimeEventHandle");
    private BlockingDeque<Long> blockingDeque=new LinkedBlockingDeque<>();
    private Set<NetSession> sessionSet=new HashSet<>();
    private boolean onoff;

    public void start(){
        onoff=true;
        TimeThreadTool.getInstance().execute(this);
    }

    public void shutdown(){
        onoff=false;
    }

    @Override
    public void run() {
        while(onoff){
            try{
                long timeCount=blockingDeque.take();
                synchronized (sessionSet){
                    for(NetSession session:sessionSet){
                        session.sendMsg(timeCount);
                    }
                }

            }catch (Exception e){
                logger.error("run err",e);
            }
        }
    }

    public void addTimeCount(long timeCount){
        blockingDeque.add(timeCount);
    }

    public void registerSession(NetSession session){
        synchronized (sessionSet){
            sessionSet.add(session);
        }

    }

    public void removeSession(NetSession session){
        synchronized (sessionSet){
            sessionSet.remove(session);
        }

    }

}
