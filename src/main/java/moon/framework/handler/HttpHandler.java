package moon.framework.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import moon.framework.process.HttpProcess;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        FullHttpResponse response =  new HttpProcess().handleRequest(msg);
        ctx.writeAndFlush(response);
    }
}
