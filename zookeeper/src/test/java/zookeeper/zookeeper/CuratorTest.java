package zookeeper.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import static zookeeper.zookeeper.demo01.Create.ZOOKEEPER_HOST;

public class CuratorTest {
    @Test
    public void test() throws Exception {
        // 尝试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);

        // 构建curator
        CuratorFramework curator = CuratorFrameworkFactory.newClient(ZOOKEEPER_HOST, retryPolicy);

        LeaderLatch leaderLatch = new LeaderLatch(curator, "/leaderlatch", "participant1");

        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                System.out.println("I'm the leader now");
            }

            @Override
            public void notLeader() {
                System.out.println("I relinquish the leadership");

            }
        });


        curator.start();

        // 开始竞选Leader
        leaderLatch.start();
        leaderLatch.await();
        System.out.println("Is leader " + leaderLatch.hasLeadership());
        // System.in.read();
        // System.out.println("After delete node: Is leader" + leaderLatch.hasLeadership()) ;
        // System.in.read();


        Thread.sleep(50000000);
        curator.close();

    }
}
