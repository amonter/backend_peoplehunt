<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>

<c:if test="${fn:length(cart.cartItems) eq 0}">
	<span style="padding-left:10px;font-size:18px;">
		<spring:message code="message.emptybasket" />&nbsp;<a style="color:#EF1A08;" href="browseevents.do" target="_top">View Upcoming Events</a>			
	</span>
</c:if>

<form:form name="theform" commandName="cart" action="${url_flow_update}" method="POST">
	<form:hidden path="id"/>
	<form:hidden path="profile.id"/>
	<div class="top_header_long" style="height:47px;">
		<span id="niceFont" style="color:#008ECF;font-size:21pt;padding:4px 0px 0px 4px;"><b>Your Event Basket<b></span>
	</div>	
	<div class="event-main-wrap" style="width:800px;">	
		<c:forEach items="${cart.cartItems}" var="cartItem" varStatus="iter">
			<fmt:formatDate var="event_date" pattern="yyyy-MM-dd" value="${cartItem.event.startDate}" />
			<form:hidden path="cartItems[${iter.index}].event.googleId"/>
			<form:hidden path="cartItems[${iter.index}].id"/>
			<form:hidden path="cartItems[${iter.index}].event.price"/>												
			<div class="event-main"  style="padding-top:10px;">
				<div class="event-date">
					<h4><fmt:formatDate pattern="MMM" value="${cartItem.event.startDate}" /><br/><fmt:formatDate pattern="dd" value="${cartItem.event.startDate}" />
					</h4>
				</div>
				<div class="event-header" style="margin-right:320px;">
					<a  target='_top' href="retrieveevent.do?eid=${cartItem.event.id}">${cartItem.event.name}</a><br/>
					<div class="ev_flat_loc" style="">
						<a  style="font-size:13px;" href="javascript:showMeTheMap('<meet:format escapedScript='true'  value='${cartItem.event.location}' />','${cartItem.event.mapLatitude}','${cartItem.event.mapLongitude}');" ><fmt:formatDate pattern="HH:mm" value="${cartItem.event.startDate}" timeZone="Europe/Dublin"/>&nbsp;hrs,&nbsp;${cartItem.event.location}</a>
					</div>
				</div>
				<div>
					<div>
						Price:&nbsp;&#8364; ${cartItem.event.price}
					</div>
					<div style="padding-top:4px;">
						 Qty:&nbsp; <form:input  cssStyle="width:50px;" path="cartItems[${iter.index}].quantity" />
					 </div>
				</div>
				<div class="event-content" style="font-size:16px;">
					${cartItem.event.content}				
				</div>
				<div class="event-info">						
					<div class="event-info-details">
						<a id="${cartItem.event.googleId}" href="#" onclick="loadEmailSender('${cartItem.event.googleId}','${event_date}','${cartItem.event.name}','${email_sender}')"> Send Invitation </a><br>						
					</div>					
					<a href="${url_flow_delete}eventId=${cartItem.event.googleId}&cartItemId=${cartItem.id}"><img src="images/delete.jpg"></a>								
				</div>				
			</div>
			<c:set var="total_price" value="${total_price + (cartItem.event.price) * cartItem.quantity}" scope="request" />					
		</c:forEach>		
	</div>		
	<input type="hidden" name="total" id="total" value="${total_price}" />	
</form:form>

<script language='javascript'>

function showMeTheMap(location,latitude, longitude) {
	
	mapPopup(location,latitude, longitude);	
}

</script> 