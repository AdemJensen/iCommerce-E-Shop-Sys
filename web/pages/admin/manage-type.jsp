<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="top.chorg.iCommerce.database.Database" %>
<%@ page import="top.chorg.iCommerce.database.ItemData" %>
<%@ page import="top.chorg.iCommerce.api.ItemType" %>
<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        Request.setLastVis(request);
        if (!Request.validateAdminLevel(request, response, 1)) return;
        Database database = null;
        try {
            database = Database.connect(Config.readConfFromFile(request));
        } catch (Exception e) {
            Request.becomeInfoPage(request, response, "error", "错误", "服务异常",
                    "服务异常，正在积极处理中，请稍后再来吧(" + e.getMessage() + ")");
        }
        assert database != null;
        ItemType[] arr = ItemData.getTypeList(database);
    %>
    <%@include file="/templates/include-header.jsp" %>
    <title>商品类别管理 - <%= conf.SYSTEM_NAME %></title>
</head>
<script>
    function openEditDialog(target, name, cover, comment) {
        document.querySelector("#editDialogLabel").innerHTML = name;
        document.querySelector("#edit_type_target").value = target;
        document.querySelector("#edit_type_name").value = name;
        document.querySelector("#edit_cover").value = cover;
        document.querySelector("#edit_type_comment").value = comment;
        $('#editDialog').modal();
    }
    function openRemoveDialog(target, name) {
        document.querySelector("#removeTypeName").innerHTML = name;
        document.querySelector("#remove_type_target").value = target;
        $('#removeDialog').modal();
    }
</script>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value="none" />
</jsp:include>

<div class="container-fluid col-8 offset-2 my-3">
    <div class="row align-content-center justify-content-between px-3">
        <h1 class="col-11">商品类别管理</h1>
        <button type="button" class="btn btn-primary col-1" data-toggle="modal" data-target="#addDialog">添加</button>
        <div class="modal fade" id="addDialog" tabindex="-1" role="dialog" aria-labelledby="addDialogLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/type/add">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addDialogLabel">添加新类别</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="type_name">类别名称</label>
                                <input class="form-control" type="text" name="type_name" id="type_name" placeholder="请输入类别名" required />
                            </div>
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="cover">封面</label>
                                <input class="form-control" type="number" name="cover" id="cover" placeholder="图床编号" required />
                            </div>
                            <div class="form-group am-form-icon am-form-feedback">
                                <label for="type_comment">类别介绍</label>
                                <textarea class="form-control" name="type_comment" id="type_comment" placeholder="在此键入类别介绍"></textarea>
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
    <div class='row <%= (arr == null || arr.length == 0) ? "align-content-center justify-content-center" : "" %>' style="min-height: 80%">
        <%
            if (arr != null && arr.length > 0) {
                out.print("<table class=\"table\">\n" +
                        "  <thead class=\"thead-dark\">\n" +
                        "    <tr>\n" +
                        "      <th scope=\"col\">Id</th>\n" +
                        "      <th scope=\"col\">名称</th>\n" +
                        "      <th scope=\"col\">封面</th>\n" +
                        "      <th scope=\"col\">注释</th>\n" +
                        "      <th scope=\"col\" style=\"width: 150px;\">操作</th>\n" +
                        "    </tr>\n" +
                        "  </thead><tbody>");
                for (ItemType itemType : arr) {
                    out.print("<tr>\n" +
                            "      <th scope=\"row\">" + itemType.id + "</th>\n" +
                            "      <td>" + itemType.name + "</td>\n" +
                            "      <th scope=\"row\"><img style=\"max-width: 40px; max-height: 40px\" src='" + Resource.getTypeCover(request, itemType.cover) + "' /></th>\n" +
                            "      <td>" + itemType.typeComment + "</td>\n" +
                            "      <td class='align-content-center justify-content-center'>" +
                            "           <button type=\"button\" class=\"btn btn-light\" onclick=\"openEditDialog(" +
                            itemType.id + ", '" + itemType.name + "', " + itemType.cover + ", '" + itemType.typeComment + "')\" >编辑</button>" +
                            "           <button type=\"button\" class=\"btn btn-danger\" onclick=\"openRemoveDialog(" +
                            itemType.id + ", '" + itemType.name + "')\" >删除</button>" +
                            "      </td>\n" +
                            "    </tr>");
                }
                out.print("</tbody></table>");
            } else {
                out.print("<label class='align-self-center' style='color:lightGray; font-size:300%;'>目前没有分类</label>");
            }
        %>
    </div>
</div>
<div class="modal fade" id="removeDialog" tabindex="-1" role="dialog" aria-labelledby="removeDialogLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/type/remove">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="removeDialogLabel">删除商品类别</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>您确定要删除类别【<label id="removeTypeName">NULL</label>】吗？</p>
                    <input class="form-control" type="hidden" name="remove_type_target" id="remove_type_target" required />
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
        <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/type/edit">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editDialogLabel">编辑类别【<label id="editTypeName">NULL</label>】</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_type_name">类别名称</label>
                        <input class="form-control" type="text" name="edit_type_name" id="edit_type_name" placeholder="请输入类别名" required />
                    </div>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_cover">封面</label>
                        <input class="form-control" type="number" name="edit_cover" id="edit_cover" placeholder="图床编号" required />
                    </div>
                    <div class="form-group am-form-icon am-form-feedback">
                        <label for="edit_type_comment">类别介绍</label>
                        <textarea class="form-control" name="edit_type_comment" id="edit_type_comment" placeholder="在此键入类别介绍"></textarea>
                    </div>
                    <input class="form-control" type="hidden" name="edit_type_target" id="edit_type_target" required />
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
