<%@ page import="top.chorg.iCommerce.function.auth.AuthInfo" %>
<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="top.chorg.iCommerce.database.Database" %>
<%@ page import="top.chorg.iCommerce.api.Order" %>
<%@ page import="top.chorg.iCommerce.database.OrderData" %>
<%@ page import="top.chorg.iCommerce.api.OrderItem" %>
<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="top.chorg.iCommerce.database.ShippingData" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<script>
    function openPhase1(orderId, orderNum) {
        $("#sendTarget").val(orderId);
        $("#onPhase1Num").html(orderNum);
        $("#onPhase1").modal();
    }
    function openPhase4(orderId, orderNum) {
        $("#completeRefundTarget").val(orderId);
        $("#onPhase4Num").html(orderNum);
        $("#onPhase4").modal();
    }
</script>
<head>
    <%
        Request.setLastVis(request);
        Database database;
        if (!AuthInfo.isAdmin(session)) {
            Request.becomeInfoPage(request, response, "error", "错误", "无权限访问界面",
                    "您还未<a href='" + request.getContextPath() + "/admin/login'>登录</a>，无法访问管理界面");
            return;
        }
        try {
            database = Database.connect(Config.readConfFromFile(request));
        } catch (Exception e) {
            Request.becomeInfoPage(request, response, "error", "错误", "服务异常",
                    "服务异常，请联系系统主管理(" + e.getMessage() + ")");
            return;
        }
        int pageSize = 10;
        int total = OrderData.getOrderListLength(database);
        int totalPage;
        if (total % pageSize == 0) totalPage = total / pageSize;
        else totalPage = total / pageSize + 1;
        String pageS = request.getParameter("pageNum");
        int pageNum = (pageS == null) ? 1 : Integer.parseInt(pageS);
        if (pageNum < 1) pageNum = 1;
        if (pageNum > totalPage) pageNum = totalPage;

        String sortS = request.getParameter("sort");
        int sort = (sortS == null) ? -1 : Integer.parseInt(sortS);

        Order[] orders = OrderData.getAllOrderList(database, pageNum, pageSize, sort);
        assert orders != null;
    %>
    <%@include file="/templates/include-header.jsp"%>
    <title>订单管理 - <%= conf.SYSTEM_NAME %></title>
</head>
<script>
    let pageNumber = <%= pageNum %>;
    let pageTotal = <%= totalPage %>;
    function setPage(num) {
        if (num === -1) pageNumber = Number.parseInt(document.querySelector("#page").value);
        else pageNumber = num;
        conditionChange();
    }
    function conditionChange() {
        let sortStr = document.querySelector("#sort").value;
        window.location.href = "${pageContext.request.contextPath}/admin/order?&sort=" + sortStr + "&pageNum=" + pageNumber;
    }
