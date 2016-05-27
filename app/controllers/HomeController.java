package controllers;

import models.t_card;
import models.t_bumon;
import models.t_category;
import models.t_syain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

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
    	List<t_card> ChoiceList = t_card.find.where().eq("card_flag","0").findList();

    	return ok(choice.render(ChoiceList));
    }

    public Result choice_re() {
    	ArrayList<t_card> CheckList = new ArrayList<>();
    	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	String[] chack_card_id = params.get("chack-card");
    	for(String s:chack_card_id){
    		 CheckList.addAll(t_card.find.where().eq("card_id",s).findList());
    	}
    	//List<t_card> CheckList = t_card.find.where().eq("card_flag","1").findList();

    	return ok(choice_re.render(CheckList));
    }

        public Result choice_re_touroku() {
    	     	ArrayList<t_card> CheckList = new ArrayList<>();
    	     	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	     	String[] checkId = params.get("card-id");

    	     	for(String s:checkId){
    	     		List<t_card> aaa = t_card.find.where().eq("card_id",s).findList();
    	     		for(t_card card : aaa){
    	     			card.card_flag = 1 ;
    	     			card.update();
    	 	    	}

    	     	}
    	     	//List<t_card> CheckList = t_card.find.where().eq("card_flag","1").findList();

    	     	return redirect(routes.HomeController.choice());
    	     }


        public Result thanks_card() {

        List<t_bumon> bumonList = t_bumon.find.all();
        List<t_category> categoryList = t_category.find.all();
        return ok(thanks_card.render(bumonList,categoryList));
    }

    public Result thanks_kakunin() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String syain =params.get("syain_id")[0];
        int syain_id = Integer.parseInt(syain);
        String card_help =params.get("card_help")[0];
        String card_bumon =params.get("bumon")[0];
        String card_comment =params.get("card_comment")[0];
        String card_category =params.get("category")[0];
        return ok(thanks_kakunin.render(syain_id,card_help,card_bumon,card_comment,card_category));
    }

    public Result new_touroku() {

    	List<t_syain> syainList = t_syain.find.all();
        return ok(new_touroku.render("新入社員追加",(syainList),formFactory.form(t_syain.class)));

    }



    public Result createData(){
    	t_syain CreateData = formFactory.form(t_syain.class).bindFromRequest().get();
        CreateData.save();
        return redirect(routes.HomeController.new_touroku());
    }



    public Result change() {
    	return ok(change.render("社員情報変更"));
    }

    public Result sousin() {
    	return ok(sousin.render("送信ボックス"));
    }

    public Result jusin() {
    	return ok(jusin.render("受信ボックス"));
    }

    public Result keiziban(){
        List<t_bumon> bumonList = t_bumon.find.all();
        String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,card_hensinflag,card_help,card_comment,card_date from t_card inner join t_syain a on t_card.sousin_id = a.syain_id inner join t_syain b on t_card.jyusin_id = b.syain_id inner join t_category c on t_card.category_id = c.category_id inner join t_bumon d on a.bumon_id = d.bumon_id inner join t_bumon e on b.bumon_id = e.bumon_id order by card_id desc";
        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();
        return ok(keiziban.render(sqlRows,bumonList));
    }
    public Result syousai(Integer id){
        //t_card task = t_card.find.byId(id);
        String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,card_hensinflag,card_help,card_comment,card_date from t_card inner join t_syain a on t_card.sousin_id = a.syain_id inner join t_syain b on t_card.jyusin_id = b.syain_id inner join t_category c on t_card.category_id = c.category_id inner join t_bumon d on a.bumon_id = d.bumon_id inner join t_bumon e on b.bumon_id = e.bumon_id where card_id = :id";
        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("id",id).findList();
        return ok(syousai.render(sqlRows));
    }
    public Result daihyou(){
        //t_card task = t_card.find.byId(id);
        String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,card_hensinflag, card_help,card_comment,card_date from t_card inner join t_syain a on t_card.sousin_id = a.syain_id inner join t_syain b on t_card.jyusin_id = b.syain_id inner join t_category c on t_card.category_id = c.category_id inner join t_bumon d on a.bumon_id = d.bumon_id inner join t_bumon e on b.bumon_id = e.bumon_id where card_flag = 1 and card_date between GETDATE() - 31 and GETDATE() order by card_id desc";
        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();
        return ok(daihyou.render(sqlRows));
    }

}
