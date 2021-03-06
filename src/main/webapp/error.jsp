<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=no;" />
  <style>
    /* SK Planet - Base 1.0 */
    html,body,form,div,p,h1,h2,h3,h4,h5,h6,dl,dt,dd,ul,ol,li,fieldset,th,td,button{margin:0;padding:0;border:0;font-size:100%;font:inherit;}
    body{-webkit-text-size-adjust:none;}
    input,textarea,select,button{font:inherit;vertical-align:middle;padding:0;}
    input[type="button"],input[type="text"],input[type="image"],input[type="submit"],textarea{-webkit-appearance:none;border-radius:0;}
    input[type="checkbox"]{-webkit-appearance:checkbox;}
    input[type="radio"]{-webkit-appearance:radio;}
    textarea{resize:none;}
    table{width:100%;border-collapse:collapse;border-spacing:0;}
    ol,ul{list-style:none;}
    img,fieldset,iframe{border:none;}
    address,cite,code,dfn,em,var,th,strong{font-style:normal;font-weight:normal;}
    a{text-decoration:none;}
    article,aside,dialog,figure,footer,header,hgroup,nav,section{display:block;}
    /* Syrup Style Event */
    html{font-size:16px;}
    body{font-size:1rem;}
    img{display:block;}
    button{padding:0;margin:0;border:0;cursor:pointer;}

    .pop-wrap .error{position:absolute;top:0;left:0;right:0;bottom:0;width:100%;height:100%;}
    .pop-wrap .msg{background:url(i_error01.png) 0 0 no-repeat;background-size:267px auto;width:267px;padding:125px 0 0 0;font-size:13px; color:#333;text-align:center;position:absolute;top:50%;left:50%;margin:-124px 0 0 -133px;line-height:1.4;}
    .pop-wrap .msg em{display:block;width:1px;height:1px;overflow:hidden;}
    .pop-wrap .tail{position:absolute;bottom:27px;left:0;width:100%;text-align:center;font-size:11px;color:#999;line-height:1.4;}
    .pop-wrap .tail a{color:#333;}
  </style>
  <title>Syrup Style</title>
</head>
<body>
<div class="pop-wrap">

  <div class="error">
    <div class="msg">
      <em>요청하신 페이지를 찾을 수 없어요...</em>
      <p>페이지의 주소가 잘못 입력되었거나, <br />변경 혹은 삭제되었을 수 있습니다.</p>
    </div>
    <div class="tail">찾으시는 페이지에 대한 문의는 <br />
      <a href="mailto:syrupstyle@skplanet.com">syrupstyle@skplanet.com</a>으로 연락주세요.</div>
  </div>

</div>
</body>
</html>