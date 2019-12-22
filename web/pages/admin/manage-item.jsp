<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="top.chorg.iCommerce.database.Database" %>
<%@ page import="top.chorg.iCommerce.api.ItemType" %>
<%@ page import="top.chorg.iCommerce.database.ItemData" %>
<%@ page import="top.chorg.iCommerce.api.Item" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        Gson gson = new Gson();
        Request.setLastVis(request);
        if (!Request.validateAdminLevel(request, response, 1)) return;
        Database database;
        try {
            database = Database.connect(Config.readConfFromFile(request));
        } catch (Exception e) {
            Request.becomeInfoPage(request, response, "error", "错误", "服务异常",
                    "服务异常，正在积极处理中，请稍后再来吧(" + e.getMessage() + ")");
            return;
        }
        ItemType[] arr = ItemData.getTypeList(database);
        String typeS = request.getParameter("type");
        int type = (typeS == null) ? 0 : Integer.parseInt(typeS);
        String sortS = request.getParameter("sort");
        int sort = (sortS == null) ? 5 : Integer.parseInt(sortS);
        Item[] items = ItemData.getItemList(database, type, sort);
    %>
    <%@include file="/templates/include-header.jsp" %>
    <title>商品管理 - <%= conf.SYSTEM_NAME %></title>
</head>
<script>
    let typeList = <%= gson.toJson(ItemType.getIdWrap(arr)) %>;
    function conditionChange() {
        let typeStr = document.querySelector("#type").value;
        let sortStr = document.querySelector("#sort").value;
        window.location.href = "${pageContext.request.contextPath}/admin/item?type=" + typeStr + "&sort=" + sortStr;
    }
    function openEditDialog(target, name, cover, detailPage, oldPrice, newPrice, quantity, types) {
        document.querySelector("#editDialogLabel").innerHTML = name;
        document.querySelector("#edit_item_target").value = target;
        document.querySelector("#edit_name").value = name;
        document.querySelector("#edit_cover").value = cover;
        document.querySelector("#edit_detail_page").value = detailPage;
        document.querySelector("#edit_price_old").value = oldPrice;
        document.querySelector("#edit_price_new").value = newPrice;
        document.querySelector("#edit_quantity").value = quantity;
        for (let i = 0; i < typeList.length; i++) {
            $("#edit-check" + typeList[i]).removeAttr("checked");
        }
        for (let i = 0; i < types.length; i++) {
            $("#edit-check" + types[i]).attr("checked","checked");
        }
        $('#editDialog').modal();
    }
    function openRemoveDialog(target, name) {
        document.querySelector("#removeItemName").innerHTML = name;
        document.querySelector("#remove_item_target").value = target;
        $('#removeDialog').modal();
    }
</script>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value="none" />
</jsp:include>

<div class="container-fluid col-10 offset-1 my-3">
    <div class="row align-content-center justify-content-between px-3">
        <h1 class="col-4">商品管理</h1>
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
        <button type="button" class="btn btn-primary col-1" data-toggle="modal" data-target="#addDialog">添加</button>
        <div class="modal fade" id="addDialog" tabindex="-1" role="dialog" aria-labelledby="addDialogLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/item/add">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addDialogLabel">添加新商品</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="name">商品名称</label>
                                <input class="form-control" type="text" name="name" id="name" placeholder="请输入商品名称" required />
                            </div>
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="cover">封面</label>
                                <input class="form-control" type="number" name="cover" id="cover" placeholder="图床编号" required />
                            </div>
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="detail_page">商品描述页面文件名称</label>
                                <input class="form-control" type="text" name="detail_page" id="detail_page" placeholder="请输入页面名称" required />
                                <small style="color: gray">请将商品的描述页面放置在custom/items目录中。</small>
                            </div>
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="price_old">原价</label>
                                <input class="form-control" pattern="^[\+\-]?[0-9.]+$" name="price_old" id="price_old" placeholder="被划掉的价格" required />
                            </div>
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="price_new">真实价格</label>
                                <input class="form-control" pattern="^[\+\-]?[0-9.]+$" name="price_new" id="price_new" placeholder="真实的价格" required />
                            </div>
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="quantity">库存</label>
                                <input class="form-control" type="number" name="quantity" id="quantity" placeholder="货物供应量" required />
                            </div>
                            <div class="form-group am-form-icon am-form-feedback">
                                <label>商品类别</label>
                                <%
                                    if (arr != null && arr.length > 0) {
                                        for (ItemType s : arr) {
                                            out.print("<div class=\"form-check\">\n" +
                                                    "     <input class=\"form-check-input\" type=\"checkbox\" name=\"add_type\" value=\"" + s.id + "\" id=\"add-check" + s.toString() + "\">\n" +
                                                    "     <label class=\"form-check-label\" for=\"check" + s.toString() + "\">\n" + s.name + "</label>\n" +
                                                    "</div>");
                                        }
                                    } else {
                                        out.print("<label class='align-self-center col-12 px-0' style='color:lightGray;'>没有可供选择的分类</label>");
                                    }
                                %>

                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                            <button type="submit" class="btn btn-primary">添加</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <hr/>
    <div class='row <%= items.length == 0 ? "align-content-center justify-content-center" : "" %>' style="min-height: 80%">
        <%
            if (items.length > 0) {
                out.print("<table class=\"table\">\n" +
                        "  <thead class=\"thead-dark\">\n" +
                        "    <tr>\n" +
                        "      <th scope=\"col\">Id</th>\n" +
                        "      <th scope=\"col\">封面</th>\n" +
                        "      <th scope=\"col\">名称</th>\n" +
                        "      <th scope=\"col\">描述页面文件名</th>\n" +
                        "      <th scope=\"col\">原价</th>\n" +
                        "      <th scope=\"col\">真实价格</th>\n" +
                        "      <th scope=\"col\">库存</th>\n" +
                        "      <th scope=\"col\">成交量</th>\n" +
                        "      <th scope=\"col\">发布时间</th>\n" +
                        "      <th scope=\"col\">商品类别</th>\n" +
                        "      <th scope=\"col\" style=\"width: 150px;\">操作</th>\n" +
                        "    </tr>\n" +
                        "  </thead><tbody>");
                for (Item item : items) {
                    out.print("<tr>\n" +
                            "      <th scope=\"row\">" + item.id + "</th>\n" +
                            "      <th scope=\"row\"><img style=\"max-width: 40px; max-height: 40px\" src='" + Resource.getItemCover(request, item.cover) + "' /></th>\n" +
                            "      <td>" + item.name + "</td>\n" +
                            "      <td><a target='_blank' href='" + String.format("%s/preview/item?page=%s&name=%s&cover=%d&price=%.2f", request.getContextPath(), item.detailPage, item.name, item.cover, item.newPrice) + "'>" + item.detailPage + "</a></td>\n" +
                            "      <td>" + String.format("%.2f", item.oldPrice) + "</td>\n" +
                            "      <td>" + String.format("%.2f", item.newPrice) + "</td>\n" +
                            "      <td>" + item.quantity + "</td>\n" +
                            "      <td>" + item.deals + "</td>\n" +
                            "      <td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.releaseDate) + "</td>\n" +
                            "      <td>" + Arrays.toString(ItemType.getNameWrap(ItemData.getType(database, item.id))) + "</td>\n" +
                            "      <td class='align-content-center justify-content-center'>" +
                            "           <button type=\"button\" class=\"btn btn-light\" onclick=\"openEditDialog(" +
                            item.id + ", '" + item.name + "', " + item.cover + ", '" + item.detailPage + "', '" + String.format("%.2f", item.oldPrice) + "', '" +
                            String.format("%.2f", item.newPrice) + "', '" + item.quantity + "', " +
                            gson.toJson(ItemType.getIdWrap(ItemData.getType(database, item.id))) + ")\" >编辑</button>" +
                            "           <button type=\"button\" class=\"btn btn-danger\" onclick=\"openRemoveDialog(" +
                            item.id + ", '" + item.name + "')\" >删除</button>" +
                            "      </td>\n" +
                            "    </tr>");
                }
                out.print("</tbody></table>");
            } else {
                out.print("<label class='align-self-center' style='color:lightGray; font-size:300%;'>目前没有商品</label>");
            }
        %>
    </div>
