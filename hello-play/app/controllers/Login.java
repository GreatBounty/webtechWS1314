package controllers;

import db.DBUser;
import models.LoginTest;
import models.User;
import models.ValidUser;
import play.api.mvc.Session;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class Login extends Controller {

	public static Result showLoginForm(){
		
		return ok(views.html.loginform.render(""));
		//return ok(views.html.index.render("Hello from Java"));
        //return ok(views.index.scala.html("Hello from Java"));
	} 
	
	public static Result dynamicform(){
		DynamicForm requestData = Form.form().bindFromRequest();
		String eMail = requestData.get("email");
		String password = requestData.get("password");
		
		if(LoginTest.logintest(eMail, password)){
			return ok("Email: " + eMail + "Password: " + password);
		}
		else{
			return badRequest("fuck");
		}
	}
	
	public static Result login3(){
		//ValidUser			ValidUser
		Form<User> form = Form.form(User.class);
		
		form = form.bindFromRequest();
		
		if (form.hasErrors()) {
		    return badRequest(views.html.loginform.render("war nix. Fehler: " +form.errors()));
		} else {
		    User user = form.get();
		    
//		    UserDB.init();
//			
//			UserDB users = UserDB.get();
//			
//		    users.create(new User(user.email,user.password));
//		    return ok("Deine Eingaben: " +user.email +" " +user.password +" remember: " +user.remember);
//		    UserDB userDB = UserDB.get();
		    User userDB = DBUser.get().validateUser(user.email, user.password);
		    if(userDB != null){
		    	session().clear();
		    	session("email", userDB.email);
		    	session("connected", "true");
		    	return ok(views.html.index.render("Hello " + session("email"),userDB));
		    	//return ok("Deine Eingaben: " +user.email +" " +user.password +" remember: " +user.remember);
		    }else{
		    	//return badRequest("falsche Angaben");
		    	return badRequest(views.html.loginform.render("falsche Angaben"));
		    }
			
		}
		
	}
	
}
