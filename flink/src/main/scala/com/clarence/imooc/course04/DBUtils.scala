package com.clarence.imooc.course04

import scala.util.Random

object DBUtils {
  def getConnection() = {
    new Random().nextInt(10) + ""
  }

  def returnConnection(conn:String) = {

  }
}
