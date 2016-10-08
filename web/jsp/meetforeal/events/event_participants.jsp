<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<c:if test="${fn:length(events) > 0}">
	
	<div class="eventBoxPart">		
		<c:set var="eventCount" value="${fn:length(events)}" />		
		<c:forEach items="${events}" var="event" varStatus="iter">											
			<c:if test="${iter.index > 0}">
				<c:set var="display" value="display:none" />
			</c:if>			
			<div id="eventboard_${iter.index}" style="clear:both;${display};">
				<h2 class="theHeader">
					<a style="color:white;"href="retrieveevent.do?eid=${event.id}">${event.name}</a>
				</h2>
				<h3 style="color:#333333;margin-left:5px;font-size:13px;font-weight:normal;">					
					Who is coming on <b><fmt:formatDate pattern="EEE dd MMM HH:mm" value="${event.startDate}" timeZone="Europe/Dublin"/></b>					
				</h3>						
				<div class="peoplePart">				
					<c:forEach items="${event.participants}" begin="0" end="20" var="profile">	
						<c:set var="imageId" value="${profile.user.imageID}" />					
						<c:if test="${imageId == -1}" >
							<c:set var="imageId" value="${profile.user.gender}" />										
						</c:if>								
						<div class="people_photo">
							<a href="retrieveuserhome.do?profileid=${profile.id}" alt="${profile.user.firstName} ${profile.user.lastName}"  title="${profile.user.firstName} ${profile.user.lastName}"><img src="picture_library/profile/${imageId}.jpg" width="50" height="50"  /></a>
						</div>		
					</c:forEach>
					<div style="clear:both;padding-left:4px;">
							<!--<a href="retrieveevent.do?eid=${event.id}" style="display:inline;width:5px;font-size:13px;padding-left:2px;">							
								<b>See More..<b>
							</a>-->					
							<c:if test="${eventCount >  1}">
								<c:choose>
									<c:when test="${eventCount eq (iter.index + 1)}">
										<span class="move" style="margin-left:170px;">
											<a style="font-size:13px;" href="javascript:showNextElement('eventboard_${iter.index}', 'eventboard_${iter.index - 1}');" ><img src="images/back_btn.jpg"></a>
										</span>
									</c:when>							
									<c:when test="${0 == iter.index }">
										<span class="move" style="margin-left:170px;">
											<a style="font-size:13px;" href="javascript:showNextElement('eventboard_${iter.index}', 'eventboard_${iter.index + 1}');"><img src="http://images.meetforeal.com/images/forward_btn.jpg"></a>			
										</span>
									</c:when>
									<c:otherwise>
										<span class="double">
											<span class="move">
												<a style="font-size:13px;" href="javascript:showNextElement('eventboard_${iter.index}', 'eventboard_${iter.index - 1}');" ><img src="images/back_btn.jpg"></a>
											</span>
											<span class="move">
												<a style="font-size:13px;" href="javascript:showNextElement('eventboard_${iter.index}', 'eventboard_${iter.index + 1}');"><img src="images/forward_btn.jpg"></a>			
											</span>
										</span>
									</c:otherwise>
								</c:choose>
							</c:if>
						
					</div>
				</div>						
			</div>
		</c:forEach>				
	</div>		
</c:if>

<script language='javascript'>

function showNextElement(element1, element2) {
	
	questionaireEffect(element1, element2);			
}
</script>
