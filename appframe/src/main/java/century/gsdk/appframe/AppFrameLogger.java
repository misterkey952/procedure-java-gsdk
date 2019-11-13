package century.gsdk.appframe;

import century.gsdk.tools.LogAssisant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppFrameLogger {
    public static final Logger GameApplication= LoggerFactory.getLogger("GameApplication");
    public static final Logger GameThreadPool= LoggerFactory.getLogger("GameThreadPool");
    static {
        LogAssisant.listLogger(AppFrameLogger.class);
    }
}
