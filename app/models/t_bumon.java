package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class t_bumon extends Model {
@Id
	public Integer bumon_id;
	public String bumon_name;
	public String bumon_buname;
	public String bumon_situname;
	public String bumon_kaname;
	public String bumon_group;

	public static Find<Integer,t_bumon> find = new Find<Integer,t_bumon>(){};

}
