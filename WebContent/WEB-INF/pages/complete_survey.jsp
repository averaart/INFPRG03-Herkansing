<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>

<jsp:include page="../../inc/pagehead.jsp" />

<h1>Enquete - ${survey.title}</h1>

<h4>Afronden</h4>
<p>
	U staat op het punt de enquete af te ronden. Als de enquete is
	afgerond, is het niet meer mogelijk om wijzigingen aan te brengen in de
	antwoorden op de enquete.
</p>

<c:choose>
	<c:when test="${allowComplete == 'false' }">
		<p class="errorMsg">De volgende vragen dienen nog beantwoord te worden voordat de enquete kan worden ingeleverd.</p>
		<ul>
		<c:forEach var="question" items="${questions}" varStatus="status">
			<c:if test="${question.answer == '' or question.answer == null}">
				<li><a href="<c:url value="/enquete/${survey.id}/${status.count}" />">${status.count} - ${ question.text }</a></li>
			</c:if>
		</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<form method="post" action="<c:url value="/enquete/${survey.id}/afronden" />">
			<input type="submit" name="complete" value="Afronden">
		</form>
	</c:otherwise>
</c:choose>
<jsp:include page="../../inc/pagefoot.jsp" />