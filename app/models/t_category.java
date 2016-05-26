package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class t_category extends Model {
@Id
	public Integer category_id;
	public String category_name;

	public static Find<Integer,t_category> find = new Find<Integer,t_category>(){};
}
