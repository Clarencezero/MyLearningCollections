package com.clarence.imooc.course08

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaProducer, KafkaSerializationSchema}
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper

object KafkaConnectorProducerApp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val data = env.socketTextStream("localhost", 9999)
    data.print()

    val topic = "test"
    val prop = new Properties()
    prop.setProperty("bootstrap.servers", "template:9092")

    val kafkaSink = new FlinkKafkaProducer[String](
      topic,
      new KeyedSerializationSchemaWrapper[String](new SimpleStringSchema()),
      prop,
      FlinkKafkaProducer.Semantic.NONE
    )


    data.addSink(kafkaSink)

    env.execute("KafkaConnectorProduceApp")
  }
}
