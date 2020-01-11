package com.clarence.dajiangtai.course01.java.datastream;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 输出一组元素, 对他们分别进行减一运算,直到==0为止
 */
public class IterativeStream {
    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Long> source = env.generateSequence(1, 100);

        // - 基于输入流构建IterativeStream（迭代头）
        org.apache.flink.streaming.api.datastream.IterativeStream<Long> iterate = source.iterate();

        // - 定义迭代逻辑（map fun等）
        DataStream<Long> minusOne= iterate.map(new MapFunction<Long, Long>() {

            @Override
            public Long map(Long value) throws Exception {
                return value - 1;
            }
        });

        // - 定义反馈流逻辑（从迭代过的流中过滤出符合条件的元素组成的部分流反馈给迭代头进行重复计算的逻辑）
        DataStream<Long> bigger = minusOne.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                return value > 0;
            }
        });

        // - 调用lterativeStream的closeWith方法可以关闭一个迭代（也可表述为定义了迭代尾）
        iterate.closeWith(bigger);

        // - 定义“终止迭代”的逻辑（符合条件的元素将被分发给下游而不用于进行下一次迭代）
        DataStream<Long> less = minusOne.filter((FilterFunction<Long>) value -> value <= 0);

        less.print();

        env.execute("IterativeStream");
    }
}
