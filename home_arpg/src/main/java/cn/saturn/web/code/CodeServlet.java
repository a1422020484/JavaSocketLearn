package cn.saturn.web.code;

import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.Utils;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proto.Protocol;
import xzj.core.dispatch.*;
import xzj.core.dispatch.annotation.Action;
import xzj.core.dispatch.annotation.Action.User;
import xzj.core.util.MsgUtils;
import xzj.core.util.RSAUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/Code")
public class CodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final Logger log = LoggerFactory.getLogger(CodeServlet.class);
    protected static final boolean codeAction = Config.booleanVal("codeAction");
    public static final Logger logCall = LoggerFactory.getLogger("call");
    public static final Logger logBack = LoggerFactory.getLogger("back");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // this.doPost(request, response);
        log.info("doGet:" + request.getRemoteAddr());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // log.info("doPost:" + request.getRemoteAddr());

        if (!codeAction) {
            writeError(response, "home不开放code功能.");
            return;
        }

        // 处理执行
        try {
            onCode(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // response.sendError(404);
            if (Utils.isDebug) {
                writeError(response, "服务器运行错误:" + e.getMessage());
            } else {
                writeError(response, "服务器繁忙");
            }
            return;
        }

    }

    /**
     * 获取客户端IP
     **/
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 逻辑处理
     *
     * @param request
     * @param response
     * @throws Exception
     */
    protected void onCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 读取数据
        ServletInputStream inputStream = request.getInputStream();
        // 读取
        byte[] buffer = new byte[1024];
        int size = inputStream.read(buffer);
        if (size <= 0) {
            writeError(response, "数据错误");
            return;
        }
        ByteBuf buf = Unpooled.wrappedBuffer(buffer, 0, size);
        // System.out.println("readableSize:" + readableSize + " " + Arrays.toString(buffer));

        // 检测数据大小(不能小于0, 也不能大于1024)
        int readableSize = buf.readableBytes();
        if (readableSize <= 0) {
            buf.release();
            writeError(response, "数据内容不足");
            return;
        }
        // 检测session 长度
        byte sessionIdLen = buf.readByte();
        readableSize = buf.readableBytes();
        if (readableSize < sessionIdLen) {
            buf.release();
            writeError(response, "session长度不足");
            return;
        }

        // 读取session
        byte[] sessionIdData = new byte[sessionIdLen];
        buf.readBytes(sessionIdData);
        // String sessionId = new String(sessionIdData, "utf-8");
        readableSize = buf.readableBytes();
        if (readableSize < 4) {
            buf.release();
            writeError(response, "session数据不够");
            return;
        }
        // 读取actionId
        int headId = buf.readInt();
        // log.debug("sessionid:{}, headid:{}", sessionid, headid);
        ActionInvoker invoker = ActionManager.getActionInvoker(headId);
        if (invoker == null) {
            buf.release();
            writeError(response, "找不到对应的指令");
            return;
        }

        Action action = invoker.getActionAnnotation();

        // 以下是protobuf数据处理
        readableSize = buf.readableBytes();
        byte[] protobufData = new byte[readableSize];
        buf.readBytes(protobufData);

        if (action.user() == User.System) {
            // 对系统用户数据解密
            try {
                protobufData = RSAUtils.decryptByPrivateKey(protobufData);
            } catch (Exception e) {
                buf.release();
                writeError(response, "数据解密出错");
                return;
            }
        } else {
            if (action.isEncrypted()) {
                if (sessionIdLen == 0) {
                    buf.release();
                    writeError(response, "session为0, 不能加密.");
                    return;
                }
                // 对数据解密
                MsgUtils.encrypt(sessionIdData, MsgUtils.otherKey);
                MsgUtils.encrypt(protobufData, sessionIdData);
            }
        }
        // 解析参数
        Object[] params = null;
        try {
            params = invoker.parseArguments(protobufData);
        } catch (Exception e) {
            buf.release();
            writeError(response, "参数解密错误.");
            return;
        }

        // 获取用户IP, 然后输出.
        // String host = request.getRemoteHost();
        // String ip = request.getRemoteHost();
        String ip = getIpAddr(request);
        String callStr = "ip=" + ip + " headId=" + headId + " params=" + Arrays.toString(params);
        callStr = callStr.replaceAll("\n", " ");
        logCall.debug(callStr);
        // 设置IP数据
        if (ip != null) {
            localIp.set(ip);
        } else {
            localIp.remove();
        }

        // 执行处理
        int actionId = headId;
        Object exeRs = invoker.execute(params);
        ByteBuf retBuf = null;
        if (exeRs != null) {
            if (exeRs instanceof MultiResponse) {
                retBuf = write((MultiResponse) exeRs);
            } else if (exeRs instanceof Response) {
                Response rp = (Response) exeRs;
                if (rp.getActionId() <= 0) {
                    ((DefaultResponse) rp).setActionId(actionId);
                }
                retBuf = write(rp);
            } else {
                // 类型不是Response时,按成功处理
                retBuf = write(ResponseFactory.ok(actionId, (MessageLite) exeRs));
            }
            if (Utils.isDebug) {
                log.info("retObj:" + actionId + " -> " + exeRs);
            }
        }
        if (retBuf != null) {
            // 返回数据
            ServletOutputStream output = response.getOutputStream();
            output.write(retBuf.array());
            log.info("writeMsg:" + actionId);
        }
    }

    /**
     * 写入错误消息
     *
     * @param response
     * @param errMsg
     */
    public static void writeError(HttpServletResponse response, String errMsg) {
        // response.sendError(404);

        // 生成错误消息
        int actionId = 0;
        Protocol.ErrSC.Builder errB = Protocol.ErrSC.newBuilder();
        errB.setCode(actionId);
        errB.setMsg(errMsg);
        xzj.core.dispatch.Response errRp = ResponseFactory.ok(0, errB.build());

        // 生成数据流
        ByteBuf retBuf = write(errRp);

        // 返回消息
        try {
            ServletOutputStream output = response.getOutputStream();
            output.write(retBuf.array());
            log.info("writeError:" + errMsg);
            logBack.error("ERROR: ip=" + getCallIp() + " " + errMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入消息
     *
     * @param resp
     * @return
     */
    public static ByteBuf write(Response resp) {
        List<Response> list = new ArrayList<>();
        list.add(resp);
        return write(list);
    }

    public static ByteBuf write(MultiResponse multiResp) {
        List<Response> list = multiResp.getResponses();
        return write(list);
    }

    public static ByteBuf write(List<Response> responses) {
        // 计算数据大小
        int totalLen = 0;
        for (Response resp : responses) {
            totalLen += 9; // 必有的 code(byte) + actionId(int)
            Object rs = resp.getResultDTO();
            if (rs != null) {
                totalLen += ((MessageLite) rs).getSerializedSize();
            }
        }
        // 创建缓存
        byte[] all = new byte[totalLen];
        ByteBuf allBuf = Unpooled.wrappedBuffer(all);
        allBuf.writerIndex(0);

        // 生成加密钥匙
        byte[] sessionIdEncrypt = null;
        String sessionId = "";
        if (sessionId != null) {
            sessionIdEncrypt = sessionId.getBytes();
            MsgUtils.encrypt(sessionIdEncrypt, MsgUtils.otherKey);
        }

        String ip = getCallIp();

        // 遍历处理
        for (Response resp : responses) {
            Object rs = resp.getResultDTO();

            // 输出返回
            String callStr = "ip=" + ip + " actionid=" + resp.getActionId() + " params=" + rs;
            callStr = callStr.replaceAll("\n", " ");
            logBack.debug("Response :" + callStr);

            if (rs != null) {
                MessageLite msglite = (MessageLite) rs;
                byte[] protoData = msglite.toByteArray();
                ActionInvoker actionInvoker = ActionManager.getActionInvoker(resp.getActionId());
                // Action.isEncrypted()默认是true,当某个actionId并没有绑定Action时,默认为是加密的
                if (actionInvoker != null && actionInvoker.getActionAnnotation().user() == User.System) {
                    // 不加密
                } else if (actionInvoker == null || actionInvoker.getActionAnnotation().isEncrypted()) {
                    if (sessionIdEncrypt == null || sessionIdEncrypt.length <= 0) {
                        throw new RuntimeException("Action ID:" + resp.getActionId() + " 需要对返回的数据加密,但sessionId为空!");
                    }
                    MsgUtils.encrypt(protoData, sessionIdEncrypt);
                }
                allBuf.writeInt(msglite.getSerializedSize() + 5);
                allBuf.writeByte(resp.getCode());
                allBuf.writeInt(resp.getActionId());
                allBuf.writeBytes(protoData);
            } else {
                allBuf.writeInt(5);
                allBuf.writeByte(resp.getCode());
                allBuf.writeInt(resp.getActionId());
            }
        }

        // 返回数据
        return allBuf;
    }

    public final static ThreadLocal<String> localIp = new ThreadLocal<String>();

    public static String getCallIp() {
        return localIp.get();
    }
}
