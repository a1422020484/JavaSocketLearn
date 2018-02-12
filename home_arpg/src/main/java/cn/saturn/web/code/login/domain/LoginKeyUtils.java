package cn.saturn.web.code.login.domain;

import cn.saturn.web.utils.BufferUtils;
import cn.saturn.web.utils.EncryptUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

public class LoginKeyUtils {
    public static class KeyObject {
        public final long accountId;
        public final String accountName;
        public final String platform;
        public final int key;

        public KeyObject(long accountId, String accountName, String platform, int key) {
            super();
            this.accountId = accountId;
            this.accountName = accountName;
            this.platform = (platform != null) ? platform : "";
            this.key = key;
        }

        @Override
        public String toString() {
            return "KeyObject [accountId=" + accountId + ", accountName=" + accountName + ", platform=" + platform + ", key=" + key + "]";
        }
    }

    // 密码，长度要是8的倍数
    public static final String password = "saturn66";

    public static String encrypt(KeyObject keyObj) {
        short accountSize = (short) keyObj.accountName.length();
        // short platformSize = (short) keyObj.platform.length();
        byte[] platforBytes;
        try {
            platforBytes = keyObj.platform.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        short platformSize = (short) platforBytes.length;
        // 计算数据大小
        int bufSize = 8 + 2 + accountSize + 2 + platformSize + 4;
        // 创建数据组
        byte[] buffer = new byte[bufSize];
        ByteBuf byteBuf = Unpooled.wrappedBuffer(buffer);
        byteBuf.writerIndex(0);
        // 写入ID
        byteBuf.writeLong(keyObj.accountId);
        // 写入账号长度
        byteBuf.writeShort(accountSize);
        // 写入账号信息
        byteBuf.writeBytes(keyObj.accountName.getBytes());
        // 写入平台信息
        byteBuf.writeShort(platformSize); // 写入平台长度
        // byteBuf.writeBytes(BufferUtils.stringToBytes(keyObj.platform));
        byteBuf.writeBytes(platforBytes);
        // 写入key
        byteBuf.writeInt(keyObj.key);

        // 加密
        buffer = EncryptUtils.DES.encrypt(buffer, password);
        if (buffer == null) {
            System.err.println("错误加密:" + keyObj);
            return null; // 加密出错
        }

        // 返回
        String code = BufferUtils.bytesToString(buffer, 0, buffer.length);

        // 输出检验
        // System.out.println(Arrays.toString(buffer));
        // byte[] buf2 = stringToBytes(code);
        // System.out.println(Arrays.toString(buf2));

        return code;

    }

    public static KeyObject decode(String code) {
        try {
            return decode0(code);
        } catch (Exception e) {
            return null;
        }
    }

    protected static KeyObject decode0(String code) {
        byte[] buffer = BufferUtils.stringToBytes(code);
        // 解密
        buffer = EncryptUtils.DES.decrypt(buffer, password);
        if (buffer == null) {
            System.err.println("错误解码:" + code);
            return null; // 解码出错
        }

        // 读取
        ByteBuf byteBuf = Unpooled.wrappedBuffer(buffer);
        // 读取id
        long accountId = byteBuf.readLong();
        // 读取账号
        short accountSize = byteBuf.readShort();
        byte[] accountBytes = new byte[accountSize];
        byteBuf.readBytes(accountBytes, 0, accountSize);
        String accountName = new String(accountBytes);
        // 读取平台信息
        short platformSize = byteBuf.readShort();
        byte[] platformBytes = new byte[platformSize];
        byteBuf.readBytes(platformBytes, 0, platformSize);
        // String platform = new String(platformBytes);
        // String platform = BufferUtils.bytesToString(platformBytes);
        String platform;
        try {
            platform = new String(platformBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        // 读取key
        int key = byteBuf.readInt();

        // 返回
        KeyObject keyObj = new KeyObject(accountId, accountName, platform, key);
        return keyObj;
    }

    public static void main(String[] args) throws Exception {
        long accountId = 567;
        String accountName = "wocaowori";
        int key = 123545;

        String code = LoginKeyUtils.encrypt(new KeyObject(accountId, accountName, "奇虎360", key));

        KeyObject keyObj = LoginKeyUtils.decode(code);

        System.out.println("code:" + code + " " + keyObj);

        // // 输出
        // String str = "Hello world!";
        // // string转byte
        // byte[] bs = str.getBytes();
        // System.out.println(Arrays.toString(bs));
        // // byte转string
        // String str2 = new String(bs);
        // // System.out.println(str2);
        // byte[] bs2 = str2.getBytes();
        // System.out.println(Arrays.toString(bs2));

        // String str = "奇虎360";
        // byte[] buffer = BufferUtils.stringToBytes(str);
        // String str0 = BufferUtils.bytesToString(buffer);
        // byte[] buffer = str.getBytes("UTF-8");
        // String str0 = new String(buffer, "UTF-8");
        // System.out.println(str + "->" + str0);
    }

}
