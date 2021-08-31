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

import entities.OffensiveWord;
import entities.User;
import exceptions.IncompleteQuestionnaireException;
import services.OffensiveWordService;
import services.SubmissionService;
import services.UserService;

@WebServlet("/SubmitQuestionnaire")
public class SubmitQuestionnaire extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB(name = "services/UserService") 
	private UserService userService;

	@EJB(name = "services/SubmissionService")
	private SubmissionService submissionService;

	@EJB(name = "services/OffensiveWordService")
	private OffensiveWordService offensiveService;

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
		String ageParam = request.getParameter("age");
		Integer age;
		String sex = request.getParameter("sex");
		String expertise = request.getParameter("expertise");
		String[] parAnswers = request.getParameterValues("answers[]");

		User user = (User) request.getSession().getAttribute("user");
		
		try {
			age = Integer.parseInt(request.getParameter("age"));
		} catch (NumberFormatException nfe) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid non numeric input for 'age' field");
			return;
		} catch (NullPointerException npe) {
			age = null;
		}

		List<String> answers = Arrays.asList(parAnswers);
		List<OffensiveWord> offensiveWords = offensiveService.findAllBadwords();

		Boolean isBanned = false;

		for(int i = 0 ; i < answers.size() ; i++) {

			String answer = answers.get(i);

			for(int j = 0 ; j < offensiveWords.size() ; j++) {
				if(answer.toLowerCase().contains(offensiveWords.get(j).getTerm().toLowerCase())) {
					isBanned = true;
				}
			}
		}

		if(isBanned) {
			userService.setBanned(user.getUsername());
			String path = getServletContext().getContextPath() + "/Banned";
			response.sendRedirect(path);
		}
		else {
			try {
			submissionService.submitDailyQuestionnaire(user, age, sex, expertise, answers);
			} catch (IncompleteQuestionnaireException iqe) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, iqe.getMessage());
				return;
			}
			String path = getServletContext().getContextPath() + "/GreetingsPage";
			response.sendRedirect(path);
		}
	}

}
