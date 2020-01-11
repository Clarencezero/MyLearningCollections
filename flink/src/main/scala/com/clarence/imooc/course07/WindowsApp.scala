package com.clarence.imooc.course07

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.table.runtime.operators.window.TimeWindow
import org.apache.flink.util.Collector

object WindowsApp {

//  class MyProcessWindowFunction extends ProcessWindowFunction[(String, Long), String, String, TimeWindow] {
//
//    //    def process(key: String, context: Context, input: Iterable[(String, Long)], out: Collector[String]): () = {
//    //      var count = 0L
//    //      for (in <- input) {
//    //        count = count + 1
//    //      }
//    //      out.collect(s"Window ${context.window} count: $count")
//    //    }
//    override def process(key: String, context: Context, elements: Iterable[(String, Long)], out: Collector[String]): Unit = {
//
//    }
//  }

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // slidingWindows(env)
    // windowsFunction(env)
    processWindowFunction(env)


    env.execute("WindowsApp")
  }


  def processWindowFunction(env: StreamExecutionEnvironment) = {
    val text = env.socketTextStream("localhost", 9999)

    //    text
    //      .flatMap(_.split(","))
    //      .keyBy(0)
    //      .timeWindow(Time.minutes(5))
    //      .process(new MyProcessWindowFunction())
  }


  /**
   * ReduceFunction
   * AggregateFunction: 增量
   * FoldFunction
   * ProcessWindowFunction: 全量
   *
   * @param env
   */
  def windowsFunction(env: StreamExecutionEnvironment) = {
    val text = env.socketTextStream("localhost", 9999)

    text.flatMap(_.split(","))
      .map(x => (1, x.toInt)) // 1,2,3,4 =>(1,1) (1,2) (1,3)
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .reduce((v1, v2) => { // 不是等待窗口所有的数据进行一次性处理,而是两两处理
        println(v1 + " ... " + v2)
        (v1._1, v1._2 + v2._2)
      })
      .print()
      .setParallelism(1)

  }


  /**
   * 滑动窗口
   * 窗口的大小为 10S, 每隔5S统计10S的数据
   */
  def slidingWindows(env: StreamExecutionEnvironment) = {
    val text = env.socketTextStream("localhost", 9999)
    val data = text.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(10), Time.seconds(5))
      .sum(1)
      .print()
      .setParallelism(1)
  }

  /**
   * 滚动窗口
   *
   * @param env
   */
  def tumbingWindows(env: StreamExecutionEnvironment) = {
    val text = env.socketTextStream("localhost", 9999)
    val data = text.flatMap(_.split(","))
      .map((_, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .print()
      .setParallelism(1)
  }
}
