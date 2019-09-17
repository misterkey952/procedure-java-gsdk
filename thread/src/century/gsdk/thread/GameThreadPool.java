package century.gsdk.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public abstract class GameThreadPool {
    private static final Logger logger= LoggerFactory.getLogger("GameThreadPool");
    private Executor executor;
    private GameThreadFactory factory;

    public GameThreadPool() {
        factory=new GameThreadFactory(this.getClass().getSimpleName());
        this.executor=initializeExecutor(factory);
    }

    public abstract Executor initializeExecutor(GameThreadFactory factory);

    public void execute(Runnable run){
        try {
            executor.execute(run);
        } catch (Exception e) {
            logger.error("execute err",e);
        }
    }

    public void shutdown() {
        ((ExecutorService) executor).shutdown();
    }
}
