<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>



<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAqOLU1ZmjoAJhpAUMXWu3fBTTib4Hydy0wq4MxfvJ2obqaysWGBQVQ9uzd6jz0_TA1bCFVmA_ZRr_Ng" type="text/javascript"></script>
			
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
