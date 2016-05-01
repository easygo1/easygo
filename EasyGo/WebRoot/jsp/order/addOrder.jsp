<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加用户</title>
<link href="../../css/bootstrap.min.css" rel="stylesheet">
<link href="../../css/dashboard.css" rel="stylesheet">

</head>
<body style="text-align: center">
	<h3>添加订单</h3>
	<form action="../../easygoservlet?methods=addOrders&&cur=1" method="post">
		<table class="table table-striped" border="0"
			style="text-align: center">
			<thead>
				<tr>
					<th style="text-align: center">属性名</th>
					<th style="text-align: center">添加说明</th>
					<th style="text-align: center">添加信息</th>
					<th style="text-align: center">提示信息</th>
				</tr>
			</thead>
			<tbody style="text-align: center">
				<tr>
					<td>房源编号</td>
					<td>只允许在已有房源中选择</td>
					<td><input type="text" name="house_id"></td>
					<td></td>
				</tr>
				<tr>
					<td>房客编号</td>
					<td>只允许在已注册用户中选择</td>
					<td><input type="text" id="txtAdd1" name="user_id"></td>
					<td></td>
				</tr>
				<tr>
					<td>入住人数</td>
					<td>不能超过房源规定最大入住人数</td>
					<td><input type="text" name="checknum"></td>
					<td></td>
				</tr>
				<tr>
					<td>入住时间</td>
					<td>在房源是开放未租状态下</td>
					<td><input type="text" name="checktime"></td>
					<td></td>
				</tr>
				<tr>
					<td>离开时间</td>
					<td>与入住时间相比不能超过房源最大入住时间</td>
					<td><input type="text" name="leavetime"></td>
					<td></td>
				</tr>
				<tr>
					<td>订单总额</td>
					<td>根据计算得出</td>
					<td><input type="text" name="total"></td>
					<td></td>
				</tr>
				<tr>
					<td>联系方式</td>
					<td>只允许字母数字长度小于30</td>
					<td><input type="text" name="tel"></td>
					<td></td>
				</tr>
				<tr>
					<td>订单状态</td>
					<td>三个字</td>
					<td><input type="text" name="order_state"></td>
					<td></td>
				</tr>
				<tr>
					<td>下单时间</td>
					<td>获取当前系统时间</td>
					<td><input type="text" name="order_time"></td>
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