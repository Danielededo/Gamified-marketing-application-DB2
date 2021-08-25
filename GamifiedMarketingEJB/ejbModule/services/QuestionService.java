package services;

import java.util.List;

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
		return em.createNamedQuery("Question.findByProduct", Question.class).setParameter("productId", productId).getResultList();
	}
	
	public Question addQuestion(Product product, String text, Boolean mandatory) {
		Question question = new Question(product, text, mandatory);
		em.persist(question);
		em.flush();
		return question;
	}
}