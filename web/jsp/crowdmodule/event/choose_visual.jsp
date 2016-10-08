<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 


<div class = "placing-1 events" >
<h1 id="titleOfCreate">Step 3 of 4: Choose Visualisation for ${event.description}</h1>
<h2 class = "form-extra-details" style="margin-left:0px;text-align:center;margin-bottom:20px;">To be displayed on screens around your event. It is also accessible via the browser for users without the iPhone or iPad app:</h2>
	<div style="margin-left:100px;">
		<c:if test="${path == 'dashboard'}">
                   <c:url value="/crowdmodule/event/update_visual_dash" var="update_visual_url"/>
           	</c:if>
           	<c:if test="${path == 'flow'}">
                   <c:url value="/crowdmodule/event/update_visual" var="update_visual_url"/>
        	   </c:if>
		<form:form action="${update_visual_url}">
  		<form:hidden path="eventId"/>	
  
  		<c:forEach items="${visuals}" var="visual">
		<div style="float:left;margin-top:100px;">
  			<form:radiobutton  path="visualId" value="${visual.visualId}"/>
		</div>
                  	 
		<div style="margin:20px 0 0 40px;;">${visual.description}:</div>
		<div style="padding:20px 0px 20px 40px;">
			<img src ="${visual.image}" style="height:200px;">
 		</div>
  	</c:forEach>
	</div> 
	<div class = "form-extra-details" style="margin-right:20px;float:right;">
		<input class = "form-boxes-buttons"  type="submit" value="Submit"/>
		</form:form>
	</div>
</div>
