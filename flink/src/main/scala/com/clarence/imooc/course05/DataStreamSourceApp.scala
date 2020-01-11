package com.clarence.imooc.course05

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

object DataStreamSourceApp {



  def main(args: Array[String]): Unit = {
    // 1.获取上下文件环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // socket接收数据
    // socketFunction(env)

    // 自定义非并行source
    // nonParallelSourceFunction(env)

    // 自定义并行source
    // parallelSourceFunction(env)

    // 自定义并行RichSourceFunctio
    parallelRichSourceFunction(env)

    env.execute("DataStreamSourceApp")
  }
//
  /**
   * 自定义并行RichSourceFunction
   * @param env
   * @return
   */
  def parallelRichSourceFunction(env: StreamExecutionEnvironment) = {
    val data = env.addSource(new CustomParallelSourceFunction).setParallelism(10)
    data.print()
  }


  import org.apache.flink.api.scala._
  def parallelSourceFunction(environment: StreamExecutionEnvironment): Unit = {
    val data = environment.addSource(new CustomParallelSourceFunction).setParallelism(100)
    data.print()
  }

  /**
   * 非并行
   */
  import org.apache.flink.api.scala._
  def nonParallelSourceFunction(environment: StreamExecutionEnvironment): Unit = {
    val data = environment.addSource(new CustomNonParallelSourceFunction).setParallelism(1)
    data.print().setParallelism(1)
  }


  def socketFunction(env:StreamExecutionEnvironment): Unit = {
    val data = env.socketTextStream("localhost", 9999 )
    data.print()
//    data.flatMap{_.toLowerCase}.print()
  }
}
