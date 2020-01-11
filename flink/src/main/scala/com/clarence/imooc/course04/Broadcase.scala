package com.clarence.imooc.course04

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem.WriteMode

/**
 * 不能太大
 * Broadcast variables allow you to make a data set available to all parallel instances of an operation,
 * in addition to the regular input of the operation.
 * This is useful for auxiliary data sets, or data-dependent parameterization.
 * The data set will then be accessible at the operator as a Collection.
 */
object Broadcase {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 1. The DataSet to be broadcast
    val toBroadcast = env.fromElements(1, 2, 3)

    val data = env.fromElements("a", "b")

    val d = data.map(new RichMapFunction[String, String]() {
      var broadcastSet: Traversable[String] = null

      override def open(config: Configuration): Unit = {
        // 3. Access the broadcast DataSet as a Collection
        import scala.collection.JavaConversions._
        broadcastSet = getRuntimeContext().getBroadcastVariable[String]("broadcastSetName")
      }

      def map(in: String): String = {
        in
      }
    }).withBroadcastSet(toBroadcast, "broadcastSetName") // 2. Broadcast the DataSet

    d.writeAsText("file:///E:\\Project\\DataSource\\distribute",WriteMode.OVERWRITE)


    env.execute("Broadcase")
  }
}
