import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import db.DBConnect;
import db.DBUser;


public class Global extends GlobalSettings {

	/**
	 * is executed on application start
	 */
	@Override
	public void onStart(Application app) {
		// ensure db is up and indexes are set
		DBUser.init();
		
		DBUser users = DBUser.get();

		
		Logger.info("User: " + users.count());
		if (users.count() == 0) {
		
//			users.create(new User("sven@weiser.de","12345"));
//			users.create(new User("sven.weiser@bwon.de","12345"));
//			users.create(new User("test@test.de","12345"));
			
		}
	}
	
	@Override
	public void onStop(Application app) {
		DBConnect.dispose();
		super.onStop(app);
	}

}