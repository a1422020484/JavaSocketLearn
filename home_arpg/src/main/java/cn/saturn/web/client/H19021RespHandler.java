package cn.saturn.web.client;

import xzj.core.client.GameClient;

/**
 * @author xiezuojie
 */
public class H19021RespHandler extends GameClient.SystemResponseHandlerAdaptor {

    @Override
    public int getHeadId() {
        return CHead.H19021;
    }

    @Override
    public void handle(GameClient.Response response) throws Exception {

    }

}