</script>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value='none' />
</jsp:include>
<div class="container-fluid col-8 offset-2 my-3">
    <div class="row">
        <h1 class="col-9">订单管理</h1>
        <div class="row col-3 align-content-center justify-content-center">
            <label for="sort" class="col-4 my-0 align-self-center text-right">筛选</label>
            <select class="form-control col-8" id="sort" onchange="conditionChange()">
                <option value="-1" <%= sort == -1 ? "selected" : "" %>>全部</option>
                <option value="0" <%= sort == 0 ? "selected" : "" %>>等待付款</option>
                <option value="1" <%= sort == 1 ? "selected" : "" %>>等待发货</option>
                <option value="2" <%= sort == 2 ? "selected" : "" %>>运输中</option>
                <option value="3" <%= sort == 3 ? "selected" : "" %>>交易成功</option>
                <option value="4" <%= sort == 4 ? "selected" : "" %>>等待退款</option>
                <option value="5" <%= sort == 5 ? "selected" : "" %>>退款成功</option>
                <option value="6" <%= sort == 6 ? "selected" : "" %>>交易关闭</option>
            </select>
        </div>
    </div>
    <hr/>
    <div class="row" style="min-height: 70%">
        <%
            if (orders.length > 0) {
                for (Order order : orders) {
                    double totalPrice = 0.0;
                    String status;
                    String color;
                    String buttonWord = "";
                    switch (order.status) {
                        case 0:
                            status = "等待买家付款";
                            color = "text-warning";
                            break;
                        case 1:
                            status = "正在准备商品";
                            color = "text-warning";
                            buttonWord = "发货";
                            break;
                        case 2:
                            status = "商品运送中";
                            color = "text-primary";
                            break;
                        case 3:
                            status = "交易已完成";
                            color = "text-success";
                            break;
                        case 4:
                            status = "正在等待退款";
                            color = "text-danger";
                            buttonWord = "完成退款";
                            break;
                        case 5:
                            status = "退款成功";
                            color = "text-secondary";
                            break;
                        case 6:
                            status = "交易关闭";
                            color = "text-secondary";
                            break;
                        default:
                            status = "未知";
                            color = "text-danger";
                    }
                    OrderItem[] items = OrderData.getOrderItem(database, order.id);
                    out.print("<div class=\"row col-12 btn btn-light m-1 p-3\">\n" +
                            "            <div class=\"row col-12 justify-content-between\">\n" +
                            "                <label class='text-uppercase'><a href='#'>#" + order.orderNum + "</a></label>\n" +
                            "                <label class='" + color + "'>" + status + "</label>\n" +
                            "            </div>\n" +
                            "            <div class=\"row col-12\">\n" +
                            "                <label>" + Objects.requireNonNull(ShippingData.getShippingById(database, order.shippingId)).getFormatOutput() + "</label>\n" +
                            "            </div>\n" +
                            (order.expressNum.length() > 0 ? "<div class=\"row col-12 justify-content-start\">\n<label>物流编号：<a target='_blank' href='https://t.17track.net/zh-cn#nums=" + order.expressNum + "'>" + order.expressNum + "</a></label></div>" : "") +
                            "            <hr/>\n" +
                            "            <div class=\"row col-12\">\n");
                    if (items != null && items.length > 0) {
                        for (OrderItem item : items) {
                            totalPrice += item.amount * item.price;
                            out.print("                <a target=\"_blank\" href=\"" + String.format("%s/preview/item?page=%s&name=%s&cover=%d&price=%.2f", request.getContextPath(), item.historyPage, item.historyName, item.historyCover, item.price) + "\" class=\"btn btn-light row col-12 m-1\">\n" +
                                    "                    <div class=\"text-left row col-12\">\n" +
                                    "                        <div class=\"col-7\">\n" +
                                    "                            <img style=\"max-width: 50px; max-height: 50px\" src=\"" + Resource.getItemCover(request, item.historyCover) + "\" alt=\"\">\n" +
                                    "                            <label>" + item.historyName + "</label>\n" +
                                    "                        </div>\n" +
                                    "                        <div class=\"col-5 text-right\">\n" +
                                    "                            <div class=\"col-12\" style=\"position: relative; transform: translateY(-50%);top: 50%; right: 0\">\n" +
                                    "                                <label class=\"text-danger\">¥" + String.format("%.2f", item.amount * item.price) + "</label><small class=\"ml-2\">¥" + String.format("%.2f", item.price) + " * " + item.amount + "</small>\n" +
                                    "                            </div>\n" +
                                    "                        </div>\n" +
                                    "                    </div>\n" +
                                    "                </a>\n"
                            );
                        }
                    } else {
                        out.print("<label class=\"align-self-center col-12 text-center\" style=\"color:lightGray; font-size:300%;\">无法获取订单信息</label>");
                    }
                    out.print("            </div>\n" +
                            "            <hr/>\n" +
                            "            <div id=\"totalDiv\" class=\"row col-12 " + (buttonWord.length() > 0 ? "justify-content-between" : "justify-content-end") + "\">\n" +
                            (buttonWord.length() > 0 ? "<button class='btn btn-primary' onclick=\"openPhase" + order.status + "(" + order.id + ", '" + order.orderNum + "')\" >" + buttonWord + "</button>\n" : "") +
                            "                <label>订单创建时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.orderTime) + "</label>\n" +
                            "            </div>\n" +
                            "            <div id=\"totalDiv\" class=\"row col-12 justify-content-end\">\n" +
                            "                <label>总计：</label>\n" +
                            "                <label class=\"text-danger\">¥" + String.format("%.2f", totalPrice) + "</label>\n" +
                            "            </div>\n" +
                            "        </div>");
                }
            } else {
                out.print("<label style='color:lightGray; font-size:300%;position: absolute;transform: translate(-50%, -50%);top: 50%;left: 50%;'>没有符合要求的订单</label>");
            }

        %>
    </div>
    <hr/>
    <div class="row align-content-center justify-content-center" <%= orders.length > 0 ? "" : "style=\"display: none\"" %>>
        <button class="btn btn-light" onclick="setPage(1)" <%= pageNum <= 1 ? "disabled" : "" %>>首页</button>
        <button class="btn btn-light" onclick="setPage(<%= pageNum - 1 %>)" <%= pageNum <= 1 ? "disabled" : "" %>>上一页</button>
        <div class="row col-2 my-0 p-0 mx-2">
            <label class="col-5 m-0 p-0"><input type="number" class="form-control col-12 text-center p-1" id="page" value="<%= pageNum %>" onchange="setPage(-1)" /></label>
            <div class="col-2 m-0 p-0 text-center"><label class="text-center" style="position: relative;transform: translateY(-50%);top: 50%;">/</label></div>
            <div class="col-5 m-0 p-0 text-center"><label class="text-center" style="position: relative;transform: translateY(-50%);top: 50%;"><%= totalPage %></label></div>
        </div>
        <button class="btn btn-light" onclick="setPage(<%= pageNum + 1 %>)" <%= pageNum == totalPage ? "disabled" : "" %>>下一页</button>
        <button class="btn btn-light" onclick="setPage(<%= totalPage %>)" <%= pageNum == totalPage ? "disabled" : "" %>>尾页</button>
    </div>
</div>
<div class="modal fade" id="onPhase1" tabindex="-1" role="dialog" aria-labelledby="onPhase1Label" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/order/phase/1" novalidate>
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="onPhase1Label">发货</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>请为【#<label class="text-uppercase" id="onPhase1Num">NULL</label>】填写物流单号以完成发货。</p>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="express">物流单号</label>
                        <input class="form-control" type="text" name="express" id="express" placeholder="请输入物流编号" required />
                        <input class="form-control" type="hidden" name="sendTarget" id="sendTarget" required />
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">确认</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal fade" id="onPhase4" tabindex="-1" role="dialog" aria-labelledby="onPhase4Label" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/order/phase/4" novalidate>
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="onPhase4Label">确认退款</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>您确定要完成订单【#<label class="text-uppercase" id="onPhase4Num">NULL</label>】的退货申请吗？</p>
                    <input class="form-control" type="hidden" name="completeRefundTarget" id="completeRefundTarget" required />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">确认</button>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
</jsp:include>
</body>
</html>