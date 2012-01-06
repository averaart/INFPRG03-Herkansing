<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<jsp:include page="../../inc/pagehead.jsp" />

<h1>Registreren</h1>
<p>Wilt u online uw enquetes beheren? Gebruik dan dit formulier om u
	te registreren. Na registratie kunt u uw enquettes aan uw lijst
	toevoegen.</p>

	<c:choose>
	    <c:when test="${not empty requestScope.invalidData}">
			<p class="errorMsg">Voer correcte gegevens in.</p>
	    </c:when>
	</c:choose>

	<c:choose>
	    <c:when test="${not empty requestScope.userExists}">
			<p class="errorMsg">Helaas, de gekozen gebruikersnaam bestaat al. Kies een andere gebruikersnaam.</p>
	    </c:when>
	</c:choose>

<form method="post" action="<c:url value="/registreren" />">
	<label>Gebruikersnaam:</label><input type="text" name="username" /><br />
	<label>Wachtwoord:</label><input type="password" name="pass" /><br />
	<label>Wachtwoord bevestigen:</label><input type="password" name="passCompare" /><br />
	<input type="submit" name="registrate" value="Registreer" />
</form>

<jsp:include page="../../inc/pagefoot.jsp" />