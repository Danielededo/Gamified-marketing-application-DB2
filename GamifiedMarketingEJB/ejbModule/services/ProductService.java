package services;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import entities.Admin;
import entities.Product;
import exceptions.CredentialsException;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "GamifiedMarketingEJB")
	private EntityManager em;

	public ProductService() {
	}
	
	public Product findDailyProduct() throws NonUniqueResultException {
		List<Product> aList = null;
		LocalDate localDate = LocalDateTime.now().plus(Duration.ofHours(2)).toLocalDate();
		Date date = Date.valueOf(localDate);
		aList = em.createNamedQuery("Product.findDailyProduct", Product.class).setParameter(1, date)
					.getResultList();
		if (aList.isEmpty())
			return null;
		else if (aList.size() == 1)
			return aList.get(0);
		throw new NonUniqueResultException("More than one daily products detected");

	}
	
	public List<Product> getAllProducts()  {
		return em.createNamedQuery("Product.getAllProducts", Product.class).getResultList();
	}
	
	public List<Product> findPreviousProduct()  {
		List<Product> aList = null;
		LocalDate localDate = LocalDateTime.now().plus(Duration.ofHours(2)).toLocalDate();
		Date date = Date.valueOf(localDate);
		aList = em.createNamedQuery("Product.findPreviousProduct", Product.class).setParameter(1, date).getResultList();
		if (aList.isEmpty())
			return null;
		else
			return aList;
	}
	
	public Product getProductById(int productId) {
		return em.createNamedQuery("Product.getProductById", Product.class).setParameter("productId", productId).getSingleResult();
	}
	
	public Product addProduct(String name, byte[] image, Date date) {
		Product product = new Product(name, image, date);
		em.persist(product);
		em.flush();
		return product;
	}
	
	public void deleteProduct(int productId) {
        Product product = em.find(Product.class, productId);
        

        if (product == null) {
            throw new IllegalArgumentException(String.format("Product with ID = %d does not exist!", productId));
        }

        em.remove(product);
        em.flush();
    }
	
}
