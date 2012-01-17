<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="data.*" %>


<jsp:include page="inc/pagehead.jsp"/>
<jsp:include page="inc/pagetitle.jsp"/>

<ul>
<c:forEach var="survey" items="${surveys}">
	<li><c:out value="${survey.title}" /> - ${survey.correspondents} correspondenten</li>
</c:forEach>
</ul>

<jsp:include page="inc/pagefoot.jsp"/>