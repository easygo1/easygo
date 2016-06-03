package com.easygo.control;

/**
 * @Author PengHong
 *
 * @Date 2016年5月26日 下午6:47:08
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.easygo.model.beans.gson.GsonAboutHouse;
import com.easygo.model.beans.gson.GsonUserInfoHobby;
import com.easygo.model.beans.house.Equipment;
import com.easygo.model.beans.house.House;
import com.easygo.model.beans.house.HouseCollect;
import com.easygo.model.beans.house.HousePhoto;
import com.easygo.model.beans.order.Assess;
import com.easygo.model.beans.order.Orders;
import com.easygo.model.beans.user.User;
import com.easygo.model.dao.house.IHouseDAO;
import com.easygo.model.dao.house.IHouseEquipmentDAO;
import com.easygo.model.dao.house.IHousePhotoDAO;
import com.easygo.model.dao.order.IAssessDAO;
import com.easygo.model.dao.order.IOrderDAO;
import com.easygo.model.dao.user.IHobbyDAO;
import com.easygo.model.dao.user.IHouseCollectDAO;
import com.easygo.model.dao.user.IUserDAO;
import com.easygo.model.dao.user.IUserHobbyDAO;
import com.easygo.model.impl.house.IHouseDAOImpl;
import com.easygo.model.impl.house.IHouseEquipmentDAOImpl;
import com.easygo.model.impl.house.IHousePhotoDAOImpl;
import com.easygo.model.impl.order.IAssessDAOImpl;
import com.easygo.model.impl.order.IOrderDAOImpl;
import com.easygo.model.impl.user.IHobbyImpl;
import com.easygo.model.impl.user.IHouseCollectDAOImpl;
import com.easygo.model.impl.user.IUserDAOImpl;
import com.easygo.model.impl.user.IUserHobbyDAOImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/appservlet")
public class AppServlet extends HttpServlet {

	private static final long serialVrsionUID = 1L;
	// 用于输出数据
	private PrintWriter mPrintWriter;

	// user的相关对象
	IUserDAO userdao;
	List<User> userList;
	int user_id;
	String user_no;
	User user;
	String user_photo;
	String user_phone;
	String user_password;
	GsonUserInfoHobby userInfoHobby;

	// Hobby的相关对象
	IHobbyDAO hobbydao;
	String hobby_name;
	int hobby_id;

	// Userhobby的相关对象
	IUserHobbyDAO userhobbydao;
	List<Integer> userhobbyidlist;
	List<String> userhobbyNamelist;

	// Order的相关对象
	IOrderDAO orderDAO;
	List<Orders> orderList;
	Orders orders;

	// House的相关对象
	IHouseDAO housedao;
	List<House> houseList;
	House house;
	int house_id;

	// HousePhoto的相关对象
	IHousePhotoDAO housePhotoDAO;
	HousePhoto housePhoto;
	List<HousePhoto> housePhotoList;

	// HouseCollect的相关对象
	HouseCollect houseCollect;
	IHouseCollectDAO houseCollectDAO;
	List<HouseCollect> houseCollectList;
	int house_collect_id;

	// HouseEquipment的相关对象
	IHouseEquipmentDAO houseEquipmentDAO;
	Equipment equipment;
	List<Equipment> houseEquipmentList;

	// Assess的相关对象
	IAssessDAO assessDAO;
	// 存每个房源评价的数量
	List<Integer> assessList;
	Assess assess;

	Gson gson;
	// gson.toJson()的结果
	String result;
	boolean flag;
	Type type;

	public AppServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
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

		case "login":
			user_phone = request.getParameter("user_phone");
			user_password = request.getParameter("user_password");
			System.out.println(user_phone);
			System.out.println(user_password);
			// 进行登录操作
			user = new User();
			userdao = new IUserDAOImpl();
			String token = userdao.login(user_phone, user_password);
			System.out.println(token);
			if (token != null) {
				mPrintWriter.write(token);
				System.out.println("登录成功");
			}

			mPrintWriter.close();
			break;
		case "register":
			// 接收到android端传过来的手机号和密码（手机号相当于用户名）
			user_phone = request.getParameter("user_phone");
			user_password = request.getParameter("user_password");
			// 对用户进行注册
			user = new User();
			user.setUser_phone(user_phone);
			user.setUser_password(user_password);

			userdao = new IUserDAOImpl();
			if (userdao.register(user)) {
				System.out.println("注册成功");
			}
			// mPrintWriter.write(userdao.register(user));
			mPrintWriter.close();
			break;
		case "addUser":
			user = new User();
			try {
				BeanUtils.populate(user, request.getParameterMap());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(user.getUser_no());
			if (userdao.addUser(user)) {
				System.out.println("成功");
			}

			break;
		case "addUserSuccess":
			break;
		case "deleteUser":
			/*// 得到要删除的user_no
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
*/			break;
		case "getAllUser":
			userList = new ArrayList<User>();
			userdao = new IUserDAOImpl();
			userList = userdao.selectAllUser();
			request.setAttribute("userList", userList);
			request.getRequestDispatcher("jsp/user/user.jsp").forward(request,
					response);
			break;
		case "findoneUser":
			/*// 得到要查询的user_no
			user_no = Integer.valueOf(request.getParameter("no"));
			userdao = new IUserDAOImpl();
			user = userdao.findSpecUserByNo(user_no);
			// 属性名为oneUser
			request.setAttribute("oneUser", user);
			request.getRequestDispatcher("jsp/user/selectOneUser.jsp").forward(
					request, response);*/
			break;
		case "updateUserByNo":
			//根据账号更新用户信息
			user_no = request.getParameter("user_no");
			System.out.println("我是用户no"+user_no);
			userdao = new IUserDAOImpl();
			user=new User();
			user.setUser_realname(request.getParameter("user_realname"));
			user.setUser_nickname(request.getParameter("user_nickname"));
			user.setUser_photo(request.getParameter("user_photo"));
			user.setUser_sex(request.getParameter("user_sex"));
			user.setUser_address_province(request.getParameter("user_address_province"));
			user.setUser_address_city(request.getParameter("user_address_city"));
			user.setUser_mood(request.getParameter("user_mood"));
			user.setUser_mail(request.getParameter("user_mail"));
			user.setUser_birthday(request.getParameter("user_birthday"));
			userdao.updateUser(user_no, user);
			break;
		case "selectInfoById":
			//点击我的信息 根据用户id得到用户的信息
			user_id = Integer.valueOf(request.getParameter("user_id"));
			System.out.println("我是用户id"+user_id);
			userdao = new IUserDAOImpl();
			user=new User();
			user=userdao.findSpecUserById(user_id);
			//查找用户的全部爱好
			userhobbyidlist=new ArrayList<Integer>();
			userhobbyNamelist=new ArrayList<String>();
			userhobbydao=new IUserHobbyDAOImpl();
			hobbydao=new IHobbyImpl();//爱好的操作
			userhobbyidlist=userhobbydao.selectAllUserHobbyById(user_id);
			for(int i=0;i<userhobbyidlist.size();i++){
				hobby_id=userhobbyidlist.get(i);
				hobby_name=hobbydao.selectNameByHobbyId(hobby_id);
				userhobbyNamelist.add(hobby_name);
			}
			userInfoHobby=new GsonUserInfoHobby(user, userhobbyNamelist);
			gson=new Gson();
			result=gson.toJson(userInfoHobby);
			mPrintWriter.write(result);
			mPrintWriter.close();
			break;
		case "updateUserById":
			//根据账号更新用户信息
			user_id = Integer.valueOf(request.getParameter("user_id"));
			System.out.println("我是用户id"+user_id);
			userdao = new IUserDAOImpl();
			user=new User();
			user.setUser_realname(new String(request.getParameter("user_realname").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_nickname(new String(request.getParameter("user_nickname").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_sex(new String(request.getParameter("user_sex").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_job(new String(request.getParameter("user_job").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_address_province(new String(request.getParameter("user_address_province").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_address_city(new String(request.getParameter("user_address_city").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_mood(new String(request.getParameter("user_mood").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_mail(new String(request.getParameter("user_mail").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_birthday(new String(request.getParameter("user_birthday").getBytes("iso8859-1"),"UTF-8"));
			flag=userdao.updateUserById(user_id, user);
			mPrintWriter.write("更新user返回结果"+flag);
			mPrintWriter.close();
			break;
		case "selectHobbyidByHobbyName":
			//根据爱好的名称找到爱好的id
			hobby_name="";
			hobbydao=new IHobbyImpl();
			hobby_id=hobbydao.SelectHobbyIDByHobbyName("读书");
			mPrintWriter.write("返回结果"+hobby_id);
			mPrintWriter.close();
			break;
		case "insertHobby":
			//在用户的爱好表中插入数据
			userhobbydao=new IUserHobbyDAOImpl();
			flag=userhobbydao.inssertUserHobby(1, 1);
			mPrintWriter.write("返回结果"+flag);
			mPrintWriter.close();
			break;
		case "selectExistUserHobby":
			//判断是否已经存在某个爱好
			userhobbydao=new IUserHobbyDAOImpl();
			flag=userhobbydao.selectExistUserHobby(1, 3);
			mPrintWriter.write("返回结果"+flag);
			mPrintWriter.close();
			break;
		case "selectNameByHobbyId":
			//根据hobbyid查询出爱好名字
			hobbydao=new IHobbyImpl();//爱好的操作
			hobby_id=Integer.valueOf(request.getParameter("hobby_id"));
			hobby_name=hobbydao.selectNameByHobbyId(hobby_id);
			mPrintWriter.write("返回结果"+hobby_name);
			mPrintWriter.close();
			break;
		case "selectAllUserHobbyById":
			//查找用户的全部爱好
			userhobbyidlist=new ArrayList<Integer>();
			userhobbyNamelist=new ArrayList<String>();
			userhobbydao=new IUserHobbyDAOImpl();
			hobbydao=new IHobbyImpl();//爱好的操作
			user_id=Integer.valueOf(request.getParameter("user_id"));
			userhobbyidlist=userhobbydao.selectAllUserHobbyById(user_id);
			for(int i=0;i<userhobbyidlist.size();i++){
				hobby_id=userhobbyidlist.get(i);
				hobby_name=hobbydao.selectNameByHobbyId(hobby_id);
				userhobbyNamelist.add(hobby_name);
			}
			mPrintWriter.write("返回爱好查询结果"+userhobbyNamelist.toString());
			mPrintWriter.close();
			break;
		case "updateUserHobbyByid":
			hobbydao=new IHobbyImpl();//爱好的操作
			userhobbydao=new IUserHobbyDAOImpl();//用户爱好的操作
			user_id=Integer.valueOf(request.getParameter("user_id"));
			String lables=new String(request.getParameter("lables").getBytes("iso8859-1"),"UTF-8");
			List<String> hobbylist=new ArrayList<String>();
			gson=new Gson();
			type = new TypeToken<List<String>>() {
            }.getType();
            hobbylist = gson.fromJson(lables, type);
            userhobbydao.DeleteUserHobbyByUserId(user_id);//先删除再进行添加
            for(int i=0;i<hobbylist.size();i++){
            	int hobby_id=hobbydao.SelectHobbyIDByHobbyName(hobbylist.get(i));//得到选择的爱好的id            	         		
            		flag=userhobbydao.inssertUserHobby(user_id,hobby_id);
            		System.out.println(flag+"::"+i);
            }
			break;
		case "updateUserPhoto":
			// 得到要更新的用户id user_no,和头像地址
			user_id = Integer.valueOf(request.getParameter("user_id"));
			user_photo = request.getParameter("user_photo");
			userdao = new IUserDAOImpl();
			flag= userdao.updateUserPhoto(user_id, user_photo);
			System.out.print("头像上传结果" + flag);
			break;
		case "selectPhotoMoodByUserId":
			//通过用户id得到用户的头像和签名
			user_id = Integer.valueOf(request.getParameter("user_id"));
			userdao = new IUserDAOImpl();
			user=userdao.selectPhotoMoodByUserId(user_id);
			gson = new Gson();
			result = gson.toJson(user);
			mPrintWriter.write(result);
			mPrintWriter.close();
			break;

		case "addOrders":
			orderDAO = new IOrderDAOImpl();
			// 获取到订单信息的每个字段
			// BeanUtils.populate(order, request.getParameterMap());
			// 改成成员变量
			house_id = Integer.valueOf(request.getParameter("house_id"));
			user_id = Integer.valueOf(request.getParameter("user_id"));
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
		// 得到所有房间
		case "getAllHouse":
			houseList = new ArrayList<House>();
			housedao = new IHouseDAOImpl();
			houseList = housedao.selectAllHouse();
			gson = new Gson();
			result = gson.toJson(houseList);
			mPrintWriter.write(result);
			mPrintWriter.close();
			break;
		case "getAllHouseByCity":
			/*
			 * App传过来的参数（city,cur,userid）
			 * 
			 * 得到某个城市的所有房子，以及房子对应的图片，以及对应的用户，
			 * 
			 * 首先接收到要查询的城市（还要有一个页码，用于判断应该查询哪几个房间），然后根据城市去查询得到所有的房间，
			 * 然后根据得到的房间，查询房屋对应的图片， 然后根据房子中的房东ID去查询房东， 根据房东ID查询房东的头像，
			 * 然后，根据房东ID找到评价表，得到评论的条数，以及星级 最后，根据使用APP的用户ID去找到收藏表， 将收藏的房屋爱心变成红色
			 */
			// 城市
			// String house_address_city = "苏州市";
			String house_address_city = request.getParameter("city");
			// 页码
			// String cur = 1 + "";
			String cur = request.getParameter("cur");
			// 用户id
			user_id = Integer.parseInt(request.getParameter("userid"));

			houseList = new ArrayList<House>();
			housedao = new IHouseDAOImpl();
			// 得到了所有该城市房源
			houseList = housedao.findSpecHouseByCity(house_address_city,
					Integer.parseInt(cur));
			// System.out.println(houseList.get(1).getHouse_address_city());
			// 得到房源的图片
			housePhotoDAO = new IHousePhotoDAOImpl();
			housePhotoList = new ArrayList<HousePhoto>();
			// 得到房源的房东
			userList = new ArrayList<>();
			userdao = new IUserDAOImpl();
			// 得到房源的评价
			assessDAO = new IAssessDAOImpl();
			assessList = new ArrayList<>();
			// 得到用户的收藏
			houseCollectDAO = new IHouseCollectDAOImpl();

			// 遍历查询
			for (House house : houseList) {
				// 房源图片List
				housePhoto = housePhotoDAO.selectSpecIHousePhotoFirst(house
						.getHouse_id());
				housePhotoList.add(housePhoto);
				// 房源的评价的数量

				assessList.add(assessDAO.selectAllAssess(house.getHouse_id())
						.size());
				// 用户List
				user = userdao.findSpecUserById(house.getUser_id());
				userList.add(user);
			}
			// 用户收藏
			houseCollectList = houseCollectDAO
					.findHouseCollectByUserId(user_id);

			GsonAboutHouse gsonAboutHouse = new GsonAboutHouse(houseList,
					userList, housePhotoList, assessList, houseCollectList);

			gson = new Gson();
			result = gson.toJson(gsonAboutHouse);
			mPrintWriter.write(result);
			mPrintWriter.close();
			break;
		case "getHouseDetailByID":
			/*
			 * 得到某个具体的房间的所有信息 1、房源信息2、房源图片、3、用户收藏4、配套设施6、房东信息
			 * 
			 * 5、可租日期（等用户点击查看后再次请求） 7、相关评价（7，在翻到的时候再请求）
			 */
			// 房源id
			house_id = Integer.parseInt(request.getParameter("houseid"));
			// 用户id
			user_id = Integer.parseInt(request.getParameter("userid"));
			// 得到房源
			housedao = new IHouseDAOImpl();
			// 得到房源的图片
			housePhotoDAO = new IHousePhotoDAOImpl();
			housePhotoList = new ArrayList<HousePhoto>();
			// 得到房源的设施
			houseEquipmentDAO = new IHouseEquipmentDAOImpl();
			houseEquipmentList = new ArrayList<>();
			// 得到房源的房东
			userdao = new IUserDAOImpl();
			// 得到房源的评价
			assessDAO = new IAssessDAOImpl();
			assessList = new ArrayList<>();
			// 得到用户的收藏
			houseCollectDAO = new IHouseCollectDAOImpl();

			// 1.得到了所有该房源的所有信息
			house = housedao.findSpecHouseById(house_id);
			// 2.房源图片List
			housePhotoList = housePhotoDAO.selectSpecIHousePhoto(house_id);
			// 3.用户收藏
			houseCollectList = houseCollectDAO
					.findHouseCollectByUserId(user_id);
			// 4.配套设施
			houseEquipmentList = houseEquipmentDAO
					.selectHouseEquipment(house_id);
			// 6.房东的信息
			user = userdao.findSpecUserById(house.getUser_id());

			// GsonAboutHouse gsonAboutHouse = new GsonAboutHouse(houseList,
			// userList, housePhotoList, assessList, houseCollectList);

			gson = new Gson();
			// result = gson.toJson(gsonAboutHouse);
			mPrintWriter.write(result);
			mPrintWriter.close();
			break;
		// 删除某个用户的收藏表的一条数据
		case "deleteHouseCollect":
			houseCollectDAO = new IHouseCollectDAOImpl();
			house_collect_id = Integer.parseInt(request
					.getParameter("houseCollectId"));
			houseCollectDAO.delHouseCollect(house_collect_id);
			break;
		// 增加某个用户的收藏表的一条数据
		case "addHouseCollect":
			houseCollectDAO = new IHouseCollectDAOImpl();
			houseCollect = new HouseCollect();
			user_id = Integer.parseInt(request.getParameter("userid"));
			house_id = Integer.parseInt(request.getParameter("houseid"));
			houseCollect.setUser_id(user_id);
			houseCollect.setHouse_id(house_id);
			houseCollectDAO.addHouseCollect(houseCollect);
			break;
		// 根据用户的id查出此人的所有订单
		case "getAllOrderByUserId":
			orderDAO = new IOrderDAOImpl();
			orderList = new ArrayList<Orders>();
			String userid = request.getParameter("userid");
			// orderList = orderDAO.selectsomeOrders(userid, 1);
			orderList = orderDAO.selectAllOrders(1);
			gson = new Gson();
			result = gson.toJson(orderList);
			// gson.tojson
			String houseJson1 = gson.toJson(house, House.class);
			// Log.e("ck",houseJson);
			System.out.println(houseJson1);

			mPrintWriter.write(houseJson1);
			mPrintWriter.close();
			break;
		// 根据发布房源传来的数据进行添房源
		case "addHouse":
			housedao = new IHouseDAOImpl();
			houseList = new ArrayList<House>();
			String house_style = request.getParameter("house_style");
			String house_most_num = request.getParameter("house_most_num");
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
