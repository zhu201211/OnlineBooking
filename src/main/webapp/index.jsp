<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
    List<Merchant> merchantss = (List<Merchant>) request.getAttribute("merchantss");
	List<Food> foods=(List<Food>)request.getAttribute("foods");
	List<Food> myfoods=(List<Food>)request.getAttribute("myfoods");
	List<Food> myfavorites=(List<Food>)request.getAttribute("myfavorites");
	User user=(User)request.getSession().getAttribute("u");
	Cart cart=(Cart)request.getAttribute("cart");
	List<CartFood> cartFoods=cart.getCartFoods();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>订餐网首页</title>
<meta name="author" content="order by fengfeng"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/in.css"/>
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
   <img src="${pageContext.request.contextPath}/images/logoko.png" title="好吃吧订餐网">
  </div>
  <div class="Search"> 
   <form method="post" id="main_a_serach" onsubmit="return check_search(this)">
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
   <li><a href="#" class="jd" title="首页">首页</a></li>
   <li><a href="${pageContext.request.contextPath}/users/allFoods" title="">所有菜品</a></li>
   <li><a href="${pageContext.request.contextPath}/users/myIntent" title="查看历史订单">我的订单</a></li>
   <li><a href="#" title="关于我们">关于我们</a></li>
  </ul>
 </nav>
</header>
<!--Start content-->
<section class="Psection MT20">
	<div class="in-left">
	 	<div class="in-left-header">
	 		<a href="#"><h2>人气餐厅</h2></a>
	 	</div>
	 	
	 	<%if(merchantss!=null){
	 	    int i=0;
	 	    for(Merchant m:merchantss){ 
	 			i++;
	 			if(i==5)
	 			    break;%>
	 	<div class="in-left-list"><a href="#" title="点击进入该店铺" class='openMer'>
	 		<p class="m_id" style="display: none;"><%=m.getMerchantId() %></p>
	 		<div class="list-main">
	 			<div class="list-main-img">
		 			<img src="${pageContext.request.contextPath }/<%=m.getMerchantImg() %>" width="110px" height="110px"/>
		 		</div>
		 		<div class="list-main-font">
		 			<h2>店名：<%=m.getMerchantName() %></h2>
		 			<p>人气：<%=m.getMerchantPop() %></p>
		 			<div class="list-info">
		 				<p>店铺简介：<%=m.getMerchantIntro() %></p>
		 			</div>
		 		</div>
	 		</div>
	 		</a>
	 	</div>
	 	<% }}%>
	 	
	 	
	 	<div class="in-left-btn">
	 		<a href="#" class="allMerchants">查看更多</a>
	 	</div>
	</div>
	<div class="in-center">
	 	<div class="in-center-header">
	 		<h2>HOT 热销</h2>
	 		<a href="#" class="allFoods">查看更多菜式</a>
	 	</div>
	 	<article class="in-center-list Overflow">
	 		<ul class="Overflow">
	 		<%if(foods!=null){
	 	    int i=0;
	 	    for(Food f:foods){ 
	 			
	 			if(i==6)
	 			    break;%>
	 			<li>
			    <a title="<%=f.getFoodIntro()%>">
			    	<img src="${pageContext.request.contextPath }/<%=f.getFoodImg() %>">
			    </a>
	    		<p class="f_id" style="display: none;"><%=f.getId() %></p>
		    	<p class="P-price FontW Font16">
		    		<span>￥<%=f.getPrice() %></span>
		    		<input type="button" class="shc" value="收藏" title="收藏"/>
		    	</p>
		    	<p class="P-title">
		    		<a title="<%=f.getFoodName() %>" class="Font16"><%=f.getFoodName() %></a>
		    		<input type="button" id="addCart" class="addCart" value="加入餐车" title="加入餐车"/>
		    	</p>
		    	<p class="P-shop Overflow">
			    	<span style="display: none;" class="m_id"><%=f.getMerchant().getMerchantId() %></span>
			    	<span class="sa">店铺：<a href="" class="mer"><%=f.getMerchant().getMerchantName() %></a></span>
			    	<span class="sp">销量：<%=f.getSalesVolume() %></span>
		   	 	</p>
    		</li>
	 			<%i++;}} %>
	 			
	 		</ul>
	 	</article>
	</div>
	<div class="in-right">
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
								<a href="#" class="delete">删除</a>
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
	 					<a href="#" title="进入购物车结算" class="account">进入结算</a>
	 				</span>共计金额: ￥<span class="total-price"><%=cart.getTotalPrice()%></span>
	 			</p>
	 		</div>
	 	</div>
	
		<div class="bestproduct">
 	 		<span class="Bpt Block FontW Font14">新入驻餐厅</span>
  			<ul>
  			<%for(Merchant m:merchantss){ 
  				if(m.getState().equals("新入驻")){
  				%>
		  	 	<li>
			    	<a href="" class="openMer"><p class="m_id" style="display: none;"><%=m.getMerchantId() %></p><img src="${pageContext.request.contextPath }/<%=m.getMerchantImg()%>"></a>
			    	
				    <p>
				     	<span class="Block FontW Font16 CorRed"><%=m.getMerchantName() %></span>
				     	<span class="Block Overflow">人气量：<i><%=m.getMerchantPop() %></i></span>
				    </p>
			   	</li>
			   	<%}} %>
	  		</ul>
	  	</div>
	
	</div>
	
