<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>

<tiles:insertTemplate template="/jsp/events/browse_pastevents_template.jsp">
    <tiles:putAttribute name="title_style" value="main_title"/>				
	<tiles:putAttribute name="sub_title" value="Past Events" />	
	<tiles:putAttribute name="the_page">
		<div>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute  name="content">
					<div class="top_header" style="padding:6px 0px 6px 6px;">
						<img src="images/past_events.png" style="3px 0px 3px 5px;" />
					</div>
					<div style="width:610px;">
						<c:forEach items="${events}" var="event">
							<fmt:formatDate var="event_date" pattern="yyyy-MM-dd" value="${event.startDate}" />		
							<div class="event-main">
								<div style="margin-top:5px;">
									<div class="event-date">
										<h4><fmt:formatDate pattern="MMM" value="${event.startDate}" /><br/><fmt:formatDate pattern="dd" value="${event.startDate}" />
										</h4>
									</div>		
									<div class="event-header" style="margin-right:25px;">
										<a style="padding:0px;" href="retrievepastevent.do?eid=${event.id}">${event.name}</a><br/>
										<a href="javascript:showMeTheMap('<meet:format escapedScript='true'  value='${event.location}' />','${event.mapLatitude}','${event.mapLongitude}');"  style="font-size:12px;letter-spacing:0px;color:#666666;"><fmt:formatDate pattern="HH:mm" value="${event.startDate}" timeZone="Europe/Dublin" />: ${event.location}</a>
									</div>									
								</div>
								<div class="event-content" style="font-size:13px;">
									${event.content}				
								</div>															
							</div>
						</c:forEach>
					<div>				
				</tiles:putAttribute>
			</tiles:insertTemplate>
		</div>
	</tiles:putAttribute>		
</tiles:insertTemplate>	

<script language='javascript'>

function showMeTheMap(location,latitude, longitude) {
	
	mapPopup(location,latitude, longitude);	
}

</script> 