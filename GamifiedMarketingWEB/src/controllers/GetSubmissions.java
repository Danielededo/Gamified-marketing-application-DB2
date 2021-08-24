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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import entities.Submission;
import services.SubmissionService;

@WebServlet("/GetSubmissions")
public class GetSubmissions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "services/SubmissionService")
	private SubmissionService submissionService;

	
	public GetSubmissions() {
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
		List<Submission> submissions;
		List<Submission> cancelledSubs;
		
		if (param == null || param.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("A nonempty paraameter is required to get the submissions");
			return;
		}
		
		submissions = submissionService.findByProduct(productId);
		cancelledSubs = submissionService.findCancelledByProduct(productId);
		
		String path = "/InspectionPage";
		request.setAttribute("submissions", submissions);
		request.setAttribute("cancelledSubs", cancelledSubs);
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	
	public void destroy() {
	}
}
