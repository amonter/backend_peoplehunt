<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>   
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div style="margin:20px 20px 20px 20px;">
	<table border="0" class="loginTable" cellspacing="0" cellpadding="0" >
		<form:form commandName="tagWeight" method="POST">
			<tr>			
				<td class="labels">Events:</td>  
				<td class="login_boxes">				
					<form:select path="event.googleId">					
						<form:options items="${events}" itemLabel="name" itemValue="googleId" />
					</form:select>
				</td>
			</tr>		
			<tr>
				<td class="labels">Tags: </td>
			   	<td class="login_boxes">
					<form:select path="tag.id">					
						<form:options items="${tags}" itemLabel="name" itemValue="id" />
					</form:select>	
				</td>
			</tr>
			<tr>
				<td class="labels">Tag Weight:</td>  
				<td class="login_boxes">
					<form:input cssClass="input_box" path="weight"/>
				</td>
			</tr>		
			<tr>
				<td colspan="2" class="submit">
					<input name="Submit" type="submit" value="Link Event" />
				</td>					
			<tr>		
		</form:form>
	</table>
</div>
<div style="margin:30px 30px 30px 30px;">
	<c:forEach items="${eventWeightList}" var="event">
	<fmt:formatDate var="event_date" pattern="yyyy-MM-dd" value="${event.startDate}" />		
	<div class="event-main">		
		<div class="event-header">
			<a href="retrieveevent.do?eid=${event.id}">${event.name}</a><br/>
			<fmt:formatDate pattern="hh:mm aa" value="${event.startDate}" />, ${event.location}
		</div>
		<div class="event-content">
			${event.content}<br>
			<c:set var="total_weight" value="0" />		
			<c:forEach items="${event.tagWeights}" var="weight">
				<div style="border-style: double; border-width: small; width: 700px;">
					Tag Name : <span style="color:#F61A0C;"><c:out value="${weight.tag.name}"/></span>&nbsp;
					Weight : <span style="color:#F61A0C;"><c:out value="${weight.weight}"/></span>
					<c:set var="total_weight" value="${total_weight + weight.weight}" />							
				</div>
				<div>
					<a href="deletetagweight.htm?tagWeightId=${weight.id}">Delete</a>				
				</div>	
			</c:forEach>
			Total Weight :	${total_weight}				
		</div>
		<div class="event-info">						
			<div class="event-info-details	">
				Posted by: <a href="" rel="nofollow">Adrian</a>				
		  	</div>
		</div>
	</div>
</c:forEach>
</div>
