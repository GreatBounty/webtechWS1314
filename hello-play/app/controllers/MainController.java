package controllers;

import db.DBUser;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

public class MainController extends Controller {
    
	@Security.Authenticated(Secured.class)
    public static Result index() {
		Logger.info("session: " + session("email"));
		//Logger.info("findById: " + UserDB.get().findById("528ddd6a119c6788a6c259f1"));
		Logger.info("aus DB: " +  DBUser.get().findByEmail(session("email")));
        return ok(views.html.index.render("Hello " + session("email"), DBUser.get().findByEmail(session("email"))));
        //return ok(views.index.scala.html("Hello from Java"));
    }
   	
//	@Security.Authenticated(Secured.class)
//	public static Result index() {
//	    return ok(index.render(
//	        Project.findInvolving(request().username()), 
//	        Task.findTodoInvolving(request().username()),
//	        User.find.byId(request().username())
//	    )); 
//	}
//    
}
