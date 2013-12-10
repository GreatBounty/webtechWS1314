package db;

import java.util.ArrayList;

import models.City;
import models.Mfg_Status;
import models.User;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import play.Logger;

import com.mongodb.DB;

public class DBCity extends Finder<City> {
	public DBCity(DB db) {
		super(db, DBConnect.COLLECTION_CITY, City.class);
	}

	/**
	 * collection name for Users
	 */
	// public static final String COLLE = "Users";
	// public static final String COLLECTION_THINGS = "things";
	// private static ThingDB instance = new ThingDB(DBConnect.getDB());

	private static DBCity instance = new DBCity(DBConnect.getDB());

	public static void init() {
		Logger.info("Initializing DB. Ensuring indexes.");
		/**
		 * geo index
		 */
		// ).ensureIndex(new BasicDBObject("lonLat", "2dsphere"))
		get().db.getCollection(DBConnect.COLLECTION_CITY);
	}

	public static DBCity get() {
		return instance;
	}

	public City create(City city) {
		return save(city);
	}

	public ArrayList<City> findByName(String filter) {
		DBCursor<City> city = getColl().find();
		ArrayList<City> filterCities = new ArrayList<>();
		
		for (City ci : city){
			//Logger.info("ci: " + ci.name + "\tfilter: " + filter);
			if(ci.name.toLowerCase().startsWith(filter.toLowerCase())){
				Logger.info("gefiltert: " + ci.name);
				filterCities.add(ci);
			}
		}
		
		return filterCities;
	}

	public DBCursor<City> list() {
		DBCursor<City> city = getColl().find();

		return city;
	}

	
	

}