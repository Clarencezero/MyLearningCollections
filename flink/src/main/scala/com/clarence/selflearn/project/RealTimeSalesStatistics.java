package com.clarence.selflearn.project;

import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.scala.function.util.ScalaFoldFunction;
import scala.util.Random;

import java.util.HashMap;
import java.util.Map;

public class RealTimeSalesStatistics {
    private static class DataSource extends RichParallelSourceFunction<Tuple2<String, Integer>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple2<String, Integer>> ctx) throws Exception {
            Random random = new Random(System.currentTimeMillis());

            while (running) {
                Thread.sleep((getRuntimeContext().getIndexOfThisSubtask() + 1) * 1000 + 500);
                String key = "类别" + (char) ('A' + random.nextInt(3));
                int value = random.nextInt(10) + 1;
                System.out.println(String.format("Emit:\t(%s, %d)", key, value));
                ctx.collect(new Tuple2<>(key, value));
            }


        }

        @Override
        public void cancel() {
            running = false;
        }
    }


    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);


        DataStreamSource<Tuple2<String, Integer>> source = env.addSource(new DataSource());

        KeyedStream<Tuple2<String, Integer>, Tuple> keyedStream = source.keyBy(0);
        keyedStream.sum(1).keyBy(0)
                .fold(
                        new HashMap<String, Integer>(), new FoldFunction<Tuple2<String, Integer>, Map<String, Integer>>() {

                            @Override
                            public Map<String, Integer> fold(Map<String, Integer> accumulator, Tuple2<String, Integer> value) throws Exception {
                                accumulator.put(value.f0, value.f1);
                                return accumulator;
                            }
                        }
                ).addSink(new SinkFunction<Map<String, Integer>>() {
            @Override
            public void invoke(Map<String, Integer> value, Context context) throws Exception {
                System.out.println(value.values().stream().mapToInt(v -> v).sum());
            }
        });


        // source.addSink(new SinkFunction<Tuple2<String, Integer>>() {
        //     @Override
        //     public void invoke(Tuple2<String, Integer> value, Context context) throws Exception {
        //         System.out.println(String.format("Get:\t(%s, %d)", value.f0, value.f1));
        //     }
        // });

        env.execute("RealTimeSalesStatistics");
    }
}
