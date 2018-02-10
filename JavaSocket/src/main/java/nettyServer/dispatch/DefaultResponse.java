package nettyServer.dispatch;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.MessageOrBuilder;

/**
 * @author zuojie.x
 */
public class DefaultResponse implements Response, Serializable {
	private static final long serialVersionUID = 1L;
	private int code;
	private int actionId;
	private Object result;

	// private boolean encrypted;

	DefaultResponse(ResponseCode code, int actionId, Object result) {
		this.code = code.code;
		this.actionId = actionId;
		this.result = result;
	}

	// DefaultResponse(ResponseCode code, int actionId, Object result, boolean encrypted) {
	// this.code = code.code;
	// this.actionId = actionId;
	// this.result = result;
	// this.encrypted = encrypted;
	// }

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	@Override
	public Object getResultDTO() {
		return result;
	}

	// /**
	// * 设置数据体是否加密
	// *
	// * @param encrypted
	// */
	// public void setEncrypted(boolean encrypted) {
	// this.encrypted = encrypted;
	// }

	// @Override
	// public boolean isEncrypted() {
	// return encrypted;
	// }

	@Override
	public String toString() {
		String desc = null;
		if (result != null) {
			if (result instanceof MessageOrBuilder) {
				// desc = TextFormat.printToUnicodeString((MessageOrBuilder) result);
				// desc = desc.replaceAll(System.getProperty("line.separator"), " ");
				StringBuilder b = new StringBuilder();
				getMsgDesc((MessageOrBuilder) result, b);
				desc = b.toString();
			} else {
				desc = result.toString();
			}
		}
		return "Response [actionId=" + actionId + ", code=" + code + ", result=" + desc + "]";
	}

	void getMsgDesc(MessageOrBuilder msg, StringBuilder b) {
		int i = 0;
		for (Map.Entry<FieldDescriptor, Object> en : ((MessageOrBuilder) msg).getAllFields().entrySet()) {
			if (i > 0) {
				b.append(", ");
			}
			b.append(en.getKey().getName()).append(":\"");
			Object value = en.getValue();
			if (value instanceof List) {
				b.append("[");
				for (Object o : (List<?>) value) {
					if (o instanceof MessageOrBuilder) {
						getMsgDesc((MessageOrBuilder) o, b);
						b.append("; ");
					} else {
						b.append(o.toString());
					}
				}
				b.append("]");
			} else {
				b.append(en.getValue());
			}
			b.append("\"");
			i++;
		}
	}
}
