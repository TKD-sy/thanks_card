package controllers;

import play.mvc.Controller;
import play.mvc.*;
import views.html.*;

public class MainController  extends Controller{

	public Result top(){
		return ok(top.render());
	}
	public Result mypage(){
		return ok(mypage.render());
	}
}






