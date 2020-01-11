package nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 分散(scatter)
 * 聚集(Gather)
 * 分散读取(Scattering Reads): 将通道中的数据分散到多个缓冲区中
 * 聚集写入(Gathering Writes): 将多个缓冲区中的数据聚集到通道中
 */
public class TestScatterAndGatter {
    public static final String TXT_FILE_PATH = "E:\\Project\\DataSource\\people.csv";
    public static final String TXT_FILE_OUT_PATH = "E:\\Project\\DataSource\\dist\\people.csv";
    public static void main(String[] args)throws Exception {
        testScatter();
    }

    public static void testScatter() throws Exception{
        // RandomAccessFile: 支持跳到文件任意位置读写数据
        //
        RandomAccessFile raf = new RandomAccessFile(TXT_FILE_PATH, "rw");
        RandomAccessFile out = new RandomAccessFile(TXT_FILE_OUT_PATH, "rw");
        // 1.获取通道
        FileChannel fileChannel = raf.getChannel();
        FileChannel fileOutChannel = out.getChannel();
        // 2.分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        // 3.分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        fileChannel.read(buf2);

        for (ByteBuffer buf : bufs) {
            buf.flip();
        }

        fileOutChannel.write(buf2);


        fileChannel.close();
        fileOutChannel.close();;

        raf.close();
        out.close();

    }
}
