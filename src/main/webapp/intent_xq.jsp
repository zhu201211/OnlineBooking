<%@ page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
Intent intent=(Intent)request.getAttribute("intent");
List<Intent_foods> intent_foodss=intent.getIntent_foodss();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>订单详情</title>
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/intent_xq.css"/>
	</head>
	<body>
		<span class="flow_title">
			<span class="title">
				订单详情
			</span>
		</span>
		<span class="flow_title top">
			订单号：<label style="color: red;"><%=intent.getId() %></label>
			<label class="time" style="float: right;">下单时间：<%=intent.getOrderTime() %></label> 
		</span>
		<span class="flow_title">
			订单状态：<label style="color: red;"><%=intent.getOrderStatus() %></label>
			<%if(intent.getOrderStatus().equals("已收货")){ %>
			<label class="time" style="float: right;">收货时间：<%=intent.getFinishTime() %></label> 
			<%} %>
		</span>
		<span class="flow_title">
			总价：<label style="color: red;font-size: 22px;">¥<%=intent.getTotalPrice()%></label>
		</span>
		<table class="U-order-D">
	   		<tr>
		    	<th>序号</th>
		     	<th>菜品名称</th>
		     	<th>菜品单价</th>
		     	<th>购买数量</th>
		     	<th>小计</th>
	   		</tr>
	   		<%
	   		int j=1;
	   		for(Intent_foods i:intent_foodss){ %>
	     	<tr>
	     		<td><%=j %></td>
		      	<td><%=i.getFood().getFoodName() %></td>
		      	<td>¥<%=i.getFood().getPrice() %></td>
		      	<td><%=i.getNum() %></td>
		      	<td>¥<%=i.getPrice() %></td>
		    </tr>
		    <%} %>
  	 	</table>
	<span class="flow_title">送餐地址：<label > <%=intent.getUserAddress().getFrontAddress() %> <%=intent.getUserAddress().getDetailAddress() %>（<%=intent.getUserAddress().getUserName() %>收）<span class="fontcolor"><%=intent.getUserAddress().getUserPhone() %></span></label>
		<input type="button" id="backIntent" value="返回订单列表"/>
	</span>
	</body>
<script type="text/javascript">
$("#backIntent").click(function(){
	window.location.href="${pageContext.request.contextPath}/admins/findFinishIntent";
})
</script>
</html>
