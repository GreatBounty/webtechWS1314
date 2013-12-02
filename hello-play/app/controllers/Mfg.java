package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import db.Constants;
import db.DBMFG;
import db.DBMfg_Status;
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

//		for(MFG x : DBMFG.get().list()){
//			x.IsDeleted = false;
//			DBMFG.get().save(x);
//		}
		
		User userDB = DBUser.get().findByEmail(session("email"));
		if (userDB.fahrer) {
			return ok(views.html.mfgAnzeigen.render("", userDB, DBMFG.get()
					.list(userDB)));
		} else {

			return ok(views.html.mfgAnzeigenAlle.render("", userDB, DBMFG.get()
					.list(),DBMfg_Status.get().list().toArray()));
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

			Date date = parseDateFromDatepicker(true);
			String FK_idUser = DBUser.get().getIdFromUser(userDB);

			MFG newMFG = null;
			if (FK_idUser != null && date != null) {
				newMFG = DBMFG.get().create(
						new MFG(mfg.start, mfg.ziel, mfg.strecke, mfg.seats,
								date, FK_idUser));
			}

			if (newMFG != null) {
				Logger.info("newMFG: " + newMFG.toString());
				Logger.info("hier sollte man sein !!!");
				return ok(views.html.mfgAnzeigen.render("", userDB, DBMFG.get()
						.list(userDB)));
			} else {
				// return badRequest("falsche Angaben");
				if (date == null) {
					return badRequest(views.html.mfgAnbieten.render(
							"Datum darf nicht in der Vergangenheit liegen",
							userDB));
				}
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

		String mfg_status_id = null;
		// mfg.seats = mfg.seats - 1;

		User userDB = DBUser.get().findByEmail(session("email"));


		// hat der user der anfrage (userDB) schon eine Anfrage auf diese mfg
		// (mfgId)
		// liste der mfg_status von der mfg durchgehen und schauen ob userDB._id
		// == _mfId
		//bei Ablehnen die mfg_status löschen
		if (!DBMfg_Status.get().hasUserRequest(userDB, mfgId)) {
			Mfg_Status mfg_status = new Mfg_Status(userDB._id,Constants.STATUS_ANFRAGE);
			mfg_status_id = DBMfg_Status.get().create(mfg_status)._id;
		}
		if (mfg_status_id != null) {
			if(mfg.mfg_Status_Id == null){
				mfg.mfg_Status_Id = new ArrayList<String>();
			}
			mfg.mfg_Status_Id.add(mfg_status_id);
		}else{
			return ok(views.html.mfgAnzeigenAlle.render("Anfrage läuft bereits", userDB, DBMFG.get()
					.list(), DBMfg_Status.get().list().toArray()));
		}

		DBMFG.get().save(mfg);
		DBUser.get().save(userDB);
		flash("message", "Gespeichert");
		return ok(views.html.mfgAnzeigenAlle.render("", userDB, DBMFG.get()
				.list(), DBMfg_Status.get().list().toArray()));

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
		Date date = parseDateFromDatepicker(true);
		if (mfg.hasErrors() || date == null) {
			if (date == null) {
				return badRequest(views.html.mfgEdit.render(
						"Datum darf nicht in der Vergangenheit liegen", userDB,
						saved, ""));
			}

			return badRequest(views.html.mfgEdit.render("Fehler in der Form",
					userDB, saved, ""));
		} else {
			// save thing
			MFG update = mfg.get();

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

	private static Date parseDateFromDatepicker(boolean inFuture) {
		try {
			Date date = new Date();
			Map<String, String[]> parameters = request().body()
					.asFormUrlEncoded();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
			Logger.info("date from js " + parameters.get("datepicker")[0]);
			String datefromForm = parameters.get("datepicker")[0];
			date = sdf.parse(datefromForm);
			if (inFuture) {
				Date dateNow = new Date();
				dateNow = sdf.parse(sdf.format(dateNow));
				Logger.info("date aus form: " + date);
				Logger.info("date von heute: " + dateNow);
				if (date.compareTo(dateNow) <= 0) {
					return null;
				} else {
					return date;
				}
			}

			return date;
		} catch (Exception e) {
			return null;
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result accept(String mfg_statusId) {
		User userDB = DBUser.get().findByEmail(session("email"));
		Mfg_Status status = DBMfg_Status.get().findById(mfg_statusId);
		
		//über mfg_statusId die mfg finden und seats--
		MFG mfg = DBMFG.get().getMfgFromMfgStatus(mfg_statusId);
		mfg.seats--;
		
		if(status != null){
			status.status = Constants.STATUS_ANGENOMMEN;
			DBMfg_Status.get().save(status);
			DBMFG.get().save(mfg);
		}
		return ok(views.html.index.render("Hello " + session("email"), userDB,
				DBMFG.get().listToDecide(userDB)));

	}

	@Security.Authenticated(Secured.class)
	public static Result deny(String mfg_statusId) {
		
		//bei Ablehnen die mfg_status löschen
		User userDB = DBUser.get().findByEmail(session("email"));
		Mfg_Status status = DBMfg_Status.get().findById(mfg_statusId);
		
		//über mfg_statusId die mfg finden und seats--
		MFG mfg = DBMFG.get().getMfgFromMfgStatus(mfg_statusId);
		
		
		if(status != null){
			status.status = Constants.STATUS_ABGELEHNT;
			DBMfg_Status.get().save(status);
			DBMFG.get().save(mfg);
		}
		return ok(views.html.index.render("Hello " + session("email"), userDB,
				DBMFG.get().listToDecide(userDB)));

	}

}
