package com.clarence.dajiangtai.course01.scala

import com.clarence.dajiangtai.course01.java.dataset.WordCountData
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
object WordCount {

  def main(args: Array[String]): Unit = {
    // 1.获取ENV
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 2. 定义Source
    val rowData = env.fromCollection(WordCountData.WORDS)
    rowData.print()


    // 3. DataSet Function
    // map: 一进一出
    // flatmap: 一进多出
    // 分词->分区->计数
    val counts = rowData.flatMap(_.toLowerCase.split("\\W+"))
        .map((_, 1))
        .groupBy(0)
        .sum(1)

    counts.print()

    // 4. Run JOB
    env.execute("WordCount")
  }



}
