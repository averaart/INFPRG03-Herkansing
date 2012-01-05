<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
	<c:choose>
	    <c:when test="${empty requestScope.title}">
	        Enquetes
	    </c:when>
	    <c:otherwise>
	        <c:out value="${requestScope.title}" />
	    </c:otherwise>
	</c:choose>
</title>
<jsp:include page="css.jsp"/>
</head>
<body>
<div id="content">
<div id="navigation">
<a href="/infprg03/">Home</a>&ensp;&mdash;&ensp;
<a href="/infprg03/surveys">Alle enquetes</a>&ensp;&mdash;&ensp;
	<c:choose>
	    <c:when test="${empty sessionScope.user}">
			<a href="/infprg03/log">Inloggen</a>
	    </c:when>
	    <c:otherwise>
	        <a href="/infprg03/log">Uitloggen</a>
	    </c:otherwise>
	</c:choose>

</div>