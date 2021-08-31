package services;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Log;
import entities.User;

@Stateless
public class LogService {

	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	private EntityManager em;
	
	public void addLog(User user) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Log log = new Log(user, timestamp);
		em.persist(log);
		em.flush();
	}
}