package controllers;

import models.t_card;
import java.util.List;
import javax.inject.Inject;

import com.avaje.ebean.ExpressionList;

import play.data.Form;
import play.data.FormFactory;


import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	@Inject
	private FormFactory formFactory;

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result choice() {
    	List<t_card> ChoiceList = t_card.find.all();

    	return ok(choice.render(ChoiceList));
    }

    public Result choice_re() {
    	List<t_card> CheckList = t_card.find.where().eq("card_flag","1").findList();
    	return ok(choice_re.render(CheckList));
    }

}
