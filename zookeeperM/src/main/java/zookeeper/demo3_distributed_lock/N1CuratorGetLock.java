package zookeeper.demo3_distributed_lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import zookeeper.demo1_zoo_connect.Config;

public class N1CuratorGetLock {
    public static final String LOCK_ROOT = "/curator/lock";
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.ZOOKEEPER_HOSTS, retryPolicy);
        client.start();

        // 创建分布式锁, 锁空间的根节点路径为/curator/lock
        InterProcessMutex mutex = new InterProcessMutex(client, LOCK_ROOT);
        mutex.acquire();

        // 获得了锁,进行业务流程
        System.out.println("Enter mutex");
        Thread.sleep(3000);
        // 完成业务流程, 释放锁
        mutex.release();

        client.close();
    }
}
