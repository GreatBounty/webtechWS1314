package db;

import java.util.ArrayList;
import java.util.regex.Pattern;

import models.City;
import models.Mfg_Status;
import models.User;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import play.Logger;

import com.mongodb.BasicDBObject;
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

	/*
	 * public ArrayList<User> findByName(String filter) { //DBCursor<User> users
	 * = getColl().find(); ArrayList<User> filterUsers = new ArrayList<>(); int
	 * maxRows = 10; int i = 0; Pattern pFilter = Pattern.compile("^"+filter,
	 * Pattern.CASE_INSENSITIVE); BasicDBObject query = new
	 * BasicDBObject("email", pFilter); DBCursor<User> Users =
	 * getColl().find(query);
	 * 
	 * for (User user : Users){ if(i > maxRows){ return filterUsers; }
	 * //Logger.info("ci: " + ci.name + "\tfilter: " + filter);
	 * 
	 * 
	 * Logger.info("gefiltert: " + user.email); filterUsers.add(user); i++; // }
	 * }
	 * 
	 * return filterUsers; }
	 */

	public ArrayList<City> findByName(String filter) {
		ArrayList<City> filterCities = new ArrayList<>();
		int maxRows = 10;
		int i = 0;
		Pattern pFilter = Pattern.compile("^" + filter,
				Pattern.CASE_INSENSITIVE);
		BasicDBObject query = new BasicDBObject("name", pFilter);
		DBCursor<City> city = getColl().find(query);
		// if(city.name.toLowerCase().startsWith(filter.toLowerCase())){
		for (City ci : city) {
			if (i > maxRows) {
				return filterCities;
			}
			Logger.info("gefiltert: " + ci.name);
			filterCities.add(ci);
			i++;
		}
		return filterCities;
	}

	public DBCursor<City> list() {
		DBCursor<City> city = getColl().find();

		return city;
	}

}