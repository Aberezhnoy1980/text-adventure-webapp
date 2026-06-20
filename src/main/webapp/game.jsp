<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Космический квест</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
<div class="page">
    <main class="content">
        <p class="eyebrow">Командир ${sessionScope.gameSession.playerName}</p>

        <c:choose>
            <c:when test="${gameView.finished}">
                <h1>Миссия завершена</h1>
            </c:when>
            <c:otherwise>
                <h1>${gameView.question}</h1>
            </c:otherwise>
        </c:choose>

        <c:if test="${not empty gameView.errorMessage}">
            <p class="error">${gameView.errorMessage}</p>
        </c:if>

        <c:choose>
            <c:when test="${gameView.finished}">
                <p class="outcome ${sessionScope.gameSession.gameResult eq 'VICTORY' ? 'outcome-win' : 'outcome-lose'}">
                    ${gameView.question}
                </p>
                <form class="game-form" action="${pageContext.request.contextPath}/restart" method="post" accept-charset="UTF-8">
                    <button type="submit" class="btn">Начать заново</button>
                </form>
            </c:when>
            <c:otherwise>
                <form class="game-form" action="${pageContext.request.contextPath}/game" method="post" accept-charset="UTF-8">
                    <div class="choices">
                        <c:forEach var="option" items="${gameView.options}">
                            <label class="choice">
                                <input type="radio" name="choice" value="${option.choice}">
                                <span>${option.label}</span>
                            </label>
                        </c:forEach>
                    </div>
                    <button type="submit" class="btn">Ответить</button>
                </form>
            </c:otherwise>
        </c:choose>
    </main>

    <footer class="stats">
        <p class="stats-title">Статистика</p>
        <p>IP address: ${gameView.clientIp}</p>
        <p>Имя в игре: ${sessionScope.gameSession.playerName}</p>
        <p>Количество игр: ${sessionScope.gameSession.gamesPlayed}</p>
    </footer>
</div>
</body>
</html>
