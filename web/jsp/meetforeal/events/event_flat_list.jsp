<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>


<div class="top_header" style="width:639px;margin-left:5px;">
	<img src="http://images.meetforeal.com/images/whatsondarkgrey.png" />
	<script>function fbs_click() {u=location.href;t=document.title;window.open('http://www.facebook.com/sharer.php?u='+encodeURIComponent(u)+'&t='+encodeURIComponent(t),'sharer','toolbar=0,status=0,width=626,height=436');return false;}</script>
	<span style="margin:0px 0px 0px 67px;">
		<a href="http://www.twitter.com/meetforeal">
			<img src="http://images.meetforeal.com/images/TwitterIcon.jpg" width="30px" height="30px" />
		</a>
		<a href="http://www.facebook.com/share.php?u=http://www.meetforeal.com" onclick="return fbs_click()" target="_blank">
			<img  src="http://images.meetforeal.com/images/FacebookIcon.jpg" alt="" width="30px" height="30px"/>
		</a>
		<a href="createeventfeeds.do">
			<img src="http://images.meetforeal.com/images/rssfeeds.jpg" width="30px" height="30px" />
		</a>
	</span>
</div>
<table class="event_flat" width="638px" style="margin:0px;">	
	<c:forEach items="${events}" var="event">
		<tr>
			<td valign="middle">
			    <div style="float:left;margin:8px;">
					<a href="retrieveevent.do?eid=${event.id}"><img src="http://images.meetforeal.com/images/${event.reference}.jpg" /></a>																					
				</div> 
				<div style="float:left;padding-top:4px;max-width:364px;" >					
					<span class="span_date">
						<span class="span_day"><b><fmt:formatDate pattern="EEE" value="${event.startDate}" /></b></span>
						<span class="span_month">
							<span style="padding:0px 7px 0px 7px;">
								<fmt:formatDate pattern="MMM dd" value="${event.startDate}" /></span>																				
							</span>											
						</span>							
					</span>
					<div class="ev_flat_content">						
						<div style="font-family:'Trebuchet MS',Helvetica,Helvetica,Arial,sans-serif;">
							<a class="ev_flat_title"  href="retrieveevent.do?eid=${event.id}">${event.name}</a>
						</div>					
						<div class="ev_flat_loc" style="line-height:105%;padding-top:4px;" >
							<fmt:formatDate pattern="HH:mm" value="${event.startDate}" timeZone="Europe/Dublin" />&nbsp;hrs,&nbsp;${event.location}
						</div>
					</div>					
				</div>									
			</td>
		</tr>
	</c:forEach>				
</table>
<div style="padding:3px 0px 3px 0px;">
	<a href="browseevents.do" style="display:inline;width:5px;font-size:13px;padding-left:6px;">							
		<b>See More..<b>
	</a>	
</div>
<script language='javascript'>

function showMeTheMap(location,latitude, longitude) {
	
	mapPopup(location,latitude, longitude);	
}

</script>
