<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>


<meet:currentUserInSession profileId="${sessionScope.userSessionContext.profileID}" />
<div class="top_header" style="padding:6px 0px 6px 6px;">
	<img src="images/whatsondarkgrey.jpg" />
</div>
<div style="width:610px;">	
	<c:forEach items="${event_list}" var="event">
		<fmt:formatDate var="event_date" pattern="yyyy-MM-dd" value="${event.startDate}" />		
		<div class="event-main">
			<div style="margin-top:5px;">				
				<div class="event-date">
					<h4>
						<fmt:formatDate pattern="MMM" value="${event.startDate}" /><br/><fmt:formatDate pattern="dd" value="${event.startDate}" />					
					</h4>
				</div>		
				<div class="event-header" style="margin-right:5px;">
					<a style="padding:0px;font-weight:normal;color:black;" href="${flowExecutionUrl}&_eventId=retrieveEvent&eid=${event.id}&date=${event_date}">${event.name}</a><br/>
					<a href="javascript:showMeTheMap('<meet:format escapedScript='true'  value='${event.location}' />','${event.mapLatitude}','${event.mapLongitude}');" style="font-size:12px;letter-spacing:0px;color:#666666;"><fmt:formatDate pattern="HH:mm" value="${event.startDate}" timeZone="Europe/Dublin" />: ${event.location}</a>
				</div>
				<div style="float:left;">
					<a id="${event.googleId}" href="javascript:saveEvent('${event.googleId}','<meet:format escapedScript='true'  value='${event.name}' />','${flowExecutionUrl}&_eventId=saveMyEvent&itemId=${event.googleId}');" >
						<img src="http://images.meetforeal.com/images/add_events.jpg" />
					</a>
				</div>
			</div>
			<div class="event-content" style="font-size:13px;">
				${event.html}				
			</div>
			<div class="event-content" style="font-size:13px;">	
		           <c:set var="ref_update_presenter"  value="updateprofilepresenter.do"/>
			<c:if test="${empty userLogged}" >
				<c:set var="ref_update_presenter"  value="javascript:notLoggedPresenter();"/>        
			</c:if>
			<c:if test="${event.reference == 'stuart_image'}">
				<a  href="${ref_update_presenter}"><img src="images/shoe_my_stuff.png" /></a>
			</c:if>					
			</div>						
			</div>			
	</c:forEach>
<div>

<script language='javascript'>

function notLoggedPresenter() {

	notLoggedPresenterPopUp();
}


function showMeTheMap(location,latitude, longitude) {
	
	mapPopup(location,latitude, longitude);	
}

function saveEvent(reference, eventName, url) {
	
	saveMyEventFlow(reference,eventName, url);
}

</script> 
