package zookeeper.zookeeper.demo01;

import org.apache.zookeeper.*;

import java.io.IOException;

public class Create {
    // public static final String ZOOKEEPER_HOST = "127.0.0.1:2181";
    public static final String ZOOKEEPER_HOST = "192.168.5.5:2184,192.168.5.5:2185,192.168.5.5:2186";
    public static final String NODE_NAME = "/a";
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        ZooKeeper zk = new ZooKeeper(ZOOKEEPER_HOST, 6000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("创建Zookeeper连接");
            }
        });

        // 2.创建节点路径。并指定权限。CreateMode.PERSISTENT表示客户端断开连接,节点不会自动删除
        try {
            String s = zk.create(NODE_NAME, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3.判断这个节点是否存在
        // Stat exists = zk.exists(NODE_NAME, watchedEvent -> {
        //     System.out.println(watchedEvent.getPath() + " | " + watchedEvent.getType().name());
        //     zk.exists(NODE_NAME, this);
        // });
        //
        // Stat exists = zk.exists(NODE_NAME, new Watcher() {
        //     @Override
        //     public void process(WatchedEvent event) {
        //         try {
        //             // lambda 是不能传入this的
        //             zk.exists(NODE_NAME, this);
        //             System.out.println("=======" + event.getPath() + " | " + event.getType().name());
        //
        //         } catch (KeeperException e) {
        //             e.printStackTrace();
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });
        //
        // if (exists == null)
        //     System.out.println("NULL ================");
        //




        Thread.sleep(1000000);
        // 关闭客户端
        zk.close();


    }
}
