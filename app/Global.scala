import play.api.db.DB
import play.api.GlobalSettings
import play.api.Application
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.{Session, SessionFactory}

object Global extends GlobalSettings {
	override def onStart(app: Application) {
		SessionFactory.concreteFactory = Some(() => 
			Session.create(
				java.sql.DriverManager.getConnection("..."),
				new MySQLAdapter
			)
		)
	}
}