package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

public class MainController extends Controller {
    
	@Security.Authenticated(Secured.class)
    public static Result index() {
		
        return ok(views.html.index.render("Hello " + session("email")));
        //return ok(views.index.scala.html("Hello from Java"));
    }
   	
    
    
}
