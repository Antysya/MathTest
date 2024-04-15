<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.concurrent.ThreadLocalRandom" %>
<%@ page import="mathtest.Question" %>
<%@ page import="mathtest.StoreData" %>

<html>
<title>Тестирование по математике</title>
<style><%@include file="/WEB-INF/css/style.css"%></style>
<body>
<%
    Question q = new Question();
    StoreData store = new StoreData();
    store.saveQuestion(q);
%>
<h2>Решите пример:</h2>
<form action='Check' method='post'>
<input type='hidden' name='questionId' value='<%=q.getId()%>'>
    <label for="answer"> <%=q.getContent()%> ? </label>
    <input type="number" name="answer" id="answer"><br><br>
    <input type='submit' value='Ответить'>
</form>
</body>
</html>
