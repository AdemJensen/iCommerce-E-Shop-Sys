<%@ page contentType="text/html;charset=UTF-8" %>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ page import="top.chorg.iCommerce.api.Config" %>
<%@ page import="java.io.FileNotFoundException" %>
<%
    request.setCharacterEncoding("UTF-8");
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Config conf;
    try {
        conf = Config.readConfFromFile(request);
    } catch (FileNotFoundException e) {
        response.sendRedirect(request.getContextPath() + "/init");
        return;
    }
%>

<!-- Set render engine for 360 browser -->
<meta name="renderer" content="webkit">

<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/img/favicon.ico">

<!-- Add to home screen for Chrome on Android -->
<meta name="mobile-web-app-capable" content="yes">
<link rel="icon" sizes="192x192" href="${pageContext.request.contextPath}/assets/img/favicon-72.png">

<!-- Add to home screen for Safari on iOS -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="Amaze UI"/>
<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/img/favicon-72.png">

<!-- Tile icon for Win8 (144x144 + tile color) -->
<meta name="msapplication-TileImage" content="assets/i/favicon-72.png">
<meta name="msapplication-TileColor" content="#0e90d2">

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="/assets/js/jquery.ie8.min.js"></script>
<script src="/assets/js/modernizr.js"></script>
<![endif]-->
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/MD5.js"></script>
