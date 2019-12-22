<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
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
        <div class="container-fluid">
            <div class="row px-0 py-5" style="background-color: #eaeaea">
                <div class="col-sm-8 offset-2">
                    <h1 style="font-size: 200%">欢迎</h1>
                    <p>欢迎来到<%= conf.SYSTEM_NAME %>，现在就来享受极致的购物体验！</p>
                    <div class="row mt-4">
                        <a href="${pageContext.request.contextPath}/items" class="position-relative btn btn-success" style="transform: translateX(-50%);left: 50%;">开始购物</a>
                    </div>
                </div>
            </div>
            <div class="row my-3">
                <div class="col col-sm-8 offset-2">
                    <p>在<%= conf.SYSTEM_NAME %>购物，您能享受到以下保障：</p>
                    <img src="${pageContext.request.contextPath}/assets/img/index/img.png" alt="权益保障" class="col-sm-12"/>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
</jsp:include>
</html>
