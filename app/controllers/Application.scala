package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import java.security.MessageDigest

object Application extends Controller {

  def index = Action {
    Ok(views.html.myindex())
  }

  def getShortUrl = Action(parse.json) { request =>
  	val reqJson = reqShrinkUrl.reads(request.body)
  	reqJson.fold(
  		valid = { originalUrl =>
  			Logger.debug("request json valid")
  			//do something, take hash and so on
  			//obtain result and send it back
  			Ok( Json.toJson( Map("shortUrl" -> Json.toJson(md5(originalUrl).toString)) ) )
  		},
  		invalid = { errors =>
  			BadRequest
  		}
		)
  }

  def md5(s: String) = {
    MessageDigest.getInstance("MD5").digest(s.getBytes)
	}

  val reqShrinkUrl: Reads[String] = (JsPath \ "originalUrl").read[String]
}