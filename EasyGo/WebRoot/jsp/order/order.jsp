<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户列表</title>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/dashboard.css" rel="stylesheet">
</head>
<body>
	<h2>订单管理</h2>
	<div style="text-align: center">
		
		<form action="easygoservlet?methods=selectsomeOrders&&cur=1" method="post">
		<a href="jsp/order/addOrder.jsp" ><button type="button" class="btn btn-info">添加订单</button></a>
		&nbsp;&nbsp;&nbsp;
		<a class="form-inline" href="#">
			<input  class="form-control" placeholder="模糊查询" name="orderserch"/>
		</a>
		<input type="submit" value="确定" class="btn btn-info">
		</form>
	</div>
	<hr>
	<table class="table table-striped" style="text-align: center">
		<thead style="text-align:center">
			<tr>
				<th style="text-align:center">订单编号</th>
				<th style="text-align:center">房源编号</th>
				<th style="text-align:center">房客编号</th>
				<th style="text-align:center">入住人数</th>
				<th style="text-align:center">入住时间</th>
				<th style="text-align:center">离开时间</th>
				<th style="text-align:center">订单总额</th>
				<th style="text-align:center">联系方式</th>
				<th style="text-align:center">订单状态</th>
				<th style="text-align:center">下单时间</th>
				<th colspan="3" style="text-align:center">操作</th>
			</tr>
		</thead>
		<tbody>
		<!-- 与表头一一对应 -->
		<c:forEach items="${requestScope.orderlist}" var="order">
				<tr>
					<td>${order.order_id}</td>
					<td>${order.house_id}</td>
					<td>${order.user_id}</td>
					<td>${order.checknum}</td>
					<td>${order.checktime}</td>
					<td>${order.leavetime}</td>
					<td>${order.total}</td>
					<td>${order.tel}</td>
					<td>${order.order_state}</td>
					<td>${order.order_time}</td>
					
					<td>
						<a href="easygoservlet?methods=getorderbyorderid&&order_id=${order.order_id}">
						<button type="button" class="btn btn-info">修改</button></a></td>
					<td>
						<a href="easygoservlet?methods=delOrders&&order_id=${order.order_id}">
						<button type="button" class="btn btn-danger">删除</button></a>
					</td>
				</tr>
				</c:forEach>
			</tbody>	
	</table>
	<center>
		<c:if test="${cur ==1}">
			<a>首页</a>
			<a>上一页</a>
		</c:if>

		<c:if test="${cur != 1}">
			<a href="/EasyGo/easygoservlet?cur=1&&methods=getAllorder">首页</a>
			<a href="/EasyGo/easygoservlet?cur=${param.cur - 1}&&methods=getAllorder">上一页</a>
		</c:if>

		<c:if test="${cur == requestScope.totalPage}">
			<a>下一页</a>
			<a>尾页</a>
		</c:if>

		<c:if test="${cur != requestScope.totalPage}">
			<a href="/EasyGo/easygoservlet?cur=${cur + 1}&&methods=getAllorder">下一页</a>
			<a href="/EasyGo/easygoservlet?cur=${requestScope.totalPage}&&methods=getAllorder">尾页</a>
		</c:if>

		<p>当前第${cur}页 总共${requestScope.totalPage}页</p>
	</center>
</body>
</html>