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

<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAqOLU1ZmjoAJhpAUMXWu3fBTTib4Hydy0wq4MxfvJ2obqaysWGBQVQ9uzd6jz0_TA1bCFVmA_ZRr_Ng" type="text/javascript"></script>

<meet:currentUserInSession profileId="${sessionScope.userSessionContext.profileID}" />
<div class="standardSpacing">		
	<div style="float:left;width:630px;">
		<div style="padding-bottom:15px;">
			<script type="text/javascript"><!--
			google_ad_client = "pub-7717112231624442";
			/* 468x60, created 27/06/09 */
			google_ad_slot = "6219966643";
			google_ad_width = 468;
			google_ad_height = 60;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>		
		</div>
		<div style="clear:both;">
			<div>
				<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
					<tiles:putAttribute  name="content">
						<!--<div class="top_header_long" style="height:49px;">
							<a id="${event.googleId}" style="padding:0px 0px 0px 400px;" href="javascript:saveEvent('${event.googleId}','${event.name}','${contextPath}/addToCart.do?itemId=${event.googleId}');" ><img src="images/add_events.png" /></a>								
						</div>-->
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
										<div style="float:left;">
											<a id="${event.googleId}" href="javascript:saveEvent('${event.googleId}','<meet:format escapedScript='true'  value='${event.name}' />','addToCart.do?itemId=${event.googleId}');" >
												<img src="http://images.meetforeal.com/images/add_events.jpg" />
											</a>								
										</div>
									</div>
									<div class="event-content" style="font-size:13px;">
										${event.content}				
									</div>
									<div class="event-content" style="margin:5px;">
										${event.html}
									</div>
									<div class="event-content" style="margin:5px">
<c:set var="ref_update_presenter"  value="updateprofilepresenter.do"/>
<c:if test="${empty userLogged}" >
	<c:set var="ref_update_presenter"  value="javascript:notLoggedPresenter();"/>        
</c:if>

<c:if test="${event.reference == 'stuart_image'}">
	<a  href="${ref_update_presenter}"><img src="images/shoe_my_stuff.png" /></a>
</c:if>	
									</div>																			
							</div>										
						</div>	
					</tiles:putAttribute>
				</tiles:insertTemplate>
			</div>														
		</div>		
		<c:if test="${!empty presenters}">
			<div style="clear:both;">
				<tiles:insertTemplate template="/jsp/events/presenters/presenters.jsp" />	
			</div>	
		</c:if>
	</div>
	<div style="float:left;height:550px;">
		<div style="margin:0px 0px 15px 0px;">	
			<div id="collapsible_user">			
			</div>
		</div>
    
		
        <div style="margin:0px 0px 15px 0px;">
    		<h2 class="main_test" style="color:#008ECF;">What people are saying...</a>
			</h2>
    		<div style="padding:15px 0px 0px 10px;height:125px;">
				<a style="float:left;padding-right:18px;" href="javascript:showVideo('<object classid=\'clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\' codebase=\'http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0\' width=\'540\' height=\'480\' id=\'testimony1updated\' align=\'middle\'><param name=\'allowScriptAccess\' value=\'sameDomain\' /><param name=\'movie\' value=\'videos/testimony1updated.swf\' /><param name=\'quality\' value=\'high\' /><param name=\'bgcolor\' value=\'#ffffff\' /><embed src=\'videos/testimony1updated.swf\' quality=\'high\' bgcolor=\'#ffffff\' width=\'540\' height=\'480\' name=\'testimony1updated\' align=\'middle\' allowScriptAccess=\'sameDomain\' type=\'application/x-shockwave-flash\' pluginspage=\'http://www.macromedia.com/go/getflashplayer\' /></object>')">
					<img  src="http://images.meetforeal.com/images/imagestart1.jpg" />
				</a>
				<a style="float:left;" href="javascript:showVideo('<object classid=\'clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\' codebase=\'http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0\' width=\'540\' height=\'480\' id=\'testimony1updated\' align=\'middle\'><param name=\'allowScriptAccess\' value=\'sameDomain\' /><param name=\'movie\' value=\'videos/testimony1updated.swf\' /><param name=\'quality\' value=\'high\' /><param name=\'bgcolor\' value=\'#ffffff\' /><embed src=\'videos/testimony1updated.swf\' quality=\'high\' bgcolor=\'#ffffff\' width=\'540\' height=\'480\' name=\'testimony1updated\' align=\'middle\' allowScriptAccess=\'sameDomain\' type=\'application/x-shockwave-flash\' pluginspage=\'http://www.macromedia.com/go/getflashplayer\' /></object>')">
					<img  src="http://images.meetforeal.com/images/imagestart2.jpg" />
				</a>
			</div>	
			<div class="testimonies" style="float:left;width:265px;" >"Today's meetforeal event was outstanding - had a really great time"	
			<br><br>
			"Interesting, eventful, insightful and very good fun. <b>Love</b> meeting new people, great networking event"
			<br><br>
			"Love the idea of looking at <b>other</b> people's work as it can be very inspirational"
			<br><br>
			"I would attend another event irrespective of the subject matter"
			<!--
			<br><br>
			"It was the most amazing event ever :). Looking forward to the next one."
			<br><br>
			"Very grateful for the ball! It gave me the opportunity to interact directly with everyone"
			<br><br>
			"Great concept!"
			<br><br>
			"I liked it, intimate, nicely specified"
			<br><br>
			"Thanks for organising the creativity + business event this afternoon. Great talks from <a href="http://www.twitter/AllanCavanagh">@AllanCavanagh</a> & <a href="http://www.twitter/lyndacookson">@lyndacookson</a> 
			<br><br>
			"Excellent speaker, good atmostphere and location"
			<br><br>
			"Very interesting subject, well presented. Interesting format to the event overall."-->
			</div>											 	
		</div>	
		
		    		 	
					
		
		<div style="margin:15px 0px 15px 0px;">
			<h2 class="main_test">
				 <a href="viewpastevents.do" style="color:#008ECF;">Recent Events</a>
				 
			</h2>	
			<a href="http://www.meetforeal.com/displayvideo.do?videoId=22"><img style="padding:10px; width:250px;" src="images/david_jackson.jpg"><a/><br><a style="width:250px;font-size:12px;padding-left:10px;" href="http://www.meetforeal.com/displayvideo.do?videoId=22">Taste & Smell: The Chemical Senses</a>						 	
		</div>					
	</div>	
	
	
</div>

<script language='javascript'>


function notLoggedPresenter() {

    notLoggedPresenterPopUp();
}

function saveEvent(reference, eventName, url) {
	
	saveMyEventFlow(reference,eventName, url);
}

function showMeTheMap(location,latitude, longitude) {
	
	mapPopup(location,latitude, longitude);	
}

function showVideo(video) {
		
	showtheVideo(video);
	
}
</script> 


