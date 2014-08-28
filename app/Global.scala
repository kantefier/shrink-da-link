import play.api.db.DB
import play.api.GlobalSettings
import play.api.Application
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.{Session, SessionFactory}
import play.api.Play.current

object Global extends GlobalSettings {
	override def onStart(app: Application) {
		val driverString = app.configuration.getString("db.default.driver").getOrElse(throw new Exception("Didn't find driver in the configuration"))

		SessionFactory.concreteFactory = Some(() => 
			Session.create(
				DB.getConnection("default", true),
				new MySQLAdapter
			)
		)
	}
}