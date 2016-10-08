<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="url_flow_home" value="logoutuserhome.do"/>
<c:set var="url_flow_cart" value="viewCart.do"/>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow_home" value="${flowExecutionUrl}&_eventId=logout"/>
</c:if>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow_cart" value="${flowExecutionUrl}&_eventId=viewCart"/>
</c:if>

<div style="width:500px;min-height:14px;margin:10px 15px 3px 0px;font-size:90%;color:#666666;text-align:right;" id="main_profile_messages">
	<meet:currentUserInSession profileId="${sessionScope.userSessionContext.profileID}">
		<div style="display:inline;">
			<img style="vertical-align:middle;" src="http://images.meetforeal.com/images/home.jpg" />
			<a href="navigateprofile.do" style="font-weight:bold;" id="logged_in_user_name">
				${sessionScope.userSessionContext.name}
			</a>
			<strong>|</strong>
		</div>
		<div style="display:inline;">		   			
			<a href="navigateprofile.do" style="font-weight:bold;">
				Profile 
			</a>	
			 <strong>|</strong>			
		</div>
		<a href='${url_flow_cart}' id="cartItemsPanel">			
			<span id='itemsNumber'>${fn:length(sessionScope.cart.cartItems)}</span>
			<span>event(s) selected</span>    			 			
		</a>
	</meet:currentUserInSession>	
	  
		
	<meet:currentUserInSession profileId="${sessionScope.userSessionContext.profileID}">
		<div style="display:inline;">
			<c:if test="${sessionScope.userSessionContext.newMessages > 0}">
			    <strong>|</strong>			
				<a href="navigateprofile.do?tabselector=inbox" style="font-weight:bold;"> (${sessionScope.userSessionContext.newMessages}) new messsages</a>				
			</c:if>
		</div>
	</meet:currentUserInSession>	
	<meet:currentUserInSession profileId="${sessionScope.userSessionContext.profileID}">
		<strong>|</strong>
		<a href="${url_flow_home}">log out</a>					
	</meet:currentUserInSession>
	<c:if test="${empty userLogged}">
		<c:if test="${sessionScope.cart != null}">
			<div style="border-right:1px solid #CCCCCC;display:inline;padding:0 7px;" >	
				<a href='viewCart.do' style="text-decoration:underline;color:blue;">		
					<span id='itemsNumber'>${fn:length(sessionScope.cart.cartItems)}</span>
					<span>event(s) selected</span>    
				</a>
			</div>
		</c:if>
		<c:if test="${sessionScope.cart == null}">
			<div style="border-right:1px solid #CCCCCC;padding:0 7px;display:none;" id="events_panel">	
				<a href='viewCart.do' id="cartItemsPanel" style="text-decoration:underline;color:blue;">			
					<span id='itemsNumber'>${fn:length(sessionScope.cart.cartItems)}</span>
					<span>event(s) selected</span>    			 			
				</a>
			</div>	
		</c:if>		
	</c:if>				    
</div>


<div style="width:500px;min-height:14px;margin:10px 15px 3px 0px;font-size:14px;color:#666666;text-align:right;display:none;" id="profile_messages">
		<div style="display:inline;">
			<img style="vertical-align:middle;" src="images/home.jpg" />
			<a href="navigateprofile.do" style="font-weight:bold;" id="logged_in_user_name">
			</a>
			<strong>|</strong>
		</div>
		<div style="display:inline;">		   			
			<a href="navigateprofile.do" style="font-weight:bold;">
				Profile 
			</a>	
			 <strong>|</strong>			
		</div>		
		<a href='viewCart.do' id="cartItemsPanel">			
			<span id='itemsNumber'>${fn:length(sessionScope.cart.cartItems)}</span>
			<span>event(s) selected</span>    			 			
		</a>		
		<strong>|</strong>
		<a href="logoutuserhome.do">log out</a>					
</div>
