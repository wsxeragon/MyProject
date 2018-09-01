package cn.inphase.control;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class MyNettyServer {
    private int port;

    public MyNettyServer(int port) {
        this.port = port;
    }

    public void run() {
        // 用于处理服务器端接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        // 进行网络通信（读写）
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 辅助工具类，用于服务器通道的一系列配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap
                    // 绑定两个线程组
                    .group(bossGroup, workerGroup)
                    // 指定NIO的模式
                    .channel(NioServerSocketChannel.class)
                    // 配置具体的数据处理方式
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            // 自定义分隔符
                            // ByteBuf byteBuf =
                            // Unpooled.copiedBuffer("&".getBytes());
                            // sc.pipeline().addLast(new
                            // DelimiterBasedFrameDecoder(1024, byteBuf));

                            // 添加string解码器，channelRead接收到的msg变为string类型
                            sc.pipeline().addLast(new StringDecoder());
                            sc.pipeline().addLast(new ServerHandler());

                        }
                    })
                    // 设置TCP缓冲区
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置发送数据缓冲大小
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    // 设置接受数据缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    // 保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new MyNettyServer(8766).run();
    }
}
