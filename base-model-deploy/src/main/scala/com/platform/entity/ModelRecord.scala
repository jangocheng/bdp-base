package com.platform.entity

import java.util

import com.alibaba.fastjson.{JSONArray, JSONObject, TypeReference}
import ml.combust.mleap.core.types.{ScalarType, StructField, StructType}
import ml.combust.mleap.runtime.frame.Row


/**
  * 模型请求的元数据结构
  * author: wlhbdp
  * create: 2020-05-27 15:53
  */
case class ModelRecord() {

  var schema: StructType = StructType()
  var rows:Seq[Row] = _
  var model:ModelMeta = _
  var output:OutputMeta = _

  def parseInputData(data:JSONObject) {
    val jsonSchema: JSONObject = data.getJSONObject("schema")
    val fields: JSONArray = jsonSchema.getJSONArray("fields")
    val modelMeta:JSONObject = data.getJSONObject("model")
    val outputMeta:JSONObject = data.getJSONObject("output")
    var temp:StructType = StructType()
    for (index <- 0 until fields.size()) {
      val itemField = fields.getJSONObject(index)
      val fieldName = itemField.getString("name")
      val fieldTypeName = itemField.getString("type")
      val fieldType = fieldTypeName match {
        case "string" => ScalarType.String
        case "double" => ScalarType.Double
        case "int" => ScalarType.Int
        case "long" => ScalarType.Long
        case "boolean" => ScalarType.Boolean
        case _ => null
      }
      temp = temp.withField(StructField(fieldName, fieldType)).get
    }

    val jsonRows:util.ArrayList[Array[Any]] = data.getObject("rows", new TypeReference[util.ArrayList[Array[Any]]](){})

    this.output = OutputMeta(outputMeta.getString("name"), outputMeta.getString("type"))
    this.model = ModelMeta(modelMeta.getString("name"), modelMeta.getString("type"))
    this.schema  = temp
    this.rows = parseRawRowData(jsonRows, fields, schema)
  }

  private def parseRawRowData(jsonRows:util.ArrayList[Array[Any]], fields: JSONArray, schema:StructType): Seq[Row] = {
    var rows:Seq[Row] = Seq()
    for (index <- 0 until jsonRows.size()) {
      val jsonRow:Array[Any] = jsonRows.get(index)
      val row:Row = parseArrayToRow(jsonRow, schema)
      rows = rows.:+(row)
    }
    rows
  }

  private def parseArrayToRow(jsonRow:Array[Any], schema: StructType): Row = {
    var row:Row = Row()
    val fieldsSchema = schema.fields
    var index = 0
    for (field <- jsonRow) {
      fieldsSchema(index).dataType match {
        case ScalarType.String =>
          val value = if (field == null) {
            ""
          } else {
            field.toString
          }
          row = row.withValue(value)
        case ScalarType.Double => row = row.withValue(java.lang.Double.valueOf(field.toString))
        case ScalarType.Int => row = row.withValue(java.lang.Integer.valueOf(field.toString))
        case ScalarType.Long => row = row.withValue(java.lang.Long.valueOf(field.toString))
        case ScalarType.Boolean =>
          val value = if (java.lang.Boolean.parseBoolean(field.toString)) {
            1
          } else {
            0
          }
          row = row.withValue(java.lang.Integer.valueOf(value))
        case _ => row = row.withValue(null)
      }
      index += 1
    }
    row
  }
}

case class ModelMeta(modelName:String, modelType:String)

case class OutputMeta(fieldName:String, fieldType:String)
