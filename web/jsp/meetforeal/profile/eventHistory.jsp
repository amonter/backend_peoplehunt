<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>
<div style="padding: 15px;">
 <div class="event_history_title">Events</div>
  
 <div style="padding:10px 0px 10px 0px;">
 	<div id="PhotoOuterBorder">
		<c:if test="${user.imageID != -1}" >
			<c:set var="imageId" value="${eventHistoryList[0].profileId.user.imageID}" />
		</c:if>
		<c:if test="${user.imageID == -1}" >
			<c:set var="imageId" value="${eventHistoryList[0].profileId.user.gender}" />										
		</c:if>								
		<img src="picture_library/profile/${imageId}.jpg" width="125" height="125" alt="profile" />										
	</div>
 </div>
 <table height="100" width="100"  class="forumtable" 
         style="width:925px;border:1px solid #E4E5E5">   
    <tr>
		<th>Name</th>
		<th>Description</th>
		<th>Content</th>
		<th>Location</th>
	</tr>
	<c:set var="count" value="1"/> 
	<c:forEach items="${eventHistoryList}" var="eventHistory">  
	   <c:choose>	
	     <c:when test="${count mod 2 == 0}"> 
			<tr>
				<td>
				   <a href="attendees.do?eventId=${eventHistory.eventId.id}">${eventHistory.eventId.name}</a>
				</td>
				<td>${eventHistory.eventId.description}</td>
				<td>${eventHistory.eventId.content}</td>
				<td>${eventHistory.eventId.location}</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr class="odd">
				<td>
				   <a href="attendees.do?eventId=${eventHistory.eventId.id}">${eventHistory.eventId.name}</a>
				</td>
				<td>${eventHistory.eventId.description}</td>
				<td>${eventHistory.eventId.content}</td>
				<td>${eventHistory.eventId.location}</td>
			</tr>
		</c:otherwise>
	  </c:choose>
	  <c:set var="count" value="${count + 1}"/> 
   </c:forEach> 		
 </table>		
</div> 		