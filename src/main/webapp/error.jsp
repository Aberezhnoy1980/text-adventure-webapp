<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ошибка — Космический квест</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
<div class="page">
    <main class="content">
        <p class="eyebrow">Бортовой журнал</p>
        <h1>Что-то пошло не так</h1>
        <p class="muted">
            Запрошенная страница недоступна или на борту произошёл сбой.
            <c:if test="${not empty requestScope['javax.servlet.error.status_code']}">
                Код: ${requestScope['javax.servlet.error.status_code']}.
            </c:if>
        </p>
        <a class="btn btn-link" href="${pageContext.request.contextPath}/welcome">Вернуться на базу</a>
    </main>
</div>
</body>
</html>
