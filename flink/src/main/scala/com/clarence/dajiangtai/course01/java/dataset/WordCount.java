
package com.clarence.dajiangtai.course01.java.dataset;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.accumulators.LongCounter;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.util.Collector;


public class WordCount {

    public static final String FILE_PATH_OUTPUT = "E:\\Project\\DataSource\\dist\\WordCount";
    public static void main(String[] args) throws Exception {

        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        DataSet<String> text;
        if (params.has("input")) {
            // read the text file from given input path
            text = env.readTextFile(params.get("input"));
        } else {
            // get default test text data
            System.out.println("Executing WordCount example with default input data set.");
            System.out.println("Use --input to specify file input.");
            text = WordCountData.getDefaultTextLineDataSet(env);
        }

        DataSet<Tuple2<String, Integer>> counts =
                text.flatMap(new Tokenizer())
                        .groupBy(0)
                        .sum(1);


        counts.writeAsText(FILE_PATH_OUTPUT, FileSystem.WriteMode.OVERWRITE).setParallelism(1);
        JobExecutionResult wordCount_example = env.execute("WordCount Example");

        System.out.println("~~~~~~~~~~~~~~RESULT~~~~~~~~~~~~~~");
        Integer accumulatorResult = wordCount_example.getAccumulatorResult("num-lines");
        Long totalWords = wordCount_example.getAccumulatorResult("total-words");
        System.out.println("Num-lines Total: " + accumulatorResult);
        System.out.println("Total Words: " + totalWords);
    }

    /**
     * FlatMap返回的都是元组
     */
    public static final class Tokenizer extends RichFlatMapFunction<String, Tuple2<String, Integer>> {
        private IntCounter counter = new IntCounter();
        private LongCounter totalWorld = new LongCounter();

        @Override
        public void open(Configuration parameters) throws Exception {
            getRuntimeContext().addAccumulator("num-lines", counter);
            getRuntimeContext().addAccumulator("total-words", totalWorld);
            super.open(parameters);
        }

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            counter.add(1);
            String[] tokens = value.toLowerCase().split("\\W+");

            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                    totalWorld.add(1);
                }
            }
        }

        @Override
        public void close() throws Exception {
            super.close();
        }
    }

}
