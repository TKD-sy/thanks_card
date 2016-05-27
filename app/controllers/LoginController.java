package controllers;

import java.util.List;

import javax.inject.Inject;

import models.Login;
import models.t_syain;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.authentication.*;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

public class LoginController extends Controller{

	    @Inject
	    private FormFactory formFactory;



	    //セッションにログイン情報が入ってた場合、マイページに飛ぶ。
	public Result index(){
		if(session("login") != null){
        	return redirect(routes.MainController.mypage());
		}
		return ok(index.render(formFactory.form(t_syain.class)));
	}

		//IDとパスワードの中身をみてエラー表示するか通すかの判断をする
	public Result authenticate(){
    	Form<t_syain> form = formFactory.form(t_syain.class).bindFromRequest();

    	if (form.hasErrors()) {
    		return badRequest(index.render(form));
    	} else {
    		t_syain login = form.get();
    		String syain_id = String.valueOf(login.syain_id);
        	session("login", syain_id);
            List<SqlRow> Yaku = Ebean.createSqlQuery("SELECT * FROM t_syain where syain_id = "+session("login") +";").findList();
            SqlRow syainRecord = Yaku.get(0);
            String Yaku_ID = syainRecord.getString("yakusyoku_id");
            String name = syainRecord.getString("syain_name");
            session("yakusyoku",Yaku_ID);
            session("name",name);
            return redirect(routes.MainController.mypage());

        }
	}

		//ログアウトのやつ
    public Result logout() {
    	session().clear();
    	return redirect(routes.LoginController.index());
	}

}
