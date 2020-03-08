<%@ page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
List<Intent> intents1=(List<Intent>)request.getAttribute("intents1");
List<Intent> intents2=(List<Intent>)request.getAttribute("intents2");
List<Intent> intents3=(List<Intent>)request.getAttribute("intents3");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>菜单管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.paginate.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.yhhDataTable.css" />

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.paginate.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.yhhDataTable.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js" ></script>
	<style type="text/css">
    	th,td{
    		text-align: center;
    		margin: auto;
    	}
    </style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   
                    <div class="ibox-tools" style="margin-top: 0px;">
                          <form action="#" method="post" role="form" class="form-inline">
                           <input type="hidden" name="" value=""/>
                           <div class="form-group" style="padding-right: 5px;padding-left: 5px;">
                               	<div class="col-sm-10" style="margin-bottom: 5px; margin-top:5px;padding-right: 0px;padding-left: 0px;">
                                   <input name="" placeholder="输入用户名或者用户ID查询" style="height: 29px;width: 220px;" class="form-control info">
                               	</div>
                           </div>
                           <div class="checkbox m-l m-r-xs" style="margin-left: 0px; margin-top:5px;">
                               <button class="btn btn-white submit"type="submit">查询</button>
                           </div>
                           
                      	</form>
                  	</div>
                    <div class="ibox-content">
                   	 	<h4 style="text-align: center;">已完成的订单列表</h4>
                        <table class="table table-striped table-bordered table-hover dataTables-example" id="testtable1">
                            <thead>                
                                <tr>
                                	<th>订单编号</th>
                                	<th>菜品数量</th>
                                	<th>下单用户</th>
                                    <th>联系人</th>
                                    <th>联系电话</th>
                                    <th>订单状态</th>
                                    <th>下单时间</th>
                                    <th>收货时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<%for(Intent i:intents1) {%>
                                <tr class="gradeX">
                                	<td class="i_id"><%=i.getId() %></td>
                                    <td><%=i.getTotalNum() %></td>
                                    <td><%=i.getUserId() %></td>
                                    <td><%=i.getUserAddress().getUserName() %></td>
                                    <td><%=i.getUserAddress().getUserPhone() %></td>
                                    <td><%=i.getOrderStatus() %></td>
                                    <td><%=i.getOrderTime() %></td>
                                    <td><%=i.getFinishTime() %></td>
                                    <td style="text-align: center;">
	                                     <button class="btn btn-white btn-bitbucket view" title="查看订单详情">
                           					查看订单详情
                        				</button>
                        				<input type="hidden" value="123"/>
                        				<button class="btn btn-white btn-bitbucket delete" title="删除订单">
                           					删除订单
                        				</button>
                        			</td>
                                </tr>
                            	<%} %>
                            </tbody>
                        </table>
                        <%if(intents2.size()>0) {%>
                        <h4 style="text-align: center;">未收货的订单列表</h4>
                         <table class="table table-striped table-bordered table-hover dataTables-example" id="testtable2">
                            <thead>                
                                <tr>
                                	<th>订单编号</th>
                                	<th>菜品数量</th>
                                	<th>下单用户</th>
                                    <th>联系人</th>
                                    <th>联系电话</th>
                                    <th>订单状态</th>
                                    <th>下单时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<%for(Intent i:intents2) {%>
                                <tr class="gradeX">
                                	<td class="i_id"><%=i.getId() %></td>
                                    <td><%=i.getTotalNum() %></td>
                                    <td><%=i.getUserId() %></td>
                                    <td><%=i.getUserAddress().getUserName() %></td>
                                    <td><%=i.getUserAddress().getUserPhone() %></td>
                                    <td><%=i.getOrderStatus() %></td>
                                    <td><%=i.getOrderTime() %></td>
                                    <td style="text-align: center;">
	                                     <button class="btn btn-white btn-bitbucket view" title="查看订单详情">
                           					查看订单详情
                        				</button>
                        				<input type="hidden" value="123"/>
                        				<button class="btn btn-white btn-bitbucket delete" title="删除订单">
                           					删除订单
                        				</button>
                        			</td>
                                </tr>
                            	<%} %>
                            </tbody>
                        </table>
                        <%} %>
                        
                        <%if(intents3.size()>0) {%>
                        <h4 style="text-align: center;">未付款的订单列表</h4>
                         <table class="table table-striped table-bordered table-hover dataTables-example" id="testtable3">
                            <thead>                
                                <tr>
                                	<th>订单编号</th>
                                	<th>菜品数量</th>
                                	<th>下单用户</th>
                                    <th>联系人</th>
                                    <th>联系电话</th>
                                    <th>订单状态</th>
                                    <th>下单时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<%for(Intent i:intents3) {%>
                                <tr class="gradeX">
                                	<td class="i_id"><%=i.getId() %></td>
                                    <td><%=i.getTotalNum() %></td>
                                    <td><%=i.getUserId() %></td>
                                    <td><%=i.getUserAddress().getUserName() %></td>
                                    <td><%=i.getUserAddress().getUserPhone() %></td>
                                    <td><%=i.getOrderStatus() %></td>
                                    <td><%=i.getOrderTime() %></td>
                                    <td style="text-align: center;">
	                                     <button class="btn btn-white btn-bitbucket view" title="查看订单详情">
                           					查看订单详情
                        				</button>
                        				<input type="hidden" value="123"/>
                        				<button class="btn btn-white btn-bitbucket delete" title="删除订单">
                           					删除订单
                        				</button>
                        			</td>
                                </tr>
                            	<%} %>
                            </tbody>
                        </table>
                        <%} %>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript">
$(".submit").click(function(){
	var info=$(".info").val();
	if (info=="") {
		alert("请输入查询内容");
		return;
	} else{
		$(".form-inline").attr({
			"action":"${pageContext.request.contextPath}/admins/findIntentByInfo?info="+info,
		});
	}
	
});

$(".view").click(function(){
	var intentId=$(this).parent("td").parent("tr").find(".i_id").html();
	window.location.href="${pageContext.request.contextPath}/admins/viewIntentXQ?intentId="+intentId;
})
$(".delete").click(function(){
	if (window.confirm("是否确认要删除该订单?")) {
		var intentId=$(this).parent("td").parent("tr").find(".i_id").html();
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/admins/deleteIntentById?intentId="+intentId,
			dataType:'json',
			async:false,
			success:function(data){
				alert(data.msg);
				window.location.reload();
				}
		});
	}
})
</script>
</html>
