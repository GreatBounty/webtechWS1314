package controllers;

import models.LoginTest;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class Login extends Controller {

	public static Result showLoginForm(){
		
		return redirect ("/assets/html/loginform.html");
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
	
}
