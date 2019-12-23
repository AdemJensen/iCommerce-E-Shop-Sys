<%@ page import="top.chorg.iCommerce.function.Resource" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<style>
.pd-ipad-pro-kf-201810 .pd-billboard-copy {
    margin: 0 auto;
    text-align: left;
    width: 674px;
}
.pd-ipad-pro-kf-201810 .pd-billboard-copy .pd-billboard-callout {
    color: #62626c;
    font-size: 22px;
}
.pd-ipad-pro-kf-201810 .pd-billboard-copy .pd-billboard-body {
    font-size: 33px;
    font-weight: 600;
    padding-top: 12px;
}


.pd-ipad-pro-kf-201810 .pd-ipad-kf-intro { background-color: #f2f2f2;}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-design,
.pd-ipad-pro-kf-201810 .pd-ipad-kf-face-id,
.pd-ipad-pro-kf-201810 .pd-ipad-kf-apple-pencil { background-color: #fff;}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-display,
.pd-ipad-pro-kf-201810 .pd-ipad-kf-chip,
.pd-ipad-pro-kf-201810 .pd-ipad-kf-smart-keyboard { background-color: #fafafa;}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-intro .pd-billboard-info {
    text-align: center;
    width: 600px;
    padding-top: 73px;
    margin: 0 auto;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-intro .pd-billboard-header {
    font-weight: 600;
    font-size: 39px;
}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-intro .pd-billboard-image {
    padding: 56px 0 99px 0;
    margin-left: -36px;
}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-design .pd-billboard-copy {
    padding-top: 88px;
}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-design .pd-billboard-image { padding: 29px 0 95px 0;}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-design .pd-billboard-image .pd-image-size {
    font-size: 24px;
    font-weight: 500;
    display: block;
    margin: -62px auto 0 auto;
    width: 680px;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-design .pd-billboard-image .pd-image-size div{ display: inline-block;}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-design .pd-billboard-image .pd-image-size .pd-image-size-12 { float: left;}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-design .pd-billboard-image .pd-image-size .pd-image-size-11 { margin-left: 361px;}


.pd-ipad-pro-kf-201810 .pd-ipad-kf-display .pd-billboard-image {
    overflow: hidden;
    margin-left: 145px;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-display .pd-billboard-image img { margin-top: -235px;}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-display .pd-billboard-copy {
    margin-top: -83px;
    padding-bottom: 95px;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-display .pd-billboard-copy .pd-billboard-body { width: 426px;}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-face-id .pd-billboard-image {padding-top: 109px; margin-left: -10px;}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-face-id .pd-billboard-copy {
    margin-top: 18px;
    padding-bottom: 95px;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-face-id .pd-billboard-copy .pd-billboard-body { width: 560px;}



.pd-ipad-pro-kf-201810 .pd-ipad-kf-chip .pd-billboard-copy {
    padding-top: 88px;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-chip .pd-billboard-copy .pd-billboard-body { width: 544px;}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-chip .pd-billboard-image {
    padding-top: 24px;
    margin-left: 143px;
    height: 532px;
}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-apple-pencil .pd-billboard-header,
.pd-ipad-pro-kf-201810 .pd-ipad-kf-apple-pencil .pd-billboard-subcopy {
    font-weight: 600;
    width: 553px;
    text-align: left;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-apple-pencil .pd-billboard-header {
    padding-top: 73px;
    font-size: 37px;
    margin: 0 auto;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-apple-pencil .pd-billboard-copy .pd-billboard-body { width: 485px;}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-apple-pencil .pd-billboard-image {
    padding-top: 54px;
    margin-left: 395px;
}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-apple-pencil .pd-billboard-subcopy {
    font-size: 32px;
    margin: 5px auto 0 auto;
    padding-bottom: 48px;
}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-smart-keyboard .pd-billboard-image { padding-top: 106px;}

.pd-ipad-pro-kf-201810 .pd-ipad-kf-smart-keyboard .pd-billboard-subcopy {
    font-weight: 600;
    width: 676px;
    text-align: left;
    font-size: 32px;
    margin: -0 auto 0 auto;
    padding-top: 51px;
}
.pd-ipad-pro-kf-201810 .pd-ipad-kf-smart-keyboard .pd-billboard-image-folio {
    padding-top: 55px;
    margin-left: 93px;
}
/* footer */
.pd-main-container-footer .pd-footer-wrappe ol{
    float:left;
    padding-right:10px;
    text-align:justify;
}
.pd-main-container-footer .pd-footer-wrappe ol li{
    margin-bottom: 3px;
    line-height:14px;
}</style>
<div class="pd-ipad-pro-kf-201810">
    <div class="pd-billboard pd-ipad-kf-intro pd-main-container">
        <div class="pd-billboard-info" data-spm-anchor-id="a1z10.4-b-s.4007-21028525263.i0.3dc0138eV5EOtN">
            <div class="pd-billboard-header">新一代 iPad Pro，全面新生。</div>
            <div class="pd-billboard-subhead">全新，全面屏，全方位强大。</div>
        </div>
        <div class="pd-billboard-image text-center"><img class="pd-billboard-hero"
                                             src="<%= Resource.getPBPic(request, 2) %>"
                                             width="1600px" alt=""></div>
    </div>
    <div class="pd-billboard pd-ipad-kf-design pd-main-container">
        <div class="pd-billboard-copy">
            <div class="pd-billboard-callout">精致设计</div>
            <div class="pd-billboard-body">最薄的 iPad 中，深藏先进技术。</div>
        </div>
        <div class="pd-billboard-image text-center"><img class="pd-billboard-hero"
                                             src="<%= Resource.getPBPic(request, 3) %>"
                                             width="1100px" alt="">
            <div class="pd-image-size">
                <div class="pd-image-size-12">12.9 英寸</div>
                <div class="pd-image-size-11">11 英寸</div>
            </div>
        </div>
    </div>
    <div class="pd-billboard pd-ipad-kf-display pd-main-container">
        <div class="pd-billboard-image text-center"><img class="pd-billboard-hero"
                                             src="<%= Resource.getPBPic(request, 4) %>"
                                             width="900px" alt=""></div>
        <div class="pd-billboard-copy">
            <div class="pd-billboard-callout">Liquid 视网膜显示屏</div>
            <div class="pd-billboard-body">绚丽的色彩，流畅的响应，在各种光线下阅读都舒服。</div>
        </div>
    </div>
    <div class="pd-billboard pd-ipad-kf-face-id pd-main-container">
        <div class="pd-billboard-image text-center"><img class="pd-billboard-hero"
                                             src="<%= Resource.getPBPic(request, 5) %>"
                                             width="900px" alt=""></div>
        <div class="pd-billboard-copy">
            <div class="pd-billboard-callout">面容 ID</div>
            <div class="pd-billboard-body">无论横向纵向，都能解锁 iPad、登录各款 App，还能扫一眼就支付。</div>
        </div>
    </div>
    <div class="pd-billboard pd-ipad-kf-chip pd-main-container">
        <div class="pd-billboard-copy">
            <div class="pd-billboard-callout">A12X 仿生</div>
            <div class="pd-billboard-body">更强的芯片，匹配更多专业级 App。</div>
        </div>
        <div class="pd-billboard-image text-center"><img class="pd-billboard-hero"
                                             src="<%= Resource.getPBPic(request, 6) %>"
                                             width="820px" alt=""></div>
    </div>
    <div class="pd-billboard pd-ipad-kf-apple-pencil pd-main-container">
        <div class="pd-billboard-header"> 用新一代 iPad Pro 做到更多。</div>
        <div class="pd-billboard-image text-center"><img class="pd-billboard-hero"
                                             src="<%= Resource.getPBPic(request, 7) %>"
                                             width="1200px" alt=""></div>
        <div class="pd-billboard-subcopy"> 新一代 Apple Pencil 支持触碰感应，本领更强大。</div>
    </div>
    <div class="pd-billboard pd-ipad-kf-smart-keyboard pd-main-container">
        <div class="pd-billboard-image text-center"><img class="pd-billboard-hero"
                                             src="<%= Resource.getPBPic(request, 8) %>"
                                             width="780px" alt=""></div>
        <div class="pd-billboard-subcopy"> 键盘式智能双面夹，围绕效率和保护全盘设计。</div>
        <div class="pd-billboard-image-folio text-center"><img class="pd-billboard-hero"
                                                   src="<%= Resource.getPBPic(request, 9) %>"
                                                   width="780px" alt=""></div>
    </div>
</div>
