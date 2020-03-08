<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta name="author" content="order by fengfeng"/>
<title>好吃吧订餐运营后台</title>

<link rel="stylesheet" href="${pageContext.request.contextPath }/css/admin-home.css" type="text/css" media="screen" />

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/tendina.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>

</head>
<body>
    <div class="layout_top_header">
            <div style="float: left"><span style="font-size: 16px;line-height: 45px;padding-left: 20px;color: #8d8d8d">欢迎进入好吃吧订餐网运营后台</span></div>
            <div id="ad_setting" class="ad_setting">
                <a class="ad_setting_a" href="javascript:; ">
                    <i class="icon-user glyph-icon" style="font-size: 20px"></i>
                    <span>${u.userName}</span>
                    <i class="icon-chevron-down glyph-icon"></i>
                </a>
                <ul class="dropdown-menu-uu" style="display: none" id="ad_setting_ul">
                    <li class="ad_setting_ul_li"> <a href="${pageContext.request.contextPath }/admin-pw.jsp" target="menuFrame" class="btn-act"><i class="icon-user glyph-icon"></i> 修改密码 </a> </li>
                    <li class="ad_setting_ul_li"> <a href="${pageContext.request.contextPath }/admin-login.jsp"><i class="icon-signout glyph-icon"></i> <span class="font-bold">退出</span> </a> </li>
                </ul>
            </div>
    </div>
    <!--菜单-->
    <div class="layout_left_menu">
        <ul id="menu">
            <li class="childUlLi">
               <a href="${pageContext.request.contextPath}/time.jsp"target="menuFrame"><i class="glyph-icon icon-home"></i>首页</a>
            </li>
            <li class="childUlLi">
                <a href="#"><i class="glyph-icon icon-reorder"></i>用户管理</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admins/findAllUser" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>所有用户</a></li>
                    <li><a href="${pageContext.request.contextPath}/addUser.jsp" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>增加用户</a></li>
                </ul>
            </li>
            <li class="childUlLi">
                <a href="#"> <i class="glyph-icon icon-reorder"></i>商家管理</a>
                <ul>
                	<li><a href="${pageContext.request.contextPath}/admins/findAllMerchant" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>商家列表</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin-addMerchant.jsp" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>增加商家</a></li>
                </ul>
            </li>
            <li class="childUlLi">
                <a href="#"> <i class="glyph-icon icon-reorder"></i>菜品管理</a>
                <ul>
                	<li><a href="${pageContext.request.contextPath}/admins/findAllFoods" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>菜品列表</a></li>
                    <li><a href="${pageContext.request.contextPath}/admins/openAddFood" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>菜品上线</a></li>
                </ul>
            </li>
            <li class="childUlLi">
                <a href="#"> <i class="glyph-icon  icon-location-arrow"></i>订单管理</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admins/findUnpaidIntent" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>未付款订单</a></li>
                    <li><a href="${pageContext.request.contextPath}/admins/findNotfinishIntent" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>未收货订单</a></li>
                    <li><a href="${pageContext.request.contextPath}/admins/findFinishIntent" target="menuFrame" class="btn-act"><i class="glyph-icon icon-chevron-right"></i>已完成订单</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <!--菜单-->
    <div id="layout_right_content" class="layout_right_content">

        <div class="route_bg">
            <a href="${pageContext.request.contextPath}/time.jsp" target="menuFrame">主页</a><i class="glyph-icon icon-chevron-right"></i>
            <a id="change"> </a>
        </div>
        <div class="mian_content">
            <div id="page_content">
                <iframe id="menuFrame" name="menuFrame" src="${pageContext.request.contextPath}/time.jsp" style="overflow:visible;"
                        scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
            </div>
        </div>
    </div>
    <div class="layout_footer">
        <p>Copyright © 版权所有 2019 CQUT锋锋工作室 技术支持 锋锋科技</p>
    </div>
</body>
	<script type="text/javascript">
		$(".btn-act").click(function(){
			var info=$(this).text();
			document.getElementById("change").innerHTML=info;
		})
	</script>
</html>