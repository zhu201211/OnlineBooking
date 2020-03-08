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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js" ></script>
<body>
<div class="passport-body ">

	<div id="passport-widget-login-entry">
		<div class="passport-widget-login-common yizhan-skin">
			<div class="header">
				<h1 class="icon-header" title="好吃吧订餐网">好吃吧订餐网</h1>
				<p class="slogan">量"胃"定做 &nbsp;   &nbsp;&nbsp;&nbsp;好吃吧订餐网！智能为您服务！</p>
			</div>
			<div class="body normal-login-mode clearfix">
				<div class="main"><h2>欢迎登录</h2>
					<form class="form" action=" " id="login-form" method="post">
						<div class="common-err-msg"></div>
						<div class="form-group account-group">
							<label class="title-label">手机号</label>
							<input id="userPhone" name="userPhone"type="text" class="form-control" autofocus placeholder="手机号" maxlength="30">
						</div>
						<div class="form-group pwd-group">
							<label class="title-label">密码</label>
							<input id="userPassword" name="userPassword" type="password" class="form-control" placeholder="密码" maxlength="30">
						</div>
						<div class="form-group action-group">
							<a id="form-forgetpwd" href="#" title="点击找回密码" target="_blank" class="forget-pwd">忘记密码？</a>
							<a href="${pageContext.request.contextPath}/register.jsp" title="用户注册" >用户注册</a>
							<a href="${pageContext.request.contextPath}/login0.jsp" title="切换短信登录" >短信登录</a>
							<a href="${pageContext.request.contextPath}/admin-login.jsp" title="管理员登录" target="_blank">管理员登录</a>
							<button id="form-submit" type="submit" class="btn btn-default login-btn">登录</button></div>
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
<script src="${pageContext.request.contextPath}/js/banner.js" type="text/javascript" charset="utf-8"></script>
</body>
	<script type="text/javascript">
	if("${msg.msg}"!=""&&"${msg.msg}"!=null){
		alert("${msg.msg}");
	}
		$(".login-btn").click(function(){
			var userPhone=document.getElementById("userPhone").value;
			var userPassword=document.getElementById("userPassword").value;
			if (userPhone==null||userPhone=="") {
				alert("账号不能为空");
				return ;
			} else if(userPassword==null||userPassword==""){
				alert("密码不能为空");
				return ;
			} else {
				$(".form").attr({
					"action":"${pageContext.request.contextPath}/users/login_1"
				})
			}
		});
		
		
	</script>

</html>
