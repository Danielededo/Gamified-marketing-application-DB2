package controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;

import entities.Product;
import services.ProductService;
import services.QuestionService;

/**
 * Servlet implementation class DeleteQuestionnaire
 */
@WebServlet("/DeleteQuestionnaire")
public class DeleteQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "services/ProductService")
	private ProductService productService;
	
	@EJB(name = "services/QuestionService")
	private QuestionService questionService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuestionnaire() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productId");
        productService.deleteProduct(Integer.parseInt(productId));
        //same for each question
        
        String path = "/DeletionPage";
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
