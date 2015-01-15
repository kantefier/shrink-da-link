package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{KeyedEntity, Schema}
import org.squeryl.annotations.Column

/**
 * Defining the Schema
 */
object AppDB extends Schema {
	val linkMapTable = table[LinkMap]("linkmap")

	on(linkMapTable) { lt => declare {
		lt.id is autoIncremented
	}}
}

/**
 * Holds the data about original URL and shortened one
 */
case class LinkMap(id: Long,
									@Column("originallink")
							originalURL: String,
									@Column("shortlink")
							shortURL: String) extends KeyedEntity[Long]