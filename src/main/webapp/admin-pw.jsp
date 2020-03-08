<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<!DOCTYPE >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath }/css/admin_pw.css" type="text/css" media="screen" />

</head>
<body>
<div class="div_from_aoto" style="width: 500px;">
    <form class="form" method="post" action="">
    	<div class="control-group">
            <label class="laber_from">密码</label>
            <div  class="controls" ><input class="input_from pw1" name="pw1" type=text placeholder="请输入密码"><P class=help-block></P></div>
        </div>
        <div class="control-group">
            <label class="laber_from">新密码</label>
            <div  class="controls" ><input class="input_from pw2" name="pw2" type=text placeholder="请输入新密码"><P class=help-block></P></div>
        </div>
        <div class="control-group">
            <label class="laber_from" >确认密码</label>
            <div  class="controls" ><input class="input_from pw3" name="pw3" type=text placeholder="请确认新密码"><P class=help-block></P></div>
        </div>
        
        <div class="control-group">
            <label class="laber_from" ></label>
            <div class="controls" ><input class="btn btn-success" type="submit" style="width:120px;" ></div>
        </div>
    </form>
</div>
<script type="text/javascript">
	$(".btn").click(function(){
		var pw1=$(".pw1").val();
		var pw2=$(".pw2").val();
		var pw3=$(".pw3").val();
		if (pw1==""||pw1==null) {
			alert("请输入登录密码");
		} else if(pw2==""||pw2==null||pw3==""||pw3==null){
			alert("请输入新密码");
		}else if(pw2!=pw3){
			alert("两次密码不一致！");
		}else{
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/admins/updatePassword",
				data:$('.form').serialize(),
				dataType:'json',
				async:false,
				success:function(data){
					alert(data.msg);
					window.location.href="${pageContext.request.contextPath}/admin-login.jsp";
					}
			});
		}
	})
	
</script>

</body>
</html>