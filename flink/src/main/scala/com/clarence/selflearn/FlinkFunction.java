package com.clarence.selflearn;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class FlinkFunction {
    public static void main(String[] args) {

    }


    // 定义Function
    public static class StartEndDuration extends KeyedProcessFunction<String, Tuple2<String, String>, Tuple2<String, Long>> {
        private ValueState<Long> startTime;


        @Override
        public void open(Configuration parameters) throws Exception {
            startTime = getRuntimeContext()
                    .getState(new ValueStateDescriptor<Long>("startTime", Long.class));
        }


        @Override
        public void processElement(Tuple2<String, String> in, Context ctx, Collector<Tuple2<String, Long>> out) throws Exception {
            switch (in.f1) {
                case "START":
                    startTime.update(ctx.timestamp());
                    ctx.timerService().registerEventTimeTimer(ctx.timestamp() + 4 * 60 * 60 * 1000);
                    break;
                case "END":
                    Long sTime = startTime.value();
                    if (sTime != null) {
                        out.collect(Tuple2.of(in.f0, ctx.timestamp() - sTime));
                        startTime.clear();
                    }
                default:
                    // do nothing
            }

        }
    }
}
