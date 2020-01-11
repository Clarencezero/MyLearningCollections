package com.clarence.imooc.course04

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem.WriteMode
object DistributeCacheApp {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    env.registerCachedFile("file:///E:\\Project\\DataSource\\distributeCache.txt","pk-scala-dc")

    val data = env.fromElements("hadoop", "spark", "flink", "storm")

    val d = data.map(new RichMapFunction[String, String] {


      override def open(parameters: Configuration): Unit = {
        // 在open方法中获取到分布式缓存的内容即可
        // When the program is executed, Flink automatically copies the file or
        // directory to the local filesystem of all workers.
        val file = getRuntimeContext.getDistributedCache.getFile("pk-scala-dc")

        // 这个是从Java里面读取出来的, 需要转换
        val lines = FileUtils.readLines(file)
        import scala.collection.JavaConversions._
        for (ele <- lines) {
          println(ele)
        }
      }


      override def map(value: String): String = {
        value
      }
    })

    d.writeAsText("file:///E:\\Project\\DataSource\\distribute",WriteMode.OVERWRITE)


    env.execute("DistributeCacheApp")
  }
}
