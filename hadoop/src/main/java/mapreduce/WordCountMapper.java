package mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 输入: a b c
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
    IntWritable intWritable = new IntWritable(1);
    Text text = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String val = value.toString();
        String[] s = val.split(" ");
        System.out.println("==============Map===================");
        for (String s1 : s) {
            text.set(s1);
            context.write(text,intWritable);
        }
    }
}
