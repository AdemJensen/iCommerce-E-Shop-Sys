<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.PrintStream" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<html>
<head>
    <%@include file="/templates/include-header.jsp"%>
    <title>出错啦</title>
</head>
<body>
<nav class="navbar navbar-light navbar-expand-lg" style="background-color: #e3f2fd;">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><%= conf.SYSTEM_NAME %></a>
</nav>

<div class="container-fluid vh-100">
    <div class="col-sm-8 text-center" style="position: relative;transform: translate(-50%, -50%);top: 40%;left: 50%;">
        <h1 style="font-size: 400%" class="text-danger my-5">页面出现错误</h1>
        <p class="text-left col-auto mt-3" style="max-height: 60%;overflow: scroll;">真抱歉，此页面出现了错误，要不等会再试试？<br/>错误信息：<%
            exception.printStackTrace();
            ByteArrayOutputStream ost = new ByteArrayOutputStream();
            exception.printStackTrace(new PrintStream(ost));
            out.print(ost);
        %></p>
    </div>
</div>
</body>
<div class="container-fluid py-3 px-0" style="background-color: slategrey;">
    <p class="text-center text-white my-0">
        Powered by. 苏卡不列斯基<br/>Copyright © ChenHui Studio 2015-2020, All rights reserved.
    </p>
</div>
</html>
