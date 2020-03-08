<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%User user=(User)request.getSession().getAttribute("u"); 
Cart cart=(Cart)request.getAttribute("cart");
List<CartFood> cartFoods=cart.getCartFoods();
List<UserAddress> uAddresses=(List<UserAddress>) request.getAttribute("uAddresses");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>我的购物车</title>
<meta name="author" content="order by fengfeng"/>
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
    <a href="${pageContext.request.contextPath }/admin-login.jsp">管理员登录</a>
   </div>
   <div class="RightNav">
   	<a href="${pageContext.request.contextPath}/users/openCenter">${u.userName}</a> 欢迎您！
    <a href="${pageContext.request.contextPath}/users/openCenter" title="我的用户中心" class="dq">用户中心</a>
    <a href="${pageContext.request.contextPath}/users/myIntent" title="我的订单">我的订单</a> 
    <a href="#" class="account" title="我的购物车">购物车（<label id="cartNum"><%=cart.getTotalNum() %></label>）</a> 
    <a href="${pageContext.request.contextPath}/users/openFavorite"  title="我的收藏">我的收藏</a>
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
   <li><a href="${pageContext.request.contextPath }/users/backToIndex" title="首页" >回到首页</a></li>
   <li><a href="${pageContext.request.contextPath }/users/allFoods" title="所有菜品">所有菜品</a></li>
   <li><a href="#" title="历史订单">我的订单</a></li>
   <li><a href="#" title="关于我们">关于我们</a></li>
  </ul>
 </nav>
</header>
<!--Start content-->
<form action="#">
 <div class="gwc" style=" margin:auto;">
  <table cellpadding="0" cellspacing="0" class="gwc_tb1">
    <tr>
      <td class="tb1_td3">菜品</td>
      <td class="tb1_td4">商家</td>
      <td class="tb1_td5">单价</td>
      <td class="tb1_td5">数量</td>  
      <td class="tb1_td6">价格</td>
      <td class="tb1_td7">操作</td>
    </tr>
  </table>
  <table cellpadding="0" cellspacing="0" class="gwc_tb2" id="table1">
    <%if(cartFoods!=null){
    for(CartFood cFood:cartFoods){%>
    <tr>
    	<td style="display: none;"><%=cFood.getId() %></td>
      	<td class="tb2_td2"><a href="#" target="_blank"><img src="${pageContext.request.contextPath}/<%=cFood.getFood().getFoodImg()%>"/></a></td>
      	<td class="tb2_td3"><a href="#" target="_blank"><%=cFood.getFood().getFoodName() %></a></td>
      	<td class="tb1_td4"><%=cFood.getFood().getMerchant().getMerchantName() %></td>
      	<td class="tb1_td5">￥<label><%=cFood.getFood().getPrice() %></label></td>
      	<td class="tb1_td5">
        	<input class="text_box" name="" type="text" value="<%=cFood.getFoodNum()%>" onchange="Change(this)"/>
      	</td>
      	<td class="tb1_td6">￥<label class="tot"><%=cFood.getPrice()%></label></td>
      	<td class="tb1_td7"><a href="#" class="delcartFood">删除</a></td>
    </tr>
   <%}}
    else{%>
    <tr>
    	<td colspan="7" align="center">
    		空空如也
    	</td>
    </tr>
    <%} %>
  </table>
  
  <div class="confirm_addr_f">
  <span class="flow_title">送餐地址：</span>
   <ul class="address">
   	<%int i=1;
   	for(UserAddress uAddress:uAddresses) {%>
   	
    <li id="style<%=i%>">
    	<input type="radio" value="<%=uAddress.getId() %>" id="<%=i%>" name="rdColor" onclick="changeColor(<%=i%>)"/>
    	<label for="<%=i%>"> <%=uAddress.getFrontAddress() %> <%=uAddress.getDetailAddress() %>  （联系人：<%=uAddress.getUserName() %>）
    		<span class="fontcolor">电话：<%=uAddress.getUserPhone() %></span>
    	</label>
    </li>
    <%
    i++;
    		if(i>uAddresses.size())
    		    break;
   	} %>
    <li>
    	<a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">
    		<img src="${pageContext.request.contextPath}/images/newaddress.png"/>
    	</a>
    </li>
   </ul>
   
   
   <form action="" id="form1" method="post">
   <div id="light" class="O-L-content">
    <ul>
   	 	<li>
   	 		<span><label for="">送餐地址：</label></span>
   	 		<p><em>*</em><input id="frontAddress" name="frontAddress" type="text"  class="Y_N"></p>
   	 	</li>
     	<li>
     		<span><label for="">详细地址：</label></span>
     		<p><em>*</em><input id="detailAddress" name="detailAddress" type="text"  class="Y_N"></p></p></li>
     	<li>
	     	<span><label for="">联系人：</label></span>
	     	<p><em>*</em><input id="userName" name="userName" type="text"></p></li>
     	<li>
	     	<span><label for="">手机号码：</label></span>
	     	<p><em>*</em><input id="userPhone" name="userPhone" type="text" ></p></li>
     	<div class="button-a">
	     	<input type="submit" value="确 定" class="C-button" name="submit"/>
	     	<a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">
	     	<input name="" type="button" value="取 消"  class="Cancel-b"/></a>
     	</div>
   	</div> 
   	<div id="fade" class="overlay"></div>
    </ul>
    
   </form>
   
   </div>
   
   <div class="pay_delivery">
  	<span class="flow_title">支持支付方式：</span>
    <ul>
    <li><label><i class="alipay" title="支付宝支付"></i></label></li>
    <li><label><i class="alipay1" title="微信支付"></i></label></li>
    </ul>
  </div>
   
  
  <table cellpadding="0" cellspacing="0" class="gwc_tb3">
    <tr>
      <td class="tb3_td2 GoBack_Buy Font14">
      	<a href="${pageContext.request.contextPath}/users/backToIndex">返回继续购物</a>
      </td>
      <td class="tb3_td3">合计:<span>￥</span>
      	<label class="zong" id='zong'><%=cart.getTotalPrice()%></label>
      </td>
      <td class="tb3_td4"><a href="#" class="jz2" id="jz2">结算</a></td>
    </tr>
  </table>
