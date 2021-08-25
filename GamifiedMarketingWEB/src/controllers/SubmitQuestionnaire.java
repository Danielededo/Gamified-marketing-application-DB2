package controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;

import entities.Product;
import services.AdminService;
import services.ProductService;
import services.QuestionService;
import services.UserService;

/**
 * Servlet implementation class Creation
 */
@WebServlet("/SubmitQuestionnaire")
public class SubmitQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "services/ProductService")
	private ProductService productService;
	
	@EJB(name = "services/QuestionService")
	private QuestionService questionService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitQuestionnaire() {
        super();
        // TODO Auto-generated constructor stub
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
		String name = request.getParameter("productName");
		String date = request.getParameter("date");
		String image = request.getParameter("img");
		byte[] b = image.getBytes(StandardCharsets.UTF_8);
		
		//List<Question> questions = ;
		
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date dateProd = null;
		boolean dateTaken = false;
		
		List<Product> products = productService.getAllProducts();
		for(int i=0;i<products.size();i++) {
			if(dateFormat.format(products.get(i).getDate()) == date)
				dateProd = products.get(i).getDate();
				dateTaken = true;
		}
        if (dateTaken) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "There is already a questionnaire scheduled for the selected date");
            return;
        }
        
        Product product = productService.addProduct(name, b, dateProd);
        //same for each question
        
        String path = "/GoToCreationPage";
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

}
