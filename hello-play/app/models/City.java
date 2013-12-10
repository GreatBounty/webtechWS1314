package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import db.Constants;
import play.data.validation.Constraints.*;

public class City extends Entity{
	
	
	public String name;
	
	public City(){
		
	}
	
    public City(String name){
    	this.name = name;
    }

}
