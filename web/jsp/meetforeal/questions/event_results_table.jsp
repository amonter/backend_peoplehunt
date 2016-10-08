<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="meet" uri="meetforealtags" %>


<div style="margin:10px;">
    <c:forEach items="${events}" var="event" varStatus="iter">
        <fmt:formatDate var="event_date" pattern="yyyy-MM-dd" value="${cartItem.event.startDate}" />
        <div style="padding-bottom:10px;font-size:18px;" >
        	<b>${iter.index + 1}</b>
        </div>	
        <div class="event-main" style="padding-top:10px;"> 
        	  
            <div class="event-date">
                <h4><fmt:formatDate pattern="MMM" value="${event.startDate}" /><br/><fmt:formatDate pattern="dd" value="${event.startDate}" />
                </h4>
            </div>           
            <div class="event-header" style="margin-right:115px;">
                <a href="retrieveevent.do?eid=${event.id}&date=${event_date}">${event.name}</a><br/>
                <div class="ev_flat_loc" style="">
                    <a  style="font-size:13px;" href="javascript:showMeTheMap('<meet:format escapedScript='true'  value='${event.location}' />','${event.mapLatitude}','${evenjt.mapLongitude}');" ><fmt:formatDate pattern="HH:mm" value="${event.startDate}" timeZone="Europe/Dublin"/>&nbsp;hrs,&nbsp;${event.location}</a>
                </div>
            </div>
            <div style="float:left;">
                <div style="float:left;margin-top:5px;">
                    <a id="${event.googleId}" href="javascript:saveEvent('${event.googleId}','${event.name}','${contextPath}/addToCart.do?itemId=${event.googleId}');"><img src="images/save_mycart.jpg" /></a>
                </div>
                <div style="float:left;margin:12px 3px 0px 3px;">
                    or
                </div>
                <div style="float:left;margin-top:5px;">
                    <a href="booknow.do?eventId=${event.googleId}"><img src="images/book_now.jpg"></a>
                </div>
            </div>
            <div class="event-content" style="font-size:12px;width: 895px;">
                ${event.content}               
            </div>
            <div class="event-info">                       
                <div class="event-info-details">
                    <a id="${event.googleId}" href="#" onclick="loadEmailSender('${event.googleId}','${event_date}','${event.name}','${email_sender}')"> Send Invitation </a><br>                       
                </div>                   
            </div>
        </div>       
    </c:forEach>
</div>


<script language='javascript'>

function showMeTheMap(location,latitude, longitude) {
	
	mapPopup(location,latitude, longitude);	
}

function saveEvent(reference, eventName, url) {
	
	saveMyEventFlow(reference,eventName, url);
}

</script> 