</div>
<div class="modal fade" id="removeDialog" tabindex="-1" role="dialog" aria-labelledby="removeDialogLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/item/remove" novalidate>
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="removeDialogLabel">删除商品</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>您确定要删除商品【<label id="removeItemName">NULL</label>】吗？</p>
                    <p class="text-danger">删除商品后，不建议您删除custom/items中的页面文件，因为用户将会以此页面为依据作为"交易快照"。</p>
                    <input class="form-control" type="hidden" name="remove_item_target" id="remove_item_target" required />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">确认</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal fade" id="editDialog" tabindex="-1" role="dialog" aria-labelledby="editDialogLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/item/edit">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editDialogLabel">编辑商品</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_name">商品名称</label>
                        <input class="form-control" type="text" name="edit_name" id="edit_name" placeholder="请输入商品名称" required />
                    </div>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_cover">封面</label>
                        <input class="form-control" type="number" name="edit_cover" id="edit_cover" placeholder="图床编号" required />
                    </div>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_detail_page">商品描述页面文件名称</label>
                        <input class="form-control" type="text" name="edit_detail_page" id="edit_detail_page" placeholder="请输入页面名称" required />
                        <small style="color: gray">请将商品的描述页面放置在custom/items目录中。</small>
                        <small class="text-danger">不建议您直接编辑custom/items中的页面文件，因为用户将会以此页面为依据作为"交易快照"，建议您新建一个描述页面并重新命名。</small>
                    </div>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_price_old">原价</label>
                        <input class="form-control" pattern="^[\+\-]?[0-9.]+$" name="edit_price_old" id="edit_price_old" placeholder="被划掉的价格" required />
                    </div>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_price_new">真实价格</label>
                        <input class="form-control" pattern="^[\+\-]?[0-9.]+$" name="edit_price_new" id="edit_price_new" placeholder="真实的价格" required />
                    </div>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_quantity">库存</label>
                        <input class="form-control" type="number" name="edit_quantity" id="edit_quantity" placeholder="货物供应量" required />
                    </div>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label>商品类别</label>
                        <%
                            if (arr != null && arr.length > 0) {
                                for (ItemType s : arr) {
                                    out.print("<div class=\"form-check\">\n" +
                                            "     <input class=\"form-check-input\" name=\"edit_type\" type=\"checkbox\" value=\"" + s.id + "\" id=\"edit-check" + s.id + "\">\n" +
                                            "     <label class=\"form-check-label\" for=\"check" + s.toString() + "\">\n" + s.name + "</label>\n" +
                                            "</div>");
                                }
                            } else {
                                out.print("<label class='align-self-center col-12 px-0' style='color:lightGray;'>没有可供选择的分类</label>");
                            }
                        %>
                    </div>
                    <input class="form-control" type="hidden" name="edit_item_target" id="edit_item_target" required />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
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
