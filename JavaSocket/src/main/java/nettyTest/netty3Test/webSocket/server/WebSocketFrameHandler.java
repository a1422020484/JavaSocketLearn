/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package nettyTest.netty3Test.webSocket.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Echoes uppercase content of text frames.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);
    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
	public void channelActive(final ChannelHandlerContext ctx) {
		// Once session is secured, send a greeting and register the channel to
		// the global channel
		// list so the channel received the messages from others.
		ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(new GenericFutureListener<Future<Channel>>() {
			@Override
			public void operationComplete(Future<Channel> future) throws Exception {
				
//				ctx.writeAndFlush(new TextWebSocketFrame("Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat service!\n"));
//				ctx.writeAndFlush(new TextWebSocketFrame("Your session is protected by " + ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() + " cipher suite.\n"));
				channels.writeAndFlush(new TextWebSocketFrame("Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat service!\n"));
				channels.add(ctx.channel());
			}
		});
	}
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String msg = ((TextWebSocketFrame) frame).text();
//            logger.info("{} received {}", ctx.channel(), msg);
//            ctx.channel().writeAndFlush(new TextWebSocketFrame(msg.toUpperCase(Locale.US)));
            
            for (Channel c : channels) {
    			if (c != ctx.channel()) {
//    				c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
    				c.writeAndFlush(new TextWebSocketFrame("[" + ctx.channel().remoteAddress() + "] " + msg + '\n'));
    			} else {
    				c.writeAndFlush(new TextWebSocketFrame("[you] " + msg + '\n'));
    			}
    		}
            
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
        ctx.fireChannelRead("test");
    }
    
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
			channels.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	// TODO Auto-generated method stub
    	cause.printStackTrace();
    }
}
