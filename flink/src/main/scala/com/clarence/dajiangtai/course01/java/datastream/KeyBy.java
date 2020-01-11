package com.clarence.dajiangtai.course01.java.datastream;


import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KeyBy {
    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // DataSource<Tuple4> source = env.fromElements(TRANSCRIPT_TUPLE);
        DataStreamSource<Tuple4> source = env.fromElements(TRANSCRIPT_TUPLE);

        source.keyBy("f0").max("f2").print().setParallelism(1);

        // source.keyBy("fo0").minBy()





        env.execute("KeyBy");
    }


    public static final Tuple4[] TRANSCRIPT_TUPLE = new Tuple4[] {
            // 班级class1
            Tuple4.of("class1", "A", "1", 1),
            Tuple4.of("class1", "A", "2", 1),
            Tuple4.of("class1", "B", "1", 2),
            Tuple4.of("class1", "B", "2", 2),
            Tuple4.of("class1", "C", "1", 3),
            Tuple4.of("class1", "C", "2", 3),

            // 班级class2
            Tuple4.of("class2", "D", "1", 1),
            Tuple4.of("class2", "D", "2", 1),
            Tuple4.of("class2", "E", "1", 2),
            Tuple4.of("class2", "E", "2", 2),
            Tuple4.of("class2", "F", "1", 3),
            Tuple4.of("class2", "F", "2", 3),

    };
}
