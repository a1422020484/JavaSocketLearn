package test;

public class Test {

	public static void main(String[] args) {
		Server server = new Server();

		ThreadB b = new ThreadB(server);
		b.start();

		ThreadA a = new ThreadA(server);
		a.start();
	}
}

class ThreadA extends Thread {

	private Server server;

	public ThreadA(Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		server.setIsRuning();
	}
}

class ThreadB extends Thread {

	private Server server;

	public ThreadB(Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		server.doSomething();
	}
}

class Server {
	public boolean isRuning;

	public void doSomething() {
		while (isRuning == false) {

		}
		System.out.println("运行结束");
	}

	public void setIsRuning() {
		isRuning = true;
	}
}
