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

public class MainController  extends Controller{

	public Result top(){
		return ok(top.render());
	}

	public Result mypa(){
		int page = 0;
		String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag,card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id "
				+ "where jyusin_id = "
				+ session("login")
				+ " order by card_id desc limit 6 offset :pege *6";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("pege", page).findList();
		String daql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag,card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id "
				+ "where jyusin_id = "
				+ session("login")
				+ " order by card_id desc";
		List<SqlRow> cardList = Ebean.createSqlQuery(daql).findList();
		int kazu = 0;
		if(cardList.size()%6 == 0){
			kazu = cardList.size()/6;
		}else{
			kazu = cardList.size()/6+1;
		}
		int asdf = page + 1;
		return ok(mypage.render(sqlRows,kazu,asdf));
	}

	public Result Mypage(Integer page){
		String sql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag,card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id "
				+ "where jyusin_id = "
				+ session("login")
				+ " order by card_id desc limit 6 offset :pege *6";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("pege", page).findList();
		String daql = "select card_id,c.category_name as category,a.syain_name as sousin,d.bumon_name as sousin_bumon,"
				+ "b.syain_name as jyusin,e.bumon_name as jyusin_bumon,hensin_id,card_kidokuflag,card_flag,"
				+ "card_hensinflag,card_help,card_comment,card_date from t_card "
				+ "inner join t_syain a on t_card.sousin_id = a.syain_id "
				+ "inner join t_syain b on t_card.jyusin_id = b.syain_id "
				+ "inner join t_category c on t_card.category_id = c.category_id "
				+ "inner join t_bumon d on a.bumon_id = d.bumon_id "
				+ "inner join t_bumon e on b.bumon_id = e.bumon_id "
				+ "where jyusin_id = "
				+ session("login")
				+ " order by card_id desc";
		List<SqlRow> cardList = Ebean.createSqlQuery(daql).findList();
		int kazu = 0;
		if(cardList.size()%6 == 0){
			kazu = cardList.size()/6;
		}else{
			kazu = cardList.size()/6+1;
		}
		int asdf = page + 1;
		return ok(mypage.render(sqlRows,kazu,asdf));
	}
}






