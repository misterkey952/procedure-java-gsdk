package century.gsdk.net.core;

public interface NetMessageHandle<T>{
    void handle(NetSession session, T msg);
}
