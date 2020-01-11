package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TestNIOServer {
    public static void main(String[] args) throws Exception{
        // 1.获取通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        // 2.切换非阻塞模式
        serverChannel.configureBlocking(false);
        
        // 3.绑定连接
        serverChannel.bind(new InetSocketAddress("localhost", 9999));

        // 4.获取选择器
        Selector selector = Selector.open();

        // 5.将通道注册到选择器上, 若监听多个状态,可以用"位或"操作符连接 |
        //  读 : SelectionKey.OP_READ （1）
        //  写 : SelectionKey.OP_WRITE （4）
        //  连接 : SelectionKey.OP_CONNECT （8）
        //  接收 : SelectionKey.OP_ACCEPT （16）
        SelectionKey register = serverChannel.register(selector, SelectionKey.OP_ACCEPT);


        // 6.轮询式的获取选择器上已经"准备就绪"的事件
        while (selector.select() > 0) {
            // 7.获取当前选择器中所有注册的"选择键"(已就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                // 8.获取准备就绪的事件
                SelectionKey sk = iterator.next();

                // 9. 判断具体是什么事件准备就绪
                if (sk.isAcceptable()) {
                    // 10.若"接收就绪" ,获取客户端连接
                    SocketChannel sChannel = serverChannel.accept();

                    // 11.切换非阻塞模式
                    sChannel.configureBlocking(false);

                    // 12.将该通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    // 13.获取当前选择器上"读就绪"状态的通道
                    SocketChannel channel = (SocketChannel)sk.channel();

                    // 14.读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = channel.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();
                    }
                }

                // 15.取消选择键
                iterator.remove();
            }

        }
    }
}
