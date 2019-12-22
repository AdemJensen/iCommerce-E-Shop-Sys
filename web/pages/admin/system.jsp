<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        String ip = request.getRemoteAddr();
        System.out.println(ip);
        if(!ip.equals("127.0.0.1") && !ip.equals("0:0:0:0:0:0:0:1")){
            Request.becomeInfoPage(request, response, "error", "错误", "您的网络环境不符合要求",
                    "由于安全策略，本页面只能在内网环境下才可以访问。");
        }
        Request.setLastVis(request);
    %>
    <%@include file="/templates/include-header.jsp" %>
    <title>系统设置 - <%= conf.SYSTEM_NAME %></title>
</head>
<script>
    function switch_MODIFIED_INDEX_FILE (status) {
        if (status) {
            $("#MODIFIED_INDEX_FILE").show();
        } else {
            $("#MODIFIED_INDEX_FILE").hide();
        }
    }
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    $(function() {
        switch_MODIFIED_INDEX_FILE(<%= conf.USE_MODIFIED_INDEX %>);
    });
    (function() {
        'use strict';
        window.addEventListener('load', function() {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            let forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
<body class="position-relative" data-spy="scroll" data-target="#navbar-set" data-offset="80">
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>"/>
    <jsp:param name="selected" value="none"/>
</jsp:include>
<div class="container-fluid">
    <div class="col-8 offset-2">

        <form id="master" class="needs-validation" method="post" action="${pageContext.request.contextPath}/admin/sys/modify" novalidate>
            <div class="row">
                <div class="col-3 sticky-top h-100 mt-3">
                    <nav id="navbar-set" class="navbar navbar-light bg-light">
                        <nav class="nav nav-pills flex-column">
                            <a class="nav-link active" href="#basic">通用</a>
                            <a class="nav-link" href="#chat">聊天系统</a>
                            <a class="nav-link" href="#database">数据库</a>
                            <a class="nav-link" href="#tables">数据库表格</a>
                        </nav>
                    </nav>
                    <div class="row mx-0 my-2">
                        <button type="submit" class="col-6 btn btn-success" id="submit">保存</button>
                        <button type="reset" class="col-6 btn btn-light" id="reset">重置</button>
                    </div>
                </div>

                <div class="col-9 mt-3">
                    <div id="basic" class="mb-3">
                        <h2>基础设置</h2>
                        <div class="form-group">
                            <label for="SYSTEM_NAME">系统名称</label>
                            <input class="form-control" type="text" name="SYSTEM_NAME" id="SYSTEM_NAME"
                                   placeholder="系统名称" value="<%= conf.SYSTEM_NAME %>" required/>
                            <div class="invalid-feedback">请提供一个有效的系统名称</div>
                        </div>
                        <div class="form-group">
                            <label for="MODIFIED_INDEX_FILE">首页</label>
                            <br/>
                            <div class="form-check-inline">
                                <input class="form-check-input" type="radio" name="USE_MODIFIED_INDEX" id="USE_MODIFIED_INDEX_1" onchange="switch_MODIFIED_INDEX_FILE(false)" value="false" <%= !conf.USE_MODIFIED_INDEX ? "checked" : "" %>>
                                <label class="form-check-label" for="USE_MODIFIED_INDEX_1">使用默认主页</label>
                            </div>
                            <div class="form-check-inline">
                                <input class="form-check-input" type="radio" name="USE_MODIFIED_INDEX" id="USE_MODIFIED_INDEX_2" onchange="switch_MODIFIED_INDEX_FILE(true)" value="true" <%= conf.USE_MODIFIED_INDEX ? "checked" : "" %>>
                                <label class="form-check-label" for="USE_MODIFIED_INDEX_2">使用自定义主页</label>
                            </div>
                            <input class="form-control" type="text" name="MODIFIED_INDEX_FILE" id="MODIFIED_INDEX_FILE"
                                   placeholder="请将jsp文件放入custom文件夹" value="<%= conf.MODIFIED_INDEX_FILE %>"/>
                        </div>
                    </div>
                    <div id="chat" class="mb-3">
                        <h2>聊天系统</h2>
                        <div class="form-group">
                            <label for="CHAT_SYSTEM">在线聊天系统代码</label>
                            <textarea class="form-control" name="CHAT_SYSTEM" id="CHAT_SYSTEM"
                                      placeholder="在此插入您的在线聊天系统代码" aria-describedby="chatHelp"><%= conf.CHAT_SYSTEM %></textarea>
                            <small id="chatHelp" class="form-text text-muted">我们推荐您使用<a href="https://www.tidio.com" target="_blank">Tidio</a>系统来完善您的网站交互体验。</small>
                        </div>
                    </div>
                    <div id="database" class="mb-3">
                        <h2>数据库</h2>
                        <div class="form-group">
                            <label for="DATABASE_ADDR">数据库地址</label>
                            <input class="form-control" type="text" name="DATABASE_ADDR" id="DATABASE_ADDR"
                                   placeholder="默认：localhost" value="<%= conf.DATABASE_ADDR %>" required/>
                            <div class="invalid-feedback">请提供一个有效的数据库地址</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_PORT">数据库端口</label>
                            <input class="form-control" type="number" name="DATABASE_PORT" id="DATABASE_PORT" min="0"
                                   max="65535" placeholder="默认：3306" value="<%= conf.DATABASE_PORT %>" required/>
                            <small id="portHelp" class="form-text text-muted">我们推荐您使用1024号以后的端口，因为0 - 1023端口一般为系统保留端口。</small>
                            <div class="invalid-feedback">请提供一个有效的端口号</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_USERNAME">数据库用户名</label>
                            <input class="form-control" type="text" name="DATABASE_USERNAME" id="DATABASE_USERNAME"
                                   placeholder="默认：root" value="<%= conf.DATABASE_USERNAME %>" required/>
                            <div class="invalid-feedback">请提供一个有效的用户名</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_PASSWORD">数据库密码</label>
                            <input class="form-control" type="password" name="DATABASE_PASSWORD" id="DATABASE_PASSWORD"
                                   placeholder="默认：root" value="<%= conf.DATABASE_PASSWORD %>" required/>
                            <div class="invalid-feedback">请提供一个有效的密码</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_SCHEMA">目标数据库名称</label>
                            <input class="form-control" type="text" name="DATABASE_SCHEMA" id="DATABASE_SCHEMA"
                                   placeholder="默认：iCommerce" value="<%= conf.DATABASE_SCHEMA %>" required/>
                            <div class="invalid-feedback">请提供一个有效的数据库名称</div>
                        </div>
                        <div class="form-group">
                            <label>Unicode字符集</label>
                            <br/>
                            <div class="form-check-inline">
                                <input class="form-check-input" type="radio" name="DATABASE_USE_UNICODE" id="DATABASE_USE_UNICODE_1" value="true" <%= conf.DATABASE_USE_UNICODE ? "checked" : "" %>>
                                <label class="form-check-label" for="DATABASE_USE_UNICODE_1">使用Unicode字符集</label>
                            </div>
                            <div class="form-check-inline">
                                <input class="form-check-input" type="radio" name="DATABASE_USE_UNICODE" id="DATABASE_USE_UNICODE_2" value="false" <%= !conf.DATABASE_USE_UNICODE ? "checked" : "" %>>
                                <label class="form-check-label" for="DATABASE_USE_UNICODE_2">不使用Unicode字符集</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_ENCODING">字符集名称</label>
                            <input class="form-control" type="text" name="DATABASE_ENCODING" id="DATABASE_ENCODING"
                                   placeholder="默认：UTF-8" value="<%= conf.DATABASE_ENCODING %>" required/>
                            <div class="invalid-feedback">请提供一个有效的字符集</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TIMEZONE">服务器时区</label>
                            <input class="form-control" type="text" name="DATABASE_TIMEZONE" id="DATABASE_TIMEZONE"
                                   placeholder="默认与服务器时区同步" value="<%= conf.DATABASE_TIMEZONE %>" />
                            <div class="invalid-feedback">请提供一个有效的时区系统</div>
                        </div>
                        <div class="form-group">
                            <label>SSL连接</label>
                            <br/>
                            <div class="form-check-inline">
                                <input class="form-check-input" type="radio" name="DATABASE_USE_SSL" id="DATABASE_USE_SSL_1" value="true" <%= conf.DATABASE_USE_SSL ? "checked" : "" %>>
                                <label class="form-check-label" for="DATABASE_USE_SSL_1">使用SSL连接</label>
                            </div>
                            <div class="form-check-inline">
                                <input class="form-check-input" type="radio" name="DATABASE_USE_SSL" id="DATABASE_USE_SSL_2" value="false" <%= !conf.DATABASE_USE_SSL ? "checked" : "" %>>
                                <label class="form-check-label" for="DATABASE_USE_SSL_2">不使用SSL连接</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>数据库连接公共密钥检索（Public Key Retrieval）</label>
                            <br/>
                            <div class="form-check-inline">
                                <input class="form-check-input" type="radio" name="PUBLIC_KEY_RETRIEVAL" id="PUBLIC_KEY_RETRIEVAL_1" value="true" <%= conf.PUBLIC_KEY_RETRIEVAL ? "checked" : "" %>>
                                <label class="form-check-label" for="PUBLIC_KEY_RETRIEVAL_1">启用</label>
                            </div>
                            <div class="form-check-inline">
                                <input class="form-check-input" type="radio" name="PUBLIC_KEY_RETRIEVAL" id="PUBLIC_KEY_RETRIEVAL_2" value="false" <%= !conf.PUBLIC_KEY_RETRIEVAL ? "checked" : "" %>>
                                <label class="form-check-label" for="PUBLIC_KEY_RETRIEVAL_2">禁用</label>
                            </div>
                            <small id="publicKeyRetrievalHelp" class="form-text text-muted">由于 MySQL®️ 的安全设置策略，当您在不持有私钥的情况下禁用SSL连接时，需要启用公钥检索功能，否则可能导致连接失败。另外，启用公钥检索功能可能会导致安全问题，请谨慎启用。</small>
                        </div>
                    </div>
                    <div id="tables" class="mb-3">
                        <h2>数据库</h2>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_ADMIN">管理员用户表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_ADMIN" id="DATABASE_TABLE_ADMIN"
                                   placeholder="默认：admin" value="<%= conf.DATABASE_TABLE_ADMIN %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_CART">购物车关系表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_CART" id="DATABASE_TABLE_CART"
                                   placeholder="默认：cart" value="<%= conf.DATABASE_TABLE_CART %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_ITEM">商品信息表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_ITEM" id="DATABASE_TABLE_ITEM"
                                   placeholder="默认：item" value="<%= conf.DATABASE_TABLE_ITEM %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_ITEM_TYPE">商品类型表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_ITEM_TYPE" id="DATABASE_TABLE_ITEM_TYPE"
                                   placeholder="默认：item_type" value="<%= conf.DATABASE_TABLE_ITEM_TYPE %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_ITEM_TYPE_BELONG">商品类型关系表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_ITEM_TYPE_BELONG" id="DATABASE_TABLE_ITEM_TYPE_BELONG"
                                   placeholder="默认：item_type_belong" value="<%= conf.DATABASE_TABLE_ITEM_TYPE_BELONG %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_ORDER">订单信息表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_ORDER" id="DATABASE_TABLE_ORDER"
                                   placeholder="默认：order" value="<%= conf.DATABASE_TABLE_ORDER %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_ORDER_BELONG">订单关系表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_ORDER_BELONG" id="DATABASE_TABLE_ORDER_BELONG"
                                   placeholder="默认：order_belong" value="<%= conf.DATABASE_TABLE_ORDER_BELONG %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_SHIPPING">运输信息表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_SHIPPING" id="DATABASE_TABLE_SHIPPING"
                                   placeholder="默认：shipping" value="<%= conf.DATABASE_TABLE_SHIPPING %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                        <div class="form-group">
                            <label for="DATABASE_TABLE_USER">用户信息表格</label>
                            <input class="form-control" type="text" name="DATABASE_TABLE_USER" id="DATABASE_TABLE_USER"
                                   placeholder="默认：user" value="<%= conf.DATABASE_TABLE_USER %>" required/>
                            <div class="invalid-feedback">请提供一个有效的值</div>
                        </div>
                    </div>
                </div>
            </div>
        </form>

    </div>
</div>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>"/>
    <jsp:param name="NO_CHAT" value="Y" />
</jsp:include>
</body>
</html>
