package models;

import java.util.ArrayList;
import java.util.Date;

import play.data.validation.Constraints.Required;

public class User extends Entity{
	
	@Required
    public String email;
	@Required
    public String password;
    //public boolean remember;
    public String nickname;
    
    public boolean fahrer;
    
    public Date date;
    
    public ArrayList<MFG> mf_MFG;
    
    public User(){
    	
    }
    
    public User(String email, String password, String nickname, boolean fahrer, Date date){
		mf_MFG = new ArrayList<MFG>();
    	this.email = email;
    	this.password = password;
    	this.nickname = nickname;
    	this.fahrer = fahrer;
    	this.date = date;
    }
}
