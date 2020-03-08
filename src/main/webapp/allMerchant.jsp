<%@ page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
    List<Merchant> merchants = (List<Merchant>) request.getAttribute("Merchants");
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>菜单管理</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.paginate.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.yhhDataTable.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/allMerchant.css" />
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
                                   <input name="info" placeholder="输入关键字查询商家" style="height: 29px;width: 220px;" class="form-control info">
                               	</div>
                           </div>
                           <div class="checkbox m-l m-r-xs" style="margin-left: 0px; margin-top:5px;">
                               <button class="btn btn-white submit sub"type="submit">查询</button>
                           </div>
                           <div class="checkbox m-l m-r-xs" style="margin-left: 20px; margin-top:5px;">
                               <select name="mer_selete" class="mer_selete" style="width: 100px;height: 30px;">
                               	<option value="0">筛选</option>
                               	<option value="1">默认排序</option>
                               	<option value="2">按人气排序</option>
                               	<option value="3">新入驻商家</option>
                               	<option value="4">营业中商家</option>
                               	<option value="5">已注销商家</option>
                               </select>
                           </div>
                      	</form>
                  	</div>
                    <div class="ibox-content">
                        <table class="table table-striped table-bordered table-hover dataTables-example" id="testtable1">
                            <thead>                
                                <tr>
                                	<th>商家号</th>
                                	<th>商家名</th>
                                	<th>电话</th>
                                    <th>地址</th>
                                    <th>简介</th>
                                    <th>人气</th>
                                    <th>商家照片</th>
                                    <th>目前状态</th>
                                    <th style="text-align: center;">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%for(Merchant m:merchants){ %>
                                <tr class="gradeX">
                                	<td class="m_id"><%=m.getMerchantId() %></td>
                                    <td><%=m.getMerchantName() %></td>
                                    <td><%=m.getMerchantPhone() %></td>
                                    <td><%=m.getMerchantAddress() %></td>
                                    <td><%=m.getMerchantIntro() %></td>
                                    <td><%=m.getMerchantPop() %></td>
                                  	<td><img  class="yu"  src="${pageContext.request.contextPath}/<%=m.getMerchantImg() %>" alt="加载中..." style="width: 45px;height: 45px;"></td>
                                    <td><%=m.getState() %></td>
                                    <td style="text-align: center;">
                                    	
	                                    <button class="btn btn-white btn-bitbucket viewfood" title="添加菜品">
                           					查看菜谱
                        				</button>
                        				<button class="btn btn-white btn-bitbucket edit" title="编辑" >
                           					编辑
                        				</button>
                        				<%if(!m.getState().equals("已注销")) {%>
	                                    <button class="btn btn-white btn-bitbucket addfood" title="添加菜品">
                           					添加菜品
                        				</button>
                        				
                        				<input type="hidden" value="123"/>
                        				<button class="btn btn-white btn-bitbucket delete" title="注销" >
                           					注销
                        				</button>
                        				<%}else{ %>
                        					<button class="btn btn-white btn-bitbucket upState" title="重新营业">
                           					重新营业
                        					</button>
                        				<%} %>
                        			</td>
                                </tr>
                            	<%} %>
                            </tbody>
                        </table>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
	<form action="#" method="post" class="form" id="form" style="width: 500px;" enctype="multipart/form-data">
		<table class="table1" >
   			<tr>
   				<td class="m_id" style="display: none;"><input type="text" name="merchantId" id="merchantId"></td>
   				<td width="25%" align="right">商家名称</td>
   				<td><input type="text" name="name" id="name"></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">联系电话</td>
   				<td><input type="text" name="phone" id="phone"></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">商家地址</td>
   				<td><input type="text" name="address" id="address"></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">商家状态</td>
   				<td>
   					<select name="state" id="state">
   					<option value="新入驻">新入驻</option>
   					<option value="营业中">营业中</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">商家简介</td>
   				<td><textarea name="intro" rows="3" cols="39" id="intro"></textarea></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">商家照片</td>
   				<td>
   					<img src="" class="img" id="img"/>
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

$(document).ready(function(){ 
	$('.mer_selete').change(function(){ 
		var v=$(".mer_selete").val();
		if(v=="1"){
			window.location.href="${pageContext.request.contextPath}/admins/findAllMerchant";
		}else if(v=="2"){
			window.location.href="${pageContext.request.contextPath}/admins/findMerchantByPop";
		}else if(v=="3"){
			window.location.href="${pageContext.request.contextPath}/admins/findMerchantByNew";
		}else if(v=="4"){
			window.location.href="${pageContext.request.contextPath}/admins/findMerchantByYyz";
		}else if(v=="5"){
			window.location.href="${pageContext.request.contextPath}/admins/findMerchantBydelete";
		}
	}) 
}) 
	
$(".delete").click(function(){
	if (window.confirm("是否确认要注销该商家?")) {
		var merchantId= $(this).parent("td").parent("tr").find(".m_id").html();
		window.location.href="${pageContext.request.contextPath}/admins/deleteMerchantById?merchantId="+merchantId;
		
	} 
})
$(".upState").click(function(){
	if (window.confirm("是否允许商家重新营业?")) {
		var merchantId= $(this).parent("td").parent("tr").find(".m_id").html();
		window.location.href="${pageContext.request.contextPath}/admins/upState?merchantId="+merchantId;
		
	} 
})
$(".edit").click(function(){
	var merchantId= $(this).parent("td").parent("tr").find(".m_id").html();
	var name=$(this).parents("tr").find("td").eq(1).html();
	var phone=$(this).parents("tr").find("td").eq(2).html();
	var address=$(this).parents("tr").find("td").eq(3).html();
	var intro=$(this).parents("tr").find("td").eq(4).html();
	var uimg=$(this).parents("tr").find("td").find(".yu").attr("src");
	var state=$(this).parents("tr").find("td").eq(7).html();
	$("#form").css({
		"display" : "block"
	});
	$("#fade").css({
		"display" : "block"
	});
	$("#merchantId").val(merchantId);
	$("#phone").val(phone);
	$("#name").val(name);
	$("#address").val(address);
	$("#intro").val(intro);
	$("#img").attr("src",uimg);
	$("#state").val(state);
	$(".form").attr({
		"action":"${pageContext.request.contextPath}/admins/updateMerchant",
	}) 
	
	
})
$(".addfood").click(function(){
	var merchantId= $(this).parent("td").parent("tr").find(".m_id").html();
	window.location.href="${pageContext.request.contextPath}/admins/addMerchantFoodById?merchantId="+merchantId;	
})
$(".viewfood").click(function(){
	var merchantId= $(this).parent("td").parent("tr").find(".m_id").html();
	window.location.href="${pageContext.request.contextPath}/admins/findMerchantFoodById?merchantId="+merchantId;
})
$(".cancel").click(function(){
	$("#form").css({
		"display" : "none"
	});
	$("#fade").css({
		"display" : "none"
	});
});
$(".sub").click(function(){
	var info=$(".info").val();
	if (info=="") {
		alert("查询内容不能为空");
		return;
	} else{
		$(".form-inline").attr({
			"action":"${pageContext.request.contextPath}/admins/findMerchantByInfo",
		}) 
	}
})
</script>
</html>

