package century.gsdk.net.netty;

import century.gsdk.net.core.NetConnect;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
/**
 *     Copyright (C) <2019>  <Century>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
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
