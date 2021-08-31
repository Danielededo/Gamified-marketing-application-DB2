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
import entities.Question;
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

	public DeleteQuestionnaire() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productId");
		Integer prod;
		if (productId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id parameter");
			return;
		}
		try {
			prod = Integer.parseInt(productId);
		} catch (NumberFormatException nfe) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid non numerical input");
			return;
		}
		try {
			productService.deleteProduct(prod);
		} catch (IllegalArgumentException iae) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, iae.getMessage());
			return;
		}

		//same for each question


		String path = "/DeletionPage";
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}



}
