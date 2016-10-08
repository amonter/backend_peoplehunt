<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 


<div style="standardSpacing">	
	<table>
		<c:forEach items="${theList}" var="question">
			<tr>
				<td>
					<a style="display:inline;padding:10px;" href="viewallprivatequestions.htm?questionphoneid=${question.questionPhoneId}">View All</a>
					<span style="width:auto;display:inline;">
						${question.questionDescription}
					</span>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
