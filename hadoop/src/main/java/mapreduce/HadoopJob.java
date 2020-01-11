package mapreduce;

import common.HadoopConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.time.LocalDateTime;

public class HadoopJob  {
    public static final String WORDCOUNT_FILE_NAME = "wordcount.txt";
    public static final String OUTPUT_FILE_NAME = "output%s.txt";
    public static void main(String[] args)  {
        try {
            // 1. 获取job对象
            Configuration conf = new Configuration();
            conf.set("mapreduce.app-submission.cross-platform","true");
            // conf.set("hadoop.home.dir", "E:\\Tools\\hadoop-2.9.2\\hadoop-2.9.2");
            // conf.set("HADOOP_HOME", "E:\\Tools\\hadoop-2.9.2\\hadoop-2.9.2");
            Job job = Job.getInstance(conf);

            // 2. 设置jar位置
            job.setJarByClass(HadoopJob.class);

            // 3. 关联Map Reduce
            job.setMapperClass(WordCountMapper.class);
            job.setReducerClass(WordCountReduce.class);

            // 4. 设置Map阶段输出数据的key value类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            // 5. 设置最终数据输出的key value类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            // 6. 设置输入路径和输出路径
            FileInputFormat.setInputPaths(job, new Path(HadoopConfig.DATA_ROOT + WORDCOUNT_FILE_NAME));
            FileOutputFormat.setOutputPath(job, new Path(HadoopConfig.DATA_ROOT + "OUTPUT//"));

            // 7. 提交job
            boolean b = job.waitForCompletion(true);
            System.out.println("任务: " + b);

            System.exit(b ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
