package db;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

import models.MFG;
import models.Mfg_Status;
import models.User;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import play.Logger;

import com.mongodb.DB;

public class DBMfg_Status extends Finder<Mfg_Status> {
	public DBMfg_Status(DB db) {
		super(db, DBConnect.COLLECTION_MFG_STATUS, Mfg_Status.class);
	}

	/**
	 * collection name for Users
	 */
	// public static final String COLLE = "Users";
	// public static final String COLLECTION_THINGS = "things";
	// private static ThingDB instance = new ThingDB(DBConnect.getDB());

	private static DBMfg_Status instance = new DBMfg_Status(DBConnect.getDB());

	public static void init() {
		Logger.info("Initializing DB. Ensuring indexes.");
		/**
		 * geo index
		 */
		// ).ensureIndex(new BasicDBObject("lonLat", "2dsphere"))
		get().db.getCollection(DBConnect.COLLECTION_MFG_STATUS);
	}

	public static DBMfg_Status get() {
		return instance;
	}

	public Mfg_Status create(Mfg_Status mfg_Status) {
		return save(mfg_Status);
	}

	// hat der user der anfrage (userDB) schon eine Anfrage auf diese mfg
	// (mfgId)
	// liste der mfg_status von der mfg durchgehen und schauen ob userDB._id
	// == _mfId
	public boolean hasUserRequest(User userDB, String mfgId) {
		if (DBMFG.get().listOfMfgStatus(mfgId) != null) {
			for (String id : DBMFG.get().listOfMfgStatus(mfgId)) {
				Mfg_Status tt = get().findById(id);
				if (tt != null) {
					if (tt._mfId.equals(userDB._id)) {
						return true;
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public DBCursor<Mfg_Status> list() {
		DBCursor<Mfg_Status> mfg = getColl().find();

		return mfg;
	}
	

}