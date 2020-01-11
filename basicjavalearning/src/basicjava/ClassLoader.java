package basicjava;

/**
 * 每个Java类都维护一个指向定义它的类加载器的引用,通过 类名.class.getClassLoader() 可以获取到此引用；然后通过 loader.getParent() 可以获取类加载器的上层类加载器。
 * 优势:Java类和它的类加载器一起具备了一种带有优先级的层次关系,通过这种层级关系可以避免类的重复加载。
 * 当父类已经加载了该类时, 就没有必要子类加载了。
 */
public class ClassLoader {
    public static void main(String[] args) {
        java.lang.ClassLoader classLoader = ClassLoader.class.getClassLoader();
        System.out.println(classLoader);  // sun.misc.Launcher$AppClassLoader@18b4aac2

        while (classLoader != null){
            System.out.println(classLoader.toString());  // sun.misc.Launcher$AppClassLoader@18b4aac2
                                                         // sun.misc.Launcher$ExtClassLoader@1b6d3586(扩展类加载器)
            classLoader = classLoader.getParent();
        }



        java.lang.ClassLoader objectClass= Object.class.getClassLoader();
        System.out.println(objectClass);  // null


    }
}
