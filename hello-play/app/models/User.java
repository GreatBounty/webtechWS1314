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
    
    public User(){
    	
    }
    
    public User(String email, String password, String nickname, boolean fahrer, Date date){
    	this.email = email;
    	this.password = password;
    	this.nickname = nickname;
    	this.fahrer = fahrer;
    	this.date = date;
    }
}
