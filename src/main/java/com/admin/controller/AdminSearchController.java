package com.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.watch.model.Order;
import com.watch.model.Product;
import com.watch.services.IOrderService;
import com.watch.services.IProductService;
import com.watch.services.Imp.OrderServiceImp;
import com.watch.services.Imp.ProductServiceImp;

/**
 * Servlet implementation class AdminSearchController
 */
@WebServlet({ "/admin-search-product", "/admin-search-order" })
public class AdminSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IProductService pService;
	private IOrderService oService;

	public AdminSearchController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		String action = "";
		if (url.contains("admin-search-product")) {
			action = "admin-search-product";
			request.setAttribute("action", action);
			searchProduct(request, response);
			request.getRequestDispatcher("/view/admin/product.jsp").forward(request, response);
		} else if (url.contains("admin-search-order")) {
			action = "admin-search-order";
			request.setAttribute("action", action);
			searchOrder(request, response);
			request.getRequestDispatcher("/view/admin/order.jsp").forward(request, response);
		}
	}

	private void searchOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		oService = new OrderServiceImp();
		String name = request.getParameter("uname");

		int recordPerPage = 4;

		int cPage = 1;
		if (cPage <= 0) {
			cPage = 1;
		}
		if (request.getParameter("page") != null) {
			cPage = Integer.parseInt(request.getParameter("page"));

		}
		List<Order> list = oService.getOrderByUserName((cPage - 1) * recordPerPage, recordPerPage, name);
		int size = oService.getNumberOfOrder();
		int totalPages = size % recordPerPage == 0 ? size / recordPerPage : (size / recordPerPage) + 1;
		request.setAttribute("currentPage", cPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("listO", list);

	}

	private void searchProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		pService = new ProductServiceImp();
		String name = request.getParameter("pName");
		String sortBy = request.getParameter("sort");
		String cateId = request.getParameter("category");
		int recordPerPage = 4;
		int cPage = 1;
		if (cPage <= 0) {
			cPage = 1;
		}
		if (request.getParameter("page") != null) {
			cPage = Integer.parseInt(request.getParameter("page"));
		}
		List<Product> list = pService.getAllProduct((cPage - 1) * recordPerPage, recordPerPage, cateId, sortBy, null,
				name);
		int size = pService.getNumOfProduct(cateId, null, name);
		int totalPages = size % recordPerPage == 0 ? size / recordPerPage : (size / recordPerPage) + 1;
		request.setAttribute("listP", list);
		request.setAttribute("pName", name);
		request.setAttribute("currentPage", cPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("listP", list);
		request.setAttribute("currentCateId", cateId);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
