package controllers;

import db.DBMFG;
import db.DBUser;
import models.LoginTest;
import models.User;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Login extends Controller {

	public static Result showLoginForm() {

		return ok(views.html.loginform.render(""));
		// return ok(views.html.index.render("Hello from Java"));
		// return ok(views.index.scala.html("Hello from Java"));
	}

	public static Result dynamicform() {
		DynamicForm requestData = Form.form().bindFromRequest();
		String eMail = requestData.get("email");
		String password = requestData.get("password");

		if (LoginTest.logintest(eMail, password)) {
			return ok("Email: " + eMail + "Password: " + password);
		} else {
			return badRequest("");
		}

	}

	public static Result login3() {
		// ValidUser ValidUser
		Form<User> form = Form.form(User.class);
		form = form.bindFromRequest();
		
		String fbEmail = form.data().get("fbEmail");
		String fbUsername = form.data().get("fbUsername");
		
		
		if (!(fbEmail.equals("") && fbUsername.equals(""))) {
			User userDB = DBUser.get().validateUser(fbEmail);
			session().clear();
			session("email", fbEmail);
			session("connected", "true");
			return ok(views.html.index.render("Hello "
					+ session("email"), userDB, DBMFG.get()
					.listToDecide(userDB)));
		} else {
			
		if (form.hasErrors()) {
			return badRequest(views.html.loginform.render("war nix. Fehler: "
					+ form.errors()));
		} else {
			User user = form.get();

			

			
				User userDB = DBUser.get().validateUser(user.email,
						user.password);
				if (userDB != null) {
					session().clear();
					session("email", userDB.email);
					session("connected", "true");
					Logger.info("TEEEEEEEEEEEEEST");
					return ok(views.html.index.render("Hello "
							+ session("email"), userDB, DBMFG.get()
							.listToDecide(userDB)));
					// return ok("Deine Eingaben: " +user.email +" "
					// +user.password +" remember: " +user.remember);
				} else {
					// return badRequest("falsche Angaben");
					return badRequest(views.html.loginform
							.render("falsche Angaben"));
				}
			}

		}

	}

}
