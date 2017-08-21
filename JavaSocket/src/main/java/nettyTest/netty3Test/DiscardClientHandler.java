package nettyTest.netty3Test;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

public class DiscardClientHandler extends SimpleChannelUpstreamHandler {
	private long transFerredBytes;
	private final byte[] content;

	public DiscardClientHandler() {
		content = new byte[DiscardClient.SIZE];
	}

	public long getTransFerredBytes() {
		return transFerredBytes;
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		if (e instanceof ChannelStateEvent) {
			if (((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
				System.err.println(e);
			}
		}
		super.handleUpstream(ctx, e);
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		generateTraffic(e);
	}

	@Override
	public void channelInterestChanged(ChannelHandlerContext ctx, ChannelStateEvent e) {
		generateTraffic(e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		System.out.println("hello world");
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) {
		transFerredBytes += e.getWrittenAmount();
	}

	private void generateTraffic(ChannelStateEvent e) {
		Channel channel = e.getChannel();
		while (channel.isWritable()) {
			ChannelBuffer m = nextMessage();
			if (m == null) {
				break;
			}
			channel.write(m);
		}
	}

	private ChannelBuffer nextMessage() {
		return ChannelBuffers.wrappedBuffer(content);
	}

}
