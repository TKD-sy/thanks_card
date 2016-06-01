package controllers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;


import models.t_syain;
import play.Logger;
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
	    session("errors","");
/*    	Form<t_syain> form = formFactory.form(t_syain.class).bindFromRequest();

・ω・`)
    	if (form.hasErrors()) {
    		return badRequest(index.render(form));
    	} else {*/

		String[] login = {"",""};

		//formの値をmapで受け取る
			Map<String, String[]> Login = request().body().asFormUrlEncoded();

			//login配列の中にfromで飛んできたのsyain_idを受け取る
			login = Login.get("syain_id");

			//飛んできた社員IDと同じテーブルを探す
	        t_syain user = t_syain.find.where().eq("syain_id", login[0]).findUnique();
	        session("login",login[0]);

	        //login配列の中にformで飛んできたsyain_passを受け取る
			login = Login.get("syain_pass");
	        if(user != null && user.syain_pass.equals(login[0])){
            List<SqlRow> Yaku = Ebean.createSqlQuery("SELECT * FROM t_syain where syain_id = "+ session("login") +";").findList();
            SqlRow syainRecord = Yaku.get(0);
            String Yaku_ID = syainRecord.getString("yakusyoku_id");
            String name = syainRecord.getString("syain_name");
            String syain_id = syainRecord.getString("syain_id");

            session("yakusyoku",Yaku_ID);
            session("name",name);
            return redirect(routes.MainController.mypage());
	        } else{
	        	session().clear();
	        	String i = "※社員IDかパスワードが間違っています";
	        	session("errors",i);
	        	return redirect(routes.LoginController.index());
	        }
	}

		//ログアウトのやつ
    public Result logout() {
    	session().clear();
    	return redirect(routes.LoginController.index());
	}

}
