package moon.framework.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import moon.framework.handler.HttpHandler;

public class HttpServer {

    public String host;

    public int port;

    public ServerBootstrap server;

    public HttpServer(String host, int port) throws InterruptedException {
        this.host = host;
        this.port = port;
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        this.server = new ServerBootstrap();
        this.server.group(boss, worker)
                .channel(NioSctpServerChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("httpCodes", new HttpServerCodec())
                                .addLast("postDecoder", new HttpObjectAggregator(1024 * 1024))
                                .addLast("hander", new HttpHandler());
                    }
                });

    }


    public void start() {
        try {
            ChannelFuture f = this.server.bind(this.host, this.port).sync();
            f.channel().closeFuture().sync();
        } catch (Throwable e) {

        } finally {
            this.server.config().group().shutdownGracefully();
            this.server.config().childGroup().shutdownGracefully();
        }
    }
}
