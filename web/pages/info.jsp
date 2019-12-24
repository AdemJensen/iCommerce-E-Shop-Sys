<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<%
    request.setCharacterEncoding("UTF-8");
    String title = request.getParameter("title");
    if (title == null) title = "错误";
%>
<head>
    <%@include file="/templates/include-header.jsp"%>
    <title><%= title %> - <%= conf.SYSTEM_NAME %></title>
</head>
<%
    String res_type = request.getParameter("info_type");
    String res_str = request.getParameter("info_str");
    String res_info = request.getParameter("info_hint");
    if (res_type == null || res_str == null) {
        res_type = "text-danger";
    } else {
        switch (res_type) {
            case "success":
                res_type = "text-success";
                break;
            case "warning":
                res_type = "text-warning";
                break;
            default:
                res_type = "text-danger";
        }
    }
    if (res_str == null) res_str = "错误";
    if (res_info == null) res_info = "未授权的访问";
    String call_back = request.getParameter("call_back");
    if (call_back == null) call_back = "";
    if (call_back.equals("-1")) call_back = Request.getLastVis(request);
%>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value="none" />
</jsp:include>
<div class="container-fluid vh-100">
    <div class="col-sm-8 text-center" style="position: relative;transform: translate(-50%, -50%);top: 40%;left: 50%;">
        <h1 style="font-size: 400%" class="my-5 <%= res_type %>"><%= res_str %></h1>
        <p class="mt-3" style="max-height: 60%;overflow: auto;"><%= res_info %></p>
        <c:choose>
            <c:when test='<%= res_type.equals("text-success") && call_back.length() > 0 %>'>
                <p><span id="counter">3</span> 秒后将返回上一页...<a href="<%= call_back %>">立即返回</a></p>
            </c:when>
        </c:choose>
    </div>
</div>
<c:choose>
    <c:when test='<%= res_type.equals("text-success") && call_back.length() > 0 %>'>
        <script>
            var count = 3;
            $(document).ready(function () {
                window.setInterval(function () {
                    if (count <= 0) {
                        location.href = "<%= call_back %>";
                    } else {
                        count--;
                        document.getElementById("counter").innerHTML = String(count);
                    }
                }, 1000);
            });
        </script>
    </c:when>
</c:choose>
</body>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
</jsp:include>
</html>
