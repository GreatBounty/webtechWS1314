package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import db.Constants;
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
	
	public String status;
	
	//public HashMap<String, String > mfg_Status;
	
	//public ArrayList<Mfg_Status> mfg_Status;
	
	public ArrayList<String> mfg_Status_Id;
	
	public MFG(){
		
	}
	
    public MFG(String start, String ziel, String strecke, int seats, Date date, String userId){
    	this.mfg_Status_Id = null;
    	this.IsDeleted = false;
    	this._userId = userId;
    	this.start = start;
    	this.ziel = ziel;
    	this.strecke = strecke;
    	this.seats = seats;
    	this.date = date;
    }

}
