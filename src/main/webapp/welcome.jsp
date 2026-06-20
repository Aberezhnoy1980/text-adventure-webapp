<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Космический квест — приветствие</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
<div class="page">
    <main class="content">
        <header class="hero">
            <p class="eyebrow">Доклад с борта «Орбита-7»</p>
            <h1>Пролог</h1>
        </header>

        <section class="prologue">
            <p>
                Ты приходишь в себя у шлюза космопорта «Ganymede Gate». Пропуск на груди
                гласит «капитан», но память будто стёрли demagnetizer'ом: кто ты, откуда
                и зачем здесь — только догадки. Зато в кармане аккуратно сложена записка:
                «Не принимай первое приглашение. И второе тоже. Но если придётся — держи
                лицо лучше, чем держишь курс».
            </p>
            <p>
                Над доками зависает чужой корабль без опознавательных знаков. Свет
                мягкий, слишком вежливый — как у тех, кто уже знает ответ на свой вопрос.
            </p>
        </section>

        <section class="crew">
            <h2>Знакомство с экипажем</h2>
            <p>
                На трап поднимается офицер с планшетом и выражением лица человека,
                который видел слишком много «временных капитанов»:
            </p>
            <blockquote>
                — Добро пожаловать на борт, <em>командир</em>. Я Astra, ваш adjutant.
                Штурман — там, у кофемашины: сержант Перегарный Шлейф, он верит, что
                курс строится по запаху эспрессо. Бортмеханик Чёрный Богдан спит под
                штурвалом — так он «синхронизируется с двигателем». А навигатор
                Сергей Стальная Пятка снимает это для «архива миссии».
                Как к вам обращаться официально?
            </blockquote>
        </section>

        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>

        <form class="welcome-form" action="${pageContext.request.contextPath}/welcome" method="post" accept-charset="UTF-8">
            <label class="field">
                <span class="field-label">Имя командира</span>
                <input type="text" name="playerName" placeholder="Введите имя" maxlength="64" required>
            </label>
            <button type="submit" class="btn">Представиться</button>
        </form>
    </main>
</div>
</body>
</html>
