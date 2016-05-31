package com.easygo.control;

/**
 * @Author PengHong
 *
 * @Date 2016年5月26日 下午6:47:08
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.easygo.model.beans.house.House;
import com.easygo.model.beans.order.Orders;
import com.easygo.model.beans.user.User;
import com.easygo.model.dao.house.IHouseDAO;
import com.easygo.model.dao.order.IOrderDAO;
import com.easygo.model.dao.user.IUserDAO;
import com.easygo.model.impl.house.IHouseDAOImpl;
import com.easygo.model.impl.order.IOrderDAOImpl;
import com.easygo.model.impl.user.IUserDAOImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/appservlet")
public class AppServlet extends HttpServlet {

	private static final long serialVrsionUID = 1L;
	// 用于输出数据
	private PrintWriter mPrintWriter;

	// user的相关方法
	IUserDAO userdao;
	// user的相关对象
	List<User> userList;
	int user_no;
	String user_photo;//头像地址
	User user;

	// Order的相关方法
	IOrderDAO orderDAO;
	// House相关对象
	List<Orders> orderList;
	Orders orders;
	
	// House的相关方法
	IHouseDAO housedao;
	// House相关对象
	List<House> houseList;
	House house;
	
	Gson gson;
	//gson.toJson()的结果
	String result;
	public AppServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 初始化
		mPrintWriter = response.getWriter();

		String method = request.getParameter("methods");

		switch (method) {
		case "goAddUser":
			request.setAttribute("oneUser", user);
			request.getRequestDispatcher("jsp/user/addUser.jsp").forward(
					request, response);
			break;
		case "addUser":
			user = new User();
			try {
				BeanUtils.populate(user, request.getParameterMap());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(user.getUser_no());
			// userdao.addUser(user);
			if (userdao.addUser(user)) {
				System.out.println("成功");
			}

			break;
		case "addUserSuccess":
			break;
		case "deleteUser":
			// 得到要删除的user_no
			user_no = Integer.valueOf(request.getParameter("no"));
			userdao = new IUserDAOImpl();
			boolean delResult = userdao.delUser(user_no);
			// 属性名为oneUser
			request.setAttribute("delResult", delResult);
			if (delResult) {
				response.sendRedirect("easygoservlet?methods=getAllUser");
			}
			// request.getRequestDispatcher("jsp/user/user.jsp").forward(request,
			// response);
			break;
		case "getAllUser":
			userList = new ArrayList<User>();
			userdao = new IUserDAOImpl();
			userList = userdao.selectAllUser();
			request.setAttribute("userList", userList);
			request.getRequestDispatcher("jsp/user/user.jsp").forward(request,
					response);
			break;
		case "findoneUser":
			// 得到要查询的user_no
			user_no = Integer.valueOf(request.getParameter("no"));
			userdao = new IUserDAOImpl();
			user = userdao.findSpecStudent(user_no);
			// 属性名为oneUser
			request.setAttribute("oneUser", user);
			request.getRequestDispatcher("jsp/user/selectOneUser.jsp").forward(
					request, response);
			break;
		case "updateUser":
			// 得到要查询的user_no
			user_no = Integer.valueOf(request.getParameter("no"));
			userdao = new IUserDAOImpl();
			user = userdao.findSpecStudent(user_no);
			// 属性名为oneUser
			request.setAttribute("oneUser", user);
			request.getRequestDispatcher("jsp/user/selectOneUser.jsp").forward(
					request, response);
			break;
		case "updateUserPhoto":
			//得到要更新的用户id user_no,和头像地址
			user_no=Integer.valueOf(request.getParameter("user_no"));
			user_photo=request.getParameter("user_photo_path");
			userdao = new IUserDAOImpl();
			boolean s=userdao.updateUserPhoto(user_no, user_photo);
			System.out.print("头像上传结果"+s);
			break;

		case "addOrders":
			orderDAO = new IOrderDAOImpl();
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
			orders = new Orders(house_id, user_id, checknum, checktime,
					leavetime, total, tel, order_state, order_time);
			// 向数据库中添加信息
			orderDAO.addOrders(orders);

			request.getRequestDispatcher("jsp/order/order.jsp").forward(
					request, response);
			break;
		// 得到全部订单
		case "getAllorder":

			break;
		case "delOrders":

			break;
		// 根据订单号得到订单
		case "getorderbyorderid":

			break;
		// 修改订单
		case "updateorder":
			
			break;
		case "selectsomeOrders":
			
			break;
		case "getAllHouse":
			houseList = new ArrayList<House>();
			housedao = new IHouseDAOImpl();
			houseList = housedao.selectAllHouse();
			gson = new Gson();
			result = gson.toJson(houseList);
			mPrintWriter.write(result);
			mPrintWriter.close();
			break;
		//根据用户的id查出此人的所有订单
		case "getAllOrderByUserId":
			orderDAO= new IOrderDAOImpl();
			orderList= new ArrayList<Orders>();
			String userid = request.getParameter("userid");
			//orderList = orderDAO.selectsomeOrders(userid, 1);
			orderList = orderDAO.selectAllOrders(1);
			gson = new Gson();
			result = gson.toJson(orderList);
            //gson.tojson
            String houseJson1 = gson.toJson(house,House.class);
            //Log.e("ck",houseJson);
            System.out.println(houseJson1);
			
			mPrintWriter.write(houseJson1);
			mPrintWriter.close();
			break;
		//根据发布房源传来的数据进行添房源	
		case "addHouse":
			housedao = new IHouseDAOImpl();
			houseList = new ArrayList<House>();
			String house_style = request.getParameter("house_style");
			String house_most_num =request.getParameter("house_most_num");
			String house_one_price = request.getParameter("house_one_price");
			String house_add_price = request.getParameter("house_add_price");
			String house_limit_sex = request.getParameter("house_limit_sex");
			String house_stay_time = request.getParameter("house_stay_time");
			house = new House();
			house.setHouse_style(house_style);
			house.setHouse_most_num(Integer.valueOf(house_most_num));
			house.setHouse_one_price(Double.valueOf(house_one_price));
			house.setHouse_add_price(Double.valueOf(house_add_price));
			house.setHouse_limit_sex(house_limit_sex);
			house.setHouse_stay_time(Integer.valueOf(house_stay_time));
			housedao.addHouse(house);
			break;

		default:
			break;
		}
	}
}
