<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <% Request.setLastVis(request); %>
    <%@include file="/templates/include-header.jsp"%>
    <title>首页 - <%= conf.SYSTEM_NAME %></title>
</head>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value="index" />
</jsp:include>

<c:choose>
    <c:when test="<%= conf.USE_MODIFIED_INDEX %>">
        <jsp:include page="<%= Config.getCustomFileRelativePath(conf.MODIFIED_INDEX_FILE) %>" />
    </c:when>
    <c:otherwise>
        <div class="am-g">
            <div class="am-g" style="background-color: #eaeaea">
                <div class="am-g" style="margin-bottom: 30px">
                    <div class="am-u-sm-8 am-u-sm-offset-2" style="margin-top: 30px">
                        <h1 style="font-size: 200%">欢迎</h1>
                        <p>欢迎来到 <%= conf.SYSTEM_NAME %> ，现在就来享受极致的购物体验！</p>
                    </div>
                </div>
                <div class="am-g" style="margin-bottom: 30px">
                    <button class="am-center am-btn am-btn-success">开始购物</button>
                </div>
            </div>
            <div class="am-g" style="margin-bottom: 30px">
                <div class="am-u-sm-8 am-u-sm-offset-2" style="margin-top: 20px;">
                    <p>在<%= conf.SYSTEM_NAME %>购物，您能享受到以下保障：</p>
                    <img src="${pageContext.request.contextPath}/assets/img/index/img.png" alt="权益保障" class="am-u-sm-12"/>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
</jsp:include>
</body>
</html>
