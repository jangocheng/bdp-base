package com.platform.migration.db

import com.platform.migration.MigrationStart
import com.platform.migration.common.util.HiveSqlUtils
import com.platform.migration.entity.TaskSchema
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.{Logger, LoggerFactory}

case class HiveDataDB() {
  private val logger: Logger = LoggerFactory.getLogger(MigrationStart.getClass)

  def save(sparkSession: SparkSession, taskSchema: TaskSchema,df : DataFrame): Unit = {
    //判断表是否存在
    val exists = sparkSession.catalog.tableExists(taskSchema.getTableName)
    if (!exists) {
      import scala.collection.JavaConverters._
      val createTableSql: String = HiveSqlUtils.createTableSql(taskSchema,df.columns.toList.asJava)
      logger.info(s"create table sql:$createTableSql")
      try {
        sparkSession.sql(createTableSql)
      } catch {
        case ex: Exception =>
          throw new RuntimeException(s"create table error，sql:$createTableSql,$ex")
      }
    }

    //保存数据
    val saveSql = HiveSqlUtils.saveSql(taskSchema)
    logger.info(s"save data sql:$saveSql")
    sparkSession.sql(saveSql)

  }
}


