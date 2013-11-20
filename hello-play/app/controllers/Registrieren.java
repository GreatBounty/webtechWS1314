package controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;

import models.User;
import models.ValidUser;
import db.UserDB;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Registrieren extends Controller {

	public static Result showRegistrieren() {

		return ok(views.html.registrieren.render(""));
	}

	public static Result doRegistrieren() {

		Map<String, String[]> parameters = request().body().asFormUrlEncoded();

		Form<User> form = Form.form(User.class);

		form = form.bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(views.html.registrieren
					.render("war nix. Fehler: " + form.errors()));
		} else {
			User user = form.get();

			// UserDB.init();
			//
			UserDB users = UserDB.get();
			//TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			Date date = new Date();
			try {
				//  30-07-1987
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Logger.info("date from js " + parameters.get("datepicker")[0]);
				String datefromForm = parameters.get("datepicker")[0];
				//String datefromForm = "2013-05-04";
				date = sdf.parse(datefromForm);
			
				Logger.info(date.toString());
			} catch (Exception e) {
				Logger.info("Fehler beim Date \n"+e);
			}

			if(date == null){
				return badRequest(views.html.registrieren
						.render("Date geht noch nicht...oder war leer!"));
			
			}

			// Date date = sdf.parse(user.date);
			User newUser = users.create(new User(user.email, user.password,
					user.nickname, user.fahrer, date));

			// +" remember: " +user.remember);
			// UserDB userDB = UserDB.get();
			// User userDB = UserDB.get().validateUser(user.email,
			// user.password);
			if (newUser != null) {

				return ok(views.html.loginform.render(""));
			} else {
				// return badRequest("falsche Angaben");
				return badRequest(views.html.registrieren
						.render("E-Mail existiert bereits"));
			}
		}
	}
}
