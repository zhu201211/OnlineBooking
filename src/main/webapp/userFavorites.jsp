<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
User user=(User)request.getSession().getAttribute("u");
Cart cart=(Cart)request.getAttribute("cart");
List<Favorite> fList=(List<Favorite>)request.getAttribute("fList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>好吃吧订餐网-用户中心|我的收藏</title>
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
	    <a href="#">修改密码</a>
	    <a href="#">联系客服</a>
	    <a href="${pageContext.request.contextPath }/admin-login.jsp" target="_blank">管理员登录</a>
   	</div>
   	<div class="RightNav">
	   	<a href="${pageContext.request.contextPath}/users/openCenter">${u.userName}</a> 欢迎您！
	    <a href="${pageContext.request.contextPath}/users/openCenter" title="我的用户中心">用户中心</a>
	    <a href="${pageContext.request.contextPath}/users/myIntent" title="我的订单">我的订单</a> 
	    <a href="#" class="account" title="我的购物车">购物车（<label id="cartNum"><%=cart.getTotalNum() %></label>）</a> 
	    <a href="#" title="我的收藏">我的收藏</a>
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
   <li><a href="#" title="查看历史订单">我的订单</a></li>
   <li><a href="#" title="关于我们">关于我们</a></li>
  </ul>
 </nav>
</header>
<!--Start content-->
<section class="Psection MT20">
<nav class="U-nav Font14 FontW">
  <ul>
   <li><i></i><a href="#" >个人中心首页</a></li>
   <li><i></i><a href="#">我的订单</a></li>
   <li><i></i><a href="#">收货地址</a></li>
   <li><i></i><a href="#" class="ym">我的收藏</a></li>
   <li><i></i><a href="#">密码管理</a></li>
   <li><i></i><a href="#">修改个人信息</a></li>
  </ul>
 </nav>
 <article class="U-article Overflow">
  <!--user Favorites-->
  <section class="ShopFav Overflow">
   <span class="ShopFavtitle Block Font14 FontW Lineheight35">我的收藏</span>
   <ul>
   <%if(fList!=null)
       for(Favorite f:fList){%>
    <li>
	    <a  title="<%=f.getFood().getFoodIntro()%>"><img src="${pageContext.request.contextPath}/<%=f.getFood().getFoodImg()%>"></a>
	    <p class="f_id" style="display: none;"><%=f.getFood().getId() %></p>
	    <p class="P-price FontW Font16">
	    	<span>￥<%=f.getFood().getPrice() %></span>
	    </p>
	    <p class="P-title">
	    	<a title="<%=f.getFood().getFoodName() %>" class="Font16"><%=f.getFood().getFoodName() %></a>
	    	<input type="button" id="addCart" class="addCart" value="加入餐车" title="加入餐车"/>
	    </p>
	    <p class="P-shop Overflow">
	    	<span style="display: none;" class="m_id"><%=f.getFood().getMerchant().getMerchantId() %></span>
	    	<span class="sa">店铺：<a href="" class="mer"><%=f.getFood().getMerchant().getMerchantName() %></a></span>
	    	<input type="button" class="shc" value="取消收藏" title="取消收藏"/>
	    </p>
    </li>
    <%} %>
   </ul>
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
$(".mer").click(function(){
	var merId=$(this).parent('.sa').parent('p').find(".m_id").html();
	$(this).attr('href',"${pageContext.request.contextPath}/users/openMerchant?merchantId="+merId);
});
$(".addCart").click(function(){
	var foodId=$(this).parent("p").parent("li").find(".f_id").html();
	
	var flag="1";
	window.location.href='${pageContext.request.contextPath}/users/addFoodToCart?foodId='+foodId+'&flag='+flag;
	
});
$(".shc").click(function(){
	var foodId=$(this).parent("p").parent("li").find(".f_id").html();
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/users/cancalFavorite?foodId="+foodId,
		dataType:'json',
		async:true,
		success:function(data){
			alert(data.msg);
			window.location.reload();
		}
	});
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
