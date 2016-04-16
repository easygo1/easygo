<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>某某用户个人信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/dashboard.css" rel="stylesheet">
</head>

<body>
	<table class="table table-striped" border="0"
		style="text-align: center">
		<thead>
			<tr>
				<th style="text-align: center">属性名</th>
				<th style="text-align: center">值</th>
			</tr>
		</thead>

		<tbody style="text-align: center">
			<tr>
				<td>用户id</td>
				<td><input type="text" class="form-control">
			</tr>
			<tr>
				<td>用户账号</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>真实姓名</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>性别</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>手机号</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>用户类型</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>头像</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>职业</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>所在省份</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>所在城市</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>个性签名</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>个人简介</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>出生日期</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>身份证号</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			
		</tbody>
	</table>
	<div style="text-align: center">
		<a href="updateUser.jsp?id=${user.id}">
			<input type="submit" value="去修改" class="btn btn-primary"></a>
			
		<a href="user.jsp">
			<input type="reset" value="返回" class="btn btn-info"></a>
	</div>
</body>
</html>
