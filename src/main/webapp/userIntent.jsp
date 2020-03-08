<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
User user=(User)request.getSession().getAttribute("u");
Cart cart=(Cart)request.getAttribute("cart");
List<Intent> iList1=(List<Intent>)request.getAttribute("iList1");
List<Intent> iList2=(List<Intent>)request.getAttribute("iList2");
List<Intent> iList=(List<Intent>)request.getAttribute("iList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>好吃吧订餐网-用户中心|我的订单</title>
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
	    <a href="#" title="我的订单">我的订单</a> 
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
   <li><a href="#" class="jd" title="查看历史订单">我的订单</a></li>
   <li><a href="#" title="关于我们">关于我们</a></li>
  </ul>
 </nav>
</header>
<!--Start content-->
<section class="Psection MT20">
<nav class="U-nav Font14 FontW">
  <ul>
   <li><i></i><a href="${pageContext.request.contextPath}/users/openCenter">个人中心首页</a></li>
   <li><i></i><a href="#"  class="ym">我的订单</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/users/myAddress">收货地址</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/users/openFavorite">我的收藏</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/userPassword.jsp">密码管理</a></li>
   <li><i></i><a href="#">修改个人信息</a></li>
  </ul>
 </nav>
 <article class="U-article Overflow">
 	<%if(iList1.size()!=0){ %>
 	<span class="Font14 FontW Lineheight35 Block">未付款订单（<%=iList1.size() %>）：</span>
   	<table class="U-order-D">
   		<tr>
	    	<th class="Font14 FontW">订单编号</th>
	     	<th class="Font14 FontW">下单时间</th>
	     	<th class="Font14 FontW">商品数量</th>
	     	<th class="Font14 FontW">订单总金额</th>
	     	<th class="Font14 FontW">操作</th>
   		</tr>
   		<%for(Intent i:iList1){ %>
     	<tr>
	      	<td class="FontW i_id"><%=i.getId() %></td>
	      	<td><%=i.getOrderTime() %></td>
	      	<td><%=i.getTotalNum() %></td>
	      	<td>￥<%=i.getTotalPrice() %></td>
	      	<td><a href="" class="play">前往付款</a> | <a href="" class="cancelPlay">取消订单</a></td>
     	</tr>
     	<%} %>
    </table>
 	<%} %>
 	<% if(iList2.size()!=0){%>
 	<span class="Font14 FontW Lineheight35 Block">未收货订单(<%=iList2.size() %>)：</span>
   	<table class="U-order-D">
   		<tr>
	    	<th class="Font14 FontW">订单编号</th>
	     	<th class="Font14 FontW">下单时间</th>
	     	<th class="Font14 FontW">商品数量</th>
	     	<th class="Font14 FontW">订单总金额</th>
	     	<th class="Font14 FontW">操作</th>
   		</tr>
   		<%for(Intent i:iList2){ %>
     	<tr>
	      	<td class="FontW i_id" ><%=i.getId() %></td>
	      	<td><%=i.getOrderTime() %></td>
	      	<td><%=i.getTotalNum() %></td>
	      	<td>￥<%=i.getTotalPrice() %></td>
	      	<td><a href="" class="view">查看详情</a> | <a href="" class="confirm">确认收货</a></td>
     	</tr>
     	<%} %>
    </table>
    <%} %>
    
  	<span class="Font14 FontW Lineheight35 Block">历史订单：</span>
   	<table class="U-order-D">
   		<tr>
	    	<th class="Font14 FontW">订单编号</th>
	     	<th class="Font14 FontW">下单时间</th>
	     	<th class="Font14 FontW">商品数量</th>
	     	<th class="Font14 FontW">订单总金额</th>
	     	<th class="Font14 FontW">操作</th>
   		</tr>
   		
     	<%for(Intent i:iList){ %>
     	<tr>
	      	<td class="FontW i_id"><%=i.getId() %></td>
	      	<td><%=i.getOrderTime() %></td>
	      	<td><%=i.getTotalNum() %></td>
	      	<td>￥<%=i.getTotalPrice() %></td>
	      	<td><a href="" class="view2">查看详情</a> | <a href="" class="delete">删除订单</a></td>
     	</tr>
     	<%} %>
     	
    </table>
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
$(".confirm").click(function(){
	var intentId=$(this).parent('td').parent("tr").find(".i_id").html();
	$(this).attr('href',"${pageContext.request.contextPath}/users/confirmIntent?intentId="+intentId);
});
$(".view").click(function(){
	var intentId=$(this).parent('td').parent("tr").find(".i_id").html();
	$(this).attr('href',"${pageContext.request.contextPath}/users/successPlay?intentId="+intentId);
});
$(".play").click(function(){
	var intentId=$(this).parent('td').parent("tr").find(".i_id").html();
	$(this).attr('href',"${pageContext.request.contextPath}/users/goToPlay?intentId="+intentId);
});
$(".cancelPlay").click(function(){
	var intentId=$(this).parent('td').parent("tr").find(".i_id").html();
	var flag="2";
	$(this).attr('href','${pageContext.request.contextPath}/users/cancelPlay?intentId='+intentId+'&flag='+flag);
})
$(".view2").click(function(){
	var intentId=$(this).parent('td').parent("tr").find(".i_id").html();
	$(this).attr('href',"${pageContext.request.contextPath}/users/viewIntent?intentId="+intentId);
});
$(".delete").click(function(){
	var intentId=$(this).parent('td').parent("tr").find(".i_id").html();
	$(this).attr('href',"${pageContext.request.contextPath}/users/deleteIntent?intentId="+intentId);
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
