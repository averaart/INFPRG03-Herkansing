<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>
	<c:choose>
	    <c:when test="${empty requestScope.title}">
	        Enquetes
	    </c:when>
	    <c:otherwise>
	        <c:out value="${requestScope.title}" />
	    </c:otherwise>
	</c:choose>
</h1>