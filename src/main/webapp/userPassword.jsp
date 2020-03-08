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
<title>好吃吧订餐网-用户中心|密码管理</title>
<meta name="author" content="order by fengfeng"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/userCenter.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/user-pw.css"/>
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
	    <a href="#">修改密码</a>
	    <a href="#">联系客服</a>
	    <a href="${pageContext.request.contextPath }/admin-login.jsp" target="_blank">管理员登录</a>
   	</div>
   	<div class="RightNav">
	   	<a href="${pageContext.request.contextPath}/users/openCenter">${u.userName}</a> 欢迎您！
	    <a href="${pageContext.request.contextPath}/users/openCenter" title="我的用户中心">用户中心</a>
	    <a href="${pageContext.request.contextPath}/users/myIntent" title="我的订单">我的订单</a> 
	    <a href="${pageContext.request.contextPath}/users/openCart" title="我的购物车">购物车（<label id="cartNum"><%=cart.getTotalNum() %></label>）</a> 
	    <a href="${pageContext.request.contextPath}/users/openFavorite" title="我的收藏">我的收藏</a>
   	</div>	
   </div>
 </section>
 <div class="Logo_search">
  <div class="Logo">
   <img src="${pageContext.request.contextPath}/images/logoko.png" title="" alt="">
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
   <li><a href="${pageContext.request.contextPath}/users/myIntent" class="account" title="查看历史订单">我的订单</a></li>
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
   <li><i></i><a href="#" class="ym">密码管理</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/userUpdate.jsp">修改个人信息</a></li>
  </ul>
 </nav>
 <article class="U-article Overflow">
  <!--"引用“user_page/user_index.html”"-->
  <section class="usercenter">
  	<span class="Weltitle Block Font16 FontW Lineheight35">修改密码</span>
  	<div class="div_from_aoto" style="width: 500px;">
    <form class="form" action="">
    	<div class="control-group">
            <label class="laber_from">密码</label>
            <div  class="controls" ><input id="pw1" name="pw1" class="input_from pw1" type=text placeholder="请输入密码"><P class=help-block></P></div>
        </div>
        <div class="control-group">
            <label class="laber_from">新密码</label>
            <div  class="controls" ><input id="pw2" name="pw2" class="input_from pw2" type=text placeholder="请输入新密码"><P class=help-block></P></div>
        </div>
        <div class="control-group">
            <label class="laber_from" >确认密码</label>
            <div  class="controls" ><input id='pw3' name="pw3" class="input_from pw3" type=text placeholder="请确认新密码"><P class=help-block></P></div>
        </div>
        
        <div class="control-group">
            <label class="laber_from" ></label>
            <div class="controls" ><input class="btn btn-success" type="submit" style="width:120px;" ></div>
        </div>
    </form>
	</div>
  	
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

$(".btn-success").click(function(){
	var pw1=$("#pw1").val();
	var pw2=$("#pw2").val();
	var pw3=$("#pw3").val();
	if (pw1==null||pw1=="") {
		alert("密码不能为空");
		return;
	} else if (pw2==""||pw2==null||pw3==""||pw3==null) {
		alert("请输入新密码");
		return;
	}else if (pw2!=pw3) {
		alert("两次输入密码不一致！请重新输入");
		return;
	} else{
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/users/updatePassword",
			data:$('.form').serialize(),
			dataType:'json',
			async:true,	
			success:function(data){
				alert(data.msg);
				window.location.href="${pageContext.request.contextPath}/login.jsp";
				}
		});
	}
})
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
