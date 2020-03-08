<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>好吃吧订餐网·运营后台|登录</title>
		
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/admin-login.css" />
		<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
		
	</head>

	<body>
		<div class="content">
			<div class="bidTitle">好吃吧订餐网·运营后台</div>
				<form action="" method="post" class="form">
					<div class="logCon">
						<span>账号:</span>
						<input class="bt_input" type="text" name="userName" id="userName"/>
						<span>密码:</span>
						<input class="bt_input" type="password" name="userPassword" id="userPassword"/>
						<input class="logingBut" type="submit" name="submit" class="submit"  id="submit" value="登录" >
						</br><font color="red">${msg}</font></br>
					</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
	
		$("#submit").click(function(){
			var user=document.getElementById("userName").value;
			var pw=document.getElementById("userPassword").value;
			if(user==""||user==null){
				alert("账号不能为空！");
				return;
			} else if(pw==""||pw==null){
				alert("密码不能为空！");
				return;
			}else{
				$(".form").attr({
					"action":"${pageContext.request.contextPath}/admins/login",
				})
			}
			
		});
	</script>
</html>