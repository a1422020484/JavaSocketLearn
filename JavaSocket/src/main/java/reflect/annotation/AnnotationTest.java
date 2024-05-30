package reflect.annotation;

import java.lang.annotation.Inherited;
import java.lang.reflect.Method;

@Inherited
@interface SuperAnnotation {}

@interface SubAnnotation {}

class SuperClass {
    @SuperAnnotation
    public void someMethod() {}
}

class SubClass extends SuperClass {
    @SubAnnotation
    public void someMethod() {}
}

public class AnnotationTest {
    public static void main(String[] args) throws NoSuchMethodException {
        Method method = SubClass.class.getMethod("someMethod");

        // 获取声明的注解，不考虑继承
        SuperAnnotation superAnnotation = method.getDeclaredAnnotation(SuperAnnotation.class);
        SubAnnotation subAnnotation = method.getDeclaredAnnotation(SubAnnotation.class);

        // 获取注解，包括继承的
        SuperAnnotation inheritedSuperAnnotation = method.getAnnotation(SuperAnnotation.class);
        SubAnnotation inheritedSubAnnotation = method.getAnnotation(SubAnnotation.class);

        System.out.println("Declared SuperAnnotation: " + superAnnotation); // 输出 null
        System.out.println("Declared SubAnnotation: " + subAnnotation); // 输出 @SubAnnotation()
        System.out.println("Inherited SuperAnnotation: " + inheritedSuperAnnotation); // 输出 @SuperAnnotation()
        System.out.println("Inherited SubAnnotation: " + inheritedSubAnnotation); // 输出 @SubAnnotation()
    }
}