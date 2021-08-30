package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.OffensiveWord;

@Stateless
public class OffensiveWordService {

	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	private EntityManager em;

	public OffensiveWordService() {}
	
	public List<OffensiveWord> findAllBadwords() {
		List <OffensiveWord> results = em.createNamedQuery("OffensiveWord.findAll", OffensiveWord.class).getResultList();
		if (results.isEmpty())
			return null;
		return results;
	}
}
