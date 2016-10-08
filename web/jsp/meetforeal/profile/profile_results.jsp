<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<script type="text/javascript"  src="http://api.recaptcha.net/js/recaptcha_ajax.js"></script>
<script type="text/javascript" src="js/recaptchaimpl.js"></script> 

<div id ="Outsidelayer">
	<tiles:insertTemplate template="/jsp/parts/regular_content.jsp">
	    <tiles:putAttribute name="title_style" value="main_title"/>				
		<tiles:putAttribute name="sub_title" value="User Search" />	
		<tiles:putAttribute name="the_page">
			<div>
				<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
					<tiles:putAttribute name="content" value="/jsp/profile/search_profile_box.jsp" />
				</tiles:insertTemplate>			
			</div>
			<div>
				<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
					<tiles:putAttribute name="content" value="/jsp/profile/profile_results_table.jsp" />
				</tiles:insertTemplate>
			</div>
		</tiles:putAttribute>		
	</tiles:insertTemplate>			 				
</div>
		
<script language='javascript'>

function sendtheMessage(profileId,name) {

	sendMessage(profileId,name);
	
}

function notLoggedIn() {

	notLoggedPopup();
}

</script>
