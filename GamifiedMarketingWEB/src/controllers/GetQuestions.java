package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Question;
import entities.Submission;
import services.ProductService;
import services.QuestionService;

@WebServlet("/GetQuestions")
public class GetQuestions extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB(name = "services/QuestionService")
	private QuestionService qService;


	public GetQuestions() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String param = request.getParameter("productId");
		int productId;
		if (param == null || param.isEmpty()) {
			ServletContext servletContext = getServletContext();
			String path = getServletContext().getContextPath() + "/AdminPage";
			response.sendRedirect(path);
			return;
		}
		try {
		productId = Integer.parseInt(param);
		} catch (NumberFormatException nfe) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid non numerical input");
			return;
		}
		List<Question> questions;

		try {
			questions = qService.getQuestions(productId);
		} catch (NullPointerException npe) {
			questions = new ArrayList<Question>();
		}
		String path = "/AdminPage";
		request.setAttribute("questions", questions);
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	public void destroy() {
	}

}
