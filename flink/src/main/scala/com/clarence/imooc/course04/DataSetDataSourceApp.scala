package com.clarence.imooc.course04

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration


object DataSetDataSourceApp {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
//    fromCollection(env)
//    fromttextFile(env)
//    fromCsvFile(env)
    readRecureiveFiles(env)
  }





  /**
   * 递归读取文件
   * @param env
   */
  def readRecureiveFiles(env:ExecutionEnvironment): Unit = {
    val filePath = "file:///E:\\Project\\DataSource\\sequencefile"
    val conf = new Configuration()
    conf.setBoolean("recursive.file.enumeration", true)
    env.readTextFile(filePath).withParameters(conf).print()
  }

  /**
   * 读取CSV File
   * @param environment
   */
  def fromCsvFile(environment: ExecutionEnvironment): Unit = {
    val filePath = "file:///E:\\Project\\DataSource\\people.csv"
    import org.apache.flink.api.scala._
    // 第一种: 需要确定CSV和列的格式
    // environment.readCsvFile[(String, Int, String)](filePath, ignoreFirstLine = true).print()

    // 第二种: 可以读取指定列
//    environment.readCsvFile[(String, Int)](filePath, ignoreFirstLine = true, includedFields = Array(0, 1)).print()

    // 第三种:
    case class MyCaseClass(name:String, age:Int)
    environment.readCsvFile[MyCaseClass](filePath, ignoreFirstLine = true, includedFields = Array(0, 1)).print()
  }

  /**
   * 数据源: 文件/文件夹
   * @param environment
   */
  def fromttextFile(environment: ExecutionEnvironment): Unit = {
    val filePath = "file:///E:\\Project\\DataSource\\sequencefile"
    environment.readTextFile(filePath).print()
  }

 /**
   * 数据源: 集合
   * @param env
   */
  def fromCollection(env: ExecutionEnvironment): Unit = {
    import org.apache.flink.api.scala._
    val data = 1 to 10
    env.fromCollection(data).print()
  }
}
