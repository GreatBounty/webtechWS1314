package db;

import models.MFG;
import models.User;

import org.mongojack.DBCursor;

import play.Logger;

import com.mongodb.DB;


public class DBUser extends Finder<User>{
	public DBUser(DB db) {
		super(db, DBConnect.COLLECTION_USERS, User.class);
	}
	/**
	 * collection name for Users
	 */
	//public static final String COLLE = "Users";
	public static final String COLLECTION_THINGS = "things";
	//private static ThingDB instance = new ThingDB(DBConnect.getDB());
	
	private static DBUser instance = new DBUser(DBConnect.getDB());
	
	
	public static void init() {
		Logger.info("Initializing DB. Ensuring indexes.");
		/**
		 * geo index
		 */
		//).ensureIndex(new BasicDBObject("lonLat", "2dsphere"))
		get().db.getCollection(DBConnect.COLLECTION_USERS);
	}
	public static DBUser get(){
		return instance;
	}
	
	
	
//	public static UserDB get(){
//		if(instance == null){
//			instance = new UserDB(DBConnect.getDB());
//		}
//		return instance;
//	}
	
	public User create(User User){
		if(!contains(User)){
			return save(User);
		}else{
			return null;
		}
	}
	public User addMFG(User user, MFG mfg){
		if(!contains(user)){
			user.mf_MFG.add(mfg);
			return save(user);
		}else{
			return null;
		}
	}
	
	/**
	 * @param skip offset 
	 * @param maxNum maximum number to return
	 */
	public DBCursor<User> list(int skip, int maxNum){
		DBCursor<User> Users = getColl().find().skip(skip).limit(maxNum);
		return Users;
	}
	
	public boolean contains(User user){
		DBCursor<User> Users = getColl().find();
		
		for(User userDB : Users){
			if(user.email.equals(userDB.email)){
				return true;
			}
		}
		return false;
	}
	
	public User findByEmail(String email){
		DBCursor<User> Users = getColl().find();
		
		for(User userDB : Users){
			if(email.equals(userDB.email)){
				return userDB;
			}
		}
		return null;
	}
	
	public String getIdFromUser(User user){
		DBCursor<User> Users = getColl().find();
		
		for(User userDB : Users){
			if(user.email.equals(userDB.email)){
				return userDB._id;
			}
		}
		return null;
	}
	
	public DBCursor<User> list(){
		DBCursor<User> Users = getColl().find();
		return Users;
	}
	
	
	public User validateUser(String email, String password){
		
//		UserDB users = UserDB.get();
//		for(User u : users.collectionName("")){
//			
//		}
		DBCursor<User> Users = getColl().find();
		for(User user : Users){
			if(user.email.equals(email) && user.password.equals(password)){
				return user;
			}
		}
		return null;
	}
	
}





