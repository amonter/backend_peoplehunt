<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<c:set var="url_flow_home" value="index.do"/>
<c:set var="url_flow_profile" value="navigateprofile.do"/>
<c:set var="url_flow_forum" value="browseforums.do"/>
<c:set var="url_flow_browseevents" value="browseevents.do"/>
<c:set var="url_flow_blog" value="retrieveblog.do"/>
<c:set var="url_flow_video" value="listvideos.do"/>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow_home" value="${flowExecutionUrl}&_eventId=home"/>
	<c:set var="url_flow_profile" value="${flowExecutionUrl}&_eventId=profile"/>
	<c:set var="url_flow_forum" value="${flowExecutionUrl}&_eventId=forum"/>
	<c:set var="url_flow_browseevents" value="${flowExecutionUrl}&_eventId=events"/>
	<c:set var="url_flow_blog" value="${flowExecutionUrl}&_eventId=blog"/>
	<c:set var="url_flow_video" value="${flowExecutionUrl}&_eventId=video"/>
</c:if>
<br>
<meet:currentUserInSession profileId="${user.profile.id}" />
<tiles:importAttribute name="tab_selector"/>
<meet:tabSelector value="${tab_selector}"/>
<div>	
	<div class="outerTab">
		<div class="innerTab">
			
			<a class="unselectedtab" id="${blogtab}" href="${url_flow_blog}">Blog</a>	
			<a class="unselectedtab" id="${videotab}" href="${url_flow_video}">Video</a>
			<a class="unselectedtab" id="${profiletab}" href="${url_flow_profile}">Profile</a>		
							
			<a class="unselectedtab" id="${eventstab}" href="${url_flow_browseevents}">Events</a>				
			<a class="unselectedtab" id="${faqtab}" href="viewpagefaq.do">FAQ</a>
		<div>
	</div>	
</div>
<script type="text/javascript">

function sendthefeedback () {
	
	sendFeedback();
}
  
</script>	