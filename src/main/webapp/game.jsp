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
        <h1>Игра скоро начнётся</h1>
        <p class="muted">
            Командир <strong>${sessionScope.gameSession.playerName}</strong>, игровой движок
            подключим на следующем этапе. Пока можно проверить, что сессия и маршруты работают.
        </p>
    </main>

    <footer class="stats">
        <p class="stats-title">Статистика</p>
        <p>IP address: ${clientIp}</p>
        <p>Имя в игре: ${sessionScope.gameSession.playerName}</p>
        <p>Количество игр: ${sessionScope.gameSession.gamesPlayed}</p>
    </footer>
</div>
</body>
</html>
