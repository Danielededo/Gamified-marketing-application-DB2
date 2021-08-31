package services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import entities.User;
import exceptions.CredentialsException;

@Stateless
public class UserService {

	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	private EntityManager em;
	
	@EJB(name = "services/AdminService")
	private LogService logService;

	public UserService() {
	}

	public User checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1) {
			logService.addLog(uList.get(0));
			return uList.get(0);
		}
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}

	public void setBanned(String username) {
		List<User> list = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username)
				.getResultList();
		User u = list.get(0);
		u.setBanned(true);
		em.persist(u);
		em.flush();
	}

	public User addUser(String username, String password, String email) {
		User user = new User(username, password, email);
		em.persist(user);
		em.flush();

		return user;
	}
	
	public boolean checkExistence(String username) {
		List<User> list = em.createNamedQuery("User.findByUsername", User.class).setParameter(1, username)
				.getResultList();
		if(list.size()== 0)
			return true;
		else
			return false;
	}

}