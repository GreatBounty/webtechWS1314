import java.io.*;

import models.City;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import db.DBCity;
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

		DBCity cities = DBCity.get();
		
		if(cities.count() < 2) {
			Logger.info("+++++++++++++++++++++++++");
			writeCities();
		}
		
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
	
	public void writeCities(){
		try {
			Logger.info("---------------------");
			BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\bounty\\Desktop\\WebTech\\orte.txt"));
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				Logger.info("Gelesene Zeile: " + zeile.substring(11));
				DBCity.get().create(new City(zeile.substring(11)));
			}
			in.close();
		} catch (IOException e) {
			Logger.info("ex: " + e);
			e.printStackTrace();
		}
			
	}

}