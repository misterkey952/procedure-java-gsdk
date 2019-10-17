package century.gsdk.net.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
public class Int32HeadDecoder extends ChannelInboundHandlerAdapter {
    private CompositeByteBuf buffer= Unpooled.compositeBuffer();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        buffer.addComponent(true,(ByteBuf)msg);
        nianbao(buffer,ctx);
    }

    private void nianbao(CompositeByteBuf buffer,ChannelHandlerContext ctx){
        if (buffer.readableBytes() < 4) {
            return;
        }

        int length = buffer.readInt();
        if (buffer.readableBytes() < length) {
            buffer.readerIndex(0);
            return;
        }
        ByteBuf dd=buffer.slice(buffer.readerIndex(),length);
        ctx.fireChannelRead(dd);
        buffer.readerIndex(buffer.readerIndex()+length);
        buffer.discardReadComponents();
        nianbao(buffer,ctx);
    }
}
