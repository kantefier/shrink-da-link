package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import ActionHelpers._

object Application extends Controller {

    def index = Action {
        Ok(views.html.myindex())
    }

    /**
     * Receives a valid JSON with Original URL
     * Returns JSON with Shortened URL
     */
    def getShortUrl = Action(parse.json) { request =>
        val reqJson = reqShrinkUrl.reads(request.body)
        reqJson.fold(
            valid = { originalUrl =>
                Logger.debug("request json valid")
                Ok(
                    Json.toJson(
                        Map("shortUrl" ->
                          Json.toJson(s"http://${request.host}/${(makeShortUrl _ compose mkPrefixedUrl).apply(originalUrl)}")
                        )
                    )
                )
            },
            invalid = { errors =>
                BadRequest
            }
        )
    }

    /**
     * Redirects from Shortened URL to original page
     */
    def resolveShortUrl(shortUrl: String) = Action {
        retrieveOriginalUrl(shortUrl) match {
            case Some(originalUrl) => Redirect(originalUrl)
            case None => NotFound
        }
    }

    /**
     * Reads section
     */
    val reqShrinkUrl: Reads[String] = (JsPath \ "originalUrl").read[String]
}