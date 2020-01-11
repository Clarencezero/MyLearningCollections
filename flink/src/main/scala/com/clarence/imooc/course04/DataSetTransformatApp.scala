package com.clarence.imooc.course04

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

import scala.collection.mutable.ListBuffer

/**
 * 小结: Map、FlatMap、MapPartition(并行、减少资源消耗)、Filter、Distinct、JOIN、OuterJoin、First-n
 * Hash-paratition
 * Range-Partition
 */
object DataSetTransformatApp {
  case class Student(city:Int, name:String)
  val collectData = List(1,2,3,4,5,6,7,8,9,10)
  val info = ListBuffer[String]()
  info.append("hadoop,hadoop")
  info.append("hadoop,flink")
  info.append("hadoop,spark")
  info.append("yarn,spark")

  // 人
  val users = ListBuffer[(Int,String)]()
  users.append((1, "姜A"))
  users.append((2, "姜B"))
  users.append((3, "姜C"))
  users.append((4, "姜D"))
  users.append((5, "姜E"))
  users.append((6, "姜F"))

  val userObj = ListBuffer[Student]()
  userObj.append(new Student(1, "姜A"))
  userObj.append(new Student(2, "姜B"))
  userObj.append(new Student(3, "姜C"))
  userObj.append(new Student(4, "姜D"))
  userObj.append(new Student(6, "姜E"))




