package com.clarence.imooc.course02

import org.apache.flink.api.scala.ExecutionEnvironment

/**
  * 使用Scala开发Flink的批处理应用程序
  */
object BatchWCScalaApp {


  def main(args: Array[String]): Unit = {

    val input = "file:///E:\\Project\\DataSource\\wordcount.txt"

    val env = ExecutionEnvironment.getExecutionEnvironment

    val text = env.readTextFile(input)

    // 引入隐式转换
    import org.apache.flink.api.scala._

    // TODO... 1) 参考Scala课程  2）API再来讲
    text.flatMap(_.toLowerCase.split(" "))
      .filter(_.nonEmpty)
      .map((_,1))
      .groupBy(0)
      .sum(1).print()

  }

}
