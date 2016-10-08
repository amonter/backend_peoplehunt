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
 <div class="event_history_title">Event Attendees</div>
 <table height="100" width="100"  class="forumtable" 
         style="border:1px solid #E4E5E5">   
    <tr>
		<th>Profile</th>
	</tr>
	<c:set var="count" value="1"/> 
	<c:forEach items="${event.eventHistory}" var="eventHistory">  
		<c:if test="${eventHistory.profileId.user.imageID != -1}" >
			<c:set var="imageId" value="${eventHistory.profileId.user.imageID}" />
		</c:if>
		<c:if test="${eventHistory.profileId.user.imageID == -1}" >
			<c:set var="imageId" value="${eventHistory.profileId.user.gender}" />										
		</c:if>	
	   <c:choose>	
	     <c:when test="${count mod 2 == 0}"> 
			<tr>
				<td>
				     <div style="float:left;">
				       <a href="retrieveuserhome.do?profileid=${eventHistory.profileId.id}">
						  <img src="picture_library/profile/${imageId}.jpg" width="70" height="70" />	
					   </a>	  						
					  </div>	
					  <div style="padding-left:80px;">								
						<c:if test="${!empty eventHistory.profileId.user.age}" >
						  <nobr>Age: <span><c:out value="${eventHistory.profileId.user.age}" /></span></nobr>
						</c:if>
						<br>
						<span>
							<c:if test="${!empty eventHistory.profileId.user.state}">
								<c:out value="${eventHistory.profileId.user.state}" />,
							</c:if>
							<c:out value="${eventHistory.profileId.user.country}" />
						</span>	
						<span style="display:block;">
							<c:out value="${eventHistory.profileId.user.gender}" />
						</span>																					
					  </div>			
					  <div style="clear:both;">
						 <span><c:out value="${eventHistory.profileId.user.userName}" /></span>
					  </div>	   
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr class="odd">
				<td>
				     <div style="float:left;">
				       <a href="retrieveuserhome.do?profileid=${eventHistory.profileId.id}">
						 <img src="picture_library/profile/${imageId}.jpg" width="70" height="70" />
					   </a>	 							
					  </div>	
					  <div style="padding-left:80px;">								
						<c:if test="${!empty eventHistory.profileId.user.age}" >
						  <nobr>Age: <span><c:out value="${eventHistory.profileId.user.age}" /></span></nobr>
						</c:if>
						<br>
						<span>
							<c:if test="${!empty eventHistory.profileId.user.state}">
								<c:out value="${eventHistory.profileId.user.state}" />,
							</c:if>
							<c:out value="${eventHistory.profileId.user.country}" />
						</span>	
						<span style="display:block;">
							<c:out value="${eventHistory.profileId.user.gender}" />
						</span>																					
					  </div>			
					  <div style="clear:both;">
						 <span><c:out value="${eventHistory.profileId.user.userName}" /></span>
					  </div>	   
				</td>
			</tr>
		</c:otherwise>
	  </c:choose>
	  <c:set var="count" value="${count + 1}"/> 
   </c:forEach> 		
 </table>		
</div> 		