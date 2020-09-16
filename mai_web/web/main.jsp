<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>Главная</title>
    <%@ include file="html/link.jsp"%>
</head>
<body>
    <div class="main" align="center">
        <div class="main_container" align="left">
            <%@ include file="html/header.jsp"%>
            <div class="add_news" align="center">
                <span>Добавить новость</span>
            </div>
            <div class="note">
                <span class="news_title"><b>Расписание временно недоступно</b></span><br><br>
                <span class="news_content">
                    Сайт МАИ не доступен. Причины проблемы не известны, но как только
                    на сайте все заработает в приложении тоже будет работать. Приносим извинения
                    за поддержку сайта так как эти инвалиды снова что-то сломали.
                </span><br>
                <span class="news_time">
                    14.09.2020
                </span>
            </div>
        </div>
    </div>
</body>
</html>
