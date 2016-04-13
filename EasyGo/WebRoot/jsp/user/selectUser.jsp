<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>某某用户个人信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="../../css/bootstrap.min.css" rel="stylesheet">
<link href="../../css/dashboard.css" rel="stylesheet">
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
				<td>账号</td>
				<td><input type="text" class="form-control">
			</tr>
			<tr>
				<td>账号</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>账号</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>账号</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>账号</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>账号</td>
				<td><input type="text" class="form-control"></td>
			</tr>
			<tr>
				<td>账号</td>
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
