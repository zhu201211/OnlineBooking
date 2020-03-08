<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
User user=(User)request.getSession().getAttribute("u");
Cart cart=user.getCart();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>好吃吧订餐网-用户中心|修改信息</title>
<meta name="author" content="order by fengfeng"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/userCenter.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/public.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>

</head>
<body>
<header>
<section class="Topmenubg">
  <div class="Topnav">
   	<div class="LeftNav">
	    <a href="${pageContext.request.contextPath }/register.jsp">注册</a>/
	    <a href="${pageContext.request.contextPath }/login.jsp">退出登录</a>
	    <a href="${pageContext.request.contextPath}/userPassword.jsp">修改密码</a>
	    <a href="#">联系客服</a>
	    <a href="${pageContext.request.contextPath }/admin-login.jsp" target="_blank">管理员登录</a>
   	</div>
   	<div class="RightNav">
	   	<a href="${pageContext.request.contextPath}/users/openCenter">${u.userName}</a> 欢迎您！
	    <a href="${pageContext.request.contextPath}/users/openCenter" title="我的用户中心">用户中心</a>
	    <a href="${pageContext.request.contextPath}/users/myIntent" title="我的订单">我的订单</a> 
	    <a href="#" class="account" title="我的购物车">购物车（<label id="cartNum"><%=cart.getTotalNum() %></label>）</a> 
	    <a href="${pageContext.request.contextPath}/users/openFavorite" title="我的收藏">我的收藏</a>
   	</div>	
   </div>
 </section>
 <div class="Logo_search">
  <div class="Logo">
   <img src="${pageContext.request.contextPath}/images/logoko.png" title="好吃吧订餐网">
  </div>
  <div class="Search"> 
   <form method="get" id="main_a_serach" onsubmit="return check_search(this)">
   <div class="Search_nav" id="selectsearch">
    <a href="javascript:;" onClick="selectsearch(this,'restaurant_name')" class="choose">餐厅</a>
    <a href="javascript:;" onClick="selectsearch(this,'food_name')">食物名</a>
   </div>
   <div class="Search_area"> 
   <input type="search" id="fkeyword" name="keyword" placeholder="请输入您所需查找的餐厅名称或食物名称..." class="searchbox" />
   <input type="submit" class="searchbutton" value="搜 索" />
   </div>
   </form>
  </div>
 </div>
 <nav class="menu_bg">
  <ul class="menu">
   <li><a href="${pageContext.request.contextPath}/users/backToIndex" title="首页">首页</a></li>
   <li><a href="${pageContext.request.contextPath}/users/allFoods" title="">所有菜品</a></li>
   <li><a href="${pageContext.request.contextPath}/users/myIntent" title="查看历史订单">我的订单</a></li>
   <li><a href="#" title="关于我们">关于我们</a></li>
  </ul>
 </nav>
</header>
<!--Start content-->
<section class="Psection MT20">
<nav class="U-nav Font14 FontW">
  <ul>
   <li><i></i><a href="${pageContext.request.contextPath}/users/openCenter">个人中心首页</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/users/myIntent">我的订单</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/users/myAddress">收货地址</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/users/openFavorite">我的收藏</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/userPassword.jsp">密码管理</a></li>
   <li><i></i><a href="#" class="ym">修改个人信息</a></li>
  </ul>
 </nav>
 <article class="U-article Overflow">
  <!--user Account-->
  <section class="AccManage Overflow">
   <span class="AMTitle Block Font14 FontW Lineheight35">修改个人信息</span>
   <form action="#" class="form1" method="post" enctype="multipart/form-data">
    <table>
    	<tr>
		    <td width="30%" align="right">我的登录手机：</td>
		    <td><p><%=user.getUserPhone() %> ( <a href="javascript:void(0)" onclick = "document.getElementById('form2').style.display='block';document.getElementById('fade').style.display='block'">更换登录手机号码</a> ) </p></td>
    	</tr>
	    <tr>
		    <td width="30%" align="right">修改头像：</td>
		    <td><img src="${pageContext.request.contextPath}/<%=user.getUserImg() %>" style="width: 50px;height: 50px;" id="img"/><input name="filedata" type="file" id="filedata" class="filedata" onchange="coursePptChange()"></td>
	    </tr>
	    <tr>
		    <td width="30%" align="right">用户昵称：</td>
		    <td><input type="text" name="userName" id="name" maxlength="16" value="<%=user.getUserName()%>"></td>
   		</tr>
	    <tr>
	    	<td><label style="display: none;">122345</label></td>
	    	<td><input name="submit" type="submit" value="保 存" class="submit form-submit"></td>
	    </tr>
    </table>
   </form>
   
   	<form action="#" method="post" style="width: 500px;height: 160px;" class="form2" id="form2">
   		<table class="table">
   			<tr>
   				<td width="30%" align="right">手机号码：</td>
   				<td><input type="text" name="phone" maxlength="11" id="phone" placeholder="输入手机号码"></td>
   			</tr>
   			<tr>
   				<td width="30%" align="right">验证码：</td>
   				<td width="30%"><input type="text" name="code" id="code" placeholder="输入验证码"></td>
   				<td align="left"><input type="button" value="发送验证码" id="sendCode" class="sendCode"></td>
   			</tr>
   			<tr>
	    	<td><label style="display: none;">122345</label></td>
	    	<td><input name="" type="submit" value="确认" class="submit" id="change"></td>
	    	<td><input name="" type="button" value="取消" class="cancel"></td>
	    </tr>
   		</table>	
  
   </form>
   <div id="fade" class="overlay"></div> 
  </section>
 </article>
