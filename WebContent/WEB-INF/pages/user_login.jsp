<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<jsp:include page="../../inc/pagehead.jsp" />

<h1>Inloggen</h1>

<c:choose>
    <c:when test="${not empty requestScope.unknown}">
		<p class="errorMsg">De door u opgegeven combinatie van inloggegevens komt niet voor in onze database.</p>
    </c:when>
</c:choose>

<form method="post" action="<c:url value="/inloggen" />">
	<label>Gebruikersnaam:</label><input type="text" name="username" /><br />
	<label>Wachtwoord:</label><input type="password" name="pass" /><br />
	<input type="submit" name="login" value="Inloggen" />
</form>

<jsp:include page="../../inc/pagefoot.jsp" />