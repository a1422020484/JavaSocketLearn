package cn.saturn.web.client;

import xzj.core.client.GameClient;

/**
 * @author xiezuojie
 */
public class H1802RespHandler extends GameClient.SystemResponseHandlerAdaptor {

    @Override
    public int getHeadId() {
        return CHead.H1802;
    }

    @Override
    public void handle(GameClient.Response response) throws Exception {

    }

}
