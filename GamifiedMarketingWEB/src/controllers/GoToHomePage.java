package controllers;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Product;
import entities.Submission;
import entities.User;
import exceptions.CredentialsException;
import services.ProductService;
import services.SubmissionService;

@WebServlet("/Home")
public class GoToHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB(name = "services/ProductService") 
	private ProductService productService;
	@EJB(name = "services/SubmissionService")
	private SubmissionService submissionService;


	public GoToHomePage() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Product dailyProduct = null;
		List<Submission> submissions;

		try {
			// query db to authenticate for admin
			dailyProduct = productService.findDailyProduct();
		} catch (NonUniqueResultException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Double daily product inconsistency");
			return;
		}
		try {
			submissions = submissionService.findByProduct(dailyProduct.getId());
		} catch (NullPointerException npe) {
			submissions  = null;
		}
		// Redirect to the Home page and add missions to the parameters
		String path = "/WEB-INF/Home.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("dailyProduct", dailyProduct);
		ctx.setVariable("submissions", submissions);

		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}
