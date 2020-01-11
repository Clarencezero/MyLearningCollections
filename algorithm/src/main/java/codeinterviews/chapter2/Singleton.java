package codeinterviews.chapter2;

/*剑指Offer表示:
* 强烈推荐算法
* */
/*总结:
    有两个问题需要注意：
    1、如果单例由不同的类装载器装入，那便有可能存在多个单例类的实例。假定不是远端存取，例如一些 servlet 容器对每个 servlet 使用完全不同的类装载器，这样的话如果有两个 servlet 访问一个单例类，它们就都会有各自的实例。
    2、如果 Singleton 实现了 java.io.Serializable 接口，那么这个类的实例就可能被序列化和复原。不管怎样，如果你序列化一个单例类的对象，接下来复原多个那个对象，那你就会有多个单例类的实例。*/


class SingletonSync {
    private static SingletonSync singletonSync;

    //插入一个验证码验证是否生成了多个对象
    public static int identifyCode;

    public static SingletonSync getInstance() {

        /*只有当实例为null时,需要进行加锁操作,当实例创建好后,则无须加锁*/
        if (singletonSync == null) {

            /*如果先判断*/
            synchronized (SingletonSync.class) {

                /*加锁后再判断一次*/
                if (singletonSync == null) {
                    singletonSync = new SingletonSync();
                    identifyCode++;
                }
            }

        }
        return singletonSync;
    }


}

/*静态内部类*/
class SingleTonStatic {
    private static class SingletonHoler {
        private static final SingleTonStatic INSTANCE = new SingleTonStatic();
    }

    private SingleTonStatic(){}

    public static final SingleTonStatic getInstance() {

        return SingletonHoler.INSTANCE;
    }

}


public class Singleton implements Runnable {
    public static void main(String[] args) {
        Singleton s1 = new Singleton();
        Singleton s2 = new Singleton();
        Thread thread = new Thread(s1);
        Thread thread2 = new Thread(s2);
        thread.start();
        thread2.start();

    }

    public void run() {

        //SingletonSync singletonSync = SingletonSync.getInstance();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SingleTonStatic singleTonStatic = SingleTonStatic.getInstance();


        System.out.println(singleTonStatic);
        //System.out.println(SingletonSync.identifyCode);
    }
}
