<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        Request.setLastVis(request);
        String itemCoverS = request.getParameter("cover");
        int itemCover = (itemCoverS == null) ? 0 : Integer.parseInt(itemCoverS);
        String itemPage = request.getParameter("page");
        String itemName = request.getParameter("name");
        String priceS = request.getParameter("price");
        double price = (priceS == null) ? 0 : Double.parseDouble(priceS);
    %>
    <%@include file="/templates/include-header.jsp"%>
    <title>浏览页面 - <%= itemName %> - <%= conf.SYSTEM_NAME %></title>
</head>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value='none' />
</jsp:include>
<div class="container-fluid col-8 offset-2 my-3">
    <div class="row">
        <div class="col-4 p-1 border border-secondary" style="height: 400px;">
            <img alt="item" class="position-relative" style="transform: translate(-50%, -50%);top: 50%;left: 50%; max-height: 390px; max-width: 95%" src="<%= Resource.getItemCover(request, itemCover) %>">
        </div>
        <div class="col-8 pl-5">
            <h2 style="font-size: 150%"><%= itemName %></h2>
            <hr/>
            <div class="row my-2">
                <label class="col-2 text-secondary">价格</label>
                <div class="col-10">
                    <label class="text-danger" style="font-size: 200%">¥<label id="price_new"><%= price %></label></label>
                </div>
            </div>
        </div>
    </div>
</div>
<hr/>
<div class="container-fluid" style="min-height: 40%">
    <c:choose>
        <c:when test="<%= new File(Config.getItemPageFileAbsolutePath(request, itemPage)).exists() %>">
            <jsp:include page='<%= Config.getItemPageFileRelativePath(itemPage) %>'/>
        </c:when>
        <c:otherwise>
            <label class="align-self-center col-12 text-center" style="color:lightGray; font-size:300%;">该商品没有简介</label>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
</jsp:include>
</body>
</html>
