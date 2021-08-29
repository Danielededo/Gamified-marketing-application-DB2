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

@WebServlet("/SubmitQuestionnaire")
public class SubmitQuestionnaire extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private TemplateEngine templateEngine;

	@EJB(name = "services/ProductService") 
	private ProductService productService;
	@EJB(name = "services/SubmissionService")
	private QuestionService questionService;

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
		
		System.out.println(request.getAttribute("question2"));

		String text;
		String id = null;
		
		

		String path = getServletContext().getContextPath() + "/GreetingsPage";
		response.sendRedirect(path);
	}

}
