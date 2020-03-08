<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
User user=(User)request.getSession().getAttribute("u"); 
Cart cart=(Cart)request.getAttribute("cart");
Intent intent=(Intent)request.getAttribute("intent");
List<Intent_foods> foods=intent.getIntent_foodss();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>好吃吧订餐网-订单详情</title>
<meta name="author" content="order by fengfeng"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/intent.css"/>
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
    <a href="${pageContext.request.contextPath }/admin-login.jsp">管理员登录</a>
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
   <img src="${pageContext.request.contextPath }/images/logoko.png" title="" alt="">
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
<section class="Psection MT20" id="Cflow">

  
  <div class="inforlist">
   <span class="flow_title">订单号：<span class="CorRed" id="i_id"><%=intent.getId() %></span><label> &nbsp;&nbsp;订单状态：<%=intent.getOrderStatus() %></label><label class="time">下单时间：<%=intent.getOrderTime() %></label> </span>
   <span class="flow_title ">详情&nabla;</span>
   <table>
   	<tr>
	    <th>名称</th>
	    <th>数量</th>
	    <th>价格</th>
	    <th>小计</th>
    </tr>
    <%for(Intent_foods f:foods){ %>
    <tr>
     <td><%=f.getFood().getFoodName() %></td>
     <td><%=f.getNum() %></td>
     <td>￥<%=f.getFood().getPrice() %></td>
     <td>￥<%=f.getPrice() %></td>
    </tr>
    <%} %>
   </table>
   <span class="flow_title">送餐地址：<label > <%=intent.getUserAddress().getFrontAddress() %> <%=intent.getUserAddress().getDetailAddress() %>（<%=intent.getUserAddress().getUserName() %>收）<span class="fontcolor"><%=intent.getUserAddress().getUserPhone() %></span></label></span>
    
    <div class="Sum_infor">
    <p class="p2">合计：<span>￥<%=intent.getTotalPrice() %></span></p>
    <%if(intent.getOrderStatus()=="待收货"||intent.getOrderStatus().equals("待收货")){ %>
    <input type="button" value="确认收货" class="p3button confirm">
    <%}else if(intent.getOrderStatus()=="已收货"||intent.getOrderStatus().equals("已收货")){ %>
    <input type="button" value="返回首页" class="p3button backIndex">
    <%} %>
    </div>
   </div>
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
    <p>网站公告</p>
   </div>
  </aside>
  <section>
    <div>
    <span><i class="i1"></i>配送支付</span>
    <ul>
     <li><a href="#" title="标题">支付方式</a></li>
     <li><a href="#" title="标题">配送方式</a></li>
     <li><a href="#" title="标题">服务费用</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i2"></i>关于我们</span>
    <ul>
     <li><a href="#" title="标题">网站介绍</a></li>
     <li><a href="#" title="标题">商家加盟</a></li>
    </ul>
    </div>
    <div>
    <span><i class="i3"></i>帮助中心</span>
    <ul>
     <li><a href="#" title="标题">服务内容</a></li>
     <li><a href="#" title="标题">服务介绍</a></li>
     <li><a href="#" title="标题">常见问题</a></li>
    </ul>
    </div>
  </section>
 </section>
<div class="copyright">Copyright ©2019 版权所有 CQUT锋锋工作室</div>
</footer>
</body>
<script type="text/javascript">
$(".confirm").click(function(){
	var intentId=document.getElementById('i_id').innerHTML;
	window.location.href='${pageContext.request.contextPath}/users/confirmIntent?intentId='+intentId;
})
$(".backIndex").click(function(){
	var intentId=document.getElementById('i_id').innerHTML;
	window.location.href='${pageContext.request.contextPath }/users/backToIndex';
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
