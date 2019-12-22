<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="top.chorg.iCommerce.api.Config" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="top.chorg.iCommerce.function.auth.AuthInfo" %>
<%@ page import="top.chorg.iCommerce.database.ItemData" %>
<%@ page import="top.chorg.iCommerce.api.ItemType" %>
<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="top.chorg.iCommerce.database.Database" %>
<%@ page import="top.chorg.iCommerce.database.AuthData" %>
<%
    Gson gson = new Gson();
    Config conf = gson.fromJson(request.getParameter("CONFIG"), Config.class);
    Database database;
    try {
        database = Database.connect(Config.readConfFromFile(request));
    } catch (Exception e) {
        Request.becomeInfoPage(request, response, "error", "错误", "服务异常",
                "服务异常，正在积极处理中，请稍后再来吧(" + e.getMessage() + ")");
        return;
    }
    ItemType[] arr = ItemData.getTypeList(database);
%>
<style>
    #userDropdownLink:hover, #adminDropdownLink:hover {
        background: rgba(142, 152, 162, 0.12);
        cursor: pointer;
    }
</style>
<nav class="navbar navbar-light navbar-expand-lg" style="background-color: #e3f2fd;">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><%= conf.SYSTEM_NAME %></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse container-fluid" id="navbarNav">
        <div class="collapse navbar-collapse pl-0">
            <ul class="navbar-nav col-sm-auto">
                <%
                    String select = request.getParameter("selected");
                %>
                <%!
                    String getSelect(String content, String target) {
                        return content.equals(target) ? "active" : "";
                    }
                    String getAddSel(String content, String target) {
                        return content.equals(target) ? "<span class=\"sr-only\">(current)</span>" : "";
                    }
                %>
                <li class="nav-item <%= getSelect(select, "index") %>">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">首页<%= getAddSel(select, "index") %></a>
                </li>
                <li class="nav-item <%= getSelect(select, "all") %>">
                    <a class="nav-link" href="${pageContext.request.contextPath}/items">所有商品<%= getAddSel(select, "type") %></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle <%= getSelect(select, "type") %>" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        分类<%= getAddSel(select, "type") %>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <%
                            if (arr != null) {
                                for (ItemType itemType : arr) {
                                    out.print("<a class=\"dropdown-item\" href=\"" + request.getContextPath()+ "/items?type=" + itemType.id + "\">" + itemType.name + "</a>");
                                }
                            }
                        %>
                    </div>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0 pl-3 col-sm-auto">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="全站搜索">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜索</button>
            </form>
        </div>
        <c:choose>
            <c:when test="<%= AuthInfo.isCustomer(session) %>">
                <div class="nav-item dropdown">
                    <div class="align-self-sm-end nav-item p-1" id="userDropdownLink"
                         data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="<%= AuthInfo.getCustomerAvatar(request) %>" alt="Avatar" style="height: 30px; border-radius: 100%"/>
                        <label class="mx-2 my-0"><%= AuthInfo.getCustomerUsername(session) %></label>
                    </div>
                    <div class="dropdown-menu" aria-labelledby="userDropdownLink" style="left: auto; right: 0;">
                        <a class="dropdown-item disabled" href="#">余额：¥<%= String.format("%.2f", AuthData.getCustomerBalance(database, AuthInfo.getCustomerId(session))) %></a>
                        <a class="dropdown-item disabled" href="#">个人主页</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/cart">购物车</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/orders">我的订单</a>
                        <a class="dropdown-item disabled" href="#">设置</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/exit">退出登录</a>
                    </div>
                </div>
            </c:when>
            <c:when test="<%= AuthInfo.isAdmin(session) %>">
                <div class="nav-item dropdown">
                    <div class="align-self-sm-end nav-item p-1" id="adminDropdownLink"
                         data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="<%= AuthInfo.getAdminAvatar(request) %>" alt="Avatar" style="height: 30px; border-radius: 100%"/>
                        <label class="mx-2 my-0"><%= AuthInfo.getAdminUsername(session) %>【权限级别<%= AuthInfo.getAdminLevel(session) %>管理员】</label>
                    </div>
                    <div class="dropdown-menu" aria-labelledby="adminDropdownLink" style="left: auto; right: 0;">
                        <c:choose>
                            <c:when test="<%= AuthInfo.getAdminLevel(session) >= 0 %>">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/order">订单管理</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/item">商品管理</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/type">商品类别管理</a>
                                <a class="dropdown-item disabled" href="${pageContext.request.contextPath}/admin/type">图床</a>
                                <c:choose>
                                    <c:when test="<%= AuthInfo.getAdminLevel(session) > 0 %>">
                                        <a class="dropdown-item disabled" href="${pageContext.request.contextPath}/admin/account/customer">顾客账户管理</a>
                                        <a class="dropdown-item disabled" href="${pageContext.request.contextPath}/admin/account/admin">管理员账户管理</a>
                                        <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/sys">系统设置</a>
                                    </c:when>
                                </c:choose>
                            </c:when>
                        </c:choose>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/exit">退出登录</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="align-self-sm-end">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-light">登录</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-primary text-white">注册</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</nav>