  // 人所在的城市
  val city = ListBuffer[(Int, String)]()
  city.append((1, "北京市"))
  city.append((2, "上海市"))
  city.append((3, "广州市"))
  city.append((4, "深圳市"))
  city.append((5, "杭州市"))

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    // mapFunction(env)
    // filterFunction(env)
    // mapPartitionFunction(env)
    // firstNFunction(env)
    // flatMapFunction(env)
    // distinctFunction(env)
    // joinFuncttion(env)
    // leftJoin(env)
    // fullOuterJoin(env)
    crosFunction(env)
  }


  // 8.cross 迪卡尔集
  def crosFunction(env:ExecutionEnvironment) = {
    val nbaTeam = List("湖人", "快船")
    val score = List(3, 1, 0)

    val nbaTeam1 = env.fromCollection(nbaTeam)
    val score1 = env.fromCollection(score)

   /* (湖人,3)
    (湖人,1)
    (湖人,0)
    (快船,3)
    (快船,1)
    (快船,0)*/
    nbaTeam1.cross(score1).print()


  }

 /* (3,姜C,广州市)
  (6,姜E,NULL)
  (1,姜A,北京市)
  (NULL,NULL,杭州市)
  (2,姜B,上海市)
  (4,姜D,深圳市)*/
  // 8.fullOuterJoin
  def fullOuterJoin(env:ExecutionEnvironment) = {
    val cities = env.fromCollection(city)
    val userOb = env.fromCollection(userObj)

    userOb.fullOuterJoin(cities).where("city").equalTo(0) {
      (u, c) => (
        if (u == null) {
          ("NULL", "NULL", c._2)
        } else if (c == null) {
          (u.city, u.name, "NULL")
        } else {
          (u.city, u.name, c._2)
        }
      )
    }.print()
  }

  // 7.leftOuterJoin & rightOuterJoin
  def leftJoin(env:ExecutionEnvironment) = {
    val cities = env.fromCollection(city)
    val userOb = env.fromCollection(userObj)

    userOb.leftOuterJoin(cities).where("city").equalTo(0) {
      (u, c) => (
        if (c == null) {
          (u.city, u.name, "NULL")
        } else {
          (u.city, u.name, c._2)
        }
      )}.print()
  }

  // 6. JOIN。
  def joinFuncttion(env:ExecutionEnvironment) = {
    val user = env.fromCollection(users)
    val cities = env.fromCollection(city)
    val userOb = env.fromCollection(userObj)

    val r = userOb.join(cities).where("city").equalTo(0) {
        (u, c) => (
          u.city, u.name, c._2
        )
      }.print()

//    (Student(3,姜C),(3,广州市))
//    (Student(1,姜A),(1,北京市))
//    (Student(5,姜E),(5,杭州市))
//    (Student(2,姜B),(2,上海市))
//    (Student(4,姜D),(4,深圳市))

/*    (3,姜C,广州市)
    (1,姜A,北京市)
    (5,姜E,杭州市)
    (2,姜B,上海市)
    (4,姜D,深圳市)*/
    userOb.join(cities)
        .where("city")
        .equalTo(0)
      .apply((user, city) => {
        (user.city, user.name, city._2)
      })
      .print()


    // user.join(cities).where(0)
  /*  ((3,姜C),(3,广州市))
    ((1,姜A),(1,北京市))
    ((5,姜E),(5,杭州市))
    ((2,姜B),(2,上海市))
    ((4,姜D),(4,深圳市))*/
    // user.join(cities).where(0).equalTo(0).print()

    /*(3,姜C,广州市)
    (5,姜E,杭州市)
    (1,姜A,北京市)
    (4,姜D,深圳市)
    (2,姜B,上海市)*/
   /* user.join(cities)
      .where(0)
      .equalTo(0)
      .apply((first, second) => {
        (first._1, first._2, second._2)
      })
      .print()*/
  }


  // 5.Distinct 去重
  def distinctFunction(env:ExecutionEnvironment) = {
    val data = env.fromCollection(info)
    data.flatMap(_.split(",")).distinct().print()
  }

  // 4.FlatMap, 可以一变多
  def flatMapFunction(env:ExecutionEnvironment): Unit = {


    val data = env.fromCollection(info)
    // data.map(_.split(",")).print()
    // 分组再求和 ∑
    data.flatMap(_.split(","))
      .map((_,1))
      .groupBy(0)
      .sum(1)
      .print()
  }

  // 3.First-n
  def firstNFunction(env:ExecutionEnvironment): Unit = {
    val info = ListBuffer[(Int, String)]()
    info.append((1, "hadoop"))
    info.append((1,"Spark"))
    info.append((1,"Flink"))
    info.append((1, "Java"))
    info.append((2, "Spring boot"))
    info.append((2, "Linux"))
    info.append((4, "VUE"))

    val data = env.fromCollection(info)

    // 获取前三条
    data.first(3).print()

    // 分组,first则对应组内的前2
    data.groupBy(0)
      .first(2)
      .print()

    // 分组、排序
    data.groupBy(0)
      .sortGroup(1, Order.ASCENDING)
      .first(2)
      .print()

  }

  // 2.算子 MapPartition
  // DataSource 100个元素, 把结果存储到数据库中
  def mapPartitionFunction(env:ExecutionEnvironment): Unit = {
    val students = new ListBuffer[String]
    for (i <-1 to 100) {
      students.append("student:" + i)
    }

    val data = env.fromCollection(students)

    // 方式二: 分区概念
    // 比如10个人, 分成两批进行处理,可以设置并行度
    data.mapPartition(x => {
      val con = DBUtils.getConnection()
      println(con + ".....")
      DBUtils.returnConnection(con)
      x
    }).setParallelism(3).print()


    // 方式一: 性能非常低下
//    data.map(x => {
//      // 1. 获取Collection
//      val conn = DBUtils.getConnection()
//      println(conn + "...")
//      println("保存到数据库中")
//      DBUtils.returnConnection(conn)
//    }).print()


  }

  /**
   * 2.算子: 过滤 Each of Element of a DataSet
   * @param env
   */
  def filterFunction(env:ExecutionEnvironment): Unit = {
    env.fromCollection(collectData)
      .map(_+1)
      .filter(_ > 5)
      .print()
  }

  /**
   * 1.Map算子
   * = y = f(x)
   * 作用在每一个element之上的
   * applies map function on each element of a DataSet
   * @param env
   */
  def mapFunction(env:ExecutionEnvironment): Unit = {
    val data = env.fromCollection(collectData)
    // data.map((x:Int) => x + 1).print()
    // data.map((x) => x + 1).print()
    // data.map(x => x + 1).print()
    data.map(_ + 1).print()

  }
}
