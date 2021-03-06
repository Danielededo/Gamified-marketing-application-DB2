package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
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

import entities.Admin;
import entities.User;
import exceptions.CredentialsException;
import services.AdminService;
import services.UserService;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "services/UserService")
	private UserService usrService;
	
	@EJB(name = "services/AdminService")
	private AdminService adminService;

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
		
		// obtain and escape params
		String usrn = null;
		String pwd = null;
		try {
			usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
			pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
			if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		
		Admin admin;
		try {
			// query db to authenticate for admin
			admin = adminService.checkCredentials(usrn, pwd);
		} catch (CredentialsException | NonUniqueResultException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
			return;
		}
		String path;
		if (admin != null) { // If the admin exists, add info to the session and go to admin page
			request.getSession().setAttribute("admin", admin);
			path = getServletContext().getContextPath() + "/AdminPage";
			response.sendRedirect(path);
			return;
		}
	
		User user;
		try {
			// query db to authenticate for user
			user = usrService.checkCredentials(usrn, pwd);
		} catch (CredentialsException | NonUniqueResultException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
			return;
		}

		// If the user exists, add info to the session and go to home page, otherwise
		// show login page with error message

		if (user == null) {
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			path = getServletContext().getContextPath() + "/LoginPage?error";
			response.sendRedirect(path);
		} else {
			request.getSession().setAttribute("user", user);
			path = getServletContext().getContextPath() + "/Home";
			response.sendRedirect(path);
		}

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		String path = getServletContext().getContextPath() + "/LoginPage";
		response.sendRedirect(path);
	}

	public void destroy() {
	}
}
