<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="container-fluid" style="background: #FAFAFA">
    <div class="row">
        <a href="${pageContext.request.contextPath}/items"><img src="<%= Resource.getPBPic(request, 54)  %>" alt="" class="col-12 m-0" /></a>
    </div>

    <div class="row col-10 offset-1">
        <a href="${pageContext.request.contextPath}/item?item=30" class="col-4" ><img src="<%= Resource.getPBPic(request, 43)  %>" alt="" class="col-12 m-0" /></a>
        <a href="${pageContext.request.contextPath}/item?item=11" class="col-4" ><img src="<%= Resource.getPBPic(request, 44)  %>" alt="" class="col-12 m-0" /></a>
        <a href="${pageContext.request.contextPath}/item?item=28" class="col-4"><img src="<%= Resource.getPBPic(request, 45)  %>" alt="" class="col-12 m-0" /></a>
    </div>

    <div class="row py-2">
        <a href="${pageContext.request.contextPath}/item?item=27"><img src="<%= Resource.getPBPic(request, 47)  %>" alt="" class="col-12 m-0 p-0" /></a>
    </div>

    <div class="row py-2">
        <a href="${pageContext.request.contextPath}/item?item=28"><img src="<%= Resource.getPBPic(request, 48)  %>" alt="" class="col-12 m-0 p-0" /></a>
    </div>

    <div class="row row-cols-2">
        <div class="col-6 p-3">
            <a href="${pageContext.request.contextPath}/item?item=30"><img src="<%= Resource.getPBPic(request, 50)  %>" alt="" class="col-12 p-0" /></a>
        </div>
        <div class="col-6 p-3">
            <a href="${pageContext.request.contextPath}/item?item=11"><img src="<%= Resource.getPBPic(request, 51)  %>" alt="" class="col-12 p-0" /></a>
        </div>
    </div>
    <div class="row row-cols-2">
        <div class="col-6 p-3">
            <a href="${pageContext.request.contextPath}/item?item=21"><img src="<%= Resource.getPBPic(request, 52)  %>" alt="" class="col-12 p-0" /></a>
        </div>
        <div class="col-6 p-3">
            <a href="${pageContext.request.contextPath}/item?item=14"><img src="<%= Resource.getPBPic(request, 53)  %>" alt="" class="col-12 p-0" /></a>
        </div>
    </div>
</div>