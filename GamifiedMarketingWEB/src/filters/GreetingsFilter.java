package filters;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import services.SubmissionService;

public class GreetingsFilter implements Filter {
	@EJB(name = "services/SubmissionService")
	private SubmissionService submissionService;
	
	public GreetingsFilter() {
        // TODO Auto-generated constructor stub
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String greetingsPath = req.getServletContext().getContextPath() + "/GreetingsPage";
		User user = (User) req.getSession().getAttribute("user");
		int userId = user.getId();
		
		Boolean alreadySubmitted = submissionService.hasAlreadySubmitted(userId);

		if (alreadySubmitted) {
			res.sendRedirect(greetingsPath);
			return;
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
		
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	
	public void destroy() {
		// TODO Auto-generated method stub
	}
}
