package century.gsdk.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class GameThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final String namePrefix;
    final AtomicInteger threadNumber = new AtomicInteger(1);

    public GameThreadFactory(String namePrefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix + "_thread_";
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group,r,namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
