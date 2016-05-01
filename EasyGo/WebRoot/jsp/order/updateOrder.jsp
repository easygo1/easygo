<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.easygo.model.beans.order.Orders"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加订单</title>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/dashboard.css" rel="stylesheet">
</head>
<body style="text-align: center">
	<h3>修改订单</h3>
	<form action="easygoservlet?methods=updateorder&&order_id=${orders.order_id}" method="post">
		<table class="table table-striped" border="0"
			style="text-align: center">
			<thead>
				<tr>
					<th style="text-align: center">属性名</th>
					<th style="text-align: center">修改说明</th>
					<th style="text-align: center">当前值</th>
					<th style="text-align: center">修改值</th>
					<th style="text-align: center">提示信息</th>
				</tr>
			</thead>
			<tbody style="text-align: center">
			<tr>
					<td>订单编号</td>
					<td>不能修改</td>
					<td>${orders.order_id}</td>
					<td></td>
				</tr>
				<tr>
					<td>房源编号</td>
					<td>只允许在已有房源中选择</td>
					<td><input type="text" name="house_id" value="${orders.house_id}"></td>
					<td></td>
				</tr>
				<tr>
					<td>房客编号</td>
					<td>只允许在已注册用户中选择</td>
					<td><input type="text" name="user_id" value="${orders.user_id}"></td>
					<td></td>
				</tr>
				<tr>
					<td>入住人数</td>
					<td>不能超过房源规定最大入住人数</td>
					<td><input type="text" name="checknum" value="${orders.checknum}"></td>
					<td></td>
				</tr>
				<tr>
					<td>入住时间</td>
					<td>在房源是开放未租状态下</td>
					<td><input type="text" name="checktime" value="${orders.checktime}"></td>
					<td></td>
				</tr>
				<tr>
					<td>离开时间</td>
					<td>与入住时间相比不能超过房源最大入住时间</td>
					<td><input type="text" name="leavetime" value="${orders.leavetime}"></td>
					<td></td>
				</tr>
				<tr>
					<td>订单总额</td>
					<td>根据计算得出</td>
					<td><input type="text" name="total" value="${orders.total}"></td>
					<td></td>
				</tr>
				<tr>
					<td>联系方式</td>
					<td>只允许字母数字长度小于30</td>
					<td><input type="text" name="tel" value="${orders.tel}"></td>
					<td></td>
				</tr>
				<tr>
					<td>订单状态</td>
					<td>待确认，待付款，待入住，已完成，取消</td>
					<td><select name="order_state">
					        <option>${orders.order_state}</option>
							<option value="待确认">待确认</option>
							<option value="待付款">待付款</option>
							<option value="待入住">待入住</option>
							<option value="已完成">已完成</option>
							<option value="已取消">已取消</option>
					</select></td>
					<td></td>
				</tr>
				<tr>
					<td>下单时间</td>
					<td>获取当前系统时间</td>
					<td>${orders.order_time}</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" value="确定" class="btn btn-primary"> 
		<input type="reset" value="重置" class="btn btn-info"> 
		<a href="easygoservlet?methods=getAllorder"><input type="button" value="返回" class="btn btn-success"></a>
	</form>

</body>
</html>