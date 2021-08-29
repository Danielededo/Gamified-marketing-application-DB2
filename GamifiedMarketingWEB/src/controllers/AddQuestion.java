package controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Product;
import services.ProductService;
import services.QuestionService;

@WebServlet("/AddQuestion")
public class AddQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB(name = "services/QuestionService")
	private QuestionService qService;

	@EJB(name = "services/ProductService")
	private ProductService pService;

	public AddQuestion() {
		// Servlet constructor
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 
		String path;

		Integer productId; // Questionnaire id
		String question; // Marketing question to add

		Product product = null;

		// Re-load all available products to have consistency in the page
		List<Product> products = null;
		products = pService.getAllProducts();

		try {
			// Get and escape parameters
			productId = Integer.parseInt(request.getParameter("productId"));
			question = StringEscapeUtils.escapeJava(request.getParameter("marketingQuestion"));
			
			qService.addQuestion(productId, question);
			
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());						
			path = getServletContext().getContextPath() + "/AdminPage";
			response.sendRedirect(path);
			return;
			/*
			String path = "/AdminPage";
			request.setAttribute("submissions", submissions);
			request.setAttribute("cancelledSubs", cancelledSubs);
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
			*/
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());			
			return;
		}
	}	
}
