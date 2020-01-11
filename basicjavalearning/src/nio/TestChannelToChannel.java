package nio;

import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestChannelToChannel {
    public static final String FILE_PATH_DIST = "E:\\Project\\DataSource\\dist\\love.jpg";
    public static void main(String[] args) throws Exception{
        channelToChannel();

    }
    public static void channelToChannel() throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get(TestChannel.FILE_PATH_SRC), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(FILE_PATH_DIST), StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        // inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }
}
