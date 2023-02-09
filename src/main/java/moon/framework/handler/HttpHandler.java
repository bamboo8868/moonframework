package moon.framework.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

    }
}
