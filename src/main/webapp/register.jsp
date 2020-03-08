<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>好吃吧订餐网登录平台</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/l.css"/></head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js" ></script>
<body>
<div class="passport-body ">

	<div id="passport-widget-login-entry">
		<div class="passport-widget-login-common yizhan-skin">
			<div class="header">
				<h1 class="icon-header">好吃吧订餐网</h1>
				<p class="slogan">量"胃"定做 &nbsp;   &nbsp;&nbsp;&nbsp;好吃吧订餐网！智能为您服务！</p>
			</div>
			<div class="body normal-login-mode clearfix">
				<div class="main"><h2>欢迎注册</h2>
					<form class="form" action="" id="login-form"  method="post">
						<div class="common-err-msg"></div>
						<div class="form-group account-group">
							<label class="title-label">账号/手机号</label>
							<input id="userPhone" name="userPhone"type="text" class="form-control" placeholder="用户名/手机号" maxlength="30">
						</div>
						<div class="form-group sms-vcode-group">
							<label class="title-label">验证码</label>
							<input id="code" name="code"  type="text" class="form-control" placeholder="验证码" maxlength="10">
							<button type="button" title="点击获取验证码"class="get-sms-vcode">获取验证码</button>
							<span class="sms-ing hidden">获取中 <em>120</em></span>
							
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
							<a href="${pageContext.request.contextPath}/login.jsp" title="切换密码登录" class="account-application">用户登录</a>
							<a href="${pageContext.request.contextPath}/admin-login.jsp" title="管理员登录" target="_blank">管理员登录</a>
							<input id="form-submit" type="submit" class="btn btn-default login-btn" value="注册">
						</div>
					</form>
				</div>
				<div class="sider icon-sider"id= "container">
				        <p class="ImgList" style="background:url('${pageContext.request.contextPath}/images/t4.jpg') no-repeat center "></p>	
				        <p class="ImgList" style="background:url('${pageContext.request.contextPath}/images/t2.jpg') no-repeat center"></p>
				        <p class="ImgList" style="background:url('${pageContext.request.contextPath}/images/t3.jpg')  no-repeat center"></p>		
		  				<p class="ImgList" style="background:url('${pageContext.request.contextPath}/images/t1.jpg')  no-repeat center"></p>
		  				<p class="ImgList" style="background:url('${pageContext.request.contextPath}/images/t5.jpg')  no-repeat center"></p>
				</div>
			</div>
		</div>
	
	</div>
</div>
<script src="js/banner.js" type="text/javascript" charset="utf-8"></script>
</body>
	<script type="text/javascript">
	if("${msg.msg}"!=""&&"${msg.msg}"!=null){
		alert("${msg.msg}");
	}
		$(".get-sms-vcode").click(function(){
		var userPhone=document.getElementById("userPhone").value;
		var myreg=/^[1][3,4,5,7,8,9][0-9]{9}$/;
		//首位为1、第二位为345789、后9位为0-9
		if (!myreg.test(userPhone)) {
			alert("请填写正确的手机号码"+userPhone);
			return ;
		} else {
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/users/sendCode2?userPhone="+userPhone,
				dataType:'json',
				async:true,
				success:function(data){
					alert(data.msg);
					}
			});
		}
	});
	
		$(".login-btn").click(function(){
			var userPhone=document.getElementById("userPhone").value;
			var code=document.getElementById("code").value;
			var userPassword=document.getElementById("userPassword").value;
			var userPassword2=document.getElementById("userPassword2").value;
			if (userPhone==null||userPhone=="") {
				alert("手机号不能为空");
			} else if(code==null||code==""){
				alert("输入验证码");
			} else if(userPassword==null||userPassword==""||userPassword2==null||userPassword2==""){
				alert("密码不能为空");
			} else if(userPassword!=userPassword2){
				alert("密码不一致,请重试!");
			}else {
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/users/register",
					async:false,
					data:$('.form').serialize(),
					dataType:'json',
					success:function(data){
						alert(data.msg);
					}
				});
			}
		});

	</script>

</html>
