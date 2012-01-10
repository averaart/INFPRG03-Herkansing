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

<h2>Vraag ${questionNumber} - ${question.text}</h2>
<c:choose>
	<c:when test="${survey.completed == 'false'}">
		<form method="post" action="<c:url value="/enquete/${survey.id}/${questionNumber}" />">
			<c:choose>
			    <c:when test="${not empty requestScope.questionType and requestScope.questionType == 'option'}">
					<c:forEach var="option" items="${question.options}">
						<c:choose>
							<c:when test="${option == question.answer}">
								<input type="radio" name="answer" value="${option}" checked="checked" /> ${option}<br />
							</c:when>
							<c:otherwise>
								<input type="radio" name="answer" value="${option}" /> ${option}<br />							
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<h4>Toelichting op uw antwoord</h4>
					<textarea name="comment" cols="60" rows="3">${question.comment}</textarea><br />	
			    </c:when>
			    
			    <c:when test="${not empty requestScope.questionType and requestScope.questionType == 'scale'}">
					<p>1 = ${question.lowText}, ${question.range} = ${question.highText}</p>
					<c:forEach var="i" begin="1" end="${question.range}" step="1">
						<c:choose>
							<c:when test="${i == question.answer}">
								<input type="radio" name="answer" value="${i}" checked="checked" /> ${i}<br />
							</c:when>
							<c:otherwise>
								<input type="radio" name="answer" value="${i}" /> ${i}<br />							
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<h4>Toelichting op uw antwoord</h4>
					<textarea name="comment" cols="60" rows="3">${question.comment}</textarea><br />	
				</c:when>
			    
			    <c:otherwise>
			    	<textarea name="answer" cols="60" rows="5"></textarea><br />	
			    </c:otherwise>
			</c:choose>
			<input style="margin-top: 30px;" type="submit" name="saveQuestion" value="Opslaan" />
		</form>
	</c:when>

	
	<c:otherwise>
		<c:choose>
		    <c:when test="${not empty requestScope.questionType and requestScope.questionType == 'option'}">
				<ul>
				<c:forEach var="option" items="${question.options}">
					<c:choose>
						<c:when test="${option == question.answer}">
							<li class="answer">${option} - Uw antwoord</li>
						</c:when>
						<c:otherwise>
							<li>${option}</li>
						</c:otherwise>
					</c:choose>							 
				</c:forEach>
				</ul>
				<h4>Toelichting op uw antwoord</h4>
				<c:choose>
					<c:when test="${not empty question.comment and question.comment != ''}">
						<p>${question.comment}</p>
					</c:when>
					<c:otherwise>
						<p>U heeft geen toelichting gegeven.</p>
					</c:otherwise>
				</c:choose>
		    </c:when>
			    
		    <c:when test="${not empty requestScope.questionType and requestScope.questionType == 'scale'}">
				<p>1 = ${question.lowText}, ${question.range} = ${question.highText}</p>
				<ul>
					<c:forEach var="i" begin="1" end="${question.range}" step="1">
						<c:choose>
							<c:when test="${i == question.answer}">
								<li class="answer">${i} - Uw antwoord</li>
							</c:when>
							<c:otherwise>
								<li>${i}</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
				<h4>Toelichting op uw antwoord</h4>
				<c:choose>
					<c:when test="${not empty question.comment and question.comment != ''}">
						<p>${question.comment}</p>
					</c:when>
					<c:otherwise>
						<p>U heeft geen toelichting gegeven.</p>
					</c:otherwise>
				</c:choose>
			</c:when>
		    
		    <c:otherwise>
		    	<p>${question.answer}</p>
		    </c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<jsp:include page="../../inc/pagefoot.jsp" />