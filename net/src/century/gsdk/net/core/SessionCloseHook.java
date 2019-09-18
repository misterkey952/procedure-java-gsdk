package century.gsdk.net.core;

public abstract class SessionCloseHook<T extends NetSession> {
    protected T session;


    protected void init(T session){
        this.session=session;
    }
    public abstract void hook();
}
