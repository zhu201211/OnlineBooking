<%@ page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
    List<Food> foods1 = (List<Food>) request.getAttribute("foods1");
	List<Food> foods2 = (List<Food>) request.getAttribute("foods2");
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="author" content="order by fengfeng"/>
    <title>菜单管理</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.paginate.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.yhhDataTable.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/allFoods.css" />
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
                                   <input name="" placeholder="输入商家名或商家号查询" style="height: 29px;width: 220px;" class="form-control infoByMer">
                               	</div>
                           </div>
                           <div class="form-group" style="padding-right: 5px;padding-left: 5px;">
                               	<div class="col-sm-10" style="margin-bottom: 5px; margin-top:5px;padding-right: 0px;padding-left: 0px;">
                                   <input name="" placeholder="输入菜名或菜名查询" style="height: 29px;width: 220px;" class="form-control infoByFood">
                               	</div>
                           </div>
                           <div class="checkbox m-l m-r-xs" style="margin-left: 0px; margin-top:5px;">
                               <button class="btn btn-white button"type="submit">查询</button>
                           </div>
                           <div class="checkbox m-l m-r-xs" style="margin-left: 20px; margin-top:5px;">
                               <select name="mer_selete" class="mer_selete" style="width: 100px;height: 30px;">
                               	<option value="0">筛选</option>
                               	<option value="1">按销量排序</option>
                               	<option value="2">正在售列表</option>
                               	<option value="3">已下线列表</option>
                               </select>
                           </div>
                      	</form>
                  	</div>
                    <div class="ibox-content">
                    	<%if(foods1.size()>0){ %>
                    	<h4 style="text-align: center;">在售菜品列表</h4>
                        <table class="table table-striped table-bordered table-hover dataTables-example" id="testtable1">
                            <thead>                
                                <tr>
                                	<th>菜名</th>
                                	<th>描述</th>
                                	<th>所属商家</th>
                                    <th>售价</th>
                                    <th>销售量</th>
                                    <th>照片</th>
                                    <th>销售状态</th>
                                    <th style="text-align: center;">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                        	for(Food f:foods1){%>
                                <tr class="gradeX">
                               		<td style="display: none" class="f_id"><%=f.getId() %></td>
                                    <td><%=f.getFoodName() %></td>
                                    <td><%=f.getFoodIntro() %></td>
                                    <td><%=f.getMerchant().getMerchantName() %></td>
                                    <td><%=f.getPrice() %></td>
                                    <td><%=f.getSalesVolume() %></td>
                                    <td><%=f.getSalesState().getState() %></td>
                                  	<td><img class="yu" src="${pageContext.request.contextPath }/<%=f.getFoodImg() %>" alt="加载中..." style="width: 45px;height: 45px;"></td>
                                    <td style="text-align: center;">
	                                     <button class="btn btn-white btn-bitbucket edit" title="编辑">
                           					编辑
                        				</button>
                        				<input type="hidden" value="123"/>
                        				<button class="btn btn-white btn-bitbucket delete" title="下线" >
                           					 下线
                        				</button>
                        			</td>
                                </tr>
                            	<%} %>
                            </tbody>
                        </table>
                        <%} 
                        if(foods2.size()>0){%>
                        <h4 style="text-align: center;">下线菜品列表</h4>
                        <table class="table table-striped table-bordered table-hover dataTables-example" id="testtable2">
                            <thead>                
                                <tr>
                                	<th>菜名</th>
                                	<th>描述</th>
                                	<th>所属商家</th>
                                    <th>售价</th>
                                    <th>销售量</th>
                                    <th>销售状态</th>
                                    <th>照片</th>
                                    <th style="text-align: center;">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%if(foods2.size()>0)
                        	for(Food f:foods2){%>
                                <tr class="gradeX">
                               		<td style="display: none" class="f_id"><%=f.getId() %></td>
                                    <td><%=f.getFoodName() %></td>
                                    <td><%=f.getFoodIntro() %></td>
                                    <td><%=f.getMerchant().getMerchantName() %></td>
                                    <td><%=f.getPrice() %></td>
                                    <td><%=f.getSalesVolume() %></td>
                                    <td><%=f.getSalesState().getState() %></td>
                                  	<td><img class="yu" src="${pageContext.request.contextPath }/<%=f.getFoodImg() %>" alt="加载中..." style="width: 45px;height: 45px;"></td>
                                    <td style="text-align: center;">
	                                    <button class="btn btn-white btn-bitbucket edit" title="编辑">
                           					编辑
                        				</button>
                        				<input type="hidden" value="123"/>
                        				<button class="btn btn-white btn-bitbucket upfood" title="下线" >
                           					 重新上线
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
	<form action="#" method="post" class="form" id="form" style="width: 500px;" enctype="multipart/form-data">
		<table class="table1" >
   			<tr>
   				<td class="f_id" style="display: none;"><input type="text" name="foodId" id="foodId" ></td>
   				<td width="25%" align="right">菜品名称</td>
   				<td><input type="text" name="name" id="name"></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">所属商家</td>
   				<td id="merchant" style="float: left;margin-left: 20px;line-height:50px;text-align: center;">沙县小吃</td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">菜品价格</td>
   				<td><input type="number" name="price" id="price" step="0.01" min="1" ></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">菜品介绍</td>
   				<td><textarea name="intro" rows="3" cols="39" id="intro"></textarea></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">菜品照片</td>
   				<td>
   					<img src="images/user-photo.png" class="img" id="img"/>
   					<input type="file" name="filedata" id="filedata" class="filedata openImg" onchange="coursePptChange()"/>
   				</td>
   			</tr>
   			<tr>
	    	<td colspan="2"><label style="display: none;">122345</label><input name="" type="submit" value="确认" class="submit" id="change"><input name="" type="button" value="取消" class="cancel"></td>
	    </tr>
   		</table>	
	</form>
	<div id="fade" class="overlay"></div> 
</body>
<script type="text/javascript">
if("${msg.msg}"!=""&&"${msg.msg}"!=null){
	alert("${msg.msg}");
}
$(".button").click(function(){
	var infoByMer=$(".infoByMer").val();
	var infoByFood=$(".infoByFood").val();
	if (infoByMer==""&&infoByFood=="") {
		alert("请输入查询内容");
		return;
	} else if(infoByMer==""&&infoByFood!=""){
		$(".form-inline").attr({
			"action":"${pageContext.request.contextPath}/admins/findFoodsByFoodInfo?info="+infoByFood,
		});
	} else if(infoByMer!=""){
		$(".form-inline").attr({
			"action":"${pageContext.request.contextPath}/admins/findFoodsByMerchantInfo?info="+infoByMer,
		});
	}
	
});
$(".delete").click(function(){
	if (window.confirm("确认要下线该商品?")) {
		var foodId= $(this).parent("td").parent("tr").find(".f_id").html();
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/admins/deleteFood?foodId="+foodId,
			async:false,
			success:function(data){
				alert(data.msg);
				window.location.reload();
			}
		});
	}else { 
	// 取消时做的操作 
	 	window.close();
	} 
});
$(".cancel").click(function(){
	$("#form").css({
		"display" : "none"
	});
	$("#fade").css({
		"display" : "none"
	});
});
$(".upfood").click(function(){
	if (window.confirm("确认重新上线该商品?")) {
		var foodId= $(this).parent("td").parent("tr").find(".f_id").html();
		$.ajax({
			type:"get",
			url:"${pageContext.request.contextPath}/admins/upFood?foodId="+foodId,
			async:false,
			success:function(data){
				alert(data.msg);
				window.location.reload();
			}
		});
	}else { 
	// 取消时做的操作 
	 	window.close();
	} 
});

