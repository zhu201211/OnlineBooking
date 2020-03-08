<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
User user=(User)request.getSession().getAttribute("u");

Cart cart=(Cart)request.getAttribute("cart");
List<CartFood> cartFoods=cart.getCartFoods();
Merchant merchant=(Merchant)request.getAttribute("merchant");
List<Food> foods=merchant.getFoods();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>商家</title>
	<meta name="author" content="order by fengfeng"/>
	<link href="${pageContext.request.contextPath }/css/index.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/in.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/shop.css"/>
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
			    <a href="${pageContext.request.contextPath }/admin-login.jsp">管理员登录</a>
		   	</div>
	   		<div class="RightNav">
		   		<a href="${pageContext.request.contextPath}/users/openCenter" target="_blank">${u.userName}</a>欢迎您！
			    <a href="${pageContext.request.contextPath}/users/openCenter" target="_blank" title="进入我的用户中心" class="dq">用户中心</a>
			    <a href="${pageContext.request.contextPath}/users/myIntent" title="查看我的订单">我的订单</a> 
			    <a href="#" class="account" title="进入我的购物车">购物车（<label id="cartNum"><%=cart.getTotalNum() %></label>）</a> 
			    <a href="${pageContext.request.contextPath}/users/openFavorite" target="_blank" title="查看我的收藏">我的收藏</a>
	   		</div>	
	  	</div>
	</section>
	<div class="Logo_search">
	  	<div class="Logo">
	   		<img src="${pageContext.request.contextPath }/images/logoko.png" title="">
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
		   	<li><a href="${pageContext.request.contextPath }/users/backToIndex"title="首页">回到首页</a></li>
		   	<li><a href="${pageContext.request.contextPath}/users/allFoods" title="">所有菜品</a></li>
   			<li><a href="${pageContext.request.contextPath}/users/myIntent" title="查看历史订单">我的订单</a></li>
   			<li><a href="#" title="关于我们">关于我们</a></li>
	  	</ul>
	</nav>
