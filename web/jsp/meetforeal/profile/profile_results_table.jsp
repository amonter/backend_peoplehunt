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

<meet:currentUserInSession profileId="${sessionScope.userSessionContext.profileID}"/>
<div style="margin:5px;">
	<c:if test="${!empty criteria}">
		<p style="margin-left:8px;"> 
			<span class="searchHeader"><spring:message code="message.search.searchedCriteria" />&nbsp;<c:out value="${criteria}" /></span>			
		</p>
	</c:if>
	<c:if test="${fn:length(profileList) != 0}">
		<meet:currentUserInSession />
		<div class="top_header">
			<span id="niceFont" style="color:black"><b>User Search</b></span>
		</div>											
		<display:table list="${profileList}" id="messagetable" style="font-size:12px;width:600px;" requestURI="searchuserhome.do" pagesize="10" class="forumtable">
			<display:column style="width:220px;">
				<table style="margin:10px;">
					<tr>
						<td width="300px">							
								<div style="float:left;">
									<c:if test="${messagetable.user.imageID != -1}" >
									<c:set var="imageId" value="${messagetable.user.imageID}" />
								</c:if>
								<c:if test="${messagetable.user.imageID == -1}" >
									<c:set var="imageId" value="${messagetable.user.gender}" />										
								</c:if>																		
								<a href="retrieveuserhome.do?profileid=${messagetable.id}"><img src="picture_library/profile/${imageId}.jpg" width="70" height="70" /></a>							
							</div>	
							<div style="padding-left:80px;">								
								<c:if test="${messagetable.user.profile.publicProfile eq true}">                                
									<nobr>Age: <span><c:out value="${messagetable.user.age}" /></span></nobr>
									<br>
								</c:if>								
								<span>
									<c:if test="${!empty messagetable.user.state}">
										<c:out value="${messagetable.user.state}" />,
									</c:if>
									<c:out value="${messagetable.user.country}" />
								</span>	
								<span style="display:block;">
									<c:out value="${messagetable.user.gender}" />
								</span>																					
								<c:if test="${empty userLogged}" >
									<br>
									<a href="javascript:notLoggedIn();" id="popupPosition" style="margin-right:5px;" onclick="notLoggedIn();"> Send Message</a>
								</c:if>																	
								<c:if test="${!empty userLogged}" >
									<br>
									<a href="javascript:sendtheMessage('${messagetable.user.profile.id}','${messagetable.user.userName}');" id="popupPosition" style="margin-right:5px;" id="popupPosition" onclick=""> Send Message </a>
								</c:if>								
							</div>			
							<div style="clear:both;">
								<span><c:out value="${messagetable.user.userName}" /></span>
							</div>																	
						</td>													
					<tr>
				</table>				
			</display:column>
			<display:column title="Interests" style="text-align:left;">
				<p style="margin:5px;font-size:12px;">
					<meet:format  value='${messagetable.user.profile.profileInfo.interests}'  length="90"/>					
				</p>
			</display:column>
			<display:column title="About Me" style="text-align:left;">
				<p style="margin:5px;font-size:12px;">				
					<meet:format  value='${messagetable.user.profile.profileInfo.othersPerception}'  length="90"/>	
				</p>
			</display:column>																							
		</display:table>		
	</c:if>
</div>				