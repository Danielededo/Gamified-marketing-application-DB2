package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "offensive_word", schema = "gamified_marketing")
public class OffensiveWord implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String term;
	
	public OffensiveWord() {
		// EJB constructor
	}
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
}
