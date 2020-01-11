package com.clarence.imooc.project

import java.text.SimpleDateFormat
import java.util
import java.util.{Date, Properties}

import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.elasticsearch.{ElasticsearchSinkFunction, RequestIndexer}
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink
import org.apache.flink.util.Collector
import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.Requests
import org.slf4j.LoggerFactory

object LogAnalysis {
  val logger = LoggerFactory.getLogger("LogAnalysis")

  def main(args: Array[String]): Unit = {
    // 1. 定义Stream ENV
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 设置EventTime时间窗口
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 2. 接收Kafka数据
    val topic = "flinkproject"
    val prop = new Properties()
    prop.setProperty("bootstrap.servers", "template:9092")
    prop.setProperty("group.id", "test-pk-group")
    val consumer = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), prop)

    val data = env.addSource(consumer)
    val logData = data.map(x => {
      val split = x.split("\t")
      val level = split(2)
      val timeStr = split(3)
      // 时间转换
      var time = 0l
      try {
        val sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        time = sourceFormat.parse(timeStr).getTime
      } catch {
        case e: Exception => {
          logger.error(s"time parse error: $timeStr", e.getMessage)
        }
      }
      val domain = split(5)
      val traffic = split(6).toLong
      (level, time, domain, traffic)
    }).filter(_._2 != 0).filter(_._1 == "E")
      .map(x => {
        (x._2, x._3, x._4) // 过滤掉第一个字段, 因为我们已经不需要他来记录了
      })

    // 过滤time为0的数据


    /**
     * 在生产上进行业务处理的时候,一定要考虑处理的健壮性以及数据的准确性
     * 脏数据或者是不符合业务规则的数据是需要全部过滤掉之后
     * 再进行相应业务逻辑的处理
     *
     * 对于我们的业务来说,我们只需要统计leven=E即可
     */

    // logData.print().setParallelism(1)
    val resultData = logData.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[(Long, String, Long)] {
      val maxOutOfOrderness = 10000L // 3.5 seconds

      //
      var currentMaxTimestamp: Long = _

      override def getCurrentWatermark: Watermark = {
        new Watermark(currentMaxTimestamp - maxOutOfOrderness)
      }

      override def extractTimestamp(element: (Long, String, Long), l: Long): Long = {
        val timestamp = element._1
        currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp)
        timestamp
      }
    }).keyBy(1)
      .window(TumblingEventTimeWindows.of(Time.seconds(60)))
      .apply(new WindowFunction[(Long, String, Long),
        (String, String, Long), Tuple, TimeWindow] {
        override def apply(key: Tuple, window: TimeWindow, input: Iterable[(Long, String, Long)], out: Collector[(String, String, Long)]): Unit = {
          val domain = key.getField(0).toString
          var sum = 0l
          val iterator = input.iterator
          var time = ""
          while (iterator.hasNext) {
            val next = iterator.next()
            sum += next._3
            time = tranTimeToString(next._1.toString)
            // 是能够获取到window里面的时间
          }
          println("时间: " + time)

          // 这一分钟的时间, 域名, traffic的和
          out.collect((time, domain, sum))
        }
      })

    val httpHosts = new util.ArrayList[HttpHost]()
    httpHosts.add(new HttpHost("template", 9200, "http"))

    val esSinkBuilder = new ElasticsearchSink.Builder[(String, String, Long)](
      httpHosts,
      new ElasticsearchSinkFunction[(String, String, Long)] {
        def createIndexRequest(element: (String, String, Long)): IndexRequest = {
          val json = new java.util.HashMap[String, Any]
          json.put("time", element._1)
          json.put("domain", element._2)
          json.put("traffic", element._3)
          val id = element._1 + "-" + element._2

          return Requests.indexRequest()
            .index("cdn")
            .`type`("traffic")
            .id(id)
            .source(json)
        }

        override def process(element: (String, String, Long), ctx: RuntimeContext, indexer: RequestIndexer): Unit = {
          indexer.add(createIndexRequest(element))
        }
      }
    )
    esSinkBuilder.setBulkFlushMaxActions(1)

    resultData.addSink(esSinkBuilder.build())


    env.execute("LogAnalysis")
  }


  /**
   * 时间转换函数:
   *
   * @param tm 时间戳->时间
   * @return
   */
  def tranTimeToString(tm: String): String = {
    val fm = new SimpleDateFormat("yyyy-MM-dd HH:mm")
    val tim = fm.format(new Date(tm.toLong))
    tim
  }
}


























