package com.clarence.imooc.course05

import java.{lang, util}

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.collector.selector.OutputSelector

object DataStreamTransformationApp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    // filterFunction(env)
    // unionFunction(env)
    splitSelectFunction(env)

    env.execute("DataStreamTransformationApp")
  }

  /**
   * SPLIT
   * Split the stream into two or more streams according to some criterion
   * @param env
   */
  def splitSelectFunction(env:StreamExecutionEnvironment) = {
    val data = env.addSource(new CustomNonParallelSourceFunction)

    // 把一个DataStream拆分成SplitStream
    // DataStream->SplitStream
    val split = data.split(
      (num: Long) => (
        (num % 2) match {
          case 0 => List("even")
          case 1 => List("odd")
        }
      )
    )
//    val split = data.split(new OutputSelector[Long] {
//      override def select(value: Long): lang.Iterable[String] = {
////        import scala.collection.JavaConversions._
//        val list = new util.ArrayList[String]()
//        if (value % 2 == 0) {
//          list.add("even")
//        } else {
//          list.add("odd")
//        }
//        list
//      }
//    })

    // SELECT
    // SplitStream -> DataStream
    split.select("even").print().setParallelism(1)

  }

  /**
   * UNION
   *
   * @param env
   * @return
   */
  def unionFunction(env: StreamExecutionEnvironment) = {
    val source1 = env.addSource(new CustomNonParallelSourceFunction)
    val source2 = env.addSource(new CustomNonParallelSourceFunction)

    source1.union(source2).print().setParallelism(1)
  }


  def filterFunction(env: StreamExecutionEnvironment): Unit = {
    val data = env.addSource(new CustomNonParallelSourceFunction)
    // 这个filter 感觉里面的表达式为true就进行下一步
    data.map(x => {
      println("received: " + x)
      x
    }).filter(_ % 2 == 0).print().setParallelism(1)
  }
}
