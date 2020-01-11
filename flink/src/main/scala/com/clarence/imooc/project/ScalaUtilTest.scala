package com.clarence.imooc.project

import java.text.SimpleDateFormat
import java.util.Date

object ScalaUtilTest {
  def main(args: Array[String]): Unit = {
    val time2 = 1566965938000l;
    val time = tranTimeToString(time2.toString)
    println(time)
  }

  def tranTimeToString(tm:String) :String={
    val fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val tim = fm.format(new Date(tm.toLong))
    tim
  }
}
