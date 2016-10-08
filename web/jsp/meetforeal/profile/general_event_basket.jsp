<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<link href="css/index.css" rel="stylesheet" type="text/css" />
<script language='javascript' src='${pageContext.request.contextPath}/tabs/com.myownmotivator.GWT3/com.myownmotivator.GWT3.nocache.js'></script>
	
<c:set var="url_flow_update" value="updateCartItemInnerTab.do" scope="request"/>
<c:set var="url_flow_delete" value="deleteCartItemInnerTab.do?" scope="request"/>
<c:set var="url_flow_checkout" value="checkoutflow.do" scope="request"/>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow_update" value="${flowExecutionUrl}&_eventId=updateItems" scope="request"/>
	<c:set var="url_flow_delete" value="${flowExecutionUrl}&_eventId=deleteBasketItem&" scope="request"/>
	<c:set var="url_flow_checkout" value="${flowExecutionUrl}&_eventId=checkout" scope="request"/>	
</c:if>


<div class="standardSpacing">
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content" value="/jsp/events/basket/event_basket_table.jsp" />
	</tiles:insertTemplate>	
</div>
<div class="standardSpacing" style="width:900px;">
	<tiles:insertAttribute name="event_footer" />
</div>

<script language='javascript'>


function loadEmailSender(id,date,name,email) {	
	loadEmailSenderBox(id,date,name,email);
}

function submitTheForm () {	
	document.theform.submit();		
}


</script> 