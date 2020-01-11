package com.clarence.selflearn.basicapiconcepts;

import com.clarence.dajiangtai.course01.java.dataset.WordCount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.ReduceOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.omg.CORBA.Environment;
import scala.concurrent.java8.FuturesConvertersImpl;

@Slf4j
public class DefineKeys {
    public static final String CSV_PATH = "E:\\Project\\DataSource\\people.csv";
    public static final String TXTPATH = "E:\\Project\\DataSource\\summap.txt";
    public static final String TXTPATH_WORDCOUNT = "E:\\Project\\DataSource\\wordcount.txt";

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // DataSource<Tuple4<String, Integer, String, Integer>> source = env.readCsvFile(CSV_PATH)
        //         .fieldDelimiter(",")
        //         .ignoreFirstLine()
        //         .types(String.class, Integer.class, String.class, Integer.class);
        //
        // source.map(new RichMapFunction<Tuple4<String, Integer, String, Integer>, User>() {
        //     User user = null;
        //     @Override
        //     public User map(Tuple4<String, Integer, String, Integer> in) throws Exception {
        //         user = new User(in.f0, in.f1, in.f2, in.f3);
        //         return user;
        //     }
        // }).flatMap(new MyFlatMapFunction()).sum(1).print();
        //

        // =============一、MAP FUNCTION=============
        // 1.1 SumTxt 主要用于测验Map Function的用法
        // sumTxt(env);

        // 1.2 测试Map函数=>Tuple
        // sumTxtReturnTuple(env);
        // =============

        //=============二、FLATMAP FUNCTION=============
        // 2.1 Tuple
        // wordCountByFlatMap(env);

        // 2.2 Class
        // wordCountByClass(env);

        //=============三、MapPartition=============
        // 一次处理一个分区的数据,当需要获取第三方资源的时候,建议使用MapParition
        // mapPartition(env);

        //============四、Filter====================
        filterFunction(env);


        env.execute("DefineKeys");
    }


    // =================================
    // Filter
    public static void filterFunction(ExecutionEnvironment env ) throws Exception {
        DataSource<String> source = env.readTextFile(TXTPATH_WORDCOUNT);
        source.flatMap(new SumAddrByFlatMap())
                .filter((value -> {
                   return value.f0.equals("the");
                })).print();
    }



    // =================================
    // MapRartition:
    public static void mapPartition(ExecutionEnvironment env) throws Exception{
        DataSource<Long> source = env.generateSequence(1, 20);
        // source.setParallelism(1);

        source.mapPartition(new MyMapPartitionFun())
                .print();
    }


    public static class MyMapPartitionFun implements MapPartitionFunction<Long, Long> {

        @Override
        public void mapPartition(Iterable<Long> values, Collector<Long> out) throws Exception {
            long sum = 0;
            for (Long value : values) {
                sum++;
            }
            out.collect(sum);
        }
    }



    // FlatMap
    // =================================

    public static void wordCountByClass(ExecutionEnvironment env) throws Exception{
        DataSource<String> source = env.readTextFile(TXTPATH_WORDCOUNT);
        DataSet<Word> result = source.flatMap(new FlatMapToClass())
                .groupBy("word")
                .reduce(new WordCounter());

        result.print();
    }

    public static class WordCounter implements ReduceFunction<Word> {

        @Override
        public Word reduce(Word in1, Word in2) throws Exception {
           return new Word(in1.word, in1.count + in2.count);
        }
    }

    public static class Word {

        public Word() {}
        public Word (String word, int count) {
            this.word = word;
            this.count = count;
        }
        public String word;
        public int count;

        @Override
        public String toString() {
            return "Word{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

    public static class FlatMapToClass implements FlatMapFunction<String, Word> {

        @Override
        public void flatMap(String in, Collector<Word> out) throws Exception {
            String[] split = in.split("\\W+");
            for (String s : split) {
                out.collect(new Word(s, 1));
            }

        }
    }

    // =================================
    public static void wordCountByFlatMap(ExecutionEnvironment env) throws Exception {
        DataSource<String> source = env.readTextFile(TXTPATH_WORDCOUNT);

        DataSet data = source.flatMap(new SumAddrByFlatMap())
                .groupBy(0)
                .sum(1);

        data.print();


    }
    public static class SumAddrByFlatMap implements FlatMapFunction<String, Tuple2<String, Long>> {

        @Override
        public void flatMap(String in, Collector<Tuple2<String, Long>> out) throws Exception {
            String[] split = in.split("\\W+");
            for (String s : split) {
                out.collect(new Tuple2<>(s, 1l));
            }

        }
    }
    // =================================


    // Map函数总结: Map Transformation作用在DataSet每个元素上面, 实现了一个one-to-one的mapping, 必要只有一个参数返回

    /**
     * 将Map->Tuple: 如果直接lambda的话, 因为没有设置返回的参数
     *
     * @param env
     * @throws Exception
     */
    public static void sumTxtReturnTuple(ExecutionEnvironment env) throws Exception {
        // 1. Source
        DataSource<String> source = env.readTextFile(TXTPATH);

        source.map(new LongAddr()).print();
    }

    public static class LongAddr implements MapFunction<String, Tuple2<String, Long>> {

        @Override
        public Tuple2<String, Long> map(String in) throws Exception {
            String[] strings = in.split(",");
            long sum = 0l;
            for (int i = 0; i < strings.length; i++) {
                sum += Long.valueOf(strings[i]);
            }
            return new Tuple2(strings[0], sum);
        }
    }

    /**
     * MAP 输出一个long
     *
     * @param env
     * @throws Exception
     */
    public static void sumTxt(ExecutionEnvironment env) throws Exception {
        // 1. Source
        DataSource<String> source = env.readTextFile(TXTPATH);

        // 2.Function
        source.map((in) -> {
            String[] strings = in.split(",");
            long sum = 0l;
            for (String adds : strings) {
                sum += Long.valueOf(adds);
            }
            return sum;
        }).print();


    }


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 自定义FlatMapFunction: (User)->(User,1)
     */
    public static class MyFlatMapFunction extends RichFlatMapFunction<User, Tuple2<User, Long>> {

        @Override
        public void flatMap(User value, Collector<Tuple2<User, Long>> out) throws Exception {
            out.collect(new Tuple2<>(value, 1l));
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        public String name;
        public Integer age;
        public String location;
        public Integer score;
    }


}