</div>
</form>
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
function Change(obj){
	var foodId=obj.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	alert(foodId);
	var num1=obj.parentNode.getElementsByTagName('input')[0].value;
	var num=parseInt(num1);
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/users/updateCartFoodNum?foodId="+foodId+"&num="+num,
		dataType:'json',
		async:false,
	})
	var price=obj.parentNode.parentNode.getElementsByTagName('td')[4].getElementsByTagName('label')[0].innerHTML;
	var p=num*price*1;
	var tot=obj.parentNode.parentNode.getElementsByTagName('td')[6].getElementsByTagName('label')[0];
	
	var cartTot=document.getElementById('zong');
	
	var zong=parseFloat(cartTot.innerHTML);
	var zong1=zong-parseFloat(tot.innerHTML)+p;
	
	cartTot.innerHTML=parseFloat(zong1).toFixed(1)
	
	tot.innerHTML=parseFloat(p).toFixed(1);
}
$(".delcartFood").click(function(){
	var cartFoodId=$(this).parent("td").parent("tr").find("td").eq(0).html();
	alert(cartFoodId);
	var flag="3";
	$(this).attr('href',"${pageContext.request.contextPath}/users/deleteCartFoodById?cartFoodId="+cartFoodId+"&flag="+flag);
});

$(".C-button").click(function(){
	var frontAddress=document.getElementById("frontAddress").value;
	var detailAddress=document.getElementById("detailAddress").value;
	var userName=document.getElementById("userName").value;
	var userPhone=document.getElementById("userPhone").value;
	if (frontAddress==null||frontAddress==''||detailAddress==null||detailAddress=='') {
		alert("地址不能为空");
		return;
	} else if(userName==null||userName==''||userPhone==null||userPhone==''){
		alert("联系人和电话均不能为空");
		return;
	}else{
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/users/addUserAddress?frontAddress="
				+frontAddress+"&detailAddress="+detailAddress+"&userName="+userName+"&userPhone="+userPhone,
			dataType:'json',
			async:false,
			success:function(data){
				alert(data.msg);
			}
		})
	}
})

$(".jz2").click(function(){
	var addressId=$("input[name='rdColor']:checked").val();
	if (addressId==null||addressId=='') {
		alert("请选择送餐地址");
		return ;
	} else{
		$(this).attr('href',"${pageContext.request.contextPath}/users/onlinePlay?addressId="+addressId);
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
