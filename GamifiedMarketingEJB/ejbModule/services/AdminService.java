package services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import entities.Admin;
import entities.User;
import exceptions.CredentialsException;

@Stateless
public class AdminService {

	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	private EntityManager em;

	public AdminService() {
	}
	
	public Admin checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<Admin> aList = null;
		try {
			aList = em.createNamedQuery("Admin.checkCredentials", Admin.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (aList.isEmpty())
			return null;
		else if (aList.size() == 1)
			return aList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
}
