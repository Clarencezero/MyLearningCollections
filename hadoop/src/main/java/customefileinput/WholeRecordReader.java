package customefileinput;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * 这个类会不断的创建新的对象,所以没有线程安全问题
 */
public class WholeRecordReader extends RecordReader<Text, BytesWritable> {
    private FileSplit split;
    private Configuration conf;
    private Text key = new Text();
    private BytesWritable value = new BytesWritable();
    private boolean isProgress = true;

    /**
     * 1. 初始化
     *
     * @param split
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        this.split = (FileSplit) split;
        conf = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // 1.获取FileSystem
        if (isProgress) {
            Path path = split.getPath();
            FileSystem fileSystem = path.getFileSystem(conf);

            // 2. 获取FSD输入流
            FSDataInputStream fis = fileSystem.open(path);

            // 3. 拷贝
            byte[] buf = new byte[(int) split.getLength()];
            IOUtils.readFully(fis, buf, 0, buf.length);

            // 4.写入value
            value.set(buf, 0, buf.length);

            // 5.写入key
            key.set(path.toString());

            // 6.关闭资源
            IOUtils.closeStream(fis);

            // 7.设置标记类
            isProgress = false;

            // 8.返回成功标记
            return true;
        }

        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
