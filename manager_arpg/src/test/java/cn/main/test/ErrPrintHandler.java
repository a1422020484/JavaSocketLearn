package cn.main.test;

import proto.Protocol;
import xzj.core.client.GameClient.Response;
import xzj.core.client.GameClient.ResponseHandlerAdaptor;

public class ErrPrintHandler extends ResponseHandlerAdaptor {

	@Override
	public int getHeadId() {
		return 0;
	}

	@Override
	public void handle(Response r) throws Exception {
		if (r.protobufData.length <= 0) {
			System.out.println("err empty msg");
			return;
		}
		Protocol.ErrSC esc = Protocol.ErrSC.parseFrom(r.protobufData);
		String msg = esc.getMsg();

		System.out.println("err: " + r.headId + ">" + msg + ">" + esc.getCode());
	}

}
