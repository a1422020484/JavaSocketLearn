package firstPart.mythread.method;

/**
 * @author Administrator
 * currentThread() 方法返回代码段正在被哪个线程调用的信息
 */
public class MyCurrentThreadMethod {

	public static void main(String[] args) {
		CountOperate countOperate = new CountOperate();
		countOperate.start();
	}

}


class CountOperate extends Thread{
	public CountOperate(){
		System.out.println("CountOperate --- begin");
		System.out.println("Thread.currentThread().getName = " + Thread.currentThread().getName());
		System.out.println("this.getName() = " + this.getName());
		System.out.println("CountOperate --- end");
	}
	
	@Override
	public void run(){
		System.out.println("run --- begin");
		System.out.println("Thread.currentThread().getName = " + Thread.currentThread().getName());
		System.out.println("this.getName() = " + this.getName());
		System.out.println("run --- end");
	}
}