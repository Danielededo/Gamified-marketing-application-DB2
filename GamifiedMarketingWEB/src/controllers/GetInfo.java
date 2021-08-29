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
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Product;
import entities.Question;
import entities.Submission;
import services.ProductService;
import services.QuestionService;
import services.SubmissionService;

@WebServlet("/GetInfo")
public class GetInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "services/SubmissionService")
	private SubmissionService submissionService;
	
	@EJB(name = "services/QuestionService")
	private QuestionService questionService;
	
	@EJB(name = "services/ProductService")
	private ProductService productService;

	public GetInfo() {
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
		int productId = Integer.parseInt(param);
		Product product = null;
		List<Submission> submissions = null;
		List<Submission> cancelledSubs = null;
		List<Question> questions = null;

		if (param == null || param.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("A nonempty paraameter is required to get the submissions");
			return;
		}

		try {
			product = productService.getProductById(productId);
		} catch (NullPointerException npe) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().println("No product with such id");
		}

		try {
			submissions = submissionService.findByProduct(productId);
		} catch (NullPointerException npe) {
			submissions = new ArrayList<Submission>();
		}

		try {
			cancelledSubs = submissionService.findCancelledByProduct(productId);
		} catch (NullPointerException npe) {
			cancelledSubs = new ArrayList<Submission>();
		}
		
		try {
			questions = questionService.getQuestions(productId);
		} catch (NullPointerException npe) {
			questions = new ArrayList<Question>();
		}
		
		String path = "/InspectionPage";
		request.setAttribute("submissions", submissions);
		request.setAttribute("cancelledSubs", cancelledSubs);
		request.setAttribute("prod", product);
		request.setAttribute("questions", questions);
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	public void destroy() {
	}
}
