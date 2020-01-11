package zookeeper.demo1_zoo_connect;

import org.apache.zookeeper.*;

import java.io.IOException;

import static zookeeper.demo1_zoo_connect.Config.NODE_NAME;

public class ZookeeperConnect {


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        // 1.实例化一个Zookeeper。地址、Session超时时间、可以注册一个连接状态监听器
        ZooKeeper zk = new ZooKeeper(Config.ZOOKEEPER_HOSTS_5, 2000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getPath());
                System.out.println(watchedEvent.getState());
                System.out.println(watchedEvent.getType());
            }
        });

        System.out.println(zk.getState());


        // 创建节点
        String s = zk.create(NODE_NAME, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);


        // 2.获取/下面节点
        // try {
        //     List<String> children = zk.getChildren("/", false);
        //     if (children == null || children.size() == 0)
        //         System.out.println("节点 / 下不存在子节点)");
        //     for (String child : children) {
        //         System.out.println(child);
        //     }
        // } catch (KeeperException e) {
        //     e.printStackTrace();
        // }
        //




        zk.close();

    }
}
