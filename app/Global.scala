import play.api.db.DB
import play.api.GlobalSettings
import play.api.Application
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.{Session, SessionFactory}
import play.api.Play.current

object Global extends GlobalSettings {
	override def onStart(app: Application) {
		play.api.Logger.debug("onStart instantinated")
		SessionFactory.concreteFactory = Some(() => 
			Session.create(
				DB.getConnection()(app),
				new MySQLAdapter
			)
		)
	}
}