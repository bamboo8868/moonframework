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

    public HttpServer(String host, int port) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker)
                    .channel(NioSctpServerChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("httpCodes",new HttpServerCodec())
                                    .addLast("postDecoder",new HttpObjectAggregator(1024 * 1024))
                                    .addLast("hander",new HttpHandler());
                        }
                    });
            ChannelFuture f = server.bind(host, port).sync();
            f.channel().closeFuture().sync();

        } finally {

        }


    }


    public void start() {

    }
}
