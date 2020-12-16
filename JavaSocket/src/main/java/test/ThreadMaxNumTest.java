package test;

public class ThreadMaxNumTest {
    public static void main(String[] args) {
        for (int i = 0; i <= 10000; i++){
            Thread t = new Thread();
            t.run();
        }
    }
}
