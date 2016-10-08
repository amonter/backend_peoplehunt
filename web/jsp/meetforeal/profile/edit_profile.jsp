 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div class="standardSpacing">
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content" value="/jsp/profile/edit_profile_table.jsp" />
	</tiles:insertTemplate>
</div>
				
<script type="text/javascript">


function hideSelect(name) {
	
	var selectedCountry = name.options[name.selectedIndex].value;
	if (selectedCountry != 'Ireland') {
		
		document.getElementById("countryStates").style.display = 'none';				
	}
	else if (selectedCountry == 'Ireland') {
		
		
		document.getElementById("countryStates").style.display = '';	
	}		
}  		
  
</script>			