package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Answer;
import entities.Product;
import entities.Question;
import entities.Statistics;
import entities.User;
import services.AnswerService;
import services.ProductService;
import services.QuestionService;
import services.SubmissionService;

@WebServlet("/SubmitQuestionnaire")
public class SubmitQuestionnaire extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private TemplateEngine templateEngine;

	@EJB(name = "services/ProductService") 
	private ProductService productService;
	
	@EJB(name = "services/QuestionService") 
	private QuestionService questionService;
	
	@EJB(name = "services/SubmissionService")
	private SubmissionService submissionService;
	
	@EJB(name = "services/AnswerService")
	private AnswerService answerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitQuestionnaire() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int age = Integer.parseInt(request.getParameter("age"));
		String sex = request.getParameter("sex");
		String expertise = request.getParameter("expertise");
		String[] parAnswers = request.getParameterValues("answers[]");
		User user = (User) request.getSession().getAttribute("user");
		
		if (parAnswers == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No answers found");
            return;
        }
		
		List<String> answers = Arrays.asList(parAnswers);
		
		submissionService.submitDailyQuestionnaire(user, age, sex, expertise, answers);
		
		
		String path = getServletContext().getContextPath() + "/GreetingsPage";
		response.sendRedirect(path);
	}

}