</section>

<footer>
 <section class="Otherlink">
  <aside>
   <div class="ewm-left">
    <p>手机扫描二维码：</p>
    <img src="${pageContext.request.contextPath }/images/Android_ico_d.gif">
    <img src="${pageContext.request.contextPath }/images/iphone_ico_d.gif">
   </div>
   <div class="tips">
    <p>客服热线</p>
    <p><i>010-1234567</i></p>
    <p>配送时间</p>
    <p><time>08：00</time>~<time>23:00</time></p>
    <p><a href="#">网站公告</a></p>
   </div>
  </aside>
  <section>
    <div>
    <span><i class="i1"></i>配送支付</span>
    <ul>
     <li><a href="#" target="_blank" title="标题">支付方式</a></li>
     <li><a href="#" target="_blank" title="标题">配送方式</a></li>
     <li><a href="#" target="_blank" title="标题">配送效率</a></li>
     <li><a href="#" target="_blank" title="标题">服务费用</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i2"></i>关于我们</span>
    <ul>
     <li><a href="#" target="_blank" title="标题">招贤纳士</a></li>
     <li><a href="#" target="_blank" title="标题">网站介绍</a></li>
     <li><a href="#" target="_blank" title="标题">配送效率</a></li>
     <li><a href="#" target="_blank" title="标题">商家加盟</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i3"></i>帮助中心</span>
    <ul>
     <li><a href="#" target="_blank" title="标题">服务内容</a></li>
     <li><a href="#" target="_blank" title="标题">服务介绍</a></li>
     <li><a href="#" target="_blank" title="标题">常见问题</a></li>
     <li><a href="#" target="_blank" title="标题">网站地图</a></li>
    </ul>
    </div>
  </section>
 </section>
<div class="copyright">Copyright ©2019 版权所有 CQUT锋锋工作室</div>
</footer>
</body>
<script type="text/javascript">
if("${msg}"!=""&&"${msg}"!=null){
	alert("${msg}");
}	
//选择图片
function coursePptChange(){
  	var MyTest = document.getElementById("filedata").files[0];
  	var reader = new FileReader();
  	reader.readAsDataURL(MyTest);
  	reader.onload = function(theFile) {
	  	var src = theFile.target.result;
	  	$("#img").attr('src', src);
		};
 	
};

$(".cancel").click(function(){
	$("#form2").css({
		"display" : "none"
	});
	$("#fade").css({
		"display" : "none"
	});
	$("#phone").val("");
	$("#code").val("");
});
$('#sendCode').click(function(){
	var userPhone=document.getElementById("phone").value;
	var myreg=/^[1][3,4,5,7,8,9][0-9]{9}$/;
	if (!myreg.test(userPhone)) {
		alert("请填写正确的手机号码");
		return ;
	}else{
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/users/sendCode3?userPhone="+userPhone,
			dataType:'json',
			async:true,
			success:function(data){
				alert(data.msg);
			}
		});
	}
});
$("#change").click(function(){
	var userPhone=document.getElementById("phone").value;
	var myreg=/^[1][3,4,5,7,8,9][0-9]{9}$/;
	var code=document.getElementById("code").value;
	if (!myreg.test(userPhone)) {
		alert("请填写正确的手机号码");
		return ;
	}else if(code==""){
		alert("验证码不能为空");
		return ;
	}else{
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/users/changePhone",
			async:false,
			data:$('.form2').serialize(),
			dataType:'json',
			success:function(data){
				alert(data.msg);
			}
		});
	}
});
$(".form-submit").click(function(){
	var name=document.getElementById("name").value;
	if(name==""||name==null){
		alert("用户昵称不能为空");
	}else{
		$(".form1").attr({
			"action":"${pageContext.request.contextPath}/users/updateInfor"
		})
	}
});
function selectsearch(theA,word){
	 obj=document.getElementById("selectsearch").getElementsByTagName("a");
	 for(var i=0;i< obj.length;i++ ){
	  obj[i].className='';
	 }
	 theA.className='choose';
	  if(word=='restaurant_name'){
	   document.getElementById('main_a_serach').action="${pageContext.request.contextPath}/users/serachMerchant";//Test url
	  }else if(word=='food_name'){
	   document.getElementById('main_a_serach').action="${pageContext.request.contextPath}/users/serachFood";//Test url
	  }
	}
</script>
</html>
