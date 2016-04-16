package com.easygo.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easygo.model.beans.order.Orders;
import com.easygo.model.beans.user.User;
import com.easygo.model.dao.order.IOrderDAO;
import com.easygo.model.dao.user.IUserDAO;
import com.easygo.model.impl.order.IOrderDAOImpl;
import com.easygo.model.impl.user.IUserDAOImpl;

/**
 * Servlet implementation class EasygoServlet
 */
@WebServlet("/easygoservlet")
public class EasygoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EasygoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("methods");
		System.out.println(method + "......");
		switch (method) {
		case "getAllUser":
			List<User> userList = new ArrayList<User>();

			IUserDAO userdao = new IUserDAOImpl();

			userList = userdao.selectAllUser();

			request.setAttribute("userList", userList);
			request.getRequestDispatcher("jsp/user/user.jsp").forward(request,
					response);
			break;
		case "":
			System.out.println("默认用户管理界面");
			break;
		case "getAllorder":
			IOrderDAO order = new IOrderDAOImpl();
			List<Orders> orderlist = new ArrayList<Orders>();
			orderlist = order.selectAllOrders();
			// System.out.println("servlet里的判断");
			request.setAttribute("orderlist", orderlist);
			request.getRequestDispatcher("jsp/order/order.jsp").forward(
					request, response);
			break;
		default:
			break;
		}
	}
}
