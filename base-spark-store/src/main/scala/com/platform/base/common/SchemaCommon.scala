package com.platform.base.common

import com.platform.utils.PropertiUtils

object SchemaCommon {

  private val canalSchemaProps = PropertiUtils.init("canalSchema.properties")

  private val mongoSchemaProps = PropertiUtils.init("mongoSchema.properties")

  private val produceSchemaProps = PropertiUtils.init("produceSchema.properties")

  private val salarySchemaProps = PropertiUtils.init("salarySchema.properties")

  /**
    * zfb
    */
  val base_MONGO_COMMON =  mongoSchemaProps.getProperty(DataTypeCommon.base_MONGO_COMMON)
  /**
    * core
    */
  val MYSQL_HXDB_BUSINESS_APPLY = canalSchemaProps.getProperty(DataTypeCommon.MYSQL_HXDB_BUSINESS_APPLY)
  val MYSQL_HXDB_BUSINESS_CONTRACT = canalSchemaProps.getProperty(DataTypeCommon.MYSQL_HXDB_BUSINESS_CONTRACT)
  val MYSQL_HXDB_CODE_LIBRARY = canalSchemaProps.getProperty(DataTypeCommon.MYSQL_HXDB_CODE_LIBRARY)
  val MYSQL_HXDB_EMPLOYEE_COMPANY_RECORD = canalSchemaProps.getProperty(DataTypeCommon.MYSQL_HXDB_EMPLOYEE_COMPANY_RECORD)
  val MYSQL_HXDB_EMPLOYEE_QUOTA_INFO = canalSchemaProps.getProperty(DataTypeCommon.MYSQL_HXDB_EMPLOYEE_QUOTA_INFO)
  val MYSQL_HXDB_EMPLOYEE_QUOTA_USELOG = canalSchemaProps.getProperty(DataTypeCommon.MYSQL_HXDB_EMPLOYEE_QUOTA_USELOG)
  val MYSQL_HXDB_IND_INFO = canalSchemaProps.getProperty(DataTypeCommon.MYSQL_HXDB_IND_INFO)
  val MYSQL_HXDB_BUSINESS_PUTOUT = canalSchemaProps.getProperty(DataTypeCommon.MYSQL_HXDB_BUSINESS_PUTOUT)

   /**
    * salary
    */
  val B_GROUP_B_GROUP_SALARY = salarySchemaProps.getProperty(DataTypeCommon.B_GROUP_B_GROUP_SALARY)
  val D_GROUP_D_GROUP_SALARY_NEW_PCEBG = salarySchemaProps.getProperty(DataTypeCommon.D_GROUP_D_GROUP_SALARY_NEW_PCEBG)
  val FII_GROUP_FII_GROUP_SALARY_CESGB = salarySchemaProps.getProperty(DataTypeCommon.FII_GROUP_FII_GROUP_SALARY_CESGB)
  val FII_GROUP_FII_GROUP_SALARY_CNSGB_TSBG = salarySchemaProps.getProperty(DataTypeCommon.FII_GROUP_FII_GROUP_SALARY_CNSGB_TSBG)
  val H_GROUP_H_GROUP_SALARY_ONE = salarySchemaProps.getProperty(DataTypeCommon.H_GROUP_H_GROUP_SALARY_ONE)
  val IDPBG_GROUP_IDPGIDPBG_GROUP_SALARY = salarySchemaProps.getProperty(DataTypeCommon.IDPBG_GROUP_IDPGIDPBG_GROUP_SALARY)
  val IPEBG_GROUP_IPEBG_GROUP_SALARY = salarySchemaProps.getProperty(DataTypeCommon.IPEBG_GROUP_IPEBG_GROUP_SALARY)

  /**
    * sdk 数据
    */
  val APP_APPLICATION = produceSchemaProps.getProperty(DataTypeCommon.APP_APPLICATION)
  val APP_CALL = produceSchemaProps.getProperty(DataTypeCommon.APP_CALL)
  val APP_CONTACT = produceSchemaProps.getProperty(DataTypeCommon.APP_CONTACT)
  val APP_SMS = produceSchemaProps.getProperty(DataTypeCommon.APP_SMS)
  val DEVICE_INFO = produceSchemaProps.getProperty(DataTypeCommon.DEVICE_INFO)
  val DEVICE_LOCATION = produceSchemaProps.getProperty(DataTypeCommon.DEVICE_LOCATION)
  val APP_ACTIVITY = produceSchemaProps.getProperty(DataTypeCommon.APP_ACTIVITY)
  val APP_EVENT = produceSchemaProps.getProperty(DataTypeCommon.APP_EVENT)
  val APP_EVENT_CONTENT = produceSchemaProps.getProperty(DataTypeCommon.APP_EVENT_CONTENT)
  val APP_LOCATION = produceSchemaProps.getProperty(DataTypeCommon.APP_LOCATION)
  val APP_SECURITY = produceSchemaProps.getProperty(DataTypeCommon.APP_SECURITY)
  val APP_SENSOR = produceSchemaProps.getProperty(DataTypeCommon.APP_SENSOR)
  val APP_UDP = produceSchemaProps.getProperty(DataTypeCommon.APP_UDP)
  val APP_WIFI = produceSchemaProps.getProperty(DataTypeCommon.APP_WIFI)
  val APP_PHONE = produceSchemaProps.getProperty(DataTypeCommon.APP_PHONE)

  /**
    * 后端埋点监控数据
    */
  val BUSSINESS_DATA = produceSchemaProps.getProperty(DataTypeCommon.BUSSINESS_DATA)


}
