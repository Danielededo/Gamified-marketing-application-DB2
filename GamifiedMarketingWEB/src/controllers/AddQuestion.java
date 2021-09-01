package controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.CreateException;
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
		String paramId = request.getParameter("productId");
		Integer productId; // Questionnaire id
		String question = request.getParameter("marketingQuestion"); // Marketing question to add

		if(paramId == null || paramId.isEmpty() || question == null || question.isEmpty()){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input parameters");			
			return;
		}

		try {
			// Get and escape parameters
			try {
				productId = Integer.parseInt(paramId);
			} catch (NumberFormatException nfe) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid non-numeric input");			
				return;
			}
			question = StringEscapeUtils.escapeJava(question);
			try {
				qService.addQuestion(productId, question);
			} catch (CreateException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());			
				return;
			}
			path = getServletContext().getContextPath() + "/CreationPage?question";
			response.sendRedirect(path);
			return;

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());			
			return;
		}
	}	
}
