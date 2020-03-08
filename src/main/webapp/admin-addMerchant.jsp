<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>运营后台-添加商家</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin-addmerchant.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
		<script src="${pageContext.request.contextPath}/js/selete.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body onload="setup();preselect('北京市');">
	
	<div class="center">
		<form class="form" action=" " method="post" enctype="multipart/form-data">
			
		<div class="logo">
			<label>商家LOGO</label>
			<div class="logo-img">
				<img src="${pageContext.request.contextPath}/images/zwtp.jpg" width="250px" height="250px" style="margin-right: 30px;" id="img"/>
			</div>
			<input type="file" name="filedata" id="filedata" class="filedata openImg" onchange="coursePptChange()"/>			
		</div>
		
		<div class="information">
			<label>商家基本信息</label>
			<div class="inputlist">
				<div class="list" >
					<span>商家名</span>
					<input type="text" id="name" name="name" value="" class="name" placeholder="商家名"/>
				</div>
				<div class="list">
					<span id="">联系电话</span>
					<input type="text" id="phone" name="phone" value="" class="phone" placeholder="联系电话" maxlength="11"/>
				</div>
				<div class="list">
					<span id="">商家地址</span>
					<div class="address">
						<select class="select" name="province" id="s1"><option></option></select> 
						<select class="select" name="city" id="s2"><option></option></select> 
						<select class="select" name="town" id="s3"><option></option></select>
						<input type="text" id="detailaddress" name="detailaddress" value="" placeholder="详细街道" class="detailaddress"/>
					</div>
					
				</div>
				<div class="list">
					<span id="">商家简要介绍</span>
					<textarea name="info" rows="" cols="" id="info" class="info" ></textarea>
				</div>
				
			</div>
		</div>
		<div class="btn">
			<input type="submit" class="submit" value="添加"/>
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
$('.submit').click(function(){
	var name=$("#name").val();
	var s1=$("#s1").val();
	var phone=$("#phone").val();
	var s2=$("#s2").val();
	var s3=$("#s3").val();
	
	var detail=$("#detailaddress").val();
	var filedate=$('#filedata').val();
	var intro=$('#info').val();
	if (filedate==""||filedate==null) {
		alert("请上传商家图片");
		return;
	} else if(name==""||name==null){
		alert("请输入商家名称");
		return;
	}else if(phone==""||phone==null){
		alert("请输入商家电话");
		return;
	} else if(s2=="市"||s3=="区县"||detail==""){
		alert("请选择正确的地址");
		return;
	}else{
		$(".form").attr({
			"action":"${pageContext.request.contextPath}/admins/addMerchant",
		});
		
	}
})
	</script>
</html>
