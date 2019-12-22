<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="top.chorg.iCommerce.api.ItemType" %>
<%@ page import="top.chorg.iCommerce.database.Database" %>
<%@ page import="top.chorg.iCommerce.database.ItemData" %>
<%@ page import="top.chorg.iCommerce.api.Item" %>
<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        Request.setLastVis(request);
        Database database = null;
        try {
            database = Database.connect(Config.readConfFromFile(request));
        } catch (Exception e) {
            Request.becomeInfoPage(request, response, "error", "错误", "服务异常",
                    "服务异常，正在积极处理中，请稍后再来吧(" + e.getMessage() + ")");
        }
        assert database != null;
        ItemType[] arr = ItemData.getTypeList(database);
        String typeS = request.getParameter("type");
        int type = (typeS == null) ? 0 : Integer.parseInt(typeS);
        String sortS = request.getParameter("sort");
        int sort = (sortS == null) ? 5 : Integer.parseInt(sortS);
        int pageSize = 12;
        int total = ItemData.getItemListLength(database, type);
        int totalPage;
        if (total % pageSize == 0) totalPage = total / pageSize;
        else totalPage = total / pageSize + 1;
        String pageS = request.getParameter("pageNum");
        int pageNum = (pageS == null) ? 1 : Integer.parseInt(pageS);
        if (pageNum < 1) pageNum = 1;
        if (pageNum > totalPage) pageNum = totalPage;
        Item[] items = ItemData.getItemList(database, pageNum, pageSize, type, sort);
    %>
    <%@include file="/templates/include-header.jsp"%>
    <title>商品 - <%= conf.SYSTEM_NAME %></title>
</head>
<style>
    .animated:hover {
        cursor: pointer;
        background: aliceblue;
    }
</style>
<script>
    let pageNumber = <%= pageNum %>;
    let pageTotal = <%= totalPage %>;
    function setPage(num) {
        if (num === -1) pageNumber = Number.parseInt(document.querySelector("#page").value);
        else pageNumber = num;
        conditionChange();
    }
    function conditionChange() {
        let typeStr = document.querySelector("#type").value;
        let sortStr = document.querySelector("#sort").value;
        window.location.href = "${pageContext.request.contextPath}/items?type=" + typeStr + "&sort=" + sortStr + "&pageNum=" + pageNumber;
    }
</script>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value='<%= type == 0 ? "all" : "type" %>' />
</jsp:include>
<div class="container-fluid col-8 offset-2 my-3">
    <div class="row">
        <h1 class="col-5">商品</h1>
        <div class="row col-3 align-content-center justify-content-center">
            <label for="type" class="col-4 my-0 align-self-center text-right">分类</label>
            <select class="form-control col-8" id="type" onchange="conditionChange()">
                <option value="0" selected>全部</option>
                <%
                    if (arr != null) {
                        for (ItemType s : arr) {
                            out.print("<option value=\"");
                            out.print(s.id);
                            out.print("\"");
                            if (type == s.id) {
                                out.print(" selected>");
                            } else out.print(">");
                            out.print(s.name);
                            out.print("</option>");
                        }
                    }
                %>
            </select>
        </div>
        <div class="row col-4 align-content-center justify-content-center">
            <label for="sort" class="col-4 my-0 align-self-center text-right">排序</label>
            <select class="form-control col-8" id="sort" onchange="conditionChange()">
                <option value="1" <%= sort == 1 ? "selected" : "" %>>按销量从高到低</option>
                <option value="2" <%= sort == 2 ? "selected" : "" %>>按销量从低到高</option>
                <option value="3" <%= sort == 3 ? "selected" : "" %>>按价格从高到低</option>
                <option value="4" <%= sort == 4 ? "selected" : "" %>>按价格从低到高</option>
                <option value="5" <%= sort == 5 ? "selected" : "" %>>最新上架</option>
                <option value="6" <%= sort == 6 ? "selected" : "" %>>最老上架</option>
                <option value="7" <%= sort == 7 ? "selected" : "" %>>最多优惠</option>
            </select>
        </div>
    </div>
    <%
        if (type != 0) {
            assert arr != null;
            for (ItemType itemType : arr) {
                if (itemType.id == type) {
                    out.print("<div class='row'><div class='col-auto'><img style=\"max-width: 60px; max-height: 90%\" src=\"" + Resource.getTypeCover(request, itemType.cover) + "\" />");
                    out.print("</div><p class='col'>" + itemType.typeComment + "</p></div>");
                    break;
                }
            }
        }
    %>
    <hr/>
    <div class="row <%= (total > 0) ? "row-cols-4" : "align-content-center justify-content-center" %>" style="min-height: 70%">
        <%
            if (total > 0) {
                for (Item item : items) {
                    out.print("<div class=\"col p-1 animated\" onclick='window.open(\"" + request.getContextPath() + "/item?item=" + item.id + "\")' >\n" +
                            "     <div class=\"col-12 p-1 border border-secondary justify-content-between flex-column\" style=\"height: 360px;\">\n" +
                            "          <div class=\"col-12 p-0\" style=\"height: 250px;\">\n" +
                            "              <img alt=\"item\" class='position-relative' style=\"transform: translate(-50%, -50%);top: 50%;left: 50%; max-width: 100%; max-height: 250px;\" src=\"" + Resource.getItemCover(request, item.cover) + "\">\n" +
                            "          </div>\n" +
                            "          <div class=\"px-1\">\n" +
                            "             <div class=\"row row-cols-2\">\n" +
                            "                 <label class=\"col text-danger\" style=\"font-size: 140%\">¥" + String.format("%.2f", item.newPrice) + "</label>\n" +
                            "                 <label class=\"col text-secondary text-right\">已售出" + item.deals + "份</label>\n" +
                            "             </div>\n" +
                            "             <div class=\"row\">\n" +
                            "                 <label class=\"col-12 text-truncate\"><a class='text-dark' href='#' title='" + item.name + "'>" + item.name + "</a></label>\n" +
                            "                 <div class=\"col-12\">\n" +
                            ((System.currentTimeMillis() - item.releaseDate.getTime() < 15920000000L) ? "<label class=\"bg-warning text-white px-1 mr-1\">新品</label>" : "") +
                            ((item.newPrice / item.oldPrice <= 0.95) ? "<label class=\"bg-success text-white px-1 mr-1\">" + ((int) (((item.oldPrice - item.newPrice ) / item.oldPrice) * 100)) + "% OFF</label>" : "") +
                            ((item.quantity <= 10 && item.quantity > 0) ? "<label class=\"bg-danger text-white px-1 mr-1\">低库存</label>" : "") +
                            ((item.quantity == 0) ? "<label class=\"bg-secondary text-white px-1 mr-1\">已售罄</label>" : "") +
                            "                 </div>\n" +
                            "             </div>\n" +
                            "          </div>\n" +
                            "      </div>\n" +
                            "  </div>");
                }
            } else {
                out.print("<label class='align-self-center' style='color:lightGray; font-size:300%;'>当前分类下没有商品</label>");
            }
        %>
    </div>
    <div class="row align-content-center justify-content-center" <%= items.length > 0 ? "" : "style=\"display: none\"" %>>
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
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
</jsp:include>
</body>
</html>
