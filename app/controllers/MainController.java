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






//    mypage.scala.htmlの人事、経営用のボタンコード
/* 			@if(User.authenticate(役職)="人事" || User.authenticate(役職)="経営"){
	<button type="button" class="btn btn-primary btn-lg myp_links">追加</button><br>
	<button type="button" class="btn btn-primary btn-lg myp_links">編集</button><br>
	<button type="button" class="btn btn-primary btn-lg myp_links">代表事例選択</button><br>
	}*/