package controllers

import javax.inject.{Inject, Singleton}

import io.swagger.annotations._
import models.DictSqler
import play.api.libs.ws._
import play.api.mvc.ControllerComponents
import play.api.{Configuration, Environment}
import auto.Dict._
import io.swagger.annotations._
import scala.concurrent.ExecutionContext


@Api("dict")
@Singleton
class DictController @Inject()(dictSpler: DictSqler,
                                ws: WSClient, environment: Environment, apiAction: ApiAction,
                                config: Configuration, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends InnerDictController(dictSpler, ws, environment, apiAction, config, cc)(executionContext) {


}
