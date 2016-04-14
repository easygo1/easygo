<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>房源列表</title>
<link href="../../css/bootstrap.min.css" rel="stylesheet">
<link href="../../css/dashboard.css" rel="stylesheet">
</head>
<body>
	<h2>房源管理</h2>
	<div style="text-align: center;">
		<a href="addUser.jsp" ><button type="button" class="btn btn-info">添加房源</button></a>
		&nbsp;&nbsp;&nbsp;
		<a class="form-inline" href="#">
			<input  class="form-control" placeholder="模糊查询">
			<button type="button" class="btn btn-info">查询</button>
		</a>
	</div>
	<hr>
	<table class="table table-striped" style="text-align: center">
		<thead style="text-align:center">
			<tr>
				<th style="text-align:center">房子id</th>
				<th style="text-align:center">房东id</th>
				<th style="text-align:center">房源标题</th>
				<th style="text-align:center">房源类型</th>
				<th style="text-align:center">一人价格</th>
				<th style="text-align:center">性别要求</th>
				<th style="text-align:center">评价次数</th>
				
				<th colspan="3" style="text-align:center">操作</th>
			</tr>
		</thead>
		<!-- 与表头一一对应 -->
		<c:forEach items="${requestScope.house}" var="house">
			<tbody>
				<tr>
					<td>${house.house_id}</td>
					<td>${house.user_id}</td>
					<td>${house.house_title}</td>
					<td>${house.house_style}</td>
					<td>${house.house_one_price}</td>
					<td>${house.house_limit_sex}</td>
					<td>${house.house_assess_sum}</td>
					<td>
						<a href="selectUser.jsp?id=${house.house_id }">
						<button type="button" class="btn btn-primary">查看</button></a>
					</td>
					<td>
						<a href="updateUser.jsp?id=${house.house_id }">
						<button type="button" class="btn btn-primary">修改</button></a>
					</td>
					<td>
						<a href="deleteServlet?id=${house.house_id }">
						<button type="button" class="btn btn-primary">删除</button></a>
					</td>
				</tr>
			</tbody>
		</c:forEach>

		<tbody>
			<tr>
				<td>1,001</td>
				<td>Lorem</td>
				<td>Lorem</td>
				<td>ipsum</td>
				<td>dolor</td>
				<td>sit</td>
				<td>dolor</td>
				<td><a href="selectHouse.jsp?id=${good.id }">
					<button type="button" class="btn btn-primary">查看</button></a>
				</td>
				<td>
					<a href="updateHouse.jsp?id=${good.id }">
						<button type="button" class="btn btn-info">修改</button></a>
				</td>
				<td><a href="deleteServlet?id=${good.id }">
					<button type="button" class="btn btn-danger">删除</button></a>
				</td>
				
			</tr>
		</tbody>
	</table>
	
	<center>
		<h3>分页显示</h3>
	</center>
</body>
</html>
