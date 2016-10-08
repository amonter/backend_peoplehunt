<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%
	pageContext.setAttribute("contextPath",request.getContextPath());	
%>


<tiles:insertTemplate template="/jsp/events/browse_pastevents_template.jsp">   		
	<tiles:putAttribute name="sub_title" value="Past Events" />	
	<tiles:putAttribute name="the_page">
		<div style="clear:both;">
			<div>
				<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
					<tiles:putAttribute  name="content">
						<div class="top_header" >
							<img src="images/past_event.png" style="padding-left:5px;" />					
						</div>
						<div style="clear:both;">						
							<div style="width:610px;">			
								<div class="event-main">
									<div style="margin-top:5px;">
										<div class="event-date-detail">
											<h4 style="font-size:19px"><b><fmt:formatDate pattern="MMM" value="${event.startDate}" /><br/><fmt:formatDate pattern="dd" value="${event.startDate}" /></b>
											</h4>
										</div>
										<div class="event-header-detail" style="margin-right:25px;">
											<span id="niceFont" style="color:black;font-size:18.5pt;">${event.name}</span><br/>
											<a href="javascript:showMeTheMap('<meet:format escapedScript='true'  value='${event.location}' />','${event.mapLatitude}','${event.mapLongitude}');" style="font-size:12px;letter-spacing:0px;color:#666666;"><fmt:formatDate pattern="HH:mm" value="${event.startDate}" timeZone="Europe/Dublin" />: ${event.location}</a>
										</div>								
									</div>
									<div class="event-content" style="font-size:13px;">
										${event.content}				
									</div>
									<div class="event-content" style="margin:5px;">
										${event.html}
									</div>									
								</div>													
							</div>										
						</div>	
					</tiles:putAttribute>
				</tiles:insertTemplate>
			</div>														
		</div>	
	</tiles:putAttribute>		
</tiles:insertTemplate>	

	