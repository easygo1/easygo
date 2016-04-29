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

		<tbody>
				<tr>
					<td>用户id</td>
					<td><c:out value="${requestScope.oneUser.user_id}"></c:out>
					</td>
				</tr>
				<tr>
					<td>用户账号</td>
					<td><c:out value="${requestScope.oneUser.user_no}"></c:out></td>
				</tr>
				<tr>
					<td>真实姓名</td>
					<td><c:out value="${requestScope.oneUser.user_realname}"></c:out></td>
				</tr>
				<tr>
					<td>密码</td>
					<td><c:out value="${requestScope.oneUser.user_password}"></c:out></td>
				</tr>
				<tr>
					<td>昵称</td>
					<td><c:out value="${requestScope.oneUser.user_nickname}"></c:out></td>
				</tr>
				<tr>
					<td>性别</td>
					<td><c:out value="${requestScope.oneUser.user_sex}"></c:out></td>
				</tr>
				<tr>
					<td>手机号</td>
					<td><c:out value="${requestScope.oneUser.user_phone}"></c:out></td>
				</tr>
				<tr>
					<td>用户类型</td>
					<td><c:out value="${requestScope.oneUser.user_type}"></c:out></td>
				</tr>
				<tr>
					<td>头像</td>
					<td><c:out value="${requestScope.oneUser.user_photo}"></c:out></td>
				</tr>
				<tr>
					<td>职业</td>
					<td><c:out value="${requestScope.oneUser.user_job}"></c:out></td>
				</tr>
				<tr>
					<td>所在省份</td>
					<td><c:out value="${requestScope.oneUser.user_address_province}"></c:out></td>
				</tr>
				<tr>
					<td>所在城市</td>
					<td><c:out value="${requestScope.oneUser.user_address_city}"></c:out></td>
				</tr>
				<tr>
					<td>个性签名</td>
					<td><c:out value="${requestScope.oneUser.user_mood}"></c:out></td>
				</tr>
				<tr>
					<td>邮箱</td>
					<td><c:out value="${requestScope.oneUser.user_mail}"></c:out></td>
				</tr>
				<tr>
					<td>个人简介</td>
					<td><c:out value="${requestScope.oneUser.user_introduct}"></c:out></td>
				</tr>
				<tr>
					<td>出生日期</td>
					<td><c:out value="${requestScope.oneUser.user_birthday}"></c:out></td>
				</tr>
				<tr>
					<td>身份证号</td>
					<td><c:out value="${requestScope.oneUser.user_idcard}"></c:out></td>
				</tr>
		</tbody>
	</table>
	<div style="text-align: center">
		<a href="easygoservlet?methods=updateUser?no=${requestScope.oneUser.user_no}">
			<input type="submit" value="去修改" class="btn btn-primary"></a>
			
		<a href="easygoservlet?methods=getAllUser">
			<input type="reset" value="返回" class="btn btn-info"></a>
	</div>
</body>
</html>
