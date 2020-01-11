package customefileinput;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

/**
 * java.lang.Exception: java.io.IOException: wrong value class: org.apache.hadoop.io.BytesWritable is not class org.apache.hadoop.io.Text
 * 这个错误是在JOB配置的时候设置 setOutputValueClass输出类型错误
 */
public class SequenceFileDriver {
    public static void main(String[] args) throws Exception{
        // 1.获取JOB实例
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2. 设置JAR包
        job.setJarByClass(SequenceFileDriver.class);

        // 3.设置MR class
        job.setMapperClass(SequenceFileMapper.class);
        job.setReducerClass(SequenceFileReducer.class);

        // 4.设置MR key/value格式
        // 坑 ByteWritable和BytesWritable不一样
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);

        // 5.设置Final 输出格式
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);
        // job.setMapOutputValueClass(BytesWritable.class);

        // 6.设置InputFormat
        job.setInputFormatClass(WholeFileInputformat.class);

        // 7.设置OutputFormat
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        // 8.设置输入文件夹路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        // 9.设置输出文件夹路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);

        System.exit(b ? 0 : 1);
    }
}
