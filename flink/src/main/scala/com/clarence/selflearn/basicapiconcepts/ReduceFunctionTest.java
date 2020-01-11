package com.clarence.selflearn.basicapiconcepts;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import javax.sql.DataSource;

@Slf4j
public class ReduceFunctionTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataStreamSource = env.addSource(new RichSourceFunction<String>() {
            private volatile boolean isRunning = true;

            @Override
            public void run(SourceContext<String> ctx) throws Exception {
                ctx.collect(new String("a-1"));
                ctx.collect(new String("a-2"));
                ctx.collect(new String("a-3"));
                ctx.collect(new String("a-4"));
                ctx.collect(new String("b-1"));
                ctx.collect(new String("b-2"));

            }

            @Override
            public void cancel() {
                isRunning = false;

            }
        });

        DataStreamSource<String> datasource2 = env.addSource(new RichSourceFunction<String>() {
            private volatile boolean isRunning = true;

            @Override
            public void run(SourceContext<String> ctx) throws Exception {
                ctx.collect(new String("b-3"));
                ctx.collect(new String("b-4"));
                ctx.collect(new String("c-1"));
                ctx.collect(new String("c-2"));
                ctx.collect(new String("c-3"));
                ctx.collect(new String("c-4"));
            }

            @Override
            public void cancel() {
                isRunning = false;
            }
        });

        // testAggregationsFunction(env, dataStreamSource);

        testMutiDataStream(env, dataStreamSource, datasource2);


        env.execute("ReduceFunctionTest");
    }


    /**
     * DataStream -> DataStream
     * Union: 主要是将两个或者多个输入的数据集合冻成一个数据集,需要保证两个数据集的格式一致,输出的数据集的格式和输入的数据集格式保持一致。
     * connect:为了合并两种或多种不同数据类型的数据集,合并后会保留原来的数据类型。连接操作会允许共享状态数据。
     * @param env
     * @param sourc1
     * @param source2
     */
    public static void testMutiDataStream(StreamExecutionEnvironment env, DataStreamSource sourc1, DataStreamSource source2) {
        DataStream unionSource = sourc1.union(source2);

        unionSource.print().setParallelism(1);

    }

    /**
     * KeyedStream -> DataStream
     * 聚合算子: sum、min、max、minBy、maxBy。
     * 计算出来的统计值并不是一次将最终整个数据集的最后求和结果输出,而是将每条记录叠加的结果输出。
     * @param env
     * @param dataStreamSource
     */
    public static void testAggregationsFunction(StreamExecutionEnvironment env, DataStreamSource dataStreamSource) {
        dataStreamSource.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                String[] strings = value.split("-");
                log.info("strings[0]: {}, strings[1]: {}", strings[0], strings[1]);
                return new Tuple2<String, Integer>(strings[0], Integer.parseInt(strings[1]));
            }
        }).keyBy("f0").min("f1").print().setParallelism(1);
    }

    /**
     * Reduce测试: 其实和Hadoop中的MapReduce是一样的。
     *
     * @param env
     * @param dataStreamSource
     */
    public static void testReduceFunction(StreamExecutionEnvironment env, DataStreamSource<String> dataStreamSource) {
        dataStreamSource.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                String[] strings = value.split("-");
                log.info("strings[0]: {}, strings[1]: {}", strings[0], strings[1]);
                return new Tuple2<String, Integer>(strings[0], Integer.parseInt(strings[1]));
            }
        }).keyBy("f0").reduce(new ReduceFunction<Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) throws Exception {
                log.info("REDUCES阶段: value1值: {}, value2值: {}", value1, value2);
                return new Tuple2<>(value1.f0, value1.f1 + value2.f1);
            }
        }).print();
    }


}
