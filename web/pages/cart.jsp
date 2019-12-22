<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="top.chorg.iCommerce.api.CartItem" %>
<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page import="top.chorg.iCommerce.api.Item" %>
<%@ page import="top.chorg.iCommerce.database.ItemData" %>
<%@ page import="top.chorg.iCommerce.database.Database" %>
<%@ page import="top.chorg.iCommerce.function.auth.AuthInfo" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        Request.setLastVis(request);
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
        CartItem[] items = ItemData.getCart(database, AuthInfo.getCustomerId(session));
        assert items != null;
    %>
    <%@include file="/templates/include-header.jsp"%>
    <title>购物车 - <%= conf.SYSTEM_NAME %></title>
</head>
<script>
    function getAll() {
        return $("input[name='item']").map(function () {
            return Number.parseInt($(this).val());
        }).get();
    }
    function getSelected() {
        return $("input[name='item']:checked").map(function () {
            return Number.parseInt($(this).val());
        }).get();
    }
    function judgeEmpty() {
        if (getSelected().length === 0) {
            $("#noneLabel").show();
            $("#downHr").hide();
            $("#totalDiv").hide();
        }
    }
    function removeItem(id) {
        $.post(
            "${pageContext.request.contextPath}/cart/set",
            {
                "item": id,
                "quantity": 0
            },
            function (data) {
                if (data === "OK") {
                    document.querySelector("#item-tab-" + id).outerHTML = "";
                    judgeEmpty();
                    selChange();
                }
            }
        );
    }
    function removeSel() {
        let sel = getSelected();
        let all = getAll();
        for (let i = 0; i < sel.length; i++) {
            removeItem(sel[i]);
        }
        if (sel.length === all.length) {
            $("#noneLabel").show();
            $("#downHr").hide();
            $("#totalDiv").hide();
        }
    }
    function selChange() {
        let tot = 0.0;
        let sel = getSelected();
        for (let i = 0; i < sel.length; i++) {
            tot += Number.parseInt($("#item-price-" + sel[i]).html());
        }
        let all = getAll();
        document.querySelector("#sel_all").checked = sel.length === all.length;
        $("#totalPrice").html(tot);
        if(getSelected().length === 0) {
            $("#del").addClass("disabled");
            $("#sub").addClass("disabled");
        } else {
            $("#del").removeClass("disabled");
            $("#sub").removeClass("disabled");
        }
    }
    function selAll() {
        if (document.querySelector("#sel_all").checked) {
            let all = getAll();
            for (let i = 0; i < all.length; i++) {
                document.querySelector("#item-sel-" + all[i]).checked = true;
            }
        } else {
            let all = getAll();
            for (let i = 0; i < all.length; i++) {
                document.querySelector("#item-sel-" + all[i]).checked = false;
            }
        }
        selChange();
    }
</script>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value='none' />
</jsp:include>
<div class="container-fluid col-8 offset-2 my-3 min-vh-100">
    <div class="row">
        <h1 class="col">购物车</h1>
    </div>
    <hr/>
    <form method="post" action="${pageContext.request.contextPath}/order/confirm">
    <div class="row col-12">
        <label id="noneLabel" style='color:lightGray; font-size:300%; position: fixed;transform: translate(-50%, -50%);top: 50%;left: 50%; <%= items.length > 0 ? "display: none;" : "" %>'>当前购物车内没有商品</label>
        <%
            if (items.length > 0) {
                for (CartItem item : items) {
                    Item info = item.item;
                    if (info == null || info.quantity == 0) continue;
                    out.print("<div id=\"item-tab-" + info.id + "\" class=\"btn btn-light row col-12 m-1\">\n" +
                            "            <div class=\"text-left row col-12\">\n" +
                            "                <div class=\"col-1\">\n" +
                            "                    <label style=\"width: 14px; height: 14px; position: relative;transform: translate(-50%, -50%);top: 50%;left: 50%;\"><input id='item-sel-" + info.id + "' class=\"form-check-input m-0\" type=\"checkbox\" onchange=\"selChange()\" name='item' value=\"" + info.id + "\" /></label>\n" +
                            "                </div>\n" +
                            "                <div class=\"col-6\">\n" +
                            "                    <img style=\"max-width: 50px; max-height: 50px\" src='" + Resource.getItemCover(request, info.cover) + "'  alt=\"\"/>\n" +
                            "                    <label><a target='_blank' href='" + request.getContextPath() + "/item?item=" + info.id + "'>" + info.name + "</a></label>\n" +
                            "                </div>\n" +
                            "                <div class=\"col-5 text-right\">\n" +
                            "                    <div class=\"col-12\" style=\"position: relative; transform: translateY(-50%);top: 50%; right: 0\">\n" +
                            "                        <label class=\"text-danger\">¥<label id=\"item-price-" + info.id + "\">" + String.format("%.2f", item.quantity * info.newPrice) + "</label></label><small class=\"ml-2\">¥" + String.format("%.2f", info.newPrice) + " * " + item.quantity + "</small>\n" +
                            "                        <button onclick='removeItem(" + info.id + ")' type='button' class='btn btn-danger ml-3'>删除</button>" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </div>");
                }
            }
        %>
    </div>
    <hr id="downHr" style="<%= items.length == 0 ? "display: none;" : "" %>"/>
    <div id="totalDiv" class="row col-12 justify-content-between" style="<%= items.length == 0 ? "display: none;" : "" %>">
        <div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="sel_all" onclick="selAll()">
                <label class="form-check-label" for='sel_all'>全选</label>
            </div>
        </div>
        <div>
            <label>总计：</label>
            <label class="text-danger">¥<label id="totalPrice">0.00</label></label>
            <button id="del" type="button" onclick="removeSel()" class="btn btn-danger disabled ml-3">删除</button>
            <button id="sub" type="submit" class="btn btn-success disabled ml-1">结算</button>
        </div>
    </div>
    </form>
</div>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
</jsp:include>
</body>
</html>
