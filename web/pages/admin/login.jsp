<%@ page import="top.chorg.iCommerce.function.auth.AuthInfo" %>
<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%
        if (AuthInfo.isUser(session)) {
            Request.becomeInfoPage(
                    request, response,
                    "error", "错误", "页面访问被拒绝", "您已登录，请先<a href='" + request.getContextPath() + "/exit'>退出登录</a>"
            );
        }
    %>
    <%@ include file="/templates/include-header.jsp"%>
    <title>管理员登录 - <%= conf.SYSTEM_NAME %></title>
</head>
<script>
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
                    } else {
                        let pas = $("#password");
                        $(pas).val(hex_md5($(pas).val()));
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
<body>
<jsp:include page="/templates/nav-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="selected" value="none" />
</jsp:include>
<div class="container-fluid h-100">
    <div class="col-4" style="position: relative;transform: translate(-50%, -50%);top: 40%;left: 50%;">
        <form method="post" class="needs-validation" action="${pageContext.request.contextPath}/admin/login/auth" novalidate>
            <h1 class="text-center">登录到<%= conf.SYSTEM_NAME %></h1>
            <label class="col-12 text-center">【管理员】账户登录</label>
            <div class="form-group am-form-icon am-form-feedback">
                <label for="username">用户名</label>
                <input class="form-control" type="text" name="username" id="username" value="root" minLength="3" maxLength="18" pattern="[A-Za-z0-9_\-]+" placeholder="请输入用户名" required />
                <div class="invalid-feedback">有效的用户名应该由3-18位字母和数字组成</div>
            </div>
            <div class="form-group am-form-icon am-form-feedback">
                <label for="password">密码</label>
                <input class="form-control" type="password" name="password" id="password" value="123456" placeholder="请输入密码" minLength="3" maxLength="18" required />
                <div class="invalid-feedback">有效的密码长度应当介于3-18位</div>
            </div>
            <small class="form-text text-muted">*为了方便老师操作，用户名和密码已经自动填写（root, 123456）</small>
            <button type="submit" class="col-12 btn btn-primary mt-2" id="submit">登录</button>
        </form>
    </div>
</div>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="NO_CHAT" value="Y" />
</jsp:include>
</body>
</html>