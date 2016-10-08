<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


			
<!-- 
<div class="standardSpacing">
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content">
			<img src="images/image_events.jpg">
		</tiles:putAttribute>
	</tiles:insertTemplate>	
</div>
-->

<div>			
	<tiles:insertAttribute name="eventList-tile" />
</div>


<div class="standardSpacing">
	<iframe src="//www.google.com/calendar/embed?showTitle=0&amp;height=600&amp;wkst=1&amp;bgcolor=%23FFFFFF&amp;src=r975a1bppid43vhdi5gemn75dg%40group.calendar.google.com&amp;color=%237A367A&amp;ctz=Europe%2FDublin" style=" border-width:0 " width="625" height="500" frameborder="0" scrolling="no">
	</iframe>
</div>			
	

<script language='javascript'>

function callme (country, state) {
	alert(country.value);
	crazyMethod(country.value,state.value);
	country.value = '';
	state.value = '';

}

function callNewThread (param, param2) {
	
	popupNewThread(param,param2);
}

function notLoggedIn() {

	notLoggedPopup();
}

function loadMailInbox () {

	loadUserInbox();
}

function loadEmailSender(id,date,name,email) {	
	loadEmailSenderBox(id,date,name,email);
}



</script> 