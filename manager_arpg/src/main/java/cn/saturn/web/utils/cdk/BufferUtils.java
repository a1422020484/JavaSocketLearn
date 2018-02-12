package cn.saturn.web.utils.cdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BufferUtils {
	public static final int longSize = 8;
	public static final int intSize = 4;

	public static byte[] copy(byte[] buffer, int offset, int size) {
		byte[] outs = new byte[size];
		System.arraycopy(buffer, offset, outs, 0, size);
		return outs;
	}

	public static byte[] subBytes(byte[] src, int offset, int length) {
		byte[] buf = new byte[length];
		System.arraycopy(src, offset, buf, 0, length);
		return buf;
	}

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


	public static int longToBytes(long value, byte[] buffer, int offset) {
		buffer[(0 + offset)] = (byte) (int) (value >> 0 & 0xFF);
		buffer[(1 + offset)] = (byte) (int) (value >> 8 & 0xFF);
		buffer[(2 + offset)] = (byte) (int) (value >> 16 & 0xFF);
		buffer[(3 + offset)] = (byte) (int) (value >> 24 & 0xFF);
		buffer[(4 + offset)] = (byte) (int) (value >> 32 & 0xFF);
		buffer[(5 + offset)] = (byte) (int) (value >> 40 & 0xFF);
		buffer[(6 + offset)] = (byte) (int) (value >> 48 & 0xFF);
		buffer[(7 + offset)] = (byte) (int) (value >> 56 & 0xFF);

		return 8;
	}

	public static long bytesToLong(byte[] buffer, int offset) {
		long value = 0L;
		value += (buffer[(0 + offset)] & 0xFF) << 0;
		value += (buffer[(1 + offset)] & 0xFF) << 8;
		value += (buffer[(2 + offset)] & 0xFF) << 16;
		value += (buffer[(3 + offset)] & 0xFF) << 24;
		value += (buffer[(4 + offset)] & 0xFF) << 32;
		value += (buffer[(5 + offset)] & 0xFF) << 40;
		value += (buffer[(6 + offset)] & 0xFF) << 48;
		value += (buffer[(7 + offset)] & 0xFF) << 56;

		return value;
	}

	public static long bytesToLong0(byte[] buffer, int offset) {
		long value = 0L;
		value += (buffer[(0 + offset)] & 0xFF) << 0;
		value += (buffer[(1 + offset)] & 0xFF) << 8;
		value += (buffer[(2 + offset)] & 0xFF) << 16;
		value += (buffer[(3 + offset)] & 0xFF) << 24;
		value += (buffer[(4 + offset)] & 0xFF) << 32;
		value += (buffer[(5 + offset)] & 0xFF) << 40;
		value += (buffer[(6 + offset)] & 0xFF) << 48;
		value += (buffer[(7 + offset)] & 0xFF) << 56;

		return value;
	}

	public static byte[] intToBytes(int value) {
		byte[] bytes = new byte[4];
		intToBytes(value, bytes, 0);
		return bytes;
	}

	public static int numberToBytes(long value, int size, byte[] buffer, int offset) {
		for (int i = 0; i < size; i++) {
			buffer[(i + offset)] = (byte) (int) (value >> i * 8 & 0xFF);
		}
		return size;
	}

	public static int numberToBytes0(long value, int size, byte[] buffer, int offset) {
		for (int i = 0; i < size; i++) {
			buffer[(i + offset)] = (byte) (int) (value >> (size - 1 - i) * 8 & 0xFF);
		}
		return size;
	}

	public static long bytesToNumber(int size, byte[] buffer, int offset) {
		long value = 0L;
		for (int i = 0; i < size; i++) {
			value += (buffer[(i + offset)] & 0xFF) << i * 8;
		}
		return value;
	}

	public static long bytesToNumber0(int size, byte[] buffer, int offset) {
		long value = 0L;
		for (int i = 0; i < size; i++) {
			value += (buffer[(i + offset)] & 0xFF) << (size - 1 - i) * 8;
		}
		return value;
	}

	public static int intToBytes(int value, byte[] buffer, int offset) {
		buffer[(0 + offset)] = (byte) (value >> 0 & 0xFF);
		buffer[(1 + offset)] = (byte) (value >> 8 & 0xFF);
		buffer[(2 + offset)] = (byte) (value >> 16 & 0xFF);
		buffer[(3 + offset)] = (byte) (value >> 24 & 0xFF);

		return 4;
	}

	public static int bytesToInt(byte[] buffer, int offset) {
		int value = 0;
		value += ((buffer[(0 + offset)] & 0xFF) << 0);
		value += ((buffer[(1 + offset)] & 0xFF) << 8);
		value += ((buffer[(2 + offset)] & 0xFF) << 16);
		value += ((buffer[(3 + offset)] & 0xFF) << 24);

		return value;
	}

	public static int intToBytes0(int value, byte[] buffer, int offset) {
		int intSize = 4;
		buffer[(0 + offset)] = (byte) (value >> 24 & 0xFF);
		buffer[(1 + offset)] = (byte) (value >> 16 & 0xFF);
		buffer[(2 + offset)] = (byte) (value >> 8 & 0xFF);
		buffer[(3 + offset)] = (byte) (value >> 0 & 0xFF);

		return intSize;
	}

	public static int bytesToInt0(byte[] buffer, int offset) {
		int value = 0;
		value += ((buffer[(0 + offset)] & 0xFF) << 24);
		value += ((buffer[(1 + offset)] & 0xFF) << 16);
		value += ((buffer[(2 + offset)] & 0xFF) << 8);
		value += ((buffer[(3 + offset)] & 0xFF) << 0);

		return value;
	}

	public static String bytesToString(byte[] buffer, int start, int size) {
		char[] strBuf = new char[size];
		for (int i = 0; i < size; i++) {
			byte b = buffer[(start + i)];
			strBuf[i] = (char) b;
		}

		String str = new String(strBuf);
		return str;
	}

	public static String bytesToString(byte[] buffer) {
		return bytesToString(buffer, 0, buffer.length);
	}

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