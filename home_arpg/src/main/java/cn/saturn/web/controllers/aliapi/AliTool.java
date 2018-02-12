package cn.saturn.web.controllers.aliapi;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class AliTool {
	
	
	static final String ENCODING = "utf-8";
    static final Logger log = LoggerFactory.getLogger("specialcode");

    /**
     * 
     * @param request {@link HttpServletRequest}
     * @return {@link Request}
     */
    public static AliRequest parse(HttpServletRequest request, String AESKey) {
        try {
            String content = IOUtils.toString(request.getInputStream(), ENCODING);
            if (StringUtils.isBlank(content)) {
                return AliRequest.EMPTY;
            }

            if (log.isDebugEnabled()) {
                log.debug("gift:{}", content);
            }
            AliRequest aliRequest = JSON.parseObject(content, AliRequest.class);
            
            return aliRequest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AliRequest.EMPTY;
    }
	

}
