package models;

import org.mongojack.Id;
import org.mongojack.ObjectId;

/**
 * Baseclass for Objects persisted as MongoDB document using mongojack
 * @author mike
 *
 */
public abstract class Entity {
	/**
	 * this is the correct setting for jackson to work for the mapping. 
	 */
	@Id
	@ObjectId
	public String _id;
	
	
	public boolean IsDeleted;
	
	@Override
	public String toString() {
		return "" +getClass().getSimpleName() +" _id:" +_id;
	}

}
