package com.clarence.imooc.course05

import org.apache.flink.streaming.api.functions.source.{ParallelSourceFunction, SourceFunction}

class CustomParallelSourceFunction extends ParallelSourceFunction[Long]{
  var count = 1L
  var isRunning = true


  override def run(sourceContext: SourceFunction.SourceContext[Long]): Unit =  {
    while (isRunning) {
      sourceContext.collect(count)
      count += 1
      Thread.sleep(1000)
    }
  }

  override def cancel(): Unit = {
    isRunning = false
  }

}
