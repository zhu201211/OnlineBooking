<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
    List<Merchant> merchants = (List<Merchant>) request.getAttribute("merchants");
	List<Food> foods=(List<Food>)request.getAttribute("foods");
	
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=(Cart)request.getAttribute("cart");
	List<CartFood> cartFoods=cart.getCartFoods();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
	<title>餐厅列表</title>
	<meta name="author" content="fengfeng"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/in.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/foods.css"/>
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
				    <a href="${pageContext.request.contextPath }/userPassword.jsp">修改密码</a>
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
		   		<img src="${pageContext.request.contextPath}/images/logoko.png">
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
			   	<li><a href="${pageContext.request.contextPath }/users/backToIndex" title="首页" >回到首页</a></li>
			   	<li><a href="${pageContext.request.contextPath}/users/allFoods" title="所有菜品">所有菜品</a></li>
			   	<li><a href="${pageContext.request.contextPath}/users/myIntent" title="历史订单">我的订单</a></li>
			   	<li><a href="#" title="关于我们">关于我们</a></li>
		  	</ul>
 		</nav>
	</header>
	
<!--Start content-->
<section class="Psection MT20">
 <article class="Searchlist Overflow">
 	<section class="Fslmenu slt" style="margin-bottom:5px">
   	<a>
   		<span class="Font16">餐厅列表</span>
   	</a>
  </section>
  <ul class="Overflow">
  	<%for(Merchant m:merchants){ %>
    <li>
    		<img src="${pageContext.request.contextPath}/<%=m.getMerchantImg() %>">
		    <p class="P-shop Overflow">
		    	<span style="display: none;" class="m_id"><%=m.getMerchantId() %></span>
		    	<span class="sa">
		    		<a href="" title="点击进入该店铺" class="Font16 CorRed mer"><%=m.getMerchantName() %></a>
		    	</span>
		    	<span class="sp">人气：<%=m.getMerchantPop() %></span></p>
		    <p class="P-shop Overflow"><%=m.getMerchantAddress() %></p>
    </li>
    <%} %>
  </ul>
  
 </article>
 <aside class="Sraside">
 	
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
 	
  <div class="bestproduct">
  <span class="Bpt Block FontW Font14">热销商品推荐</span>
  	<ul>
  		<%if(foods!=null){
  		    int i=0;
  		    for(Food f:foods){
  				i++;
  				if(i>5)
  				    break;
  		%>
   		<li>
    		<a href=""><img src="${pageContext.request.contextPath}/<%=f.getFoodImg()%>"></a>
	    	<p>
	    		<span style="display: none;" class="f_id"><%=f.getId() %></span>
			   	<span class="Block FontW Font16 CorRed">￥<%=f.getPrice() %></span>
			    <span class="Block Overflow">
			    	<a class="Font12 f_name"><%=f.getFoodName() %></a>
			    	<a href="" class="addCart">加入餐车</a>
			    </span>
			    <span class="Block Overflow">累计销量：<i><%=f.getSalesVolume() %></i>笔</span>
	    	</p>
   		</li>
   		<%}} %>
  	</ul>
  </div>
 </aside>
</section>
<!--End content-->

<footer>
 <section class="Otherlink">
  <aside>
   <div class="ewm-left">
    <p>手机扫描二维码：</p>
    <img src="${pageContext.request.contextPath}/images/Android_ico_d.gif">
    <img src="${pageContext.request.contextPath}/images/iphone_ico_d.gif">
   </div>
   <div class="tips">
    <p>客服热线</p>
    <p><i>010-1234567</i></p>
    <p>配送时间</p>
    <p><time>08：00</time>~<time>23:00</time></p>
    <p><a href="" target="_blank">网站公告</a></p>
   </div>
  </aside>
  <section>
    <div>
    <span><i class="i1"></i>配送支付</span>
    <ul>
     <li><a href=" " target="_blank"  >支付方式</a></li>
     <li><a href=" " target="_blank"  >配送方式</a></li>
     <li><a href=" " target="_blank"  >配送效率</a></li>
     <li><a href=" " target="_blank"  >服务费用</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i2"></i>关于我们</span>
    <ul>
     <li><a href=" " target="_blank"  >招贤纳士</a></li>
     <li><a href=" " target="_blank"  >网站介绍</a></li>
     <li><a href=" " target="_blank"  >配送效率</a></li>
     <li><a href=" " target="_blank"  >商家加盟</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i3"></i>帮助中心</span>
    <ul>
     <li><a href=" " target="_blank"  >服务内容</a></li>
     <li><a href=" " target="_blank"  >服务介绍</a></li>
     <li><a href=" " target="_blank"  >常见问题</a></li>
     <li><a href=" " target="_blank"  >网站地图</a></li>
    </ul>
    </div>
  </section>
 </section>
<div class="copyright">Copyright ©2019 版权所有 CQUT锋锋工作室</div>
</footer>
</body>

<script type="text/javascript">
$(".addCart").click(function(){
	var foodId=$(this).parent("span").parent("p").find(".f_id").html();
	var flag="4";
	$(this).attr('href','${pageContext.request.contextPath}/users/addFoodToCart?foodId='+foodId+'&flag='+flag);
});
$(".mer").click(function(){
	var merId=$(this).parent('.sa').parent('p').find(".m_id").html();
	
	$(this).attr('href',"${pageContext.request.contextPath}/users/openMerchant?merchantId="+merId);

});	
$(".delete").click(function(){
	var cartFoodId=$(this).parent("th").find(".cartFoodId").html();
	var flag="5";
	$(this).attr('href',"${pageContext.request.contextPath}/users/deleteCartFoodById?cartFoodId="+cartFoodId+"&flag="+flag);
});
$(".account").click(function(){
	var cartNum=document.getElementById("cartNum").innerHTML;
	if (cartNum!=0) {
		$(this).attr('href',"${pageContext.request.contextPath}/users/openCart");
	} else{
		alert("餐车为空！请选择菜品");
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
