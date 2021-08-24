package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "submission", schema = "gamified_marketing")
@NamedQuery(name = "Submission.findByUsername", query = "SELECT s FROM Submission s WHERE s.user.username = :username")
@NamedQuery(name = "Submission.findByProduct", query = "SELECT s FROM Submission s WHERE s.product.id = :productId")
@NamedQuery(name = "Submission.findByDate", query = "SELECT s FROM Submission s WHERE s.product.date = :dateNow ORDER BY s.points DESC")
public class Submission implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer points;
	private Boolean cancelled;
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	
	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="submission", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true )
	private List<Answer> answers;
	
	public Submission() {
		// EJB constructor
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	
}
