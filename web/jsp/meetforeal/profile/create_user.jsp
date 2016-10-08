<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="java.util.*" %>


<div class="standardSpacing">
	<div id="progress_bar"></div>
</div>
<div class="standardSpacing" style="margin-top: 35px;">
	<img src="http://images.meetforeal.com/images/sign_up_onepage.jpg"/>
</div>
<div style="margin:20px 5px 5px 20px;"> 
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content" value="/jsp/profile/create_user_content.jsp" />
	</tiles:insertTemplate>	
</div>	
<script type="text/javascript">

function hideSelect(name) {
	
	var selectedCountry = name.options[name.selectedIndex].value;
	if (selectedCountry != 'Ireland') {
		
		document.getElementById("countryStates").style.display = 'none';
		document.getElementById("input_type_state").options[0].value = '';		
						
	}
	else if (selectedCountry == 'Ireland') {
	
		document.getElementById("countryStates").style.display = '';	
	}		
} 

function openProgress() {
     
	setProgressPanel('');
	return true;	
}


function setState () {
	
		
}
  
</script>
 
