package century.gsdk.net.core;

public class NetHandleThread implements Runnable{
    private NetSession session;
    private Object msg;
    private NetMessageHandle handle;

    public NetHandleThread(NetSession session, Object msg, NetMessageHandle handle) {
        this.session = session;
        this.msg = msg;
        this.handle = handle;
    }

    @Override
    public void run() {
        handle.handle(session,msg);
    }
}
