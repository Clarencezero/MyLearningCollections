package com.clarence

object strTest {
  def main(args: Array[String]): Unit = {
    val str = "hello helloworld"
    val res = str.split(" ")
    for (i <- 0 to (res.length - 1)) {
      println(res(i))
    }

  }
}