</header>
<!--Start content-->
<section class="Shop-index">
 	<article>
  	<div class="shopinfor">
   	<div class="imginfor">
	    <div class="shopimg">
	     	<img src="${pageContext.request.contextPath }/<%=merchant.getMerchantImg() %>" id="showimg">
	     	<p><span>店名：</span><%=merchant.getMerchantName() %></p>
	    </div>
	    <div class="shoptext">
	   		<p><span>电话：</span><%=merchant.getMerchantPhone() %></p>
	   		<p><span>店铺简介：</span><%=merchant.getMerchantIntro()%></p>
	   		<p><span>人气：</span><%=merchant.getMerchantPop() %></p>
	     	<p><span>地址：</span><%=merchant.getMerchantAddress() %></p>
	    
	   </div>
  	</div>
 	<div class="shopcontent">
	  	<div class="title2 cf">
	    	<ul class="title-list fr cf ">
	      		<li class="on">菜谱</li>
	      		<p><b></b></p>
	    	</ul>
	  	</div>
  		<div class="menutab-wrap">
   			<a name="ydwm">
			    <div class="menutab show">
			     	<ul class="products">
			     	<%for(Food f:foods) {%>
						<li title="<%=f.getFoodIntro()%>">
						 	<a title="酸辣土豆丝">
				         		<img src="${pageContext.request.contextPath }/<%=f.getFoodImg() %>" class="foodsimgsize">
				         	</a>
				        	<div class="item">
				        		<p style="display: none;" class="f_id"><%=f.getId() %></p>
					  			<p href="" class="foodname"><%=f.getFoodName() %></p>
					  			<span>销售量：<%=f.getSalesVolume() %></span><br/>
					  			<a href="" class="shc" title="收藏">收藏</a>
					  			<span class="sales" title="价格">￥<%=f.getPrice() %></span>
					  			<input type="button" id="addCart" class="addCart" value="加入餐车" title="加入餐车"/>
						 	</div>
						</li>
						<%} %>
				 	</ul>
			    </div>
   			</a>
  		</div>
	</div>
	</div>
	</article>
 	<aside>
  		<div class="in-right-cart">
	 		<div class="in-right-cart-title">
	 			<p>我的餐车</p>
	 		</div>
	 		<div class="in-right-cart-list">
	 			<table id="cartcontent" class="cart-list-tab" fitColumns="true">
					<thead>
						<tr>
							<th width="45%" align="center" field="name">商品</th>
							<th width="15%" align="center" field="quantity">数量</th>
							<th width="25%" align="center" field="price">价格</th>
							<th width="15%" align="center" field="price">操作</th>
						</tr>
					</thead>
					<tbody>
						<%
						if(cartFoods!=null){ 
							for(CartFood cFood:cartFoods){
						%>
						<tr>
							<th width="45%" align="center" field="name"><%=cFood.getFood().getFoodName() %></th>
							<th width="15%" align="center" field="quantity"><%=cFood.getFoodNum() %></th>
							<th width="25%" align="center" field="price"><%=cFood.getPrice() %></th>
							<th width="15%" align="center" field="price">
								<p class="cartFoodId" style="display: none;"><%=cFood.getId() %></p>								
								<a href="" class="delete">删除</a>
							</th>
						</tr>
						<%}}else{ %>
						<tr>
							<th colspan="4" align="center">空空如也</th>
						</tr>
						<%} %>
					</tbody>
					
							
				</table>
	 		</div>
	 		<div class="in-right-cart-btn">
	 			<p class="Ptc">
	 				<span class="Cbutton">
	 					<a href="" title="进入购物车结算" class="account">进入结算</a>
	 				</span>共计金额: ￥<span class="total-price"><%=cart.getTotalPrice()%></span>
	 			</p>
	 		</div>
	 	</div>
 	</aside>
 
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
    <p><i>1830927**73</i></p>
    <p>配送时间</p>
    <p><time>09：00</time>~<time>22:00</time></p>
   </div>
  </aside>
  <section>
    <div>
    <span><i class="i1"></i>配送支付</span>
    <ul>
     <li><a href="" target="_blank" title="标题">支付方式</a></li>
     <li><a href="" target="_blank" title="标题">配送方式</a></li>
     <li><a href="" target="_blank" title="标题">配送效率</a></li>
     <li><a href="" target="_blank" title="标题">服务费用</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i2"></i>关于我们</span>
    <ul>
     <li><a href="" target="_blank" title="标题">招贤纳士</a></li>
     <li><a href="" target="_blank" title="标题">网站介绍</a></li>
     <li><a href="" target="_blank" title="标题">配送效率</a></li>
     <li><a href="" target="_blank" title="标题">商家加盟</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i3"></i>帮助中心</span>
    <ul>
     <li><a href="" target="_blank" title="标题">服务内容</a></li>
     <li><a href="" target="_blank" title="标题">服务介绍</a></li>
     <li><a href="" target="_blank" title="标题">常见问题</a></li>
     <li><a href="" target="_blank" title="标题">网站地图</a></li>
    </ul>
    </div>
  </section>
 </section>
<div class="copyright">Copyright ©2019 版权所有 CQUT锋锋工作室</div>
</footer>
<script type="text/javascript">
$(".account").click(function(){
	var cartNum=document.getElementById("cartNum").innerHTML;
	if (cartNum!=0) {
		$(this).attr('href',"${pageContext.request.contextPath}/users/openCart");
	} else{
		alert("餐车为空！请选择菜品");
	}
});
$(".delete").click(function(){
	var cartFoodId=$(this).parent("th").find(".cartFoodId").html();
	var flag="2";
	$(this).attr('href',"${pageContext.request.contextPath}/users/deleteCartFoodById?cartFoodId="+cartFoodId+"&flag="+flag);
});
$(".addCart").click(function(){
	var foodId=$(this).parent(".item").find(".f_id").html();
	alert(foodId);
	var flag="2";
	window.location.href='${pageContext.request.contextPath}/users/addFoodToCart?foodId='+foodId+'&flag='+flag;
	
});
$(".shc").click(function(){
	var foodId=$(this).parent('p').parent('li').find(".f_id").html();
	alert(foodId);
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/users/favorite?foodId="+foodId,
		async:false,
		dataType:'json',
		success:function(data){
			alert(data.msg);
		}
	})
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
</body>
</html>
