<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Вход</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/authorisation.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png" type="image/png">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/authorisation.js"></script>
  </head>
  <body onload="init()">
    <div class="main" align="center">
      <div class="form">
        <form method="post" action="${pageContext.request.contextPath}/auth" id="form_key">
          <span class="title1">MAI</span><span class="title2">mobile</span><span class="title3"> Admin panel</span><br>
          <textarea class="key" name="key" id="key"></textarea><br>
          <span class="error">${error_text}</span><button class="login">Вход в систему</button>
        </form>
      </div>
    </div>
  </body>
</html>
