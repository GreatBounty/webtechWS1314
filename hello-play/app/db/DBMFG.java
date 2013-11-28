package db;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

import models.MFG;
import models.User;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import play.Logger;

import com.mongodb.DB;


public class DBMFG extends Finder<MFG>{
	public DBMFG(DB db) {
		super(db, DBConnect.COLLECTION_MFG, MFG.class);
	}
	/**
	 * collection name for Users
	 */
	//public static final String COLLE = "Users";
	//public static final String COLLECTION_THINGS = "things";
	//private static ThingDB instance = new ThingDB(DBConnect.getDB());
	
	private static DBMFG instance = new DBMFG(DBConnect.getDB());
	
	
	public static void init() {
		Logger.info("Initializing DB. Ensuring indexes.");
		/**
		 * geo index
		 */
		//).ensureIndex(new BasicDBObject("lonLat", "2dsphere"))
		get().db.getCollection(DBConnect.COLLECTION_MFG);
	}
	public static DBMFG get(){
		return instance;
	}
	
	public MFG create(MFG mfg){
		return save(mfg);
	}
	public DBCursor<MFG> list(User user){
		//user._id
		DBCursor<MFG> mfg = getColl().find().is("_userId", user._id);
		for (MFG mfgs : mfg){
			Logger.info("mfg found: " + mfgs._id + "\tfrom user: " + DBUser.get().findById(user._id));
		}
		return mfg;
	}
	
	public DBCursor<MFG> list(){
		DBCursor<MFG> mfg = getColl().find();
		return mfg;
	}
}