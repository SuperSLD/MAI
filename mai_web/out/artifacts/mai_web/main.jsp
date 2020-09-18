<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>Главная</title>
    <%@ include file="html/link.jsp"%>
    <script>
        document.contextPath = "${pageContext.request.contextPath}";
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/news.js"></script>
</head>
<body onload="init()">
    <div class="main" align="center">
        <div class="main_container" align="left">
            <%@ include file="html/header.jsp"%>
            <div class="add_news" align="left">
                <span class="title_add_note"><b>Создание новости</b></span><br>
                <span class="subtitle">Заголовок новости:</span><br>
                <input type="text" id="title_new_note" class="title_input"><br>
                <span class="subtitle">Текст новости:</span><br>
                <textarea id="text_new" class="text_input"></textarea>
                <div align="right">
                    <button id="send_button">Создать новость</button>
                </div>
            </div>
            <div id="news_container">
            ${news}
            </div>
        </div>
    </div>
</body>
</html>
