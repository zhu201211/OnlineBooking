<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%@ page import="com.cqut.OnlineMealBooking.pojo.*"%>
<%
List<Merchant> merchants=(List<Merchant>) request.getAttribute("merchants");
Merchant merchant= (Merchant)request.getAttribute("merchant");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/admin-addFood.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/jquery.searchableSelect.css"/>
		<script src="${pageContext.request.contextPath }/js/jquery-1.11.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath }/js/jquery.searchableSelect.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<div class="div_from_aoto" style="width: 800px;">
			
		    <form method="post" enctype="multipart/form-data" class="form">
		        <div class="control-group">
		            <label class="laber_from">菜品名称</label>
		            <div  class="controls" ><input class="input_from" name="foodName" type=text placeholder=" 请输入菜品名称"><P class=help-block></P></div>
		        </div>
		        <div class="control-group">
		            <label class="laber_from">输入单价</label>
		            <div  class="controls" ><input class="input_from" name="price" type="number" step="0.01" min="1" placeholder=" 请输入单价"><P class=help-block></P></div>
		        </div>
		        <div class="control-group">
		            <label class="laber_from">所属商家</label>
		            <div  class="controls" >
		                <select class="input_select" name="merchantId">
		                	<%if(merchant!=null){ %>
		                	<option selected value="<%=merchant.getMerchantId()%>"><%=merchant.getMerchantName() %></option>
		                	<%}else{ %>
		                    <option selected value="请选择">请选择所属商家</option>
		                    <%} %>
		                    <%for(Merchant m:merchants){ %>
		                    <option value="<%=m.getMerchantId()%>"><%=m.getMerchantName() %></option>
		                    <%} %>
		                </select>
		            </div>
		        </div>
		        <div class="control-group">
		            <label class="laber_from" >菜品介绍</label>
		            <div  class="controls" ><textarea name="foodIntro" rows="4" cols="32" placeholder=" 请输入菜品介绍"></textarea><P class=help-block></P></div>
		        </div>
		        <div class="control-group">
		            <label class="laber_from" >选择菜品图片</label>
		            <div  class="controls" >
		            	<img src="${pageContext.request.contextPath }/images/zwtp.jpg" width="205px" height="170px" style="margin-right: 30px;" id="img"/>
		            	<input type="file" name="filedata" id="filedata" class="filedata openImg" onchange="coursePptChange()"/>			
		            	<P class=help-block></P>
		            </div>
		       		
		        </div>
		        <div class="control-group">
		            <label class="laber_from" ></label>
		            <div class="controls" ><input class="btn btn-success" type="submit" style="width:180px;height: 25px;" value="确认"></div>
		        </div>
		    </form>
		</div>
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
		 $(function(){
			$('select').searchableSelect();
		});
		 $(".btn").click(function(){
				var m_id=$(".input_select").val();
				var foodName=$(".foodName").val();
				var price=$(".price").val();
				var foodIntro=$(".foodIntro").val();
				var filedata=$("#filedata").val();
				if(foodName=="") {
					alert("输入菜品名称");
					return;
				}else if(price=="") {
					alert("输入价格");
					return;
				}else if (m_id=="请选择") {
					alert("请选择所属商家");
					return;
				}else if (filedata==""||filedata==null) {
					alert("请上传图片");
					return;
				}else{
					$(".form").attr({
						"action":"${pageContext.request.contextPath}/admins/addFood",
					});
				}
			});
	</script>
</html>
