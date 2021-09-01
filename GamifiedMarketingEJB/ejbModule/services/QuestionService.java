package services;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Product;
import entities.Question;

@Stateless
public class QuestionService {
	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	private EntityManager em;

	public QuestionService() {
	}

	public List<Question> getQuestions(Integer productId) {
		return em.createNamedQuery("Question.findByProduct", Question.class)
				.setParameter("productId", productId)
				.getResultList();
	}

	public Question addQuestion(int productId, String text) throws CreateException {
		Product product = em.find(Product.class, productId);
		LocalDate localDate = LocalDateTime.now().plus(Duration.ofHours(2)).toLocalDate();
		Date date = Date.valueOf(localDate);
		if(!product.getDate().before(date)) {
			Question question = new Question(product, text);
			em.persist(question);
			em.flush();
			return question;
		} else  {
			throw new CreateException("Invalid date");
		}
	}
}
