package cn.saturn.web.utils;

import java.io.*;

public class BufferUtils {

    /**
     * @param src
     * @param offset
     * @param length
     * @return
     */
    public static byte[] subBytes(byte[] src, int offset, int length) {
        byte[] buf = new byte[length];
        System.arraycopy(src, offset, buf, 0, length);
        return buf;
    }

    /**
     * @param obj
     * @return
     */
    public static byte[] objectToBytes(Object obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream outputStream = null;
            outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = null;
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bytes = outputStream.toByteArray();
            objectOutputStream.close();
            outputStream.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return bytes;
    }

    /**
     * @param bytes
     * @return
     */
    public static Object bytesToObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream inputStream = null;
            inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = null;
            objectInputStream = new ObjectInputStream(inputStream);
            obj = objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static final int intSize = 4;

    /**
     * @param value
     * @return
     */
    public static byte[] intToBytes(int value) {
        byte[] bytes = new byte[intSize];
        intToBytes(value, bytes, 0);
        return bytes;
    }

    public static int intToBytes(int value, byte[] buffer, int offset) {
        int intSize = 4;
        buffer[0 + offset] = (byte) ((value >> 0) & 0xff);
        buffer[1 + offset] = (byte) ((value >> 8) & 0xff);
        buffer[2 + offset] = (byte) ((value >> 16) & 0xff);
        buffer[3 + offset] = (byte) ((value >> 24) & 0xff);

        // for (int i = 0; i < intSize; i++) {
        // buffer[i + offset] = (byte) ((value >> (8 * i)) & 0xFF);
        // }
        return intSize;
    }

    public static int bytesToInt(byte[] buffer, int offset) {
        int value = 0;
        value = value + ((buffer[0 + offset] & 0xFF) << 0);
        value = value + ((buffer[1 + offset] & 0xFF) << 8);
        value = value + ((buffer[2 + offset] & 0xFF) << 16);
        value = value + ((buffer[3 + offset] & 0xFF) << 24);

        // int value = 0;
        // for (int i = 0; i < intSize; i++) {
        // value += (buffer[i + offset] & 0xFF) << (8 * i);
        // }
        return value;
    }

    /**
     * @param buffer
     * @param start
     * @param size
     * @return
     */
    public static String bytesToString(byte[] buffer, int start, int size) {
        char[] strBuf = new char[size];
        for (int i = 0; i < size; i++) {
            byte b = buffer[start + i];
            strBuf[i] = (char) b;
        }
        String str = new String(strBuf);
        return str;
    }

    /**
     */
    public static String bytesToString(byte[] buffer) {
        return bytesToString(buffer, 0, buffer.length);
    }

    /**
     * @param str
     * @return
     */
    public static byte[] stringToBytes(String str) {
        int size = str.length();
        byte[] buffer = new byte[size];
        for (int i = 0; i < size; i++) {
            char c = str.charAt(i);
            buffer[i] = (byte) c;
        }

        return buffer;
    }

}
