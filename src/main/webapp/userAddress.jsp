<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
User user=(User)request.getSession().getAttribute("u"); 
Cart cart=(Cart)request.getAttribute("cart");
List<UserAddress> userAddresses=(List<UserAddress>)request.getAttribute("userAddresses");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>好吃吧订餐网-用户中心|我的地址</title>
<meta name="author" content="order by fengfeng"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/userCenter.css"/>
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
   <li><a href="#" title="查看历史订单">我的订单</a></li>
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
   <li><i></i><a href="#"  class="ym">收货地址</a></li>
   <li><i></i><a href="${pageContext.request.contextPath}/users/openFavorite">我的收藏</a></li>
   <li><i></i><a href="#">密码管理</a></li>
   <li><i></i><a href="#">修改个人信息</a></li>
  </ul>
 </nav>
 <article class="U-article Overflow">
  <!--user Address-->
  <section class="Myaddress Overflow">
   	<span class="MDtitle Font14 FontW Block Lineheight35">我的地址（<%=userAddresses.size() %>）： 
   		<a href="javascript:void(0)" class="add" style="float: right;color:seagreen;text-decoration: underline;">新增</a> 
   	</span>
   	<table class="U-order-D">
   		<tr>
	    	<th class="Font14 FontW">所在市、区</th>
	     	<th class="Font14 FontW">详细街道</th>
	     	<th class="Font14 FontW">联系人</th>
	     	<th class="Font14 FontW">联系电话</th>
	     	<th class="Font14 FontW">操作</th>
   		</tr>
   		<%for(UserAddress u:userAddresses) {%>
     	<tr>
     		<td style="display: none;"><%=u.getId() %></td>
	      	<td><%=u.getFrontAddress() %></td>
	      	<td><%=u.getDetailAddress() %></td>
	      	<td><%=u.getUserName() %></td>
	      	<td class="FontW"><%=u.getUserPhone() %></td>
	      	<td><input type="button" name="edit" class="edit" value="编辑" style="cursor: pointer;"/> | <input type="button" name="edit" class="delete" value="删除" style="cursor: pointer;"/></td>
     	</tr>
     	<%} %>
    </table>
    
    <form action=" " class="form1">
	   	<div id="light" class="O-L-content">
	    	<ul>
	    		<input type="text" name="id" id="id" style="display: none;"/>
     			<li><span><label for="">所在市、区：</label></span><p><em>*</em><input id="frontAddress" name="frontAddress" type="text"  class="Y_N"></p></li>
     			<li><span><label for="">详细街道：</label></span><p><em>*</em><input id="detailAddress" name="detailAddress" type="text"  class="Y_N"></p></p></li>
     			<li><span><label for="">联系人：</label></span><p><em>*</em><input id="userName" name="userName" type="text"></p></li>
     			<li><span><label for="">联系电话：</label></span><p><em>*</em><input id="userPhone" name="userPhone" type="text"></p></li>
     		</ul>
     		<div class="button-a">
     			<input type="submit" value="确 定" class="C-button" />
     			<input name="" type="submit" value="取 消"  class="Cancel-b"/>
     			
     		</div>	
	   	</div> 
   		<div id="fade" class="overlay"></div> 
   	<!--End add new address-->
   	</form>
    
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
$(".add").click(function(){
	if($(".O-L-content").css("display") == "none"&&$(".overlay").css("display") == "none"){
		$(".O-L-content").css({
			"display" : "block"
		});	
		$(".overlay").css({
			"display" : "block"
		});	
		$(".C-button").attr({
			"value" : "添加"
		});
		//添加
		$(".C-button").click(function() {
			$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/users/addAddress",
				data : $(".form1").serialize(),
				async : false,
				success : function(data) {
					alert(data.msg);
					window.location.reload();
				}
			});
		})
	}
})
$(".Cancel-b").click(function(){
	$(".O-L-content").css({
		"display" : "none"
	});

	$(".overlay").css({
		"display" : "none"
	});

	/* 清空残留数据 */
	$("#id").val("");
	$("#frontAddress").val("");
	$("#detailAddress").val("");
	$("#userName").val("");
	$("#userPhone").val("");
	
});
$(".delete").click(function(){
	
	if (window.confirm("是否确认要删除?")) { 
		// 确认时做的操作 
		var a_id=$(this).parent('td').parent('tr').find('td').eq(0).html();
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/users/deleteAddress?id="+a_id,
			async:false,
			success:function(data){
				alert(data.msg);
				window.location.reload();
			}
		});
		} else { 
		// 取消时做的操作 
		 	window.close();
		} 
})

$(".edit").click(function(){
	var a_id=$(this).parent('td').parent('tr').find('td').eq(0).html();
	var f_address=$(this).parent('td').parent('tr').find('td').eq(1).html();
	var d_address=$(this).parent('td').parent('tr').find('td').eq(2).html();
	var u_name=$(this).parent('td').parent('tr').find('td').eq(3).html();
	var u_phone=$(this).parent('td').parent('tr').find('td').eq(4).html();

	if($(".O-L-content").css("display") == "none"&&$(".overlay").css("display") == "none"){
		$(".O-L-content").css({
			"display" : "block"
		});	
		$(".overlay").css({
			"display" : "block"
		});	
		$(".C-button").attr({
			"value" : "修改"
		});
		
		$("#id").val(a_id);
		$("#frontAddress").val(f_address);
		$("#detailAddress").val(d_address);
		$("#userName").val(u_name);
		$("#userPhone").val(u_phone);
		
		$('.C-button').click(function(){
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/users/updateAddress",
				data:$('.form1').serialize(),
				dataType:'json',
				async:true,
				success:function(data){
					alert(data.msg);
					window.location.reload();
					}
			});
		})
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
