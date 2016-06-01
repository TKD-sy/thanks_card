package controllers;

import models.t_card;
import models.t_bumon;
import models.t_category;
import models.t_syain;
import models.t_yakusyoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

import javax.inject.Inject;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;

import play.data.Form;
import play.data.FormFactory;

import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests to the
 * application's home page.
 */
public class HomeController extends Controller {

	/**
	 * An action that renders an HTML page with a welcome message. The
	 * configuration in the <code>routes</code> file means that this method will
	 * be called when the application receives a <code>GET</code> request with a
	 * path of <code>/</code>.
	 */
	@Inject
	private FormFactory formFactory;
/*最初からあるindex---------------------------------------------------------------------------------------------------*/
	public Result index() {
		return ok(index.render("Your new application is ready."));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*代表事例選択--------------------------------------------------------------------------------------------------------*/
	public Result choice() {
		List<t_card> ChoiceList = t_card.find.where().eq("card_flag", "0").findList();
		String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag,card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id where card_flag = 0 order by card_flag desc";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();

		return ok(choice.render(sqlRows));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*代表事例選択の確認--------------------------------------------------------------------------------------------------*/
	public Result choice_re() {
		ArrayList<t_card> CheckList = new ArrayList<>();
		ArrayList<t_card> DelList = new ArrayList<>();
		String error = "";
		Map<String, String[]> params_check = request().body().asFormUrlEncoded();
		Map<String, String[]> params_del = request().body().asFormUrlEncoded();
		String[] check_card_id = params_check.get("chack-card");
		String[] del_card_id   = params_del.get("del-card");
		if(check_card_id==null){
			error = "何も選択されていません。";
		}else{
			for (String s : check_card_id) {
				CheckList.addAll(t_card.find.where().eq("card_id", s).findList());
			}
		}

		if(del_card_id==null){
			error = "何も選択されていません。";
		}else{
			for (String s : del_card_id) {
				DelList.addAll(t_card.find.where().eq("card_id", s).findList());
			}
		}
		return ok(choice_re.render(CheckList,DelList,error));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*選択した代表事例をDBへ登録------------------------------------------------------------------------------------------*/
	public Result choice_re_touroku() {
		ArrayList<t_card> CheckList = new ArrayList<>();
		ArrayList<t_card> DelList = new ArrayList<>();
		Map<String, String[]> params_check = request().body().asFormUrlEncoded();
		Map<String, String[]> params_del = request().body().asFormUrlEncoded();
		String[] checkId = params_check.get("card-id");
		String[] delId = params_del.get("del_card-id");
		if(checkId==null){

		}else{
			for (String s : checkId) {
				List<t_card> aaa = t_card.find.where().eq("card_id", s).findList();
				for (t_card card : aaa) {
					card.card_flag = 1;
					card.update();
				}
			}
		}

		if(delId==null){

		}else{
			for (String s : delId) {
				int in = Integer.parseInt(s);
				t_card.find.deleteById(in);
			}
		}

		return redirect(routes.HomeController.choice());
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*感謝カードの作成----------------------------------------------------------------------------------------------------*/
	public Result thanks_card() {

		List<t_category> categoryList = t_category.find.all();
		return ok(thanks_card.render(categoryList));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*感謝カードの作成確認------------------------------------------------------------------------------------------------*/
	public Result thanks_kakunin() {
		String anser = "";
		t_card newCard = formFactory.form(t_card.class).bindFromRequest().get();

		List<t_category> category = t_category.find.all();
		List<t_syain> syain = t_syain.find.all();
		Map<String, String[]> params = request().body().asFormUrlEncoded();
		String[] chack_card_id = params.get("jyusin_id");
		for (String s : chack_card_id) {
			List<t_card> ans = t_card.find.where().eq("card_id", s).findList();
			if (ans.isEmpty()) {
				anser = "入力された社員IDは登録されていません";
			} else {
				String sql = "select syain_name  from t_syain where syain_id = :id;";
				List<SqlRow> aaa = Ebean.createSqlQuery(sql).setParameter("id", s).findList();
				SqlRow direct = aaa.get(0);

				anser = (String) direct.get("syain_name");
			}
		}
		return ok(thanks_kakunin.render(newCard, category, syain, anser));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*作成した感謝カードをDBへ登録----------------------------------------------------------------------------------------*/
	public Result senddate() {

		t_card newCard = formFactory.form(t_card.class).bindFromRequest().get();
		t_card dateCard = new t_card();

		dateCard.category_id = newCard.category_id;
		dateCard.sousin_id = newCard.sousin_id;
		dateCard.jyusin_id = newCard.jyusin_id;
		dateCard.hensin_id = 0;
		dateCard.card_kidokuflag = 0;
		dateCard.card_flag = 0;
		dateCard.card_hensinflag = 0;
		dateCard.card_help = newCard.card_help;
		dateCard.card_comment = newCard.card_comment;
		dateCard.card_date = newCard.card_date;
		dateCard.save();
		return ok(senddate.render());
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*社員登録メソッド----------------------------------------------------------------------------------------------------*/
	public Result new_touroku() {
		List<t_bumon> bumonList = t_bumon.find.all();
		List<t_yakusyoku> yakusyokuList = t_yakusyoku.find.all();
		return ok(new_touroku.render("社員追加", formFactory.form(t_syain.class),bumonList,yakusyokuList));

	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*登録内容確認メソッド------------------------------------------------------------------------------------------------*/
	public Result touroku_kakunin() {

		t_syain CreateData = formFactory.form(t_syain.class).bindFromRequest().get();
		return ok(touroku_kakunin.render("内容確認", CreateData));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*登録内容をDBに登録--------------------------------------------------------------------------------------------------*/
	public Result createData() {

		t_syain CreateData = formFactory.form(t_syain.class).bindFromRequest().get();
		CreateData.save();
		return ok(createData.render());
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*社員情報の変更------------------------------------------------------------------------------------------------------*/
	 /*public Result change() {
	    	List<t_syain> syainList = t_syain.find.all();


	        return ok(change.render("情報表示",syainList));
	 }*/

/*--------------------------------------------------------------------------------------------------------------------*/

/*指定した社員のデータ表示--------------------------------------------------------------------------------------------*/
	/* public Result syainData() {
		 String anser = "";
	    	ArrayList<t_syain> dataList = new ArrayList<>();
	    	Map<String, String[]> params = request().body().asFormUrlEncoded();
	    	String[] dataMap = params.get("syain_data");
	    	for(String s:dataMap){
	    		List<t_card> ans = t_card.find.where().eq("card_id", s).findList();
				if (ans.isEmpty()) {
					anser = "入力された社員IDは登録されていません";
				} else {
	    		// dataList.addAll(t_syain.find.where().eq("syain_id",s).findList());
		 String sql = "select *  from t_syain where syain_id = :id;";
			List<SqlRow> aaa = Ebean.createSqlQuery(sql).setParameter("id", s).findList();
			SqlRow direct = aaa.get(0);
			anser = (String) direct.get("*");
				}
	    	}
	    	return ok(syainData.render("社員データ表示",dataList,anser));
	    }*/
/*--------------------------------------------------------------------------------------------------------------------*/

/*変更内容確認--------------------------------------------------------------------------------------------------------*/
  /* public Result hennkou_kakunin(){
    	return ok(hennkou_kakunin.render());
    }*/
/*--------------------------------------------------------------------------------------------------------------------*/

/*送信ボックス--------------------------------------------------------------------------------------------------------*/
	public Result sousin() {
		return ok(sousin.render("送信ボックス"));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*受信ボックス--------------------------------------------------------------------------------------------------------*/
	public Result jusin() {
		return ok(jusin.render("受信ボックス"));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*感謝カードを掲示板に表示--------------------------------------------------------------------------------------------*/
	public Result keiziban(Integer pege) {
		List<t_bumon> bumonList = t_bumon.find.all();
		String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag,card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id order by card_id desc limit 6 offset :pege *6";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("pege", pege).findList();
		List<t_card> cardList = t_card.find.all();
		int kazu = 0;
		if(cardList.size()%6 == 0){
			kazu = cardList.size()/6;
		}else{
			kazu = cardList.size()/6+1;
		}

		int asdf = pege + 1;
		return ok(keiziban.render(sqlRows,bumonList,kazu,asdf));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*クリックした感謝カードの詳細画面を表示------------------------------------------------------------------------------*/
	public Result syousai(Integer id) {
		// t_card task = t_card.find.byId(id);
		String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag,card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id where card_id = :id";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("id", id).findList();
		return ok(syousai.render(sqlRows));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

/*代表的事例の表示----------------------------------------------------------------------------------------------------*/
	public Result daihyou(Integer pege) {
		List<t_bumon> bumonList = t_bumon.find.all();
		String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag, card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id "
				+ "where card_flag = 1 and card_date between GETDATE() - 31 and GETDATE() order by card_id desc "
				+ "limit 6 offset :pege *6";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("pege", pege).findList();
		String daql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag, card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id "
				+ "where card_flag = 1 and card_date between GETDATE() - 31 and GETDATE() order by card_id desc";
		List<SqlRow> cardList = Ebean.createSqlQuery(daql).findList();
		int kazu = 0;
		if(cardList.size()%6 == 0){
			kazu = cardList.size()/6;
		}else{
			kazu = cardList.size()/6+1;
		}

		int asdf = pege + 1;
		return ok(daihyou.render(sqlRows, bumonList,kazu,asdf));
	}
/*--------------------------------------------------------------------------------------------------------------------*/

}
