<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<c:set var="url_flow_createuser" value="createuser.do"/>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow_createuser" value="${flowExecutionUrl}&_eventId=createaccount"/>	
</c:if>

<c:if test="${!empty booking_message}">
	<div class="standardSpacing">
	    <span class="main_title" style="font-size:30px;color:#66CC66;">
	        To book your selected event. You need to <a href="${url_flow_createuser}">create an account</a> or log in.
	    </span>
	</div>	
</c:if>
<div class="standardSpacing" style="padding:10px 0px 20px 50px;">
Before you go any further, please take a second to log in or create an account:
</div>
<div class="standardSpacing" style="padding-left:50px;">
<div style="float:left;">
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content" value="/jsp/login/loginbox.jsp" />
	</tiles:insertTemplate>
</div>
<div style="margin-left:140px; margin-top:40px;float:left;">
	        <a style="color:#CA090C;" href="${url_flow_createuser}"><img src="http://images.meetforeal.com/images/sign_up.jpg" /></a>
	 
</div>
<div style="margin-left:20px;float:left;">
	<img src="http://images.meetforeal.com/images/itonly.jpg" alt="short_sign_up"/>
</div>
</div>
