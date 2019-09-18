package century.gsdk.net.netty;

import century.gsdk.net.core.NetSession;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public class NetSessionCloseEvent implements GenericFutureListener<ChannelFuture> {
    private NetSession session;

    public NetSessionCloseEvent(NetSession session) {
        this.session = session;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        session.close();
    }
}
