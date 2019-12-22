<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="top.chorg.iCommerce.database.Database" %>
<%@ page import="top.chorg.iCommerce.database.ItemData" %>
<%@ page import="top.chorg.iCommerce.api.Item" %>
<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.io.File" %>
<%@ page import="top.chorg.iCommerce.function.auth.AuthInfo" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        Request.setLastVis(request);
        Database database;
        try {
            database = Database.connect(Config.readConfFromFile(request));
        } catch (Exception e) {
            Request.becomeInfoPage(request, response, "error", "错误", "服务异常",
                    "服务异常，正在积极处理中，请稍后再来吧(" + e.getMessage() + ")");
            return;
        }
        String itemIdS = request.getParameter("item");
        int itemId = (itemIdS == null) ? 0 : Integer.parseInt(itemIdS);
        Item info = ItemData.getItemById(database, itemId);
        if (info == null) {
            Request.becomeInfoPage(request, response, "error", "404", "未找到你要访问的界面",
                    "唔姆，未找到你要访问的界面呢，你是从哪里点进来的？");
            return;
        }
    %>
    <%@include file="/templates/include-header.jsp"%>
    <title><%= info.name %> - <%= conf.SYSTEM_NAME %></title>
</head>
<script>
    let pn = <%= info.newPrice %>;
    let po = <%= info.oldPrice %>;
    let qu = <%= info.quantity %>;
    function calcPrice() {
        let quantity = Number.parseInt(document.querySelector("#quantity").value);
        if (quantity > qu) document.querySelector("#quantity").value = qu;
        if (quantity < 1) document.querySelector("#quantity").value = 1;
        quantity = Number.parseInt(document.querySelector("#quantity").value);
        document.querySelector("#price_new").innerHTML = (String) (Math.floor(quantity * pn * 100) / 100);
        document.querySelector("#price_old").innerHTML = (String) (Math.floor(quantity * po * 100) / 100);
    }

    function carted() {
        let q = Number.parseInt(document.querySelector("#quantity").value);
        if (q <= 0) return;
        $.post(
            "${pageContext.request.contextPath}/cart/add",
            {
                "item": <%= info.id %>,
                "quantity": q
            },
            function (data) {
                if (data === "OK") {
                    document.querySelector("#addToCart").innerHTML = "添加成功";
                    setTimeout(function () {
                        document.querySelector("#addToCart").innerHTML = "加入购物车";
                    }, 3000);
                } else {
                    document.querySelector("#addToCart").innerHTML = "添加失败";
                    setTimeout(function () {
                        document.querySelector("#addToCart").innerHTML = "加入购物车";
                    }, 3000);
                }
            }
        ).error(function () {
            document.querySelector("#addToCart").innerHTML = "添加失败";
            setTimeout(function () {
                document.querySelector("#addToCart").innerHTML = "加入购物车";
            }, 3000);
        });

    }
    $(function () {
        calcPrice();
    });
</script>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value='none' />
</jsp:include>
<div class="container-fluid col-8 offset-2 my-3">
    <div class="row">
        <div class="col-4 p-1 border border-secondary" style="height: 400px;">
            <img alt="item" class="position-relative" style="transform: translate(-50%, -50%);top: 50%;left: 50%; max-height: 390px; max-width: 95%" src="<%= Resource.getItemCover(request, info.cover) %>">
        </div>
        <div class="col-8 pl-5">
            <h2 style="font-size: 150%"><%= info.name %></h2>
            <hr/>
            <div class="row my-2">
                <label class="col-2 text-secondary">价格</label>
                <div class="col-10">
                    <label class="text-danger" style="font-size: 200%">¥<label id="price_new"><%= info.newPrice %></label></label>
                    <%= ((info.oldPrice - info.newPrice < 0.01) ? "" : "<small class=\"text-secondary\" style=\"text-decoration: line-through\">¥<label id=\"price_old\" style=\"text-decoration: line-through\">" + info.oldPrice + "</label></small>")  %>
                    <%= ((info.newPrice / info.oldPrice <= 0.95) ? "<label class=\"bg-success text-white px-1 mr-1\">" + ((int) (((info.oldPrice - info.newPrice ) / info.oldPrice) * 100)) + "% OFF</label>" : "") %>
                </div>
            </div>
            <div class="row my-2">
                <label class="col-2 text-secondary">累计销量</label>
                <label>共售出 <%= info.deals %> 件</label>
            </div>
            <div class="row my-2">
                <label class="col-2 text-secondary">库存</label>
                <label>剩余 <%= info.quantity %> 件</label>
            </div>
            <div class="row my-2">
                <label class="col-2 text-secondary">发布时间</label>
                <label><%= new SimpleDateFormat("yyyy年MM月dd日").format(info.releaseDate) %></label>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/order/confirm">
                <div class="row my-2">
                    <label class="col-2 text-secondary" for="quantity">数量</label>
                    <input class="form-control" onchange="calcPrice()" value="1" type="number" name="quantity" id="quantity" placeholder="购买数量" style="width: 150px;" required />
                    <input value="<%= info.id %>" type="hidden" name="itemId" required />
                </div>
                <div class="row my-5">
                    <div class="col-10 offset-2 px-0">
                        <button <%= AuthInfo.isCustomer(session) ? "" : "title='请登录以提交订单' disabled" %>
                                type="submit" class="btn btn-success mr-1 <%= AuthInfo.isCustomer(session) ? "" : "disabled" %>">立即购买</button>
                        <button <%= AuthInfo.isCustomer(session) ? "" : "title='请登录以使用购物车' disabled" %>
                                type="button" id="addToCart" onclick="carted()"
                                class="btn btn-primary <%= AuthInfo.isCustomer(session) ? "" : "disabled" %>">加入购物车</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<hr/>
<div class="container-fluid" style="min-height: 40%">
    <c:choose>
        <c:when test="<%= new File(Config.getItemPageFileAbsolutePath(request, info.detailPage)).exists() %>">
            <jsp:include page='<%= Config.getItemPageFileRelativePath(info.detailPage) %>'/>
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
