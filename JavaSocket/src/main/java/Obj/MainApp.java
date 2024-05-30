package Obj;

public class MainApp {
    public static void main(String[] args) {
        try {
            ParentObject son1Obj = Son1Obj.class.newInstance();
            son1Obj.doSomething();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
