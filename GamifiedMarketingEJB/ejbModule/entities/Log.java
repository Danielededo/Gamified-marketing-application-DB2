package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "gamified_marketing")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer user;
	private LocalDateTime datetime;
	
	public Log() {
		// EJB constructor
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
}
