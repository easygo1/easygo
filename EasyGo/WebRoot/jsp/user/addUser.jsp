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
<body>
	<h2>添加用户</h2>
	<form action="" method="post" class="form-group">
		<table class="table table-striped"
			style="text-align: center;font-size: 20px">
			<thead>
				<tr>
					<th style="text-align: center;width: 20%">属性名</th>
					<th style="text-align: center;width: 50%">值</th>
					<th style="text-align: center;width: 30%">添加说明</th>
				</tr>
			</thead>

			<tr>
				<td>用户账号</td>
				<td><input name = "no" type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>真实姓名</td>
				<td><input name = "realname" type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input name = "password" type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input  name = "nickname" type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>性别</td>
				<td style="text-align:left;">
					男<input type="radio" name="sex" value="man">
					 女<input type="radio" name="sex" value="woman">
				</td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>手机号</td>
				<td><input name = "phone" type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>用户类型</td>
				<td style="text-align:left;"><select name="type"  >
						<option value="volvo">房客</option>
						<option value="saab">房东</option>
				</select></td>
				<td>请选择用户类型（房东/房客）</td>
			</tr>
			<tr>
				<td>头像</td>
				<td><input type="file" ></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>职业</td>
				<td><input type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>所在省份</td>
				<td><input type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>所在城市</td>
				<td><input type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>个性签名</td>
				<td><input type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td><input type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>个人简介</td>
				<td><input type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>出生日期</td>
				<td><input type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
			<tr>
				<td>身份证号</td>
				<td><input type="text" class="form-control"></td>
				<td>只允许字母数字长度小于30</td>
			</tr>
		</table>
		<div style="text-align: center">
			<input type="submit" value="确定" class="btn btn-info"> <input
				type="reset" value="重置" class="btn btn-info">
		</div>
	</form>

</body>
</html>