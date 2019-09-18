package century.gsdk.net.netty;

import century.gsdk.net.core.NetConnect;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public class NetConnectCloseEvent implements GenericFutureListener<ChannelFuture> {
    private NetConnect connect;

    public NetConnectCloseEvent(NetConnect connect) {
        this.connect = connect;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        connect.close();
    }
}
