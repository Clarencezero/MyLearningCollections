package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 通道: 源节点与目标节点的连接, 在NIO中负责缓冲区的传输, Channel本身不存储数据,需要配合缓冲区进行传输
 * 通道分类(java.nio.channels.Channel):
 *      FileChannel (操作本地文件)
 *      SocketChannel (套接字)
 *      ServerSocketChannel (TCP)
 *      DatagramChannel (UDP)
 * 获取通道(3种方式)getChannel():
 *  一、  本地: FileInputStream、FileOutPutStream、RandomAccessFile
 *        网络: Socket、ServerSocket、DatagramSocket
 *  二、1.7 NIO2针对各个通道提供了静态方法 open()
 *  三、1.7 NIO2 的Files工具类的newByteChannel()
 */
public class TestChannel {
    public static final String FILE_PATH_SRC = "E:\\Project\\DataSource\\love.jpg";
    public static final String FILE_PATH_DIST = "E:\\Project\\DataSource\\dist\\love";
    public static void main(String[] args) throws Exception{
        // cpBychannel();
        // cpByDirectBuff();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 10; i++) {
                            TestChannel.cpByMapBuffer(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }


    // 2.利用直接缓冲区完成文件的复制
    public static void cpByMapBuffer(int i) throws Exception {
        FileChannel readChannel = FileChannel.open(Paths.get(FILE_PATH_SRC), StandardOpenOption.READ);
        FileChannel writeChannel = FileChannel.open(Paths.get(FILE_PATH_DIST + Thread.currentThread().getName() + i + ".jpg"),
                StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.READ);

        // 通过FileChannel的map()方法将文件区域直接映射到内存中来创建。该方法返回MappedByteBuffer。
        //
        MappedByteBuffer readDirectBuffer = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, readChannel.size());

        // 这个channel并没有设置写模式, 所以会抛出NonReadableChannelException异常
        MappedByteBuffer outDirectBuffer = writeChannel.map(FileChannel.MapMode.READ_WRITE, 0, readChannel.size());


        // 直接对缓冲区进行数据读写操作
        byte[] dst = new byte[readDirectBuffer.limit()];
        readDirectBuffer.get(dst);
        outDirectBuffer.put(dst);

        readChannel.close();
        writeChannel.close();


    }

    public static void cpByDirectBuff() throws Exception {
        // 1. 通过OPEN获取通道
        FileChannel readChannel = FileChannel.open(Paths.get(FILE_PATH_SRC), StandardOpenOption.READ);
        FileChannel writeChannel = FileChannel.open(Paths.get(FILE_PATH_DIST), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        // 2. 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        // 3.完成缓冲区数据传输
        while (readChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            writeChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        readChannel.close();
        writeChannel.close();


    }

    // 1. 利用通道完成文件复制
    public static void cpBychannel() throws Exception{
        // 1.获取流
        FileInputStream fis = new FileInputStream(FILE_PATH_SRC);
        FileOutputStream fos = new FileOutputStream(FILE_PATH_DIST);

        // 2. 创建通道
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();

        // 3.完成数据传输
        // 3.1 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 3.2 将通道中的数据存入缓冲区中
        while (fisChannel.read(byteBuffer) != -1) {
            // 3.3 将缓冲区中的数据写入通道
            // 缓冲区切换为读模式
            byteBuffer.flip();
            // foschannel 写数据
            fosChannel.write(byteBuffer);
            // 清空缓冲区
            byteBuffer.clear();
        }

        // 4. 关闭通道
        fisChannel.close();
        fosChannel.close();
        fis.close();
        fos.close();
    }
}
