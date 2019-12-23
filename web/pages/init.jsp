<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

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

    <title>正在初始化页面</title>
</head>
<script>
    $(window).on('load', function () {
        window.location = "${pageContext.request.contextPath}/init"
    })
</script>
<body>
<div class="container-fluid vh-100">
    <div class="col-sm-8 text-center" style="position: relative;transform: translate(-50%, -50%);top: 40%;left: 50%;">
        <h1 style="font-size: 400%" class="my-5">正在初始化页面</h1>
        <p class="col-auto mt-3" style="max-height: 60%;overflow: scroll;">网站正在初始化中，请稍侯...</p>
    </div>
</div>
</body>
</html>
