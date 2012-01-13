<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<jsp:include page="../../inc/pagehead.jsp" />

<h1>Geen toegang</h1>

<p>U heeft geen toegang tot de pagina om de volgende reden:</p>
<p class="errorMsg">${status}</p>

<jsp:include page="../../inc/pagefoot.jsp" />