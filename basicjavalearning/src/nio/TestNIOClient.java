package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class TestNIOClient {
    public static void main(String[] args) throws Exception
    {
        // 1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9999));

        // 2.切换非阻塞模式
        socketChannel.configureBlocking(false);

        // 3.分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 4.发送给数据给服务端
        buffer.put(new Date().toString().getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();

        // 5.关闭通道
        socketChannel.close();
    }
}
