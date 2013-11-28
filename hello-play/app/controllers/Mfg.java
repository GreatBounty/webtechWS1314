package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import db.DBMFG;
import db.DBUser;
import play.Logger;
import play.api.mvc.Session;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import models.*;

public class Mfg extends Controller {

	@Security.Authenticated(Secured.class)
	public static Result show() {

		return ok(views.html.mfgAnbieten.render("MFG Anbieten", DBUser.get()
				.findByEmail(session("email"))));
		// return ok(views.html.index.render("Hello from Java"));
		// return ok(views.index.scala.html("Hello from Java"));
	}

	@Security.Authenticated(Secured.class)
	public static Result list() {
		
		User userDB = DBUser.get().findByEmail(session("email"));
		if(userDB.fahrer){
		return ok(views.html.mfgAnzeigen.render("", userDB,
				DBMFG.get().list(userDB)));
		}else{
			return ok(views.html.mfgAnzeigenAlle.render("", userDB,
					DBMFG.get().list()));
		}
		// return ok(views.html.index.render("Hello from Java"));
		// return ok(views.index.scala.html("Hello from Java"));
	}

	@Security.Authenticated(Secured.class)
	public static Result edit(String id) {

		MFG mfg = DBMFG.get().findById(id);
		User userDB = DBUser.get().findByEmail(session("email"));

		if (null == mfg) {
			flash("message", "MFG nicht gefunden");
			return ok(views.html.mfgAnzeigen.render("", userDB, DBMFG.get()
					.list(userDB)));
		} else {
			// Form<MFG> form = fillForm(mfg);
			Logger.info("Bevor format: " + mfg.date);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			String date = sdf.format(mfg.date);
			Logger.info("nach format: " + date);
			return ok(views.html.mfgEdit
					.render("Bearbeiten", userDB, mfg, date));
		}

	}

	private static Form<MFG> fillForm(MFG mfg) {
		Form<MFG> mfgForm = Form.form(MFG.class);
		mfgForm = mfgForm.fill(mfg);

		return mfgForm;
	}

	@Security.Authenticated(Secured.class)
	public static Result showNew() {

		User userDB = DBUser.get().findByEmail(session("email"));
		Form<MFG> form = Form.form(MFG.class);
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		form = form.bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(views.html.mfgAnbieten.render("war nix. Fehler: "
					+ form.errors(), userDB));
		} else {
			MFG mfg = form.get();

			Date date = new Date();

			try {

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
				Logger.info("date from js " + parameters.get("datepicker")[0]);
				String datefromForm = parameters.get("datepicker")[0];
				date = sdf.parse(datefromForm);

				Logger.info(date.toString());
			} catch (Exception e) {
				Logger.info("Fehler beim Date \n" + e);
			}

			if (date == null) {
				return badRequest(views.html.mfgAnbieten.render(
						"Date geht noch nicht...oder war leer!", userDB));

			}
			String FK_idUser = DBUser.get().getIdFromUser(userDB);

			MFG newMFG = null;
			if (FK_idUser != null) {
				newMFG = DBMFG.get().create(
						new MFG(mfg.start, mfg.ziel, mfg.strecke, mfg.seats,
								date, FK_idUser));
			}

			Logger.info("newMFG: " + newMFG.toString());

			if (newMFG != null) {
				Logger.info("hier sollte man sein !!!");
				return ok(views.html.mfgAnzeigen.render("", userDB, DBMFG.get()
						.list(userDB)));
			} else {
				// return badRequest("falsche Angaben");
				return badRequest(views.html.mfgAnbieten.render(
						"Fehler beim Erzeugen von der MFG", userDB));
			}
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result create() {

		Form<MFG> form = Form.form(MFG.class).bindFromRequest();

		// flash("message", "Neues Thing '" + mfg.name + "' wurde angelegt");
		return ok(views.html.mfgAnbieten.render("Neues Thing anlegen", DBUser
				.get().findByEmail(session("email"))));

	}
	@Security.Authenticated(Secured.class)
	public static Result anfragen(String mfgId) {
		MFG mfg = DBMFG.get().findById(mfgId);
		mfg.seats = mfg.seats - 1;
		User userDB = DBUser.get().findByEmail(session("email"));

		// save thing
		
		if(userDB.mf_MFG == null){
			userDB.mf_MFG = new ArrayList<MFG>();
		}
		userDB.mf_MFG.add(mfg);

		DBMFG.get().save(mfg);
		DBUser.get().save(userDB);
		flash("message", "Gespeichert");
		return ok(views.html.mfgAnzeigenAlle.render("", userDB, DBMFG.get()
				.list()));
	

	}
	

	public static Result save(String id) {
		// search for thing
		User userDB = DBUser.get().findByEmail(session("email"));

		MFG saved = DBMFG.get().findById(id);
		if (saved == DBMFG.get().findById(id)) {
			flash("message", "Nicht gefunden");
			return ok(views.html.mfgAnzeigen.render("", userDB, DBMFG.get()
					.list(userDB)));
			// return redirect(routes.MFG.list());
		}

		// validate form
		Form<MFG> mfg = Form.form(MFG.class).bindFromRequest();
		if (mfg.hasErrors()) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			String date = sdf.format(saved.date);
			return badRequest(views.html.mfgEdit.render("Fehler in der Form",
					userDB, saved, date));
		} else {
			// save thing
			MFG update = mfg.get();
			Date date = parseDateFromDatepicker();
			saved.date = date;
			saved.start = update.start;
			saved.ziel = update.ziel;
			saved.seats = update.seats;

			DBMFG.get().save(saved);
			flash("message", "Gespeichert");
			return ok(views.html.mfgAnzeigen.render("", userDB, DBMFG.get()
					.list(userDB)));
		}
	}

	private static Date parseDateFromDatepicker() {
		try {
			Date date = new Date();
			Map<String, String[]> parameters = request().body()
					.asFormUrlEncoded();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
			Logger.info("date from js " + parameters.get("datepicker")[0]);
			String datefromForm = parameters.get("datepicker")[0];
			date = sdf.parse(datefromForm);
			return date;
		} catch (Exception e) {
			return null;
		}
	}
}
