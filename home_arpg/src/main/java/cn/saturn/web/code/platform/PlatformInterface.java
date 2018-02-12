package cn.saturn.web.code.platform;

import java.util.Map;

/**
 * 平台接口
 *
 * @author xiezuojie
 */
public interface PlatformInterface {
    /**
     * 连接SDK平台进行登陆验证
     *
     * @param params
     * @return 登录结果, 不能返回null.
     */
    LoginResponse login(Map<String, String> params);

    /**
     * @return 平台标识, 例当乐为dangle
     */
    String ptFlag();

    /**
     * @return 各平台需要的参数数组, 如果没有参数返回null.
     */
    String[] requireParams();
}
