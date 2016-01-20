package controllers

import models.{LinkMap, AppDB}
import org.squeryl.PrimitiveTypeMode._
import scala.annotation.tailrec

/**
 * Controller's backstage
 */
object ActionHelpers {

    /**
     * Adler32 Checksum
     * Thanks to Alvin Alexander
     * http://alvinalexander.com/scala/scala-adler-32-checksum-algorithm
     */
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
     *  Tail-recursive func that generates short Urls until they're distinct
     */
    @tailrec
    def generateCheckedShortUrl(originalUrl: String): String = {
        val shortUrl = (adler32sum(originalUrl) + System.nanoTime).toHexString
        from(AppDB.linkMapTable) {item =>
            where(item.shortURL === shortUrl) select item
        }.headOption match {
            case Some(found) => generateCheckedShortUrl(originalUrl)
            case None => shortUrl
        }
    }

    /**
     * Returns hash of original URL
     * If not found in DB, inserts new row
     */
    def makeShortUrl(originalUrl: String): String = transaction {
        from(AppDB.linkMapTable) { item =>
            where(item.originalURL === originalUrl) select item
        }.headOption match {
            case Some(mappingFound) =>
                mappingFound.shortURL
            case None =>
                val shortUrl = generateCheckedShortUrl(originalUrl)
                AppDB.linkMapTable insert LinkMap(0, originalUrl, shortUrl)
                shortUrl
        }
    }

    /**
     * Maps shortened URL back to original, if such is present in DB
     */
    def retrieveOriginalUrl(shortUrl: String): Option[String] = transaction {
        from(AppDB.linkMapTable) { item =>
            where(item.shortURL === shortUrl) select item
        }.headOption match {
            case Some(mappingFound) => Some(mappingFound.originalURL)
            case None => None
        }
    }

    /**
     * Prefixes URL with "http", if it is not already
     */
    def mkPrefixedUrl(inputUrl: String): String = {
        if (!inputUrl.startsWith("http"))
            "http://" + inputUrl
        else
            inputUrl
    }
}