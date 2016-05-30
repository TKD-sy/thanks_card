package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import models.t_card;
import models.t_bumon;
import models.t_category;
import models.t_syain;
import java.util.ArrayList;
import java.util.Date;
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
    	List<t_card> ChoiceList = t_card.find.all();

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

        public Result thanks_card() {

        List<t_category> categoryList = t_category.find.all();
        return ok(thanks_card.render(categoryList));
    }

    public Result thanks_kakunin() {
    	String anser = "";
    	t_card newCard = formFactory.form(t_card.class).bindFromRequest().get();

    	List<t_category> category = t_category.find.all();
    	List<t_syain> syain = t_syain.find.all();
    	Map<String, String[]> params = request().body().asFormUrlEncoded();
    	String[] chack_card_id = params.get("jyusin_id");
    	for(String s:chack_card_id){
    		 List<t_card> ans = t_card.find.where().eq("card_id",s).findList();
    		 if(ans.isEmpty()){
    			 anser = "いない";
    		 }else{
    			 String sql = "select syain_name  from t_syain where syain_id = :id;";
    			 List<SqlRow> aaa = Ebean.createSqlQuery(sql) .setParameter("id",s).findList();
    			 SqlRow direct = aaa.get(0);

    			 anser =(String)direct.get("syain_name");
    		 }
    	}
        return ok(thanks_kakunin.render(newCard,category,syain,anser));
    }


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
        //List<t_card> taskList = t_card.find.all();
        String sql = "select card_id,c.category_name as category,a.syain_name as sousin,b.syain_name as jyusin,hensin_id,card_kidokuflag,card_flag,card_hensinflag,card_help,card_comment,card_date from t_card inner join t_syain a on t_card.sousin_id = a.syain_id inner join t_syain b on t_card.jyusin_id = b.syain_id inner join t_category c on t_card.category_id = c.category_id";
        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();
        return ok(keiziban.render(sqlRows));
    }
    public Result syousai(Integer id){
        //t_card task = t_card.find.byId(id);
        String sql = "select card_id,c.category_name as category,a.syain_name as sousin,b.syain_name as jyusin,hensin_id,card_kidokuflag,card_flag,card_hensinflag,card_help,card_comment,card_date from t_card inner join t_syain a on t_card.sousin_id = a.syain_id inner join t_syain b on t_card.jyusin_id = b.syain_id inner join t_category c on t_card.category_id = c.category_id where card_id = :id";
        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("id",id).findList();
        return ok(syousai.render(sqlRows));
    }
    public Result daihyou(){
        //t_card task = t_card.find.byId(id);
        String sql = "select card_id,c.category_name as category,a.syain_name as sousin,b.syain_name as jyusin,hensin_id,card_kidokuflag,card_flag,card_hensinflag,card_help,card_comment,card_date from t_card inner join t_syain a on t_card.sousin_id = a.syain_id inner join t_syain b on t_card.jyusin_id = b.syain_id inner join t_category c on t_card.category_id = c.category_id where card_flag = 1 and card_date between GETDATE() - 31 and GETDATE()";
        List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();
        return ok(daihyou.render(sqlRows));
    }

}
