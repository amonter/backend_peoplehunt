<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div style="standardSpacing">	
	<table>
		<tr>
			<td>
				${questions[0].questionDescription}
			</td>
		</tr>
		<c:forEach items="${questions[0].answers}" var="answer">
			<tr>
				<td>
					<span style="display:block;">${answer.textualAnswer}</span>					
				</td>
			</tr>
		</c:forEach>
		<c:forEach items="${questions}" var="question">
			<tr >
				<td style="padding:10px;">
					<span style="display:inline;">${question.id}</span>
					<span style="display:inline;">Created by ${question.profiles[0].user.userName}</span>
					<span style="width:auto;display:inline;">
						${question.questionDescription}
					</span>
					<span style="display:inline;padding:13px;">
						<form action="deletetheprivatequestion.htm" method="POST">
							<input type="hidden" name="id" value="${question.id} " />
							<input type="hidden" name="questionPhoneId" value="${question.questionPhoneId} " />
							<input type="submit" value="Delete" />	
						</form>
					</span>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
