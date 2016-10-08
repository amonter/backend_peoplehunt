<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:if test="${fn:length(cart.cartItems) > 0}">
	<div >		
		<div style="margin-left:480px;">
			<div class="update_msg">
				Update any quantities above?
			</div>
			<div>						
				<div style="padding-top:8px;">
					<a href="#" onclick="submitTheForm();"><img src="images/update.jpg"></a>
				</div>				
			</div>			
		</div>
		<div class="subtotal_div">
			<span><b>subtotal&nbsp;=&nbsp;</b></span>&#8364; ${total_price}
			<br><br><br>
		</div>
		<div class="checkout_div">
			<a href="${url_flow_checkout}" target="_top"><img src="images/book_now.jpg" ></a>
		</div>
	</div>
</c:if>
	