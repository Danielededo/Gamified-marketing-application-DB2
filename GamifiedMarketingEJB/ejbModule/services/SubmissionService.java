package services;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import entities.Answer;
import entities.Product;
import entities.Question;
import entities.Statistics;
import entities.Submission;
import entities.User;

@Stateless
public class SubmissionService {

	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	private EntityManager em;

	@EJB(name = "services/ProductService") 
	private ProductService productService;

	@EJB(name = "services/QuestionService") 
	private QuestionService questionService;

	public SubmissionService(){
	}

	public List<Submission> findLeaderboard() {
		LocalDate localDate = LocalDateTime.now().plus(Duration.ofHours(2)).toLocalDate();
		Date date = Date.valueOf(localDate);
		List<Submission> leaderboard = em.createNamedQuery("Submission.findByDate", Submission.class).setParameter("dateNow", date).getResultList();
		return leaderboard;
	}

	public List<Submission> findSubmission(Date date){
		List<Submission> submissions = em.createNamedQuery("Submission.findByDate", Submission.class).setParameter("dateNow", date).getResultList();
		return submissions;
	}

	public Boolean hasAlreadySubmitted(Integer userId){
		LocalDate localDate = LocalDateTime.now().plus(Duration.ofHours(2)).toLocalDate();
		Date date = Date.valueOf(localDate);
		List<Submission> submissions = em.createNamedQuery("Submission.hasAlreadySubmitted", Submission.class)
				.setParameter("dateNow", date)
				.setParameter("userId", userId)
				.getResultList();
		if(submissions == null || submissions.isEmpty())
			return false;
		return true;
	}

	public List<Submission> findByProduct(Integer product){
		List<Submission> submissions = em.createNamedQuery("Submission.findByProduct", Submission.class).setParameter("productId", product).getResultList();
		return submissions;
	}

	public List<Submission> findCancelledByProduct(Integer product){
		List<Submission> submissions = em.createNamedQuery("Submission.findCancelledByProduct", Submission.class).setParameter("productId", product).getResultList();
		return submissions;
	}

	public Submission submitDailyQuestionnaire(User user, int age, String sex, String expertise, List<String> strAnswers) {
		Product product = productService.findDailyProduct();
		List<Question> dailyQuestions = questionService.getQuestions(product.getId());
		List<Answer> answers;
		Submission sub = new Submission(user, product);
		Statistics stats = new Statistics(sub, age, sex, expertise);
		sub.addStatistics(stats);
		for(int i = 0; i<dailyQuestions.size(); i++) {
			Answer answer = new Answer(sub, dailyQuestions.get(i), strAnswers.get(i));
			sub.addAnswer(answer);
		}
		em.persist(sub);
		return sub;
	}

}