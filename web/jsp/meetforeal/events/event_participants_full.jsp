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
		<div style="clear:both;">
			<h2 class="theHeader">
				<a style="color:white;"href="retrieveevent.do?eid=${event.id}">${event.name}</a>
			</h2>
			<h3 style="color:#333333;margin-left:5px;font-size:13px;font-weight:normal;">					
				Who is coming on <b><fmt:formatDate pattern="EEE dd MMM HH:mm" value="${event.startDate}"  timeZone="Europe/Dublin"/></b>					
			</h3>						
			<div class="peoplePart">	
				<c:forEach items="${event.participants}" begin="0" end="1000" var="profile">	
					<c:set var="imageId" value="${profile.user.imageID}" />					
					<c:if test="${imageId == -1}" >
						<c:set var="imageId" value="${profile.user.gender}" />										
					</c:if>								
					<div class="people_photo">
						<a href="retrieveuserhome.do?profileid=${profile.id}" alt="${profile.user.firstName} ${profile.user.lastName}"  title="${profile.user.firstName} ${profile.user.lastName}"><img src="picture_library/profile/${imageId}.jpg" width="50" height="50"  /></a>
					</div>		
				</c:forEach>					
			</div>						
		</div>					
	</div>		
</c:if>