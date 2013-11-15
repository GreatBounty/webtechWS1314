package models;


import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class DBtest {

	public DBtest() {
		
		try {
			MongoClient mC = new MongoClient();
			DB db = mC.getDB("mydb");

			DBCollection coll = db.getCollection("test");
			
			BasicDBObject doc = new BasicDBObject("name", "MongoDB")
				.append("type", "database")
				.append("count",1)
				.append("info", new BasicDBObject("x",203).append("y", 102));
						
			coll.insert(doc);
			
			BasicDBObject query = new BasicDBObject("i",71);
			DBCursor cursor = coll.find(query);
			
			try{
				while(cursor.hasNext()){
					System.out.println(cursor.next());
				}
			}catch(Exception e){}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
