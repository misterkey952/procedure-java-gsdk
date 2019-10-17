package century.gsdk.net.netty;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Copyright (C) <2019>  <Century>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * <p>
 * Author's Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class Int32HeadEncoder extends ChannelOutboundHandlerAdapter {
    private ByteBuf buffer= Unpooled.directBuffer();
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        buffer.clear();
        buffer.retain(2);
        ByteBuf bytemsg= (ByteBuf) msg;
        buffer.writeInt(bytemsg.readableBytes());
        buffer.writeBytes(bytemsg);
        ctx.writeAndFlush(buffer,promise);
    }
}