$(document).ready(function(){ 
	$('.mer_selete').change(function(){ 
		
		var v=$(".mer_selete").val();
		if(v=="1"){
			window.location.href="${pageContext.request.contextPath}/admins/findFoodByXL";
		}else if(v=="2"){
			window.location.href="${pageContext.request.contextPath}/admins/findFoodByZS";
		}else if(v=="3"){
			window.location.href="${pageContext.request.contextPath}/admins/findFoodByXX";
		}
	}) 
});

$(".edit").click(function(){
	var foodId= $(this).parent("td").parent("tr").find(".f_id").html();
	var name=$(this).parents("tr").find("td").eq(1).html();
	var merchant=$(this).parents("tr").find("td").eq(3).html();
	var intro=$(this).parents("tr").find("td").eq(2).html();
	var price=$(this).parents("tr").find("td").eq(4).html();
	var uimg=$(this).parents("tr").find("td").find(".yu").attr("src");
	$("#form").css({
		"display" : "block"
	});
	$("#fade").css({
		"display" : "block"
	});
	$("#foodId").val(foodId);
	$("#price").val(price);
	$("#name").val(name);
	$("#intro").val(intro);
	$("#merchant").html(merchant);
	$("#img").attr("src",uimg);
	$(".form").attr({
		"action":"${pageContext.request.contextPath}/admins/updateFood",
	}) 
	
});
//选择图片
function coursePptChange(){
  	var MyTest = document.getElementById("filedata").files[0];
  	var reader = new FileReader();
  	reader.readAsDataURL(MyTest);
  	reader.onload = function(theFile) {
	  	var src = theFile.target.result;
	  	$("#img").attr('src', src);
		};
};
</script>
</html>
