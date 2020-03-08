<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>好吃吧订餐网登录平台</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/addUser.css"/></head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js" ></script>
<body>
<div class="passport-body ">

	<div id="passport-widget-login-entry">
		<div class="passport-widget-login-common yizhan-skin">
			<div class="body normal-login-mode clearfix">
				<div class="main">
					<form class="form-horizontal" enctype="multipart/form-data" method="post" action="" id="addUser_form">
						<div class="common-err-msg"></div>
						<div class="form-group account-group tx">
							<label class="title-label">头像</label>
							<img src="${pageContext.request.contextPath }/images/user-photo.png" width="50px" height="50px" style="float: left;margin-right: 30px;" id="img"/>
							<input type="file" name="filedata" id="filedata" class="filedata openImg" onchange="coursePptChange()"style="float: left;margin-top: 20px;"/>
						</div>
						<div class="form-group account-group">
							<label class="title-label">手机号</label>
							<input id="userPhone" name="userPhone"type="text" class="form-control" placeholder="手机号" maxlength="30">
						</div>
						<div class="form-group sms-vcode-group">
							<label class="title-label">验证码</label>
							<input id="code"name="code" type="text" class="form-control" placeholder="验证码" maxlength="10">
							<button type="button" title="点击获取验证码"class="get-sms-vcode">获取验证码</button>
							<span class="sms-ing hidden">获取中 <em>120</em></span>
							
						</div>
						<div class="form-group account-group">
							<label class="title-label">用户昵称</label>
							<input id="userName" name="userName"type="text" class="form-control" placeholder="用户昵称" maxlength="30">
						</div>
						<div class="form-group pwd-group">
							<label class="title-label">密码</label>
							<input id="userPassword" name="userPassword"type="password" class="form-control" placeholder="密码" maxlength="30">
						</div>
						<div class="form-group pwd-group">
							<label class="title-label">确认密码</label>
							<input id="userPassword2" name="userPassword2"type="password" class="form-control" placeholder="确认密码" maxlength="30">
						</div>
						<div class="form-group action-group">
							<button id="form-submit" type="submit" class="btn btn-default login-btn">添加</button>
						</div>
					</form>
				</div>
				
			</div>
		</div>
	
	</div>
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
		//发送短信
		$(".get-sms-vcode").click(function(){
			var userPhone=document.getElementById("userPhone").value;
			var myreg=/^[1][3,4,5,7,8,9][0-9]{9}$/;
			//首位为1、第二位为345789、后9位为0-9
			if (!myreg.test(userPhone)) {
				alert("请填写正确的手机号码");
				return ;
			} else {
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/admins/sendCode?userPhone="+userPhone,
					dataType:'json',
					async:true,
					success:function(data){
						alert(data.msg);
						}
				});
			}
		});
		//添加用户
		$(".login-btn").click(function(){
			var userPhone=document.getElementById("userPhone").value;
			var code=document.getElementById("code").value;
			var pw=document.getElementById("userPassword").value;
			var pwr=document.getElementById("userPassword2").value;
			if(userPhone==""||userPhone==null){
				alert("手机号不能为空！");
				return;
			}else if (code==""||code==null) {
				alert("验证码不能为空！");
				return;
			} else if(pw==""||pw==null||pwr==""||pwr==null){
				alert("密码不能为空！");
				return;
			} else if(pw!=pwr){
				alert("两次密码不一致！请重试!");
				return;
			} else{
				$("#addUser_form").attr({
					"action":"${pageContext.request.contextPath}/admins/addUser",
				});
			}
		})
	</script>

</html>
