package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

@Entity
public class t_yakusyoku extends Model {
	@Id
	public Integer yakusyoku_id;
	public String yakusyoku_name;

	public static Find<Integer,t_yakusyoku> find = new Find<Integer,t_yakusyoku>(){};
}
