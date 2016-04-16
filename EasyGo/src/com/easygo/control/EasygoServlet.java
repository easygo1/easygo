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
		// 获取当前页面显示的第几页
		String cur = request.getParameter("cur");
		// 第一次加载页面，让其显示第一页
		if (cur == null) {
			cur = "1";
		}
		String method = request.getParameter("methods");

		switch (method) {
		case "getAllUser":
			List<User> userList = new ArrayList<User>();
			IUserDAO userdao = new IUserDAOImpl();
			userList = userdao.selectAllUser();
			request.setAttribute("userList", userList);
			request.getRequestDispatcher("jsp/user/user.jsp").forward(request,
					response);
			break;
		case "addOrders":
			IOrderDAO order = new IOrderDAOImpl();
			// 获取到订单信息的每个字段
			// BeanUtils.populate(order, request.getParameterMap());
			int house_id = Integer.valueOf(request.getParameter("house_id"));
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			int checknum = Integer.valueOf(request.getParameter("checknum"));
			String checktime = request.getParameter("checktime");
			String leavetime = request.getParameter("leavetime");
			double total = Double.valueOf(request.getParameter("total"));
			String tel = request.getParameter("tel");
			String order_state = request.getParameter("order_state");
			String order_time = request.getParameter("order_time");
			// 添加订单信息
			// 用网页信息初始化订单对象
			Orders orders = new Orders(house_id, user_id, checknum, checktime,
					leavetime, total, tel, order_state, order_time);
			// 向数据库中添加信息
			order.addOrders(orders);

			request.getRequestDispatcher("jsp/order/order.jsp").forward(
					request, response);
			break;
		case "getAllorder":
			IOrderDAO order1 = new IOrderDAOImpl();
			List<Orders> orderlist = new ArrayList<Orders>();
			orderlist = order1.selectAllOrders(Integer.parseInt(cur));
			// 总共被分成了几页
			int totalPage = order1.getTotalPage();
			request.setAttribute("orderlist", orderlist);
			request.setAttribute("cur", cur);
			request.setAttribute("totalPage", totalPage);
			request.getRequestDispatcher("jsp/order/order.jsp").forward(
					request, response);
			break;
		case "delOrders":
			IOrderDAO order2 = new IOrderDAOImpl();
			int order_id = Integer.parseInt(request.getParameter("order_id"));
			if (order2.delOrders(order_id)) {
				request.getRequestDispatcher(
						"easygoservlet?methods=getAllorder").forward(request,
						response);
			}
			break;
		default:
			break;
		}
	}
}
