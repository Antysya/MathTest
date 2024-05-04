<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 15.04.2024
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>

</head>
<body>
<h2>Форма регистрации</h2>
<form action="Registration" method="post">
    <label for="firstName">Фамилия:</label>
    <input type="text" id="firstName" name="firstName" placeholder="Введите фамилию"><br><br>

    <label for="lastName">Имя:</label>
    <input type="text" id="lastName" name="lastName" placeholder="Введите имя"><br><br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" placeholder="Введите пароль"><br><br>

    <input type="submit" id="register" name="register"
           value="Зарегистрироваться">
</form>
</body>
</html>
