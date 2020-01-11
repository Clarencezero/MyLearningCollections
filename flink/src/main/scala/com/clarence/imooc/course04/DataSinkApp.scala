package com.clarence.imooc.course04

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

import scala.collection.mutable.ListBuffer

object DataSinkApp {
  case class Student(city:Int, name:String)
  // 人
  val userObj = ListBuffer[Student]()
  userObj.append(new Student(1, "姜A"))
  userObj.append(new Student(2, "姜B"))
  userObj.append(new Student(3, "姜C"))
  userObj.append(new Student(4, "姜D"))
  userObj.append(new Student(6, "姜E"))

  // 城市
  val city = ListBuffer[(Int, String)]()
  city.append((1, "北京市"))
  city.append((2, "上海市"))
  city.append((3, "广州市"))
  city.append((4, "深圳市"))
  city.append((5, "杭州市"))

  val filePath = "file:///E:\\Project\\DataSource\\sink"

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    writeAsText(env)

    // 需要显式启动JOB
    env.execute("DataSinkApp")
  }

  def writeAsText(env:ExecutionEnvironment) = {
    val user = env.fromCollection(userObj)
    val cities = env.fromCollection(city)

    //
//    val data = user.join(cities).where("city").equalTo(0) {
//      (u, c) => (
//        u.city, u.name, c._2
//      )
//    }

    // 如果指定了并行度, 则相当于是输出两个文件
    user.writeAsText(filePath, WriteMode.OVERWRITE).setParallelism(3)
  }

}
