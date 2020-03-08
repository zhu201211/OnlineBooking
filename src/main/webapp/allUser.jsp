<%@ page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>用户管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.paginate.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.yhhDataTable.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/alluser.css"/>
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
                                   <input name="info" placeholder="输入关键字查询用户" style="height: 29px;width: 220px;" class="form-control info">
                               	</div>
                           </div>
                           <div class="checkbox m-l m-r-xs" style="margin-left: 0px; margin-top:5px;">
                               <button class="btn btn-white submit sub"type="submit">查询</button>
                           </div>
                      	</form>
                  	</div>
                    <div class="ibox-content">
                        <table class="table table-striped table-bordered table-hover dataTables-example" id="testtable1">
                            <thead>                
                                <tr>
                                	<th>ID</th>
                                	<td>用户头像</td>
									<td>用户昵称</td>
									<td>手机号码</td>
									<td>登录密码</td>
                                    <th style="text-align: center;">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%for(User u:users){ %>
                                <tr class="gradeX">
                                	<td class="u_id"><%=u.getId() %></td>
                                	<td><img src="${pageContext.request.contextPath}/<%=u.getUserImg() %>" class="yu" alt="加载中..." style="width: 45px;height: 45px;"></td>
                                    <td><%=u.getUserName() %></td>
                                    <td><%=u.getUserPhone() %></td>
                                    <td><%=u.getUserPassword() %></td>
                                    <td style="text-align: center;">
                                    	<button class="btn btn-white btn-bitbucket view" title="查看用户订单" >
                           					查看用户订单
                        				</button>
                        				<button class="btn btn-white btn-bitbucket edit" title="编辑" >
                           					编辑
                        				</button>
                        				<button class="btn btn-white btn-bitbucket delete" title="删除" >
                           					删除
                        				</button>
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
   				<td width="25%" align="right">用户ID</td>
   				<td><input type="text" name="id" id="userId" readonly></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">用户头像</td>
   				<td>
   					<img src="images/user-photo.png" class="img" id="img"/>
   					<input type="file" name="filedata" id="filedata" class="filedata openImg" onchange="coursePptChange()"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">用户昵称</td>
   				<td><input type="text" name="userName" id="name"></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">登录手机</td>
   				<td><input type="text" name="userPhone" id="phone"></td>
   			</tr>
   			<tr>
   				<td width="25%" align="right">密码</td>
   				<td><input type="text" name="userPassword" id="pw"></td>
   			</tr>
   			
   			<tr>
	    		<td colspan="2"><input name="" type="submit" value="确认" class="submit" id="change"><input name="" type="button" value="取消" class="cancel"></td>
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

	
$(".delete").click(function(){
	var uId='${u.id}';
		if (window.confirm("是否确认要删除?")) { 
			// 确认时做的操作 
			var userId= $(this).parent("td").parent("tr").find(".u_id").html();
			$.ajax({
				type:"get",
				url:"${pageContext.request.contextPath}/admins/deleteUser?id="+userId,
				async:false,
				success:function(data){
					alert(data.msg);
					window.location.reload();
				}
			});
		}
})

$(".edit").click(function(){
	var userId= $(this).parent("td").parent("tr").find(".u_id").html();
	var name=$(this).parents("tr").find("td").eq(2).html();
	var phone=$(this).parents("tr").find("td").eq(3).html();
	var pw=$(this).parents("tr").find("td").eq(4).html();
	var uimg=$(this).parents("tr").find("td").find(".yu").attr("src");
	
	$("#form").css({
		"display" : "block"
	});
	$("#fade").css({
		"display" : "block"
	});
	$("#userId").val(userId);
	$("#phone").val(phone);
	$("#name").val(name);
	$("#pw").val(pw);
	$("#img").attr("src",uimg);
	$(".form").attr({
			"action":"${pageContext.request.contextPath}/admins/updataUser",
	}) 
		
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
	var searchInfo=$(".info").val();
	if (searchInfo=="") {
		alert("查询内容不能为空");
		return;
	} else{
		$(".form-inline").attr({
			"action":"${pageContext.request.contextPath}/admins/searchUser?searchInfo="+searchInfo,
		}) 
	}
})
$(".view").click(function(){
	var userId= $(this).parent("td").parent("tr").find(".u_id").html();
	window.location.href="${pageContext.request.contextPath}/admins/findIntentByUserId?userId="+userId;
})
</script>
</html>
