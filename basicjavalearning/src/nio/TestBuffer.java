package nio;


import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Buffer: 底层就是数组,存储不同数据类型的数据。
 * 提供了对应类型的缓存区,boolean除外
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * <p>
 * 缓冲区管理方式一致,通过allocate()获取对应的缓冲区
 * 存储数据两个核心方法: put():存入数据到缓冲区、get()获取缓冲区的数据
 * <p>
 * Buffer核心属性 position <=limit<=capacity
 * private int mark = -1; // 标记, 表示记录当前position的位置,可以通过reset()恢复到mark的位置
 * private int position = 0; // 位置, 表示缓冲区中正在操作数据的位置
 * private int limit;  // 界限,表示缓冲区中可以操作数据的大小,即limit后的数据不能进行读写
 * private int capacity; // 表示缓冲区容量,不能更改
 */
public class TestBuffer {
    public static final String str = "abc";

    public static void main(String[] args) {
        // testBufferAllocate();
        // testMark();
        allocateDirect();

    }


    public static void allocateDirect() {
        // 分配直接缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println("判断是否是直接缓冲区: " + byteBuffer.isDirect());
    }


    public static void testMark() {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        allocate.put(str.getBytes());


        allocate.flip();
        byte[] read = new byte[1024];
        allocate.get(read, 0, 2);
        System.out.println("=====第一次Read之后的指针数据=====");
        printByteBufferDetail(allocate);
    }


    public static void testBufferAllocate() {

        // 1. 分配一个指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 2. 初始值
        printByteBufferDetail(byteBuffer);

        System.out.println("数据长度: " + str.getBytes().length);

        // 3.放入数据
        byteBuffer.put(str.getBytes());

        System.out.println("=====PUT=====");
        printByteBufferDetail(byteBuffer);

        // 4.读取数据, 数组从序号0开始计数
        System.out.println("=====FLIP=====");
        Buffer flip = byteBuffer.flip();
        printByteBufferDetail(byteBuffer);
        System.out.println(flip.capacity());
        byte b = byteBuffer.get(0);
        System.out.println("读取第一个数据: " + b);

        byte[] dst = new byte[byteBuffer.limit()];
        byteBuffer.get(dst);
        System.out.println(new String(dst, 0, dst.length));

        // 5.Flip完成之后,现在Buffer指针情况
        System.out.println("=====5.Flip完成之后,现在Buffer指针情况=====");
        printByteBufferDetail(byteBuffer);

        // 6. rewind
        System.out.println("=====6.Rewind 重置Position指针,用以重复读取数据=====");
        byteBuffer.rewind();
        printByteBufferDetail(byteBuffer);

        // 7.clear()
        System.out.println("=====7.clear=====清空缓冲区,但是里面的数据处理被遗忘状态");
        byteBuffer.clear();
        printByteBufferDetail(byteBuffer);
    }


    public static void printByteBufferDetail(ByteBuffer byteBuffer) {
        System.out.println("position:" + byteBuffer.position()); //0
        System.out.println("limit:" + byteBuffer.limit()); // 1024
        System.out.println("capacity: " + byteBuffer.capacity()); //1024
    }
}

/**
 * int型与byte型数组的转换 int:4个字节 byte:1个字节,所以需要4个byte存储
 */
