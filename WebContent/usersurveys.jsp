<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="data.*" %>


<jsp:include page="inc/pagehead.jsp"/>
<jsp:include page="inc/pagetitle.jsp"/>

<ul>
<c:forEach var="survey" items="${mySurveys}">
	<li><a href="disconnect?surveyid=${survey.id}"><c:out value="${survey.title}" /></a></li>
</c:forEach>
</ul>

<h2>Overige enquetes</h2>

<ul>
<c:forEach var="survey" items="${otherSurveys}">
	<li><a href="connect?surveyid=${survey.id}"><c:out value="${survey.title}" /></a></li>
</c:forEach>
</ul>


<jsp:include page="inc/pagefoot.jsp"/>