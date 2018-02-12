package cn.saturn.web.utils.cdk;



public class cdkey32
{
  public static char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', 
    '4', '5', '6', '7', '8', '9' };

  protected static final BaseN basen = new BaseN(5, chars);

  public static String encodekey(int time, int seed, int v)
  {
    byte[] buffer = new byte[10];
    int offset = 0;

    offset += BufferUtils.numberToBytes(v, 1, buffer, offset);
    offset += BufferUtils.intToBytes(time, buffer, offset);
    offset += BufferUtils.intToBytes(seed, buffer, offset);

    byte code = (byte)((time + v + seed) % 256);
    buffer[(buffer.length - 1)] = code;
    code = (byte)Math.abs(code);

    buffer = EncryptUtils.overturn(buffer, 0, buffer.length - 1, code % 3);

    buffer = EncryptUtils.inversion(buffer, 0, buffer.length - 1, code % 3, 136);

    buffer = EncryptUtils.offset(buffer, 0, buffer.length - 1, code % 3, -(code % 100));

    return basen.encode(buffer);
  }

  public static Object[] decodekey(String str) {
    byte[] buffer = basen.decode(str);

    byte code = buffer[(buffer.length - 1)];
    code = (byte)Math.abs(code);

    buffer = EncryptUtils.offset(buffer, 0, buffer.length - 1, code % 3, code % 100);

    buffer = EncryptUtils.inversion(buffer, 0, buffer.length - 1, code % 3, 136);

    buffer = EncryptUtils.overturn(buffer, 0, buffer.length - 1, code % 3);

    int offset = 0;

    int v = (int)BufferUtils.bytesToNumber(1, buffer, offset);
    offset++;

    int time = BufferUtils.bytesToInt(buffer, offset);
    offset += 4;

    int seed = BufferUtils.bytesToInt(buffer, offset);
    offset += 4;

    return new Object[] { Integer.valueOf(time), Integer.valueOf(seed), Integer.valueOf(v) };
  }
}