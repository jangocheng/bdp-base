package com.platform.handler

import com.platform.base.common.{DataTypeCommon, SchemaCommon}
import com.platform.store.{BussinessDataStore, CanalStore, MongoStore, ProduceStore, SalaryStore}
import com.platform.utils.DataAnalyticsOperate
import org.apache.spark.sql.types.{DataType, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.storage.StorageLevel

case class DataHandler(){

  def createSalaryDataHandler(filePath : String,sparkSession: SparkSession): Unit = {

    val salaryDF = sparkSession.read.textFile(filePath)
    salaryDF.persist(StorageLevel.MEMORY_AND_DISK)

    val groupBGroupSalaryDs = salaryDF.filter(f => {
      DataTypeCommon.B_GROUP_B_GROUP_SALARY.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))
    })
    new SalaryStore().save(groupBGroupSalaryDs, sparkSession, SchemaCommon.B_GROUP_B_GROUP_SALARY, DataTypeCommon.B_GROUP_B_GROUP_SALARY_TABLE)
    val groupDGroupSalaryNewPcebgDs = salaryDF.filter(f => {
      DataTypeCommon.D_GROUP_D_GROUP_SALARY_NEW_PCEBG.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))
    })
    new SalaryStore().save(groupDGroupSalaryNewPcebgDs, sparkSession, SchemaCommon.D_GROUP_D_GROUP_SALARY_NEW_PCEBG, DataTypeCommon.D_GROUP_D_GROUP_SALARY_NEW_PCEBG_TABLE)
    val groupFiiGroupSalaryCesgbDs = salaryDF.filter(f => {
      DataTypeCommon.FII_GROUP_FII_GROUP_SALARY_CESGB.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))
    })
    new SalaryStore().save(groupFiiGroupSalaryCesgbDs, sparkSession, SchemaCommon.FII_GROUP_FII_GROUP_SALARY_CESGB, DataTypeCommon.FII_GROUP_FII_GROUP_SALARY_CESGB_TABLE)
    val groupFiiGroupSalaryCnsgbTsbgDs = salaryDF.filter(f => {
      DataTypeCommon.FII_GROUP_FII_GROUP_SALARY_CNSGB_TSBG.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))
    })
    new SalaryStore().save(groupFiiGroupSalaryCnsgbTsbgDs, sparkSession, SchemaCommon.FII_GROUP_FII_GROUP_SALARY_CNSGB_TSBG, DataTypeCommon.FII_GROUP_FII_GROUP_SALARY_CNSGB_TSBG_TABLE)
    val groupHGroupSalaryOneDs = salaryDF.filter(f => {
      DataTypeCommon.H_GROUP_H_GROUP_SALARY_ONE.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))
    })
    new SalaryStore().save(groupHGroupSalaryOneDs, sparkSession, SchemaCommon.H_GROUP_H_GROUP_SALARY_ONE, DataTypeCommon.H_GROUP_H_GROUP_SALARY_ONE_TABLE)
    val groupIdpgidpbgGroupSalaryDs = salaryDF.filter(f => {
      DataTypeCommon.IDPBG_GROUP_IDPGIDPBG_GROUP_SALARY.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))
    })
    new SalaryStore().save(groupIdpgidpbgGroupSalaryDs, sparkSession, SchemaCommon.IDPBG_GROUP_IDPGIDPBG_GROUP_SALARY, DataTypeCommon.IDPBG_GROUP_IDPGIDPBG_GROUP_SALARY_TABLE)
    val groupIpebgGroupSalaryDs = salaryDF.filter(f => {
      DataTypeCommon.IPEBG_GROUP_IPEBG_GROUP_SALARY.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))
    })
    new SalaryStore().save(groupIpebgGroupSalaryDs, sparkSession, SchemaCommon.IPEBG_GROUP_IPEBG_GROUP_SALARY, DataTypeCommon.IPEBG_GROUP_IPEBG_GROUP_SALARY_TABLE)

    salaryDF.unpersist()

  }

  def createApplicationDataHandler(filePath : String,sparkSession: SparkSession): Unit = {
    val appDF = sparkSession.read.textFile(filePath)
    appDF.persist(StorageLevel.MEMORY_AND_DISK)

    val phoneDs = appDF.filter(f=>{DataTypeCommon.APP_PHONE.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(phoneDs,sparkSession,SchemaCommon.APP_PHONE,DataTypeCommon.APP_PHONE)

    val appCallDs = appDF.filter(f => DataTypeCommon.APP_CALL.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE)))
    new ProduceStore().save(appCallDs, sparkSession, SchemaCommon.APP_CALL, DataTypeCommon.APP_CALL)

    val appApplicationDs = appDF.filter(f => DataTypeCommon.APP_APPLICATION.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE)))
    new ProduceStore().save(appApplicationDs, sparkSession, SchemaCommon.APP_APPLICATION, DataTypeCommon.APP_APPLICATION)

    val appContactDs = appDF.filter(f => DataTypeCommon.APP_CONTACT.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE)))
    new ProduceStore().save(appContactDs, sparkSession, SchemaCommon.APP_CONTACT, DataTypeCommon.APP_CONTACT)

    val smsDs = appDF.filter(f=>{DataTypeCommon.APP_SMS.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(smsDs,sparkSession,SchemaCommon.APP_SMS,DataTypeCommon.APP_SMS)

    val infoDs = appDF.filter(f=>{DataTypeCommon.DEVICE_INFO.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(infoDs,sparkSession,SchemaCommon.DEVICE_INFO,DataTypeCommon.DEVICE_INFO)

    val locationDs = appDF.filter(f=>{DataTypeCommon.DEVICE_LOCATION.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(locationDs,sparkSession,SchemaCommon.DEVICE_LOCATION,DataTypeCommon.DEVICE_LOCATION)

    val activityDs = appDF.filter(f=>{DataTypeCommon.APP_ACTIVITY.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(activityDs,sparkSession,SchemaCommon.APP_ACTIVITY,DataTypeCommon.APP_ACTIVITY)

    val eventDs = appDF.filter(f=>{DataTypeCommon.APP_EVENT.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(eventDs,sparkSession,SchemaCommon.APP_EVENT,DataTypeCommon.APP_EVENT)

    val eventContentDs = appDF.filter(f=>{DataTypeCommon.APP_EVENT_CONTENT.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(eventContentDs,sparkSession,SchemaCommon.APP_EVENT_CONTENT,DataTypeCommon.APP_EVENT_CONTENT)

    val appLocationDs = appDF.filter(f=>{DataTypeCommon.APP_LOCATION.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(appLocationDs,sparkSession,SchemaCommon.APP_LOCATION,DataTypeCommon.APP_LOCATION)

    val securityDs = appDF.filter(f=>{DataTypeCommon.APP_SECURITY.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(securityDs,sparkSession,SchemaCommon.APP_SECURITY,DataTypeCommon.APP_SECURITY)

    val sensorDs = appDF.filter(f=>{DataTypeCommon.APP_SENSOR.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(sensorDs,sparkSession,SchemaCommon.APP_SENSOR,DataTypeCommon.APP_SENSOR)

    val udpDs = appDF.filter(f=>{DataTypeCommon.APP_UDP.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(udpDs,sparkSession,SchemaCommon.APP_UDP,DataTypeCommon.APP_UDP)

    val wifiDs = appDF.filter(f=>{DataTypeCommon.APP_WIFI.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new ProduceStore().save(wifiDs,sparkSession,SchemaCommon.APP_WIFI,DataTypeCommon.APP_WIFI)

    val bussinessDataDs = appDF.filter(f => {
      DataTypeCommon.BUSSINESS_DATA.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))
    })
    new BussinessDataStore().save(bussinessDataDs, sparkSession, SchemaCommon.BUSSINESS_DATA, DataTypeCommon.BUSSINESS_DATA_TABLE)

    appDF.unpersist()
  }

  def createCanalDataHandler(filePath : String,sparkSession: SparkSession): Unit ={
    //读取文件
    val canalDS = sparkSession.read.textFile(filePath)
    //缓存
    canalDS.persist(StorageLevel.MEMORY_AND_DISK)

    val hxdbBusinessApplyDs = canalDS.filter(f=>{DataTypeCommon.MYSQL_HXDB_BUSINESS_APPLY.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new CanalStore().save(hxdbBusinessApplyDs,sparkSession,SchemaCommon.MYSQL_HXDB_BUSINESS_APPLY,DataTypeCommon.MYSQL_HXDB_BUSINESS_APPLY)
    val hxdbBusinessContractDs = canalDS.filter(f=>{DataTypeCommon.MYSQL_HXDB_BUSINESS_CONTRACT.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new CanalStore().save(hxdbBusinessContractDs,sparkSession,SchemaCommon.MYSQL_HXDB_BUSINESS_CONTRACT,DataTypeCommon.MYSQL_HXDB_BUSINESS_CONTRACT)
    val hxdbCodeLibraryDs = canalDS.filter(f=>{DataTypeCommon.MYSQL_HXDB_CODE_LIBRARY.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new CanalStore().save(hxdbCodeLibraryDs,sparkSession,SchemaCommon.MYSQL_HXDB_CODE_LIBRARY,DataTypeCommon.MYSQL_HXDB_CODE_LIBRARY)
    val hxdbEmployeeCompanyRecordDs = canalDS.filter(f=>{DataTypeCommon.MYSQL_HXDB_EMPLOYEE_COMPANY_RECORD.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new CanalStore().save(hxdbEmployeeCompanyRecordDs,sparkSession,SchemaCommon.MYSQL_HXDB_EMPLOYEE_COMPANY_RECORD,DataTypeCommon.MYSQL_HXDB_EMPLOYEE_COMPANY_RECORD)
    val hxdbEmployeeQuotaInfoDs = canalDS.filter(f=>{DataTypeCommon.MYSQL_HXDB_EMPLOYEE_QUOTA_INFO.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new CanalStore().save(hxdbEmployeeQuotaInfoDs,sparkSession,SchemaCommon.MYSQL_HXDB_EMPLOYEE_QUOTA_INFO,DataTypeCommon.MYSQL_HXDB_EMPLOYEE_QUOTA_INFO)
    val hxdbEmployeeQuotaUselogDs = canalDS.filter(f=>{DataTypeCommon.MYSQL_HXDB_EMPLOYEE_QUOTA_USELOG.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new CanalStore().save(hxdbEmployeeQuotaUselogDs,sparkSession,SchemaCommon.MYSQL_HXDB_EMPLOYEE_QUOTA_USELOG,DataTypeCommon.MYSQL_HXDB_EMPLOYEE_QUOTA_USELOG)
    val hxdbIndInfoDs = canalDS.filter(f=>{DataTypeCommon.MYSQL_HXDB_IND_INFO.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new CanalStore().save(hxdbIndInfoDs,sparkSession,SchemaCommon.MYSQL_HXDB_IND_INFO,DataTypeCommon.MYSQL_HXDB_IND_INFO)
    val hxdbBusinessPutoutDs = canalDS.filter(f=>{DataTypeCommon.MYSQL_HXDB_BUSINESS_PUTOUT.equals(DataAnalyticsOperate.originDataHandler(f).get.getString(DataTypeCommon.DATA_TYPE))})
    new CanalStore().save(hxdbBusinessPutoutDs,sparkSession,SchemaCommon.MYSQL_HXDB_BUSINESS_PUTOUT,DataTypeCommon.MYSQL_HXDB_BUSINESS_PUTOUT)

    canalDS.unpersist()
  }



  def createMongoShakeDataHandler(filePath : String,sparkSession: SparkSession): Unit ={
    //读取文件
    val schema = DataType.fromJson(SchemaCommon.base_MONGO_COMMON).asInstanceOf[StructType]
    val mongoShakeDS = sparkSession.read.schema(schema).json(filePath)
    //缓存
    mongoShakeDS.persist(StorageLevel.MEMORY_AND_DISK)

    new MongoStore().save(mongoShakeDS,sparkSession)

    mongoShakeDS.unpersist()
  }

}
