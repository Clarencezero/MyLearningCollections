package com.clarence.imooc.course06

import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.scala._
import org.apache.flink.types.Row

object tableSqlAPI {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val tEnv = BatchTableEnvironment.create(env)

    val filePath = "file:///E:\\Project\\DataSource\\people.csv"

    // 获取到DataSet
    val csv =  env.readCsvFile[SalesLog](filePath, ignoreFirstLine = true)

    // 将DataSet=>table
    val salesTable = tEnv.fromDataSet(csv)


    // 将table注册到一张表
    tEnv.registerTable("sales", salesTable)

    // table=>table
    val resultTable = tEnv.sqlQuery("select customerId,sum(amountPaid) money from sales group by customerId")

    // 输出
    tEnv.toDataSet[Row](resultTable).print()



  }

  case class SalesLog(transactionId:String,
                      customerId:String,
                      itemId:String,
                      amountPaid:Double)

}
