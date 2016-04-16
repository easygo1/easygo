<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<h2>用户管理</h2>
	<div style="text-align: center" align="center">
		<a href="addUser.jsp" ><button type="button" class="btn btn-info">添加用户</button></a>
		&nbsp;&nbsp;&nbsp;
		<a class="form-inline" href="">
			<input  class="form-control" placeholder="模糊查询">
			<button type="button" class="btn btn-info">查询</button>
		</a>
	</div>
	<hr>
	<table class="table table-striped" style="border:1px;" >
		<thead>
			<tr>
				<th style="text-align:center">用户id</th>
				<th style="text-align:center">用户账号</th>
				<th style="text-align:center">真实姓名</th>
				<th style="text-align:center">用户昵称</th>
				<th style="text-align:center">性别</th>
				<th style="text-align:center">手机号</th>
				<th style="text-align:center">用户类型</th>
				<th colspan="3" style="text-align:center" width="10%">操作</th>
			</tr>
		</thead>
		<!-- 与表头一一对应 -->
		<tbody style="text-align:center">
			<c:forEach items="${requestScope.userList}" var="user">
				<tr>
					<td>${user.user_id}</td>
					<td>${user.user_no}</td>
					<td>${user.user_realname}</td>
					<td>${user.user_nickname}</td>
					<td>${user.user_sex}</td>
					<td>${user.user_phone}</td>
					<td>${user.user_type}</td>
					<td>
						<a href="easygoservlet?methods=findUser&&id=${user.user_no }">
						<button type="button" class="btn btn-primary">查看</button></a>
					</td>
					<td>
						<a href="updateUser.jsp?id=${user.user_id }">
						<button type="button" class="btn btn-info">修改</button></a>
					</td>
					<td>
						<a href="deleteServlet?id=${user.user_id }">
						<button type="button" class="btn btn-danger">删除</button></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<center>
		<h3>分页显示</h3>
	</center>
</body>
</html>
