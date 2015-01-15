package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import ActionHelpers._

object Application extends Controller {

  def index = Action {
    Ok(views.html.myindex())
  }

  def getShortUrl = Action(parse.json) { request =>
  	val reqJson = reqShrinkUrl.reads(request.body)
  	reqJson.fold(
  		valid = { originalUrl =>
  			Logger.debug("request json valid")
  			Ok( Json.toJson( Map("shortUrl" -> Json.toJson(makeShortUrl(originalUrl))) ) )
  		},
  		invalid = { errors =>
  			BadRequest
  		}
		)
  }

	/**
	 * Reads section
	 */
  val reqShrinkUrl: Reads[String] = (JsPath \ "originalUrl").read[String]
}