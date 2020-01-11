package chapter2;

import codeinterviews.chapter2.DBConnection;
import codeinterviews.chapter2.EnumSingleton;
import org.junit.Test;

public class SingletonTest {
    @Test
    public void testGetSingleton() throws InterruptedException {



        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DBConnection connection = EnumSingleton.DATASOURCE.getConnection();
                    System.out.println("CurrentThread: " + Thread.currentThread().getName() + " : " + connection);
                }
            }).start();

            Thread.sleep(1000);
        }
    }
}
