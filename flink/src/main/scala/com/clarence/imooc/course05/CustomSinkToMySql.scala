package com.clarence.imooc.course05

import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._


object CustomSinkToMySql {
  case class Student(id:Int, name:String, age:Int)
  def main(args: Array[String]): Unit = {
    // 1.环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 2.配置源(socket)
    val source = env.socketTextStream("localhost", 9999)

    source.map(u => {
      val data = u.split(",")
//      data.foreach(println)
//      Student stud = new Student(Int(data(0)), data(1), data(2))

    }).print()

    // 4.显式执行
    env.execute("CustomSinkToMySql")
  }

}
