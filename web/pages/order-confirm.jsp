<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page import="top.chorg.iCommerce.database.ItemData" %>
<%@ page import="top.chorg.iCommerce.database.Database" %>
<%@ page import="top.chorg.iCommerce.function.auth.AuthInfo" %>
<%@ page import="top.chorg.iCommerce.api.*" %>
<%@ page import="top.chorg.iCommerce.database.ShippingData" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        Gson gson = new Gson();
        Database database;
        if (!AuthInfo.isCustomer(session)) {
            Request.becomeInfoPage(request, response, "error", "错误", "无权限访问界面",
                    "您还未<a href='" + request.getContextPath() + "/login'>登录</a>，无法访问购物车界面");
            return;
        }
        try {
            database = Database.connect(Config.readConfFromFile(request));
        } catch (Exception e) {
            Request.becomeInfoPage(request, response, "error", "错误", "服务异常",
                    "服务异常，正在积极处理中，请稍后再来吧(" + e.getMessage() + ")");
            return;
        }

        String itemS = request.getParameter("itemId");
        String isCart;
        CartItem[] list;
        if (itemS == null) {
            isCart = "true";
            String[] itemListS = request.getParameterValues("item");
            CartItem[] temp = ItemData.getCart(database, AuthInfo.getCustomerId(session));
            if (temp == null) {
                Request.becomeInfoPage(request, response, "error", "错误", "服务异常",
                        "服务异常，请稍后再试");
                return;
            }
            list = new CartItem[itemListS.length];
            for (int i = 0; i < itemListS.length; i++) {
                int iid = Integer.parseInt(itemListS[i]);
                for (CartItem cartItem : temp) {
                    if (iid == cartItem.item.id) {
                        list[i] = cartItem;
                        if (list[i].quantity <= 0) {
                            Request.becomeInfoPage(request, response, "error", "错误", "无效的订单",
                                    "订单无效，系统发生错误");
                            return;
                        }
                        break;
                    }
                }
            }
        } else {
            isCart = "false";
            int itemId = Integer.parseInt(itemS);
            String quantityS = request.getParameter("quantity");
            int quantity;
            if (quantityS == null) quantity = 1;
            else quantity = Integer.parseInt(quantityS);
            if (quantity <= 0) {
                Request.becomeInfoPage(request, response, "error", "错误", "无效的订单",
                        "订单无效，系统发生错误");
                return;
            }
            list = new CartItem[1];
            list[0] = new CartItem(ItemData.getItemById(database, itemId), quantity);
        }
        OrderSubmitItem[] order = new OrderSubmitItem[list.length];
        double price = 0.0;
        for (int i = 0; i < list.length; i++) {
            order[i] = new OrderSubmitItem(list[i].item.id, list[i].quantity);
            price += list[i].item.newPrice * list[i].quantity;
        }
        String orderInfoS = gson.toJson(order);

        Shipping[] ship = ShippingData.getShippingList(database, AuthInfo.getCustomerId(session));
    %>
    <%@include file="/templates/include-header.jsp"%>
    <title>确认订单 - <%= conf.SYSTEM_NAME %></title>
</head>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value='none' />
</jsp:include>
<div class="container-fluid col-8 offset-2 my-3 min-vh-100">
    <div class="row">
        <h1 class="col">确认订单</h1>
    </div>
    <hr/>
    <div class="row">
        <h2 class="col-2">送货信息</h2>
        <button class="btn btn-primary disabled">新建</button>
    </div>
    <form method="post" action="${pageContext.request.contextPath}/order/add">
        <div class="row">
            <div class="row col-12">
                <%
                    if (ship == null || ship.length == 0) {
                        out.print("<label id=\"noneLabel\" style='color:lightGray;'>没有收货地址</label>");
                    } else {
                        for (Shipping s : ship) {
                            out.print("<div class=\"btn btn-light row col-12 m-1 p-3 text-left\">\n" +
                                    "                <div class=\"row col-12\">\n" +
                                    "                    <div class=\"col-1\">\n" +
                                    "                        <label style=\"width: 14px; height: 14px; position: relative;transform: translate(-50%, -50%);top: 50%;left: 50%;\"><input class=\"form-check-input m-0\" type=\"radio\" name='address' value=\"" + s.id + "\" /></label>\n" +
                                    "                    </div>\n" +
                                    "                    <div class=\"col-11\">\n" +
                                    "                        <label class=\"mb-0\">" + s.getFormatOutput() + "</label>\n" +
                                    "                    </div>\n" +
                                    "                </div>\n" +
                                    "            </div>");
                        }
                    }

                %>

            </div>
        </div>
        <hr/>
        <div class="row">
            <h2 class="col">订单信息</h2>
            <div class="row col-12">
                <%
                    for (CartItem item : list) {
                        Item info = item.item;
                        if (info == null || info.quantity == 0) continue;
                        out.print("<div class=\"btn btn-light row col-12 m-1\">\n" +
                                "            <div class=\"text-left row col-12\">\n" +
                                "                <div class=\"col-7\">\n" +
                                "                    <img style=\"max-width: 50px; max-height: 50px\" src='" + Resource.getItemCover(request, info.cover) + "'  alt=\"\"/>\n" +
                                "                    <label><a target='_blank' href='" + request.getContextPath() + "/item?item=" + info.id + "'>" + info.name + "</a></label>\n" +
                                "                </div>\n" +
                                "                <div class=\"col-5 text-right\">\n" +
                                "                    <div class=\"col-12\" style=\"position: relative; transform: translateY(-50%);top: 50%; right: 0\">\n" +
                                "                        <label class=\"text-danger\">¥<label id=\"item-price-" + info.id + "\">" + String.format("%.2f", item.quantity * info.newPrice) + "</label></label><small class=\"ml-2\">¥" + String.format("%.2f", info.newPrice) + " * " + item.quantity + "</small>\n" +
                                "                    </div>\n" +
                                "                </div>\n" +
                                "            </div>\n" +
                                "        </div>");
                    }
                %>
            </div>
        </div>
        <hr id="downHr"/>
        <input value='<%= orderInfoS %>' type="hidden" name="orderContent" required />
        <input value="<%= isCart %>" type="hidden" name="isCart" required />
        <div class="row col-12 justify-content-end">
            <label>总计：</label>
            <label class="text-danger">¥<%= String.format("%.2f", price) %></label>
            <button id="sub" type="submit" class="btn btn-success ml-1">提交订单</button>
        </div>
    </form>
</div>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
</jsp:include>
</body>
</html>
