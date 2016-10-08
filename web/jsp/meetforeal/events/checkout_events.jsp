<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%
	pageContext.setAttribute("orderId" , System.currentTimeMillis()+"");
%>


<div class="standardSpacing"> 
	<form:form commandName="processed_order" action="reset_orderItems.do" method="post">
		<form:hidden path="cart.profile.id"/>		
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute name="content" value="/jsp/events/checkout_events_table.jsp" />
		</tiles:insertTemplate>	
		<div style="margin: 20px 0px 40px 270px;">
			<div class="update_msg">
				Update any quantities above?
			</div>
			<div>						
				<div style="padding-top:8px;">
					<input type="image" src="images/update.jpg" />
				</div>				
			</div>			
		</div>
	</form:form>
	<form action="https://www.paypal.com/cgi-bin/webscr"  name="paypal_form" method="post">
		<input type="hidden" name="cmd" value="_cart">
			<input type="hidden" name="upload" value="1">
		<input type="hidden" name="business" value="adrian@meetforeal.com">
		<input type="hidden" name="first_name" value="${processed_order.cart.profile.user.firstName}">
		<input type="hidden" name="last_name" value="${processed_order.cart.profile.user.lastName}">
		<input type="hidden" name="cn" value="${processed_order.cart.profile.user.userName}">
		<input type="hidden" name="invoice" value="${orderId}">					
		<c:forEach items="${processed_order.cart.cartItems}" var="cartItem" varStatus="loop">		
			<input type="hidden" name="item_name_${loop.index + 1}" value="${cartItem.event.name}">
			<input type="hidden" name="item_number_${loop.index + 1}" value="${cartItem.id}">
			<input type="hidden" name="amount_${loop.index + 1}" value="${cartItem.event.price}">
			<input type="hidden" name="quantity_${loop.index + 1}" value="${cartItem.quantity}">				
		</c:forEach>
		<input type="hidden" name="no_shipping" value="0">
		<input type="hidden" name="no_note" value="1">
		<input type="hidden" name="currency_code" value="EUR">
		<input type="hidden" name="lc" value="IE">
		<input type="hidden" name="bn" value="PP-BuyNowBF">	
	</form>
</div>

<!-- 


<div class="standardSpacing"> 
	<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" name="paypal_form" method="post">
		<input type="hidden" name="cmd" value="_cart">
		<input type="hidden" name="upload" value="1">
		<input type="hidden" name="business" value="adrian_1231414447_biz@gmail.com">
		<input type="hidden" name="first_name" value="${processed_order.cart.profile.user.firstName}">
		<input type="hidden" name="last_name" value="${processed_order.cart.profile.user.lastName}">
		<input type="hidden" name="cn" value="${processed_order.cart.profile.user.userName}">
		<input type="hidden" name="invoice" value="${orderId}">					
		<c:forEach items="${processed_order.cart.cartItems}" var="cartItem" varStatus="loop">		
			<input type="hidden" name="item_name_${loop.index + 1}" value="${cartItem.event.name}">
			<input type="hidden" name="item_number_${loop.index + 1}" value="${cartItem.id}">
			<input type="hidden" name="amount_${loop.index + 1}" value="${cartItem.event.price}">
			<input type="hidden" name="quantity_${loop.index + 1}" value="${cartItem.quantity}">				
		</c:forEach>
		<input type="hidden" name="no_shipping" value="0">
		<input type="hidden" name="no_note" value="1">
		<input type="hidden" name="currency_code" value="EUR">
		<input type="hidden" name="lc" value="IE">
		<input type="hidden" name="bn" value="PP-BuyNowBF">	
	</form>
</div>
  -->

<div style="margin-left:400px;">
	<div id="the_anchor">
		<a href="javascript:saveTheOrder(${processed_order.cart.id},'${orderId}', 'the_anchor')">
			<img src="https://www.paypal.com/en_US/i/btn/btn_buynowCC_LG.gif" border="0"  alt="">
		</a>
		<span style="display:block;font-size:90%;">(You don't need a PayPal account, you can pay with your credit card)</span>
		</a>
	</div>
</div>



<script language='javascript'>

function saveTheOrder(cartId, orderId, anchor) {	
	
	saveOrder(cartId, orderId, anchor);		
}

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}

</script> 


