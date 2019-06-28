package secondPart;

/**
 * @author yangxp
 * @date 2017年8月3日 下午6:32:02
 *       <p>
 *       同步不具有继承性，所以在子类中要添加synchonized。子类继承了父类的同步方法，子类的方法是没有同步性的。
 *       必须要在子类也加上synchroized。
 */
public class ThreadDemo6 {

	public static void main(String[] args) {

	}

}

class Main6 {
	synchronized public void serviceMethod() {
		System.out.println("");
	}
}
