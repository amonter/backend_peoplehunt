<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    

<div>
${message}
<div style="margin:10px;">
	<form method="get" action="retrieveuserbyemail.htm" >  
		<span style="display:block">Search User by email</span>
		<input type="text" size="40px" name="email"/>
		<input type="submit" value="Search"/>
	</form>		
</div>
<display:table list="${configItems}" id="items" pagesize="20" requestURI="adminconfig.htm"  class="forumtable">					 
	<display:column>
		<form method="post" action="adminaddupdateconfiguration.htm" >  
			<input type="hidden" size="100px" name="name" value="${items.name}"/><c:out value="${items.name}" />
			<input type="hidden" size="100px" name="pk" value="${items.id}"/>
	</display:column>				
	<display:column>				
			<input type="text" size="100px" name="value" value="${items.value}"/>
	</display:column>				
	<display:column>				
			<input type="submit" value="Update"/>
		</form>
	</display:column>				
	<display:column>				
		<a onclick="return confirmDelete();" href="admindeleteconfig.htm?pk=${items.id}">Delete</a>
	</form>	
	</display:column>					
	<display:caption>
		<form method="post" action="adminaddupdateconfiguration.htm" >       
        	<input type="text" name="name">
            <input type="text" name="value"/>
            <input type="submit" value="Add"/>
       	</form>
	</display:caption>
  </display:table>

<br>

  
<span class = login_error>
	<spring:hasBindErrors name="file">			
		<c:forEach items="${errors.allErrors}" var="error">
			<spring:message message="${error}" />
		</c:forEach>			
	</spring:hasBindErrors>
</span>    
<div style="margin-left:50px;margin-top:30px;" >
	<form>
		<table height="45" class="usersearchbox" border="0" cellspacing="0" cellpadding="0">
			<tr>											
				<td class="alignment">
					Country										
					</td>	
					<td>
						<input type='text' id='country'/>
					</td>
				</tr>		    		
			<tr>											
				<td class="alignment">
					State										
					</td>	
					<td>
						<input type='text' id='state'/>
					</td>
				</tr>	
			
			<tr>
				<td colspan="2">
					<input type='button' onclick="callme(document.getElementById('country'), document.getElementById('state'));"
					value='Search'  style="margin-left:110px;margin-top:15px;" />	
				</td>
			</tr>							
		</table>
	</form>
<div>
<div>
	<form>
 		<table height="45" class="usersearchbox" border="0" cellspacing="0" cellpadding="0">
 				<tr>											
				<td class="alignment">
					Event Name									
	 			</td>	
	 			<td>
	 				<input type='text' id='eventId'/>
	 			</td>
	 		</tr>			
			<tr>
				<td colspan="2">
					<input type='button' onclick="callme2(document.getElementById('eventId'));"
					value='Search'  style="margin-left:110px;margin-top:15px;" />	
				</td>
			</tr>							
		</table>
	</form>
</div>
<div id="firstext" style="margin-left:20px;margin-top:20px;">			
</div>
			
<script language='javascript'>

function callme (country, state) {
	
	crazyMethod(country.value,state.value);
	country.value = '';
	state.value = '';

}

function callme2(eventId) {
	
	searchEventUsers(eventId.value);	
}


function confirmDelete (country, state) {
	
	return confirm('Are you sure you want to delete this.');
	
}


</script> 
</html>