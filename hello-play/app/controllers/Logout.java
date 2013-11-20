package controllers;

import db.UserDB;
import models.LoginTest;
import models.User;
import models.ValidUser;
import play.api.mvc.Session;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Logout extends Controller {

	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Login.showLoginForm());
	}
}
