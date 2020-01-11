package com.clarence.imooc.course05

import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

class CustomRichParallelSourceFunction extends RichParallelSourceFunction[Long]{
  var count = 1L
  var isRunning = true
  override def run(ctx: SourceFunction.SourceContext[Long]): Unit = {
    while (isRunning) {
      count+=1
      Thread.sleep(100)
    }
  }

  override def cancel(): Unit = {
    isRunning = false
  }
}
