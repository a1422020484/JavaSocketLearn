package io;

import java.io.*;

/**
 * @author yangxp
 * @date 2017年8月14日 上午10:54:41
 * 管道流测试
 */
public class PipeExemple {

	public static void main(String[] args) {
		final PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in = null;
		try {
			in = new PipedInputStream(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PipeThreadReadA pipeThreadReadA = new PipeThreadReadA(in);
		PipeThreadWirteB pipeThreadWirteB = new PipeThreadWirteB(out);
		pipeThreadReadA.start();
		pipeThreadWirteB.start();
	}

}

class PipeThreadReadA extends Thread {

	private PipedInputStream in;
	public PipeThreadReadA(PipedInputStream in){
		this.in = in;
	}
	@Override
	public void run() {
		try {
			byte b[] = new byte[1024];
			int data = in.read(b);
//			读入缓冲区的总字节数；如果由于已到达流末尾而不再有数据，则返回 -1。 
			while (data != -1) {
				data = in.read(b);
			}
			String teString = new String(b,0,b.length);
			System.out.println(teString);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class PipeThreadWirteB extends Thread {

	private PipedOutputStream out;
	public PipeThreadWirteB(PipedOutputStream out){
		this.out = out;
	}
	
	@Override
	public void run() {
		try {
			out.write("hello world".getBytes());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
