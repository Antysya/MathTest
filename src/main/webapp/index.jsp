<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.concurrent.ThreadLocalRandom" %>
<%@ page import="mathtest.Question" %>
<%@ page import="mathtest.StoreData" %>

<html>
<body>
<%
    Question q = new Question();
    StoreData store = new StoreData();
    store.saveQuestion(q);
%>

<form action='Check' method='post'>
<input type='hidden' name='questionId' value='<%=q.getId()%>'>
How many is <%=q.getContent()%> ? <input type='number' name='answer'>?
<input type='submit' value='check'>
</form>
</body>
</html>
