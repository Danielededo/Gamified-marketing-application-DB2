package controllers;

import java.io.IOException;

import javax.ejb.EJB;
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

import services.UserService;

/**
 * Servlet implementation class GoToRegisterPage
 */
@WebServlet("/Registration")
public class GoToRegisterPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB(name = "services/UserService")
	private UserService usrService;

	public GoToRegisterPage() {
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


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usrn = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		String email = request.getParameter("email");
		if(usrn == null || usrn.isEmpty() || pwd == null || pwd.isEmpty() || email == null || email.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing input parameters");			
			return;
		}
		usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
		pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
		email = StringEscapeUtils.escapeJava(request.getParameter("email"));
		Boolean existence = usrService.checkExistence(usrn);
		if(existence) {
			usrService.addUser(usrn, pwd, email);
			String path = getServletContext().getContextPath() + "/LoginPage?register";
			response.sendRedirect(path);
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "There is already a user with this username");
		}
	}

}
