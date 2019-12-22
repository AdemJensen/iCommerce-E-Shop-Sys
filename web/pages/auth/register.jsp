<%@ page import="top.chorg.iCommerce.function.Request" %>
<%@ page import="top.chorg.iCommerce.function.auth.AuthInfo" %>
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
    <title>注册 - <%= conf.SYSTEM_NAME %></title>
</head>
<script>
    function changeSubmit() {
        if (document.getElementById("agreement").checked) $("#submit").removeAttr("disabled");
        else $("#submit").attr("disabled","disabled");
    }
    let re;
    $(function () {
        re = document.getElementById("password-re");
    });
    function reChange() {
        if ($("#password-re").val() === $("#password").val()) {
            re.setCustomValidity("");
        } else {
            re.setCustomValidity("错误");
        }
    }
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
                        let pasRe = $("#password-re");
                        $(pas).val(hex_md5($(pas).val()));
                        $(pasRe).val(hex_md5($(pasRe).val()));
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
<div class="container-fluid h-100 overflow-auto mt-5">
    <div class="col-4 overflow-auto" style="position: relative;transform: translate(-50%, -50%);top: 40%;left: 50%;">
        <form class="needs-validation" method="post" action="${pageContext.request.contextPath}/register/auth" novalidate>
            <h1 class="text-center">注册<%= conf.SYSTEM_NAME %>账户</h1>
            <div class="form-group am-form-icon am-form-feedback">
                <label for="email">电子邮件</label>
                <input class="form-control" type="email" name="email" id="email" placeholder="请输入电子邮件" required />
                <div class="invalid-feedback">请提供一个有效的电子邮件</div>
            </div>
            <div class="form-group am-form-icon am-form-feedback">
                <label for="username">用户名</label>
                <input class="form-control" type="text" name="username" id="username" minLength="3" maxLength="18" pattern="[A-Za-z0-9_\-]+" placeholder="请输入用户名" required />
                <small id="usernameHelp" class="form-text text-muted">用户名应该由3-18位字母和数字组成</small>
                <div class="invalid-feedback">有效的用户名应该由3-18位字母和数字组成</div>
            </div>
            <div class="form-group am-form-icon am-form-feedback">
                <label for="password">密码</label>
                <input class="form-control" type="password" name="password" id="password" placeholder="请输入密码" minLength="3" maxLength="18" required />
                <small id="passwordHelp" class="form-text text-muted">密码长度介于3-18位</small>
                <div class="invalid-feedback">有效的密码长度应当介于3-18位</div>
            </div>
            <div class="form-group am-form-icon am-form-feedback">
                <label for="password-re">确认密码</label>
                <input class="form-control" type="password" name="password-re" id="password-re" placeholder="再次输入密码" oninput="reChange()" required />
                <div class="invalid-feedback">两次输入密码不一致</div>
            </div>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="agreement" onclick="changeSubmit()">
                <label class="form-check-label" for="agreement">我已阅读并同意<a href="#">《用户协议》</a></label>
            </div>
            <button type="submit" class="col-12 btn btn-primary mt-2" id="submit" disabled>注册</button>
        </form>
    </div>
</div>
<jsp:include page="/templates/bottom-bar.jsp">
    <jsp:param name="CONFIG" value="<%= conf.getJson() %>" />
    <jsp:param name="NO_CHAT" value="Y" />
</jsp:include>
</body>
</html>