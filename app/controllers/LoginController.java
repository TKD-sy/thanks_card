package controllers;

import javax.inject.Inject;

import models.Login;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.authentication.*;

public class LoginController extends Controller{

	    @Inject
	    private FormFactory formFactory;



	    //セッションにログイン情報が入ってた場合、マイページに飛ぶ。
	public Result index(){
		if(session("login") != null){
//			return ok("こんにちは、" + session("login") + "さん");
        	return redirect(routes.MainController.mypage());
		}
		return ok(index.render(formFactory.form(Login.class)));
	}

		//IDとパスワードの中身をみてログインするかどうかを判断する
	public Result authenticate(){
    	Form<Login> form = formFactory.form(Login.class).bindFromRequest();

    	if (form.hasErrors()) {
    		return badRequest(index.render(form));
    	} else {
    		Login login = form.get();
    		session("login", login.username);
//    		return ok("ようこそ " + login.username + " さん!!");
        	return redirect(routes.MainController.mypage());
        }
	}

		//ログアウトのやつ
    public Result logout() {
    	session().clear();
    	return redirect(routes.LoginController.index());
	}

}
