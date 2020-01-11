package com.clarence.imooc.course04

import akka.io.Tcp.Write
import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem.WriteMode
object CounterApp {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val data = env.fromElements("hadoop", "spark", "flink", "storm")

    val info = data.map(new RichMapFunction[String, String] {

      // 1. 定义计数器
      var counter = new LongCounter()

      override def open(parameters: Configuration): Unit = {
        // 2.注册计数器
        getRuntimeContext.addAccumulator("ele-counts-scala", counter)
      }

      override def map(value: String): String = {
        counter.add(1)
        value
      }
    })

//    info.print()
    info.writeAsText("file:///E:\\Project\\DataSource\\sink", WriteMode.OVERWRITE).setParallelism(5)

    val jobResult = env.execute("CounterApp")
    // 获取计数器
    val num = jobResult.getAccumulatorResult[Long]("ele-counts-scala")
    print(num)

    // 错误示例
//    data.map(new RichMapFunction[String, Long] {
//      var count = 0l
//      override def map(value: String): Long = {
//        count +=1
//        print("count: " + count)
//        count
//      }
//    }).setParallelism(3).print()
  }
}
