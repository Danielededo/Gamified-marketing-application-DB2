package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "log", schema = "gamified_marketing")
@NamedQuery(name = "Log.findByUsername", query = "SELECT l FROM Log l WHERE l.user.username = :username")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	private Timestamp datetime;
	
	public Log() {
		// EJB constructor
	}
	
	public Log(User user, Timestamp datetime) {
		super();
		this.user = user;
		this.datetime = datetime;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}
	
}