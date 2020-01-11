package com.clarence.imooc.course04

import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._

object WindowWordCount {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text = env.socketTextStream("localhost", 9999)
    // 试验: {} 和() 如果里面只包含一个的话可以用(), \\W+ 需要大写, 不然获取不到数据
    // windows上 nc -l -p 端口号
    val counts = text.flatMap{_.toLowerCase.split("\\W+") filter(_.nonEmpty)}
      .map {(_, 1)}
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)

    counts.print()
    env.execute("Window Stream WordCount")
  }
}

//object WindowWordCount {
//  def main(args: Array[String]) {
//
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    val text = env.socketTextStream("localhost", 9999)
//
//    // 这个fileter 后面是 {}
//    val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
//      .map { (_, 1) }
//      .keyBy(0)
//      .timeWindow(Time.seconds(5))
//      .sum(1)
//
//    counts.print()
//
//    env.execute("Window Stream WordCount")
//  }
//}