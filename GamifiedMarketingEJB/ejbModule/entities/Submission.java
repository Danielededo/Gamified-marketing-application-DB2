package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "submission", schema = "gamified_marketing")
@NamedQuery(name = "Submission.findByUsername", query = "SELECT s FROM Submission s WHERE s.user.username = :username")
@NamedQuery(name = "Submission.findByProduct", query = "SELECT s FROM Submission s WHERE s.product.id = :productId")
@NamedQuery(name = "Submission.findByDate", query = "SELECT s FROM Submission s WHERE s.product.date = :date")
public class Submission implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer points;
	private Boolean canceled;
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	@ManyToOne()
	@JoinColumn(name = "product")
	private Product product;
	
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

	public Boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}
	
}
