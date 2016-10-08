<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>   
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


${message}
<div>
	<a href="#" onclick="loadEventWindow()">show events window</a>
	<a href="http://itouchmap.com/latlong.html">Find google maps point</a>
	<a href="sincevents.htm">Sync all the events</a>
</div>
<display:table list="${events}" id="event"  requestURI="retrievedatabaseevents.htm" class="forumtable">					 
	<display:column  class="alignment" title="name" sortable="true" sortProperty="name">
		<form method="post" action="updateeventprice.htm" >  		    
			<input type="hidden" name="eventId" value="${event.id}"/><c:out value="${event.name}" />			
	</display:column >						
	<display:column title="Date" class="alignment"  sortable="true" sortProperty="startDate">				
			<fmt:formatDate pattern="EEE, d MMM yyyy" value="${event.startDate}" />
	</display:column>
	<display:column title="Location" class="alignment">
			<c:out value="${event.location}" />
	</display:column>
	<display:column title="Price" class="alignment">
			<input type="text" name="price" value="${event.price}"/>
	</display:column>
	<display:column title="Description" class="alignment">
			<textarea cols="30" rows="6" name="description">
				${event.description}
			</textarea>
	</display:column>
	<display:column title="Html" class="alignment">
			<a href="retrieveevent.htm?eventId=${event.id}">Edit Html</a>		
	</display:column>
	<display:column title="Reference" class="alignment">
			<input type="text" name="reference" value="${event.reference}"/>
	</display:column>	
	<display:column title="Latitude" class="alignment">
			<input type="text" name="latitude" value="${event.mapLatitude}"/>
	</display:column>
	<display:column title="Longitude" class="alignment">
			<input type="text" name="longitude" value="${event.mapLongitude}"/>
	</display:column>				
	<display:column class="alignment">				
			<input type="submit" value="Update Event"/>
		</form>
	</display:column>
	<display:column class="alignment">	
		<form method="post" action="insertgadget.htm">
			<input type="hidden" name="eventId" value="${event.googleId}"/>	
			<input type="text" name="shortMessage" value=""/>
			<input type="submit" value="Insert Gadget"/>
		</form>
	</display:column>	
	<display:column class="alignment">	
		<form method="post" action="deleteevent.htm">
			<input type="hidden" name="eventId" value="${event.googleId}"/>			
			<input type="submit" value="Delete Event"/>
		</form>
	</display:column>									
 </display:table>
 
<script language='javascript'>

function loadEventWindow () {

	loadUpcomingEvents();
}

</script> 
 