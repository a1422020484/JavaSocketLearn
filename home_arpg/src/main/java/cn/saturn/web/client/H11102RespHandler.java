package cn.saturn.web.client;

import xzj.core.client.GameClient;

/**
 * @author xiezuojie
 */
public class H11102RespHandler extends GameClient.SystemResponseHandlerAdaptor {

    @Override
    public int getHeadId() {
        return CHead.H11102;
    }

    @Override
    public void handle(GameClient.Response response) throws Exception {

    }

}
