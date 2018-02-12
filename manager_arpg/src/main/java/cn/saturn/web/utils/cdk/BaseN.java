package cn.saturn.web.utils.cdk;

public class BaseN {
	protected final int n;
	protected final char[] chars;
	protected final byte[] tables;
	protected final int nq;
	protected final int s;
	protected final int d;
	protected final byte bitmax;

	public BaseN(int nq, char[] chars) {
		this.nq = nq;

		if ((nq <= 1) || (nq >= 8)) {
			throw new Error("不能创建base N为:" + nq + "的对象");
		}

		this.n = (int) Math.pow(2.0D, nq);
		if (chars.length < this.n) {
			throw new Error("字符数不足, 至少需要" + this.n + "个.");
		}

		byte bitmax = 0;
		for (int i = 0; i < nq; i++) {
			bitmax = (byte) (bitmax + (1 << i));
		}
		this.bitmax = bitmax;

		this.chars = chars;

		int maxtable = 0;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			maxtable = Math.max(maxtable, c);
		}
		this.tables = new byte[maxtable + 1];

		for (int i = 0; i < this.tables.length; i++) {
			this.tables[i] = -1;
		}

		for (int i = 0; i < chars.length; i++) {
			int b = chars[i];
			this.tables[b] = (byte) i;
		}

		int maxs = 10;
		int maxd = 8;
		int s = 0;
		int d = 0;
		int minof = 2147483647;
		for (int i = 1; i < maxs; i++) {
			for (int j = 1; j < maxd; j++) {
				int sn = nq * i;
				int dn = j * 8;
				if (sn >= dn) {
					int offset = sn - dn;
					if (offset < minof) {
						minof = offset;
						s = i;
						d = j;
					}
				}
			}
		}
		this.s = s;
		this.d = d;
	}

	public String encode(byte[] data) {
		int dsize = data.length;

		int csize = (int) Math.ceil(dsize / this.d * this.s);

		char[] chars = new char[csize];

		long value = 0L;
		int vi = 0;
		int offset = 0;

		for (int i = 0; i < dsize; i++) {
			byte b = data[i];

			value = (b & 0xFF << vi) + value;
			vi += 8;

			while (vi >= this.nq) {
				int b0 = (int) (value & this.bitmax);
				char c = this.chars[b0];
				chars[(offset++)] = c;

				value >>>= this.nq;
				vi -= this.nq;
			}

		}

		if (vi > 0) {
			int b0 = (int) (value & this.bitmax);
			char c = this.chars[b0];
			chars[(offset++)] = c;
		}

		return new String(chars);
	}

	public byte[] decode(String str) {
		return decode(str.toCharArray());
	}

	public byte[] decode(char[] chars) {
		int csize = chars.length;
		int dsize = (int) (csize / this.s * this.d);

		byte[] buffer = new byte[dsize];

		long value = 0L;
		int vi = 0;
		int offset = 0;
		for (int i = 0; i < csize; i++) {
			char c = chars[i];

			byte b = this.tables[c];

			value = (b & this.bitmax << vi) + value;
			vi += this.nq;

			while (vi >= 8) {
				int b0 = (int) (value & 0xFF);
				buffer[(offset++)] = (byte) b0;

				value >>>= 8;
				vi -= 8;
			}
		}

		return buffer;
	}

	public static byte[] baseNBuffer(String str, char[] chars) {
		char[] strchars = str.toCharArray();
		int strsize = strchars.length;

		byte[] buffer = new byte[strsize];
		for (int i = 0; i < strsize; i++) {
			char c = strchars[i];
			int b = -2;
			for (int j = 0; j < chars.length; j++) {
				if (chars[j] == c) {
					b = j;
					break;
				}
			}
			buffer[i] = (byte) b;
		}

		return buffer;
	}

	public static String baseNString(byte[] buffer, int bitsize) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < buffer.length; i++) {
			byte bit = buffer[i];
			for (int j = 0; j < bitsize; j++) {
				int v = bit & 0x1;
				bit = (byte) (bit >>> 1);
				strBuf.append(v);
			}
			strBuf.append('|');
		}
		return strBuf.toString();
	}
}