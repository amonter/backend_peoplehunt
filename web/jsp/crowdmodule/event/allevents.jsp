<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div class="placing-1 events">
	<h1 id="titleOfCreate">Event Questions</h1>
	<h2 class = "form-extra-details" style="text-align:center; margin-left:0px;">Answer the questions from the event that you are attending</h2>
		<c:forEach items="${events}" var="event">
 	<div style="display:block;">
		<div class="form" style="margin-top:40px;">
		<!--		
 		<img style="float:left;background-color:black; padding:20px;" src='<c:url value="/crowdmodule/event/${event.id}/image" />'/>
		-->
			<div>
				${event.description}
			</div>
			<div class = "form-extra-details margin_left">${event.date}, ${event.location}</div>
			<div class = "form-extra-details margin_left">
				<c:if test="${event.visual != null}">		
					Visualisation <a href='<c:url  value="/crowdmodule/${event.permaLink}"/>'/>here</a>
				</c:if>
			</div>
		</div>
		<div style="float:right; margin:-100px 250px 0 0px;">	
			<c:url value='/crowdmodule/public/${event.id}/questions/' var="eventUrl"/>
			<a href="${eventUrl}"><button type="button" class="form-boxes-buttons" >I am attending</button></a>
		</div>
	</div>
	</c:forEach>
</div>
