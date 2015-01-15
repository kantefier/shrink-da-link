package controllers

import models.{LinkMap, AppDB}
import org.squeryl.PrimitiveTypeMode._

object ActionHelpers {

  def adler32sum(s: String): Int = {
    val MOD_ADLER = 65521
    var a = 1
    var b = 0
    s.getBytes.foreach(char => {
      a = (char + a) % MOD_ADLER
      b = (b + a) % MOD_ADLER
    })
    b * 65536 + a
  }

  /**
   * Returns short URL hash
   */
  def makeShortUrl(originalUrl: String): String = transaction {
    from(AppDB.linkMapTable) { item =>
      where(item.originalURL === originalUrl) select item
    }.headOption match {
      case Some(mappingFound) =>
        mappingFound.shortURL
      case None               =>
        val shortUrl = adler32sum(originalUrl).toHexString
        AppDB.linkMapTable insert LinkMap(0, originalUrl, shortUrl)
        shortUrl
    }
  }
}
