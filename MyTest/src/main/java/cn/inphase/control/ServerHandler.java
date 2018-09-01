package cn.inphase.control;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ByteBuf buf = (ByteBuf) msg;
        // byte[] data = new byte[buf.readableBytes()];
        // buf.readBytes(data);
        // String request = new String(data, "utf-8");
        // System.out.println("server: " + request);
        System.out.println("server: " + msg);
        ctx.writeAndFlush(Unpooled.copiedBuffer("已收到".getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("捕获到异常: " + cause.getMessage());
        ctx.close();
    }
}