<article class="Searchlist Overflow">
  <section class="Fslmenu slt" style="margin-bottom:5px">
   	<span class="Font16 CorRed" style="margin-left: 10px;">猜你喜欢<label class="Font12 TJfont" style="margin-left: 10px;">根据您的购买/收藏等行为为您推荐</label></span>
   	<input  class="button" value="换一批" />
  </section>
  <ul class="Overflow" style="display: block;" id="t1">
  	<%if(myfoods!=null){
  	    int i=0;
  	    for(Food f:myfoods){
  		i++;
  		if(i>4){
  		    break;
  		}
  	    %>
    <li>
	    <a title="<%=f.getFoodIntro()%>">
	    	<img src="${pageContext.request.contextPath }/<%=f.getFoodImg()%>">
	    </a>
	    <p class="f_id" style="display: none;"><%=f.getId() %></p>
	    <p class="P-price FontW Font16">
	    	<span>￥<%=f.getPrice() %></span>
	    	<input type="button" class="shc" value="收藏" title="收藏"/>
	    </p>
	    <p class="P-title">
	    	<a title="<%=f.getFoodName() %>" class="Font16"><%=f.getFoodName() %></a>
	    	<input type="button" id="addCart" class="addCart" value="加入餐车" title="加入餐车"/>
	    </p>
	    <p class="P-shop Overflow">
	    	<span style="display: none;" class="m_id"><%=f.getMerchant().getMerchantId() %></span>
	    	<span class="sa">店铺：<a href="" class="mer"><%=f.getMerchant().getMerchantName() %></a></span>
	    	<span class="sp">销量：<%=f.getSalesVolume() %></span>
	    </p>
    </li>
    <%}} %>
    
  </ul>
  
  <ul class="Overflow" style="display: none;" id="t2">
  	<%if(myfavorites!=null){
  	    int i=0;
  	    for(Food f:myfavorites){
  		i++;
  		if(i>4){
  		    break;
  		}
  	    %>
    <li>
	    <a title="<%=f.getFoodIntro()%>">
	    	<img src="${pageContext.request.contextPath }/<%=f.getFoodImg()%>">
	    </a>
	    <p class="f_id" style="display: none;"><%=f.getId() %></p>
	    <p class="P-price FontW Font16">
	    	<span>￥<%=f.getPrice() %></span>
	    	<input type="button" class="shc" value="收藏" title="收藏"/>
	    </p>
	    <p class="P-title">
	    	<a title="<%=f.getFoodName() %>" class="Font16"><%=f.getFoodName() %></a>
	    	<input type="button" id="addCart" class="addCart" value="加入餐车" title="加入餐车"/>
	    </p>
	    <p class="P-shop Overflow">
	    	<span style="display: none;" class="m_id"><%=f.getMerchant().getMerchantId() %></span>
	    	<span class="sa">店铺：<a href="" class="mer"><%=f.getMerchant().getMerchantName() %></a></span>
	    	<span class="sp">销量：<%=f.getSalesVolume() %></span>
	    </p>
    </li>
    <%}} %>
    
  </ul>
  
 </article>
	
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

$(".addCart").click(function(){
	var foodId=$(this).parent("p").parent("li").find(".f_id").html();
	
	var flag="1";
	window.location.href='${pageContext.request.contextPath}/users/addFoodToCart?foodId='+foodId+'&flag='+flag;
	
});
$(".mer").click(function(){
	var merId=$(this).parent('.sa').parent('p').find(".m_id").html();
	
	$(this).attr('href',"${pageContext.request.contextPath}/users/openMerchant?merchantId="+merId);
});
$(".delete").click(function(){
	var cartFoodId=$(this).parent("th").find(".cartFoodId").html();
	var flag="1";
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

$(".openMer").click(function(){
	var merId=$(this).find(".m_id").html();
	$(this).attr('href',"${pageContext.request.contextPath}/users/openMerchant?merchantId="+merId);
});
$(".allFoods").click(function(){
	$(this).attr('href',"${pageContext.request.contextPath}/users/allFoods");
});
$(".allMerchants").click(function(){
	$(this).attr('href',"${pageContext.request.contextPath}/users/allMerchants");
});
$(".shc").click(function(){
	var foodId=$(this).parent('p').parent('li').find(".f_id").html();
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
$(".button").click(function(){
	var m1=document.getElementById('t1');
   	var m2=document.getElementById('t2');
   	 if(m1.style.display=='block'){
      	m1.style.display='none';
      	m2.style.display='block';
   	}else if(m2.style.display=='block'){
      	m2.style.display='none';
      	m1.style.display='block';
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
