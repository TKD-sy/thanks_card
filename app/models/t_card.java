package models;

import com.avaje.ebean.Model;

import java.security.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class t_card extends Model {
	@Id
    public Integer card_id;
    public Integer category_id;
    public Integer sousin_id;
    public Integer jyusin_id;
    public Integer hensin_id;
    public Integer card_kidokuflag;
    public Integer card_flag;
    public Integer card_hensinflag;
    public String card_help;
    public String card_comment;
    public Date card_date;


    public static Find<Integer,t_card> find = new Find<Integer,t_card>(){};
}