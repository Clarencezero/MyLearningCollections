package zookeeper.demo3_distributed_lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import zookeeper.demo1_zoo_connect.Config;

class Print implements Runnable {
    private static Integer number = new Integer(0);
    @Override
    public void run() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
        CuratorFramework curator = CuratorFrameworkFactory.newClient(Config.ZOOKEEPER_HOSTS, retryPolicy);
        curator.start();
        InterProcessMutex mutex = new InterProcessMutex(curator, N1CuratorGetLock.LOCK_ROOT);
        try {
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            number++;
        }
        System.out.println("当前线程: " + Thread.currentThread().getName() + ", 打印数字: " +number);

        try {
            mutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class N2CuratorMutiThread {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Print()).start();
        }
    }
}
