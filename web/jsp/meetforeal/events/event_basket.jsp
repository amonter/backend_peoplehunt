<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href="css/index.css" rel="stylesheet" type="text/css" />
<script language='javascript' src='/myownmotivator/tabs/com.myownmotivator.GWT3.nocache.js'></script>
<c:set var="url_flow_update" value="updateCartItemInnerTab.do"/>
<c:set var="url_flow_delete" value="deleteCartItemInnerTab.do?"/>
<c:set var="url_flow_checkout" value="checkoutflow.do"/>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow_update" value="${flowExecutionUrl}&_eventId=updateItems"/>
	<c:set var="url_flow_delete" value="${flowExecutionUrl}&_eventId=deleteBasketItem&"/>
	<c:set var="url_flow_checkout" value="${flowExecutionUrl}&_eventId=checkout"/>	
</c:if>

<c:if test="${fn:length(cart.cartItems) eq 0}">
		<span style="padding-left:10px;font-size:18px;">
			<spring:message code="message.emptybasket" />&nbsp;<a style="color:#EF1A08;" href="browseevents.do" target="_top">View Upcoming Events</a>
			
		</span>
</c:if>
aloha
<form:form name="theform" commandName="cart" action="${url_flow_update}" method="POST">
	<form:hidden path="id"/>
	<form:hidden path="profile.id"/>
	<div class="event-main-wrap">	
		<c:forEach items="${cart.cartItems}" var="cartItem" varStatus="iter">
			<fmt:formatDate var="event_date" pattern="yyyy-MM-dd" value="${cartItem.event.startDate}" />
			<form:hidden path="cartItems[${iter.index}].event.googleId"/>
			<form:hidden path="cartItems[${iter.index}].id"/>
			<form:hidden path="cartItems[${iter.index}].event.price"/>												
			<div class="event-main">
				<div class="event-date">
					<h4><fmt:formatDate pattern="MMM" value="${cartItem.event.startDate}" /><br/><fmt:formatDate pattern="dd" value="${cartItem.event.startDate}" />
					</h4>
				</div>
				<div class="event-header" style="margin-right:320px;">
					<a href="retrieveevent.do?eid=${cartItem.event.id}">${cartItem.event.name}</a><br/>
					<fmt:formatDate pattern="hh:mm aa" value="${cartItem.event.startDate}" timeZone="Europe/Dublin" />, ${cartItem.event.location}
				</div>
				<div>
					<div>
						Price:&nbsp; ${cartItem.event.price}
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
					<a href="${url_flow_delete}eventId=${cartItem.event.googleId}&cartItemId=${cartItem.id}"><img src="images/delete_btn.png"></a>								
				</div>				
			</div>
			<c:set var="total_price" value="${total_price + (cartItem.event.price) * cartItem.quantity}" />					
		</c:forEach>		
	</div>		
	<input type="hidden" name="total" id="total" value="${total_price}" />	
</form:form>



<c:if test="${fn:length(cart.cartItems) > 0}">
	<div class="event-main-wrap" style="margin-bottom:40px;">
		<div class="checkout_div">
			<a href="checkoutflow.do" target="_top"><img src="images/checkout_btn.png" width="110px" height="35px"></a>
		</div>
		<div>
			<div class="update_msg">
				Update any quantities above?
			</div>
			<div>						
				<div>
					<a href="#" onclick="submitTheForm();"><img src="images/update_grey_btn.png" width="110px" height="35px"></a>
				</div>				
			</div>			
		</div>
		<div class="subtotal_div">
			<span><b>subtotal&nbsp;=&nbsp;</b></span>${total_price}
			<br><br><br>
		</div>
	</div>
</c:if>

<script language='javascript'>


function loadEmailSender(id,date,name,email) {	
	loadEmailSenderBox(id,date,name,email);
}

function submitTheForm () {	
	document.theform.submit();		
}


</script> 