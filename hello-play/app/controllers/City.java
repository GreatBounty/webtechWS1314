package controllers;


import db.DBCity;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class City extends Controller {

	public static Result filterCity(String term) {
		Logger.info("filterCity");
		//renderJSON(DBCity.get().findByName(term));
		return ok(Json.toJson(DBCity.get().findByName(term)));
	}
}
