<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
Cart cart=(Cart)request.getAttribute("cart");
List<CartFood> cartFoods=cart.getCartFoods();
Intent intent=(Intent)request.getAttribute("intent");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>好吃吧订餐网-订单在线支付</title>
<meta name="author" content="fengfeng"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/cart.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/public.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jqpublic.js"></script>

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

	
<!-- 订单提交成功后则显示支付 -->
<section class="Psection MT20 Textcenter" style="display:block;">
  
  <p class="Font14 Lineheight35 FontW">订单提交成功！待付款</p>
  <p class="Font14 Lineheight35 FontW">订单编号为：<span id="i_id"><%=intent.getId() %></span>  共计金额：￥<span class="zong CorRed" id="zong"><%=intent.getTotalPrice() %></span></p>
  <p class="Font14 Lineheight35 FontW">请选择支付方式：</p>
  <ul class="QRCode">
  	<li><p>支付宝</p><img src="${pageContext.request.contextPath }/images/zfb.jpg"/></li>
  	<li><p>微信</p><img src="${pageContext.request.contextPath }/images/wx.png"/></li>
  </ul>
  
  <p class="btn">
  	<input type="button" class="successplay" title="完成支付" value="完成支付" />
  	<input type="button" class="cancelPlay" title="取消支付" value="取消支付" />
  </p>

 </section>
<!--End content-->
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
    <p>网站公告</p>
   </div>
  </aside>
  <section>
    <div>
    <span><i class="i1"></i>配送支付</span>
    <ul>
     <li><a href=" " title="标题">支付方式</a></li>
     <li><a href=" " title="标题">配送方式</a></li>
     <li><a href=" " title="标题">服务费用</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i2"></i>关于我们</span>
    <ul>
     <li><a href=" " title="标题">网站介绍</a></li>
     <li><a href=" " title="标题">商家加盟</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i3"></i>帮助中心</span>
    <ul>
     <li><a href=" " title="标题">服务内容</a></li>
     <li><a href=" " title="标题">服务介绍</a></li>
     <li><a href=" " title="标题">常见问题</a></li>
    </ul>
    </div>
  </section>
 </section>
<div class="copyright">Copyright ©2019 版权所有 CQUT锋锋工作室</div>
</footer>
<script type="text/javascript">

$(".successplay").click(function(){
	var intentId=document.getElementById('i_id').innerHTML;
	window.location.href='${pageContext.request.contextPath}/users/successPlay?intentId='+intentId;
})
$(".cancelPlay").click(function(){
	var intentId=document.getElementById('i_id').innerHTML;
	var flag="1";
	window.location.href='${pageContext.request.contextPath}/users/cancelPlay?intentId='+intentId+'&flag='+flag;
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
</body>
</html>
