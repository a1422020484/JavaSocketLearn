package cn.saturn.web.controllers.ucapi;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiezuojie
 */
public class UCTool {

    static final String ENCODING = "utf-8";
    static final Logger log = LoggerFactory.getLogger("gift");

    /**
     * 解析来自UC的请求
     * @param request {@link HttpServletRequest}
     * @return {@link UCRequest}
     */
    public static UCRequest parse(HttpServletRequest request, String AESKey) {
        try {
            String content = IOUtils.toString(request.getInputStream(), ENCODING);
            if (StringUtils.isBlank(content)) {
                return UCRequest.EMPTY;
            }

            if (log.isDebugEnabled()) {
                log.debug("gift:{}", content);
            }
            UCRequest ucRequest = JSON.parseObject(content, UCRequest.class);
            ucRequest.decrypt(AESKey);
            return ucRequest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UCRequest.EMPTY;
    }
}
