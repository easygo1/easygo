<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>轻松住</title>
      
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">   
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="js/menu.js"></script>
<script type="text/javascript">
function setTaba(m,n){
	var tli=document.getElementById("leftmenu"+m).getElementsByTagName("li");
	var mli=document.getElementById("mcont"+m).getElementsByTagName("ul");
	for(i=0;i<tli.length;i++){  
	  tli[i].className=i==n?"hover":"";
	  mli[i].style.display=i==n?"block":"none";
	}
}
</script>

  </head>

  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">轻松住后台管理界面</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">管理员</a></li>
            <li><a href="#">了解我们</a></li>
            <li><a href="#">关于我们</a></li>
            <li><a href="#">帮助</a></li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="搜索...">
          </form>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar" id="leftmenu0">   
            <li onclick="setTaba(0,0)"><a href="#">用户表</a></li>
            <li onclick="setTaba(0,1)"><a href="#">钱包表</a></li>
            <li onclick="setTaba(0,2)"><a href="#">房源信息表</a></li>
            <li><a href="#">订单表</a></li>
            <li><a href="#">订单状态表</a></li>
            <li><a href="#">日期管理表</a></li>
            <li><a href="#">房屋设施表</a></li>
            <li><a href="#">爱好表</a></li>
            <li><a href="#">管理员表Admin</a></li>
            <li><a href="#">好友表</a></li>
            <li><a href="#">消息表</a></li>
            <li><a href="#">动态表</a></li>
            <li><a href="#">说说表</a></li>
            <li><a href="#">评论表</a></li>
            <li><a href="#">收藏房源表</a></li>
            <li><a href="#">评价表</a></li>
            <li><a href="#">签到表</a></li>
          </ul>
        </div>
          
          <div style="margin-left:210px" id="mcont0">
            <ul style="display: block">
                <iframe src="jsp/user/user.jsp" width="100%" height="650px" scrolling="no" frameborder="0"> </iframe>
       		</ul>
        	<ul style="display: none">
                <iframe src="jsp/user/userwallet.jsp" width="100%" height="650px" scrolling="no" frameborder="0"> </iframe>
       		</ul>    
       		<ul style="display: none">
                <iframe src="jsp/house/house.jsp" width="100%" height="650px" scrolling="no" frameborder="0"> </iframe>
       		</ul>     
          </div> 
      </div>
    </div>
  </body>
</html>