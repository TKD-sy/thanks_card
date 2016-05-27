package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class t_syain extends Model {


	@Id
	    public Integer syain_id;

	    public Integer bumon_id;

	    public Integer yakusyoku_id;

	    public String syain_name;

	    public String syain_kana;

	    public Date syain_birth;

	    public Date syain_nyuusyabi;

	    public String syain_pass;

	    public String syain_sex;

	    public static Find<Integer,t_syain> find = new Find<Integer,t_syain>(){};
	    
	    


}



