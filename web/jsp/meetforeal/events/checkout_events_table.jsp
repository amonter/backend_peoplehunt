<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div style="width:620px;">
	<div class="top_header_long" style="height:50px;">
		<span id="niceFont" style="color:#008ECF;font-size:21pt;padding:8px 0px 8px 8px;"><b>Book Your Place - Payment</b></span>
	</div>
		<c:forEach items="${processed_order.cart.cartItems}" var="cartItem" varStatus="iter">
				<form:hidden path="cart.cartItems[${iter.index}].id"/>
				<fmt:formatDate var="event_date" pattern="yyyy-MM-dd" value="${cartItem.event.startDate}" />									
				<div class="event-main"  style="padding-top:10px;">
					<div class="event-date">
						<h4><fmt:formatDate pattern="MMM" value="${cartItem.event.startDate}" /><br/><fmt:formatDate pattern="dd" value="${cartItem.event.startDate}" />
						</h4>
					</div>
					<div class="event-header">
						<a href="retrieveevent.do?eid=${cartItem.event.id}">${cartItem.event.name}</a><br/>
						<fmt:formatDate pattern="hh:mm aa" value="${cartItem.event.startDate}" timeZone="Europe/Dublin" />, ${cartItem.event.location}
					</div>
					<div>
						<div>
							<nobr>Price:&nbsp;&#8364; ${cartItem.event.price}</nobr>
						</div>
						<div style="padding-top:4px;">
							 Qty:&nbsp; <form:input   maxlength="3" size="2" path="cart.cartItems[${iter.index}].quantity" />
						 </div>
					</div>
					<div class="event-content">
						${cartItem.event.content}	 								
					</div>												
				</div>				
				<c:set var="total_price" value="${total_price + (cartItem.event.price) * cartItem.quantity}" />				
			</c:forEach>
		</div>	
	<div style="padding-left:5px;">
		Total Price:&nbsp;<b>&#8364; ${total_price}</b>
	</div>
</div>