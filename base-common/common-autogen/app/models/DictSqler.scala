package models

import javax.inject.Inject

import auto.Dict.InnerDictSqler
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext

class DictSqler @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends InnerDictSqler {

}