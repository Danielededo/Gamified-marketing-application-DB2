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

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Answer;
import entities.Product;
import entities.Question;
import services.ProductService;
import services.QuestionService;

/**
 * Servlet implementation class SubmitMarketingQuestions
 */
@WebServlet("/SubmitMarketingQuestions")
public class SubmitMarketingQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private TemplateEngine templateEngine;
	
	@EJB(name = "services/ProductService") 
	private ProductService productService;
	@EJB(name = "services/SubmissionService")
	private QuestionService questionService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitMarketingQuestions() {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Product dailyProduct = productService.findDailyProduct();
		Integer productId = dailyProduct.getId();
		List<Question> questions = questionService.getQuestions(productId);
		List<Answer> answers = new ArrayList();
		String text;
		String id = null;
		for(Question q: questions) {
			id = Integer.toString(q.getId());
			text = StringEscapeUtils.escapeJava(request.getParameter(id));
			answers.add(new Answer(q, text));
		}
		
		String path = "/GoToQuestionnairePage";
		request.setAttribute("marketingAnswers", answers);
		request.setAttribute("statistic", true);
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

}
