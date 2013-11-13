package models;

public class LoginTest {
	
	public static boolean logintest(String eMail, String password){
		
		if(eMail.equals("s.w@p.de")&& password.equals("123")){
			return true;
		}
		else{
			return false;
		}
	}
}
