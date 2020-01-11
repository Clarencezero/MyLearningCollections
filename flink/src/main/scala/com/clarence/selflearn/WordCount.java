package com.clarence.selflearn;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import scala.tools.nsc.interactive.Lexer;

@Slf4j
public class WordCount {
    public static final String WORD_COUNT_TXT_PATH = "E:\\Project\\DataSource\\wordcount.txt";

    public static void main(String[] args) throws Exception{
        // 1.ENV
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 2.source
        DataSource<String> dataSource = env.readTextFile(WORD_COUNT_TXT_PATH);

        // 3.Function
        dataSource.flatMap(new Tokenizer()).print();


        // 3.execute
    }


    public static class Tokenizer implements FlatMapFunction<String, Tuple2<String, Long>> {

        @Override
        public void flatMap(String in, Collector<Tuple2<String, Long>> out) throws Exception {
            String[] split = in.split("\\W+");
            for (String s : split) {
                out.collect(new Tuple2<String, Long>(s, 1l));
            }
        }
    }
}
