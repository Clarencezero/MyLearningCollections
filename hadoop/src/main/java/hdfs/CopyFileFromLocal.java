package hdfs;

import common.HadoopConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.util.StopWatch;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class CopyFileFromLocal {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        StopWatch clock = new StopWatch();
        clock.start("HDFS客户端连接测试: ");
        // 1. 定义配置。这里的配置>项目配置>集群配置
        Configuration conf = new Configuration();

        // 2. 创建FileSystem对象,这个对象用来操作HDFS文件
        // 注意: 需要配置用户名,不然会有权限校验
        FileSystem fileSystem = FileSystem
                .get(new URI(HadoopConfig.HDFS_URI), conf, "root");

        clock.stop();

        // 3. 增
        // uploadLocalFile(fileSystem, clock);

        // 4. 删除
        // deleteFile(fileSystem, clock);

        // 5. IO上传
        // uploadHdfsFileByStreams(fileSystem, clock, conf);
        readHdfsFileByStreams(fileSystem, clock, conf);


        System.out.println(clock.prettyPrint());
    }


    /**
     * HDFS流下载
     * HDFS文件 <-文件输入流
     * LOCAL FILE <-文件输出流
     * 详见 Hadoop权威指南 第3.6 数据流章节
     * 细节:
     *  1. 访问NN节点, 获取文件在DN上的数据
     *  2. 选择连接距离最近的文件中第一个块所在的DN节点
     *  3. 通过对流反复调用read()方法,可以将数据从DN传输到客户端
     *  4. 到达块的末端时,DFSInputStream关闭与该DN的连接,然后寻找下一个块的最佳DN节点
     *
     * 重点: 客户端可以直连DN检索数据,且NN告知客户端最佳块所在地
     */
    public static void readHdfsFileByStreams(FileSystem fileSystem, StopWatch clock, Configuration conf) throws IOException {
        // 加了buffersize似乎对性能没有什么大影响1024*1024可能太小了
        clock.start("客户端流下载文件测试: ");
        FileOutputStream fos = new FileOutputStream(new File("E:\\Project\\DataSource\\videoDown.mp4"));
        FSDataInputStream open = fileSystem.open(new Path("/video.mp4"), 1024*1024*10);

        IOUtils.copyBytes(open, fos, conf);

        clock.stop();

    }

    /**
     * HDFS IO流上传
     * 本地文件 <-输入流读取 --
     *
     * HDFS <-输出流关联   --
     */
    public static void uploadHdfsFileByStreams(FileSystem fileSystem, StopWatch clock, Configuration conf) throws IOException {
        clock.start("客户端流上传文件测试: :");
        // 1.创建输入流
        FileInputStream input = new FileInputStream(new File("E:\\Project\\DataSource\\hdfsvediotest.mp4"));

        // 2. 获取输出流
        FSDataOutputStream fos = fileSystem.create(new Path("/video.mp4"));

        // 3.流对拷
        IOUtils.copyBytes(input, fos, conf );

        clock.stop();
    }

    /**
     * HDFS 删除文件
     */
    public static void deleteFile(FileSystem fileSystem, StopWatch clock) throws IOException {
        clock.start("客户端删除文件测试: ");
        boolean delete = fileSystem.delete(new Path("/20190811"), true);
        clock.stop();

        clock.start("客户端删除大文件测试: ");
        boolean delete2 = fileSystem.delete(new Path("/videotest.mp4"), true);
        clock.stop();
    }

    /**
     * HDFS 创建目录
     */
    public static boolean mkdir(FileSystem fileSystem, String path) throws IOException {
        return fileSystem.mkdirs(new Path(path));
    }

    /**
     * HDFS 上传文件
     */
    public static void uploadLocalFile(FileSystem fileSystem, StopWatch clock) throws IOException {
        clock.start("上传文件测试");

        // 3.1 创建目录
        // mkdir(fileSystem, "/20190811");

        // 3.2 上传本地文件
        fileSystem.copyFromLocalFile(new Path("E:\\Project\\DataSource\\love.jpg"), new Path("/love.jpg"));
        clock.stop();


        clock.start("客户端上传大文件测试: ");
        /* 3.3 上传超过block大小的文件*/
        fileSystem.copyFromLocalFile(new Path("E:\\Project\\DataSource\\hdfsvediotest.mp4"),
                new Path("/videotest.mp4"));
        clock.stop();

        // 5.

        clock.start("HDFS客户端关闭测试: ");
        fileSystem.close();
        clock.stop();
    }
}
