package models;

import java.util.Date;

import org.mongojack.Id;
import org.mongojack.ObjectId;

import play.data.validation.Constraints.*;

public class MFG extends Entity{
	
	
	public String _userId;
	@Required
    public String start;
	@Required
    public String ziel;
    //public boolean remember;
	@Required
    public String strecke;
	
    public Date date;
	@Required
	@Min(value = 1)
	@Max(value = 9)
    public int seats;
	
	

	
	public MFG(){
		
	}
	
    public MFG(String start, String ziel, String strecke, int seats, Date date, String userId){
    	this._userId = userId;
    	this.start = start;
    	this.ziel = ziel;
    	this.strecke = strecke;
    	this.seats = seats;
    	this.date = date;
    }

}
