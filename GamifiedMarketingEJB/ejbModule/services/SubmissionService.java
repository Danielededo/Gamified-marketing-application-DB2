package services;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import entities.Submission;

@Stateless
public class SubmissionService {
	
	@PersistenceContext(unitName = "GamifiedMarketingEJB")
    private EntityManager em;
	
	public SubmissionService(){
	}
	
	public List<Submission> findLeaderboard() {
		LocalDate localDate = LocalDateTime.now().plus(Duration.ofHours(2)).toLocalDate();
		Date date = Date.valueOf(localDate);
		List<Submission> leaderboard = em.createNamedQuery("Submission.findByDate", Submission.class).setParameter("dateNow", date).getResultList();
        return leaderboard;
    }
}