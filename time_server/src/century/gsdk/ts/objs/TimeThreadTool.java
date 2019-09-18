package century.gsdk.ts.objs;

import century.gsdk.thread.GameThreadFactory;
import century.gsdk.thread.GameThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TimeThreadTool extends GameThreadPool {
    private static final TimeThreadTool instance=new TimeThreadTool();
    private TimeThreadTool(){}
    public static TimeThreadTool getInstance(){
        return instance;
    }
    @Override
    public Executor initializeExecutor(GameThreadFactory factory) {
        return Executors.newFixedThreadPool(8,factory);
    }
}
