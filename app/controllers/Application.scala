package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import org.squeryl.PrimitiveTypeMode._

import models._

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
  			Ok( Json.toJson( Map("shortUrl" -> Json.toJson(makeShortUrl(originalUrl))) ) )
  		},
  		invalid = { errors =>
  			BadRequest
  		}
		)
  }

  def makeShortUrl(originalUrl: String): String = {
  	//TODO: calculate checksum of link, check presence of such record in DB, add or update to DB
  	val shortUrl = adler32sum(originalUrl).toHexString
  	transaction {
			AppDB.linkMapTable insert (new LinkMap(originalUrl, shortUrl))
		}
		shortUrl
  }

	def adler32sum(s: String): Int = {
		val MOD_ADLER = 65521

    var a = 1
    var b = 0
    s.getBytes().foreach(char => {
      a = (char + a) % MOD_ADLER
      b = (b + a) % MOD_ADLER
    })
    // note: Int is 32 bits, which this requires
    return b * 65536 + a     // or (b << 16) + a
  }

  val reqShrinkUrl: Reads[String] = (JsPath \ "originalUrl").read[String]
}