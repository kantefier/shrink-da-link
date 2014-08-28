package models

import org.squeryl.{Schema, KeyedEntity}

class LinkMap(originalLink: String, shortLink: String) extends KeyedEntity[Long] {
	val id: Long = 0
}

object AppDB extends Schema {
	val linkMapTable = table[LinkMap]("linkmap")
}