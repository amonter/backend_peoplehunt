<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>


<meet:currentUserInSession profileId="${sessionScope.userSessionContext.profileID}"/>

<ul class="search">
<c:choose>
	<c:when test="${fn:length(profileList) eq 0 && fn:length(threads) eq 0}">
		<li>			
			<spring:message code="message.search.notfound" /><c:out value="${message}" />						
		</li>			
	</c:when>
	<c:otherwise>
		<li class="header">
			<span class="searchHeader"><spring:message code="message.search.searchedCriteria" />&nbsp;<c:out value="${message}" /></span>						
		</li>	
	</c:otherwise>
</c:choose>
	<c:if test="${fn:length(profileList) != 0}">
		<li>
			<span style="padding-left:6px;font-size:10pt;">Users Results</span>
		</li>
		<li>				
		<display:table list="${profileList}" id="messagetable" style="font-size:12px;max-width:600px;" pagesize="10" requestURI="searchforums.do" class="forumtable">
			<display:column style="width:220px;">
				<table style="margin:10px;">
					<tr>
						<td width="300px">							
							<div style="float:left;">
								<c:if test="${user.imageID != -1}" >
									<c:set var="imageId" value="${messagetable.user.imageID}" />
								</c:if>
								<c:if test="${user.imageID == -1}" >
									<c:set var="imageId" value="${messagetable.user.gender}" />										
								</c:if>	
								<a href="retrieveuserhome.do?profileid=${messagetable.id}"><img src="picture_library/profile/${imageId}.jpg" width="70" height="70" /></a>									
							</div>	
							<div style="padding-left:80px;">								
								<c:if test="${!empty messagetable.user.age}" >
									<nobr>Age: <span><c:out value="${messagetable.user.age}" /></span></nobr>
								</c:if>
								<br>
								<span>
									<c:if test="${!empty messagetable.user.state}">
										<c:out value="${messagetable.user.state}" />,
									</c:if>									
									<c:out value="${messagetable.user.country}" />
								</span>
								<span style="display:block;">
									<c:out value="${thread.profile.user.gender}" />
								</span>																													
								<c:if test="${empty userLogged}" >
									<br>
									<a href="javascript:notLoggedIn();" id="popupPosition" style="margin-right:5px;" > Send Message</a>
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
	</li>	
	</c:if>			
	<c:if test="${fn:length(threads) != 0}">
	<li>
		<span style="padding-left:6px;font-size:10pt;">Forum Results</span>
	</li>
	<li>			
		<display:table list="${threads}" id="thread"  style="max-width:600px;font-size:12px;" pagesize="10" requestURI="searchforums.do" class="forumtable">
			<display:column style="width:220px;">
				<table style="margin:10px;">
					<tr>
						<td width="300px">									
							<div style="float:left;">													
								<c:if test="${thread.profile.user.imageID != -1}" >
									<c:set var="imageId" value="${thread.profile.user.imageID}" />
								</c:if>
								<c:if test="${thread.profile.user.imageID == -1}" >
									<c:set var="imageId" value="${thread.profile.user.gender}" />										
								</c:if>								
								<a href="retrieveuserhome.do?profileid=${thread.profile.id}"><img src="picture_library/profile/${imageId}.jpg" width="70" height="70" /></a>									
							</div>	
							<div style="padding-left:80px;">								
								<c:if test="${!empty thread.profile.user.age}" >
									<nobr>Age: <span><c:out value="${thread.profile.user.age}" /></span></nobr>
								</c:if>
								<br>
								<span>
									<c:if test="${!empty thread.profile.user.state}">
										<c:out value="${thread.profile.user.state}" />,
									</c:if>									
									<c:out value="${thread.profile.user.country}" />
								</span>
								<span style="display:block;">
									<c:out value="${thread.profile.user.gender}" />
								</span>
								<c:if test="${!empty thread.profile}">
									<c:if test="${empty userLogged}" >
										<br>
										<a id="popupPosition" style="margin-right:5px;" href="javascript:notLoggedIn();"> Send Message</a>
									</c:if>																	
									<c:if test="${!empty userLogged}" >
										<br>
										<a id="popupPosition" style="margin-right:5px;" id="popupPosition" href="javascript:sendtheMessage('${thread.profile.user.profile.id}','${thread.profile.user.userName}');"> Send Message </a>
									</c:if>
								</c:if>								
							</div>			
							<div style="clear:both;">
								<span><c:out value="${thread.profile.user.userName}" /></span>
							</div>	
						</td>
					<tr>
				</table>							
			</display:column>
			<display:column style="text-align:left;">
				<p style="margin:10px;">	
					<a href="retrieveforumthreads.do?postpk=${thread.post.id}">
						<meet:format  value='${thread.content}'  length="90"/>
					</a>
				</p>
			</display:column>
			<display:column title="Topic" style="text-align:left;">
				<p style="margin:10px;">					
					<meet:format  value='${thread.post.title}'  length="90"/>		
				</p>
			</display:column>	
			<display:column title="Forum" style="text-align:left;">
				<p style="margin:10px;">					
					<meet:format  value='${thread.post.forum.topic}'  length="90"/>
				</p>
			</display:column>																
		</display:table>
		</c:if>		
	</li>
</ul>