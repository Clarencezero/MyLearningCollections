package zookeeper.demo1_zoo_connect;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZookeeperGetValue {
    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(Config.ZOOKEEPER_HOSTS, Config.SESSIONTIME, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("Zookeeper连接");
                System.out.println("准备打印WatchEvent事件");
                System.out.println("type:" + watchedEvent.getType());
                System.out.println("state: " + watchedEvent.getState());
                System.out.println("path: " + watchedEvent.getPath());
            }
        });


        // 判断节点是否存在及节点信息
        Stat nodeStat = zk.exists(Config.NODE_NAME, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.toString());
            }
        });

        if (nodeStat != null) {
            printNodeInfo(nodeStat);
        } else {
            // 创建节点
            zk.create(Config.NODE_NAME, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        //
        zk.setData(Config.NODE_NAME,"hello".getBytes(), 0);

        System.out.println("更新节点后--------------");
        printNodeInfo(nodeStat);


    }



    public static void printNodeInfo(Stat nodeStat) {
        if (nodeStat != null) {
            System.out.println(nodeStat.getCtime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sd = sdf.format(new Date(Long.parseLong(String.valueOf(nodeStat.getCtime()))));
            System.out.println("已存在节点的信息:------------------------------ ");
            System.out.println("节点创建时间: " + sd);
            System.out.println("Aversion: " + nodeStat.getAversion());
            System.out.println("Cversion:" + nodeStat.getCversion());
            System.out.println("NumChildren:" + nodeStat.getNumChildren());
        }
    }
}
