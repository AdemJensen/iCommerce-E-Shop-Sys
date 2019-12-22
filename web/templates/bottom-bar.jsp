<%@ page import="com.google.gson.Gson" %>
<%@ page import="top.chorg.iCommerce.api.Config" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Gson gson = new Gson();
    Config conf = gson.fromJson(request.getParameter("CONFIG"), Config.class);
%>
<div class="container-fluid py-3 px-0" style="background-color: slategrey;">
    <p class="text-center text-white my-0">
        Powered by. 苏卡不列斯基 | <a class="text-white" href="${pageContext.request.contextPath}/admin/login">管理员登录</a><br/>
        Copyright © ChenHui Studio 2015-2020, All rights reserved.
    </p>
</div>
<%= request.getParameter("NO_CHAT") == null ? conf.CHAT_SYSTEM : "" %>