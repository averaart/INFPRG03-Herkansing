<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="data.*" %>


<jsp:include page="inc/pagehead.jsp"/>
<jsp:include page="inc/pagetitle.jsp"/>

<ul>
<c:forEach var="survey" items="${mySurveys}">
	<li><a href="<c:url value="/enquete/${survey.id}/1" />"><c:out value="${survey.title}" /></a>
	<c:if test="${survey.completed == false}">
		<a href="disconnect?surveyid=${survey.id}"><img src="img/delete.png" /></a>
	</c:if>
	</li>
</c:forEach>
</ul>

<c:if test="${not empty otherSurveys}">
<h2>Overige enquetes</h2>

<ul>
<c:forEach var="survey" items="${otherSurveys}">
	<li><c:out value="${survey.title}" />
		<a href="connect?surveyid=${survey.id}"><img src="img/add.png" /></a>
	</li>
</c:forEach>
</ul>
</c:if>

<jsp:include page="inc/pagefoot.jsp"/>