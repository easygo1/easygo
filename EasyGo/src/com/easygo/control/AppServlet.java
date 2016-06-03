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

import com.easygo.model.beans.chat.Friend;
import com.easygo.model.beans.gson.GsonAboutHouse;
import com.easygo.model.beans.house.Equipment;
import com.easygo.model.beans.house.House;
import com.easygo.model.beans.house.HouseCollect;
import com.easygo.model.beans.house.HouseEquipment;
import com.easygo.model.beans.house.HousePhoto;
import com.easygo.model.beans.order.Assess;
import com.easygo.model.beans.order.Orders;
import com.easygo.model.beans.user.User;
import com.easygo.model.dao.chat.IFriendDAO;
import com.easygo.model.dao.house.IHouseDAO;
import com.easygo.model.dao.house.IHouseEquipmentDAO;
import com.easygo.model.dao.house.IHousePhotoDAO;
import com.easygo.model.dao.order.IAssessDAO;
import com.easygo.model.dao.order.IOrderDAO;
import com.easygo.model.dao.user.IHouseCollectDAO;
import com.easygo.model.dao.user.IUserDAO;
import com.easygo.model.impl.chat.IFriendDAOImpl;
import com.easygo.model.impl.house.IHouseDAOImpl;
import com.easygo.model.impl.house.IHouseEquipmentDAOImpl;
import com.easygo.model.impl.house.IHousePhotoDAOImpl;
import com.easygo.model.impl.order.IAssessDAOImpl;
import com.easygo.model.impl.order.IOrderDAOImpl;
import com.easygo.model.impl.user.IHouseCollectDAOImpl;
import com.easygo.model.impl.user.IUserDAOImpl;
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
	
	//friend的相关对象
	IFriendDAO frienddao;
	List<Friend> friendlist;
	int user_id1=-1,user_id2=-1;
	Friend friend;
	
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
	HouseEquipment houseEquipment;

	// Assess的相关对象
	IAssessDAO assessDAO;
	// 存每个房源评价的数量
	List<Integer> assessList;
	Assess assess;

	Gson gson;
	Type type;
	// gson.toJson()的结果
	String result;
	boolean flag;

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
		case "a":
			break;
		case "goAddUser":
			request.setAttribute("oneUser", user);
			request.getRequestDispatcher("jsp/user/addUser.jsp").forward(
					request, response);
			break;
		//用户登录	
		case "login":
			user_phone=request.getParameter("user_phone");
		    user_password=request.getParameter("user_password");
		    System.out.println("手机号为："+user_phone);
		    System.out.println("密码为："+user_password);
		    //进行登录操作
		    user=new User();
		    userdao= new IUserDAOImpl();
		    String token=userdao.login(user_phone,user_password);
		    String u_id=userdao.selectUserID(user_phone)+"";
		    
		    List<String> list=new ArrayList<>();
		    list.add(u_id);
		    list.add(token);
			if (token != null) {
				gson = new Gson();
				result = gson.toJson(list);
				mPrintWriter.write(result);
				System.out.println("登录成功");
			}
			mPrintWriter.close();
			break;
		//用户注册	
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
		//添加好友	
		case "addFriend":
			//1.获取到双方的手机号
			user=new User();
			userdao= new IUserDAOImpl();
			String phone1=request.getParameter("phone1");
			String phone2=request.getParameter("phone2");
			//2.根据手机号查找user表查找到对应的id
			int user_id1=userdao.selectUserID(phone1);
			int user_id2=userdao.selectUserID(phone2);
			friend=new Friend();
			frienddao=new IFriendDAOImpl();
			friend.setUser_id1(user_id1);
			friend.setUser_id2(user_id2);
			//如果id1和id2不为-1（不为空）的话进行向数据库中插入数据
			if(user_id1!=-1&&user_id2!=-1){
				//1.进行判断所添加的好友（user_id1,user_id2）是否存在于user表中,如果查询出的结果为两条的话，继续进行添加操作
				//判断数据库中是否存在这样一条数据,如果找不到，就向数据库中插入数据
				if(!frienddao.selectTwoFriend(user_id1,user_id2)){
					if(frienddao.addIFriend(friend)){
						System.out.println("好友添加成功");
					}
				}else{
					System.out.println("您已经与该用户为好友");
				}
			}
			break;
		//模糊查找好友
		case "selectFriend":
			
			break;
		//显示好友列表
		case "showfriendlist":
			List<String> friendlist=new ArrayList<>();
			user=new User();
			userdao= new IUserDAOImpl();
			//1.根据phone查找出id
			String phone=request.getParameter("phone");
			user_id=userdao.selectUserID(phone);
			//2.在friend表中查出该id所有的好友的id集合
			friend=new Friend();
			frienddao=new IFriendDAOImpl();
			List<Integer> friend_id_list=frienddao.selectAllFriend(user_id);
			//3.从数据库中 获取到好友id的用户名集合
			for(int i=0;i<friend_id_list.size();i++){
				int id=friend_id_list.get(i);
				friendlist.add(userdao.selectUserPhone(id));
			}
			//4.将获取到的phone数据封装成Gson传送出去
			gson = new Gson();
			result = gson.toJson(friendlist);
			mPrintWriter.write(result);
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
		case "updateUserById":
			//根据账号更新用户信息
			user_id = Integer.valueOf(request.getParameter("user_id"));
			System.out.println("我是用户id"+user_id);
			userdao = new IUserDAOImpl();
			user=new User();
			user.setUser_realname(new String(request.getParameter("user_realname").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_nickname(new String(request.getParameter("user_nickname").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_photo(request.getParameter("user_photo"));
			user.setUser_sex(new String(request.getParameter("user_sex").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_address_province(new String(request.getParameter("user_address_province").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_address_city(new String(request.getParameter("user_address_city").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_mood(new String(request.getParameter("user_mood").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_mail(new String(request.getParameter("user_mail").getBytes("iso8859-1"),"UTF-8"));
			user.setUser_birthday(request.getParameter("user_birthday"));
			flag=userdao.updateUserById(user_id, user);
			mPrintWriter.write("返回结果"+flag);
			mPrintWriter.close();
			break;
		case "updateUserPhoto":
			// 得到要更新的用户id user_no,和头像地址
			user_id = Integer.valueOf(request.getParameter("user_id"));
			user_photo = request.getParameter("user_photo");
			userdao = new IUserDAOImpl();
			boolean s = userdao.updateUserPhoto(user_id, user_photo);
			System.out.print("头像上传结果" + s);
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
			String user_id = request.getParameter("user_id");
			String house_title = request.getParameter("house_title");
			String house_style = request.getParameter("house_style");
			String house_address_province = request.getParameter("house_address_province");
			String house_address_city1 = request.getParameter("house_address_city");
			String house_address_lng = request.getParameter("house_address_lng");
			String house_address_lat = request.getParameter("house_address_lat");
			String house_most_num = request.getParameter("house_most_num");
			String house_one_price = request.getParameter("house_one_price");
			String house_add_price = request.getParameter("house_add_price");
			String house_describe = request.getParameter("house_describe");
			String house_traffic = request.getParameter("house_traffic");
			String house_limit_sex = request.getParameter("house_limit_sex");
			String house_stay_time = request.getParameter("house_stay_time");
			//房源图片地址json字符串
			String photoList = new String(request.getParameter("photoList").getBytes("iso8859-1"),"UTF-8");
			//房源设施json字符串
			String equipmentList = new String(request.getParameter("equipmentList").getBytes("iso8859-1"),"UTF-8");
			type = new TypeToken<List<String>>(){}.getType();
			System.out.println("666"+photoList);
			List<String> mList = new ArrayList<>();
			List<String> mEquipmentList = new ArrayList<>();
			gson = new Gson();
			mList = gson.fromJson(photoList,type);
			mEquipmentList = gson.fromJson(equipmentList,type);
			house = new House();
			house.setUser_id(Integer.valueOf(user_id));
			house.setHouse_title(new String(house_title.getBytes("iso8859-1"),"UTF-8"));
			house.setHouse_style(new String(house_style.getBytes("iso8859-1"),"UTF-8"));
			house.setHouse_address_province(new String(house_address_province.getBytes("iso8859-1"),"UTF-8"));
			house.setHouse_address_city(new String(house_address_city1.getBytes("iso8859-1"),"UTF-8"));
			house.setHouse_address_lng(Double.valueOf(house_address_lng));
			house.setHouse_address_lat(Double.valueOf(house_address_lat));
			house.setHouse_most_num(Integer.valueOf(house_most_num));
			house.setHouse_one_price(Double.valueOf(house_one_price));
			house.setHouse_add_price(Double.valueOf(house_add_price));
			house.setHouse_describe(new String(house_describe.getBytes("iso8859-1"),"UTF-8"));
			house.setHouse_traffic(new String(house_traffic.getBytes("iso8859-1"),"UTF-8"));
			house.setHouse_limit_sex(new String(house_limit_sex.getBytes("iso8859-1"),"UTF-8"));
			house.setHouse_stay_time(Integer.valueOf(house_stay_time));
			housedao.addHouse(house);
			house = housedao.findSpecHouseByUserId(Integer.valueOf(user_id));
			System.out.println("9999");
			housePhoto = new HousePhoto();
			housePhotoDAO = new IHousePhotoDAOImpl();
			for (int i = 0; i < mList.size(); i++) {
				if (i == 0) {
					housePhoto.setHouse_id(house.getHouse_id());
					housePhoto.setHouse_photo_path(mList.get(i));
					housePhoto.setIsFirst(1);
					housePhotoDAO.addSpecIHousePhoto(housePhoto);
				}
				else {
					housePhoto.setHouse_id(house.getHouse_id());
					housePhoto.setHouse_photo_path(mList.get(i));
					housePhoto.setIsFirst(0);
					housePhotoDAO.addSpecIHousePhoto(housePhoto);
				}
			}
			houseEquipment = new HouseEquipment();
			houseEquipmentDAO = new IHouseEquipmentDAOImpl();
			int equipment_id = 0;
			for (int i = 0; i < mEquipmentList.size(); i++) {
				equipment_id = houseEquipmentDAO.selectEquipmentId(mEquipmentList.get(i));
				houseEquipment.setHouse_id(house.getHouse_id());
				houseEquipment.setEquipment_id(equipment_id);
				houseEquipmentDAO.addHouseEquipment(houseEquipment);
			}
			break;

		default:
			break;
		}
	}
}
