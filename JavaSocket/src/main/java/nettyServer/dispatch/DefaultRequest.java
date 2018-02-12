package nettyServer.dispatch;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.MessageOrBuilder;

/**
 * @author yangxp
 */
public class DefaultRequest implements Request, Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;
	private String sessionId;
	private int actionID;
	private Object[] params;

	private HttpRequest httpRequest;
	private ChannelHandlerContext channelHandlerContext;

	protected DefaultRequest(String sessionId, int actionID, Object[] params) {
		this.sessionId = sessionId;
		this.actionID = actionID;
		this.params = params != null ? params : new Object[0];
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public Integer getPlayerId() {
		if (session == null) {
			return null;
		}
		return session.getPlayerId();
	}

	@Override
	public Session getSession() {
		return session;
	}

	/**
	 * 设置此次连接的会话
	 *
	 * @param session
	 */
	public void setSession(Session session) {
		this.session = session;
		if (this.session != null) {
			this.sessionId = this.session.getSessionId();
		}
	}

	@Override
	public String getHostAddress() {
		if (channelHandlerContext == null) {
			return "unknown";
		}
		InetSocketAddress isaddr = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
		if (isaddr == null) {
			return "unknown";
		}
		InetAddress addr = isaddr.getAddress();
		if (addr == null) {
			return "unknown";
		}
		return addr.getHostAddress();
	}

	@Override
	public int getActionId() {
		return actionID;
	}

	@Override
	public Object[] getParams() {
		return params;
	}

	public void setHttpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
		this.channelHandlerContext = channelHandlerContext;
	}
	
	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}

	@Override
	public String toString() {
		int i = 0;
		String desc = "";
		for (Object o : params) {
			if (i++ > 0)
				desc += ", ";
			if (o instanceof MessageOrBuilder) {
//				desc += TextFormat.shortDebugString((MessageOrBuilder) o);
				int j = 0;
				for (Map.Entry<FieldDescriptor, Object> en : ((MessageOrBuilder) o).getAllFields().entrySet()) {
					if (j > 0) {
						desc += ", ";
					}
					desc += en.getKey().getName() + ":\"" + en.getValue() + "\"";
					j ++;
				}
			} else {
				desc += o;
			}
		}
		return "Request [actionId=" + actionID + ", address=" + getHostAddress() + ", params=(" + desc + ")]";
	}
}
