<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>

<jsp:include page="../../inc/pagehead.jsp" />

<h1>Enquete - ${survey.title}</h1>

<c:if test="${requestScope.hasPrev =='true'}">
	<a href="<c:url value="/enquete/${survey.id}/${questionNumber - 1}" />">Vorige vraag</a>
</c:if>
<c:choose>
   	<c:when test="${requestScope.hasNext =='true'}">
   		<a href="<c:url value="/enquete/${survey.id}/${questionNumber + 1}" />">Volgende vraag</a>
   	</c:when>
   	<c:otherwise>
   		<a href="<c:url value="/enquete/${survey.id}/afronden" />">Afronden</a>
   	</c:otherwise>
</c:choose>

<form method="post" action="<c:url value="/" />">
	<h2>Vraag ${questionNumber} - ${question.text}</h2>
	<c:choose>
	    <c:when test="${not empty requestScope.questionType and requestScope.questionType == 'option'}">
			<c:forEach var="option" items="${question.options}">
				<input type="radio" name="answer" value="${option}" /> ${option}<br />
			</c:forEach>
			<h4>Toelichting</h4>
			<textarea name="explanation" cols="60" rows="3"></textarea><br />	
	    </c:when>
	    <c:when test="${not empty requestScope.questionType and requestScope.questionType == 'scale'}">
			<p>1 = ${question.lowText}, ${question.range} = ${question.highText}</p>
			<c:forEach var="i" begin="1" end="${question.range}" step="1">
				<input type="radio" name="answer" value="${i}" /> ${i}<br />
			</c:forEach>
			<h4>Toelichting</h4>
			<textarea name="explanation" cols="60" rows="3"></textarea><br />	
		</c:when>
	    <c:otherwise>
	    	<textarea name="answer" cols="60" rows="5"></textarea><br />	
	    </c:otherwise>
	</c:choose>
	<input style="margin-top: 30px;" type="submit" name="saveQuestion" value="Opslaan" />
</form>

<jsp:include page="../../inc/pagefoot.jsp" />