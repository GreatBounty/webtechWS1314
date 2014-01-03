package db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import models.City;
import models.MFG;
import models.Mfg_Status;
import models.User;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;

import play.Logger;

import com.mongodb.DB;

public class DBMFG extends Finder<MFG> {
	public DBMFG(DB db) {
		super(db, DBConnect.COLLECTION_MFG, MFG.class);
	}

	/**
	 * collection name for Users
	 */
	// public static final String COLLE = "Users";
	// public static final String COLLECTION_THINGS = "things";
	// private static ThingDB instance = new ThingDB(DBConnect.getDB());

	private static DBMFG instance = new DBMFG(DBConnect.getDB());

	public static void init() {
		Logger.info("Initializing DB. Ensuring indexes.");
		/**
		 * geo index
		 */
		// ).ensureIndex(new BasicDBObject("lonLat", "2dsphere"))
		get().db.getCollection(DBConnect.COLLECTION_MFG);
	}

	public static DBMFG get() {
		return instance;
	}

	public MFG create(MFG mfg) {
		return save(mfg);
	}

	public DBCursor<MFG> list(User user) {
		// user._id
		deleteExpired();
		DBCursor<MFG> mfg = getColl().find(DBQuery.is("_userId", user._id).is("IsDeleted", false));
		for (MFG mfgs : mfg) {
			Logger.info("mfg found: " + mfgs._id + "\tfrom user: "
					+ DBUser.get().findById(user._id));
		}
		return mfg;
	}

	public DBCursor<MFG> list() {
		deleteExpired();
		DBCursor<MFG> mfg = getColl().find(DBQuery.is("IsDeleted", false).greaterThan("seats", 0));		
		//.is("IsDeleted", false);
		return mfg;
	}
	
	public  ArrayList<MFG> list(String filter) {
		DBCursor<MFG> mfg = list();
		ArrayList<MFG> mfgReturn = new ArrayList<>();
		
		
		for(MFG mfgObj : mfg){
			if(mfgObj.start.toLowerCase().startsWith(filter.toLowerCase()) || mfgObj.ziel.toLowerCase().startsWith(filter.toLowerCase())){
				mfgReturn.add(mfgObj);
			}
		}
		

		return mfgReturn;
	}
	

	private void deleteExpired() {
		DBCursor<MFG> mfg = getColl().find();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

		Date dateNow = new Date();
		try {
			dateNow = sdf.parse(sdf.format(dateNow));
			
			for (MFG mfgObj : mfg) {
				Logger.info("mfgobj: " + mfgObj.date.toString());
				

				if( (dateNow.getTime() + (30*60*1000))-mfgObj.date.getTime() > 0){
					Logger.info("deleted = true");
					mfgObj.IsDeleted = true;
					DBMFG.get().save(mfgObj);
				}

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> listOfMfgStatus(String id) {
		MFG mfg = get().findById(id);
		return mfg.mfg_Status_Id;
	}

	public MFG getMfgFromMfgStatus(String mfgStatusId) {
		DBCursor<MFG> mfg = getColl().find();
		for (MFG mfgObj : mfg) {
			if (mfgObj.mfg_Status_Id != null) {
				for (String statusId : mfgObj.mfg_Status_Id) {
					if (statusId.equals(statusId)) {
						return mfgObj;
					}
				}
			}
		}
		return null;
	}

	public ArrayList<MFG> listToDecide(User userDB) {
		// DBCursor<MFG> mfg = getColl().find();
		ArrayList<MFG> listOfMfgs = new ArrayList<MFG>();
		ArrayList<String> ids = null;

		Logger.info("hier wird aussortiert" + DBMFG.get().list(userDB).size());

		for (MFG x : (ArrayList<MFG>) DBMFG.get().list(userDB).toArray()) {
			listOfMfgs.add(x);
		}

		// listOfMfgs = (ArrayList<MFG>) DBMFG.get().list(userDB).toArray();
		for (MFG mfgobj : listOfMfgs) {
			if (mfgobj.mfg_Status_Id != null) {
				ids = new ArrayList<String>();
				for (String idOfMfg : mfgobj.mfg_Status_Id) {
					for (Mfg_Status status : DBMfg_Status.get().list()) {
						if (status._id.equals(idOfMfg)) {
							if (status.status.equals(Constants.STATUS_ANFRAGE)) {
								ids.add(status._id);
							}
						}
					}
				}
				mfgobj.mfg_Status_Id.clear();
				mfgobj.mfg_Status_Id = ids;
			}
		}

		removeMfgWithoutStatus(listOfMfgs);

		return listOfMfgs;
	}

	private void removeMfgWithoutStatus(ArrayList<MFG> listOfMfgs) {
		Logger.info("...??..." + listOfMfgs.size());
		ArrayList<MFG> listToRemove = new ArrayList<MFG>();
		if (listOfMfgs != null) {
			for (MFG mfgObj : listOfMfgs) {
				if(mfgObj.mfg_Status_Id != null){
					if (mfgObj.mfg_Status_Id.size() == 0
							|| mfgObj.mfg_Status_Id == null) {
						listToRemove.add(mfgObj);
						// listOfMfgs.remove(mfgObj);
					}
				}
				
			}
		}
		for (MFG remove : listToRemove) {
			listOfMfgs.remove(remove);
		}
	}

	public ArrayList<double[]> getMapCoor(String id) {
		return (ArrayList<double[]>)get().findById(id).lonLat;
	}

	
	
	

}