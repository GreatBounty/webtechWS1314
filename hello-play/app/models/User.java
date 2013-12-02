package models;


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
    
    //public ArrayList<String> mf_MFG;
//    public HashMap<String, String> mf_MFG_id;
    
    public User(){
    	
    }
    
    public User(String email, String password, String nickname, boolean fahrer, Date date){
//		this.mf_MFG_id = new HashMap<String,String>();
    	this.IsDeleted = false;
    	this.email = email;
    	this.password = password;
    	this.nickname = nickname;
    	this.fahrer = fahrer;
    	this.date = date;
    }
}
