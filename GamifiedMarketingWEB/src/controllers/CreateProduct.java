package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.thymeleaf.TemplateEngine;

import entities.Product;
import services.AdminService;
import services.ProductService;
import services.QuestionService;
import services.UserService;
import utils.ImageUtils;

/**
 * Servlet implementation class Creation
 */
@WebServlet("/CreateProduct")
@MultipartConfig
public class CreateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB(name = "services/ProductService")
	private ProductService productService;

	@EJB(name = "services/QuestionService")
	private QuestionService questionService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateProduct() {
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
		Part image = request.getPart("img");


		byte[] b;
		Date dateProd;
		LocalDate now = LocalDateTime.now().plus(Duration.ofHours(2)).toLocalDate();

		if(name == null || name.isEmpty() || date == null || date.isEmpty() || image == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input parameters");			
			return;
		}

		try {
			InputStream photoContent = image.getInputStream();
			b = ImageUtils.readImage(photoContent);
		} catch (IOException ioe) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to load the image file");			
			return;
		}
		try {
			dateProd = Date.valueOf(date);
		} catch(Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to load the date");			
			return;
		}

		if(dateProd.before(Date.valueOf(now))) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid value of the date");			
			return;
		}
		List<Product> products = productService.getAllProducts();

		boolean dateTaken = false;
		for(Product p: products) {
			if(p.getDate().equals(dateProd)) {
				dateTaken = true;
			}
		}

		if (dateTaken) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "There is already a questionnaire scheduled for the selected date");
			return;
		}
		productService.addProduct(name, b, dateProd);

		//same for each question

		String path = getServletContext().getContextPath() + "/CreationPage?product";
		response.sendRedirect(path);

	}

}
