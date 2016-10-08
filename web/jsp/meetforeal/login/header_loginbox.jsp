
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<c:set var="url_flow_home" value="index.do"/>
<c:set var="url_flow_profile" value="navigateprofile.do"/>
<c:set var="url_flow_forum" value="browseforums.do"/>
<c:set var="url_flow_browseevents" value="browseevents.do"/>
<c:set var="url_flow_blog" value="retrieveblog.do"/>
<c:set var="url_flow_video" value="listvideos.do"/>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow_home" value="${flowExecutionUrl}&_eventId=home"/>
	<c:set var="url_flow_profile" value="${flowExecutionUrl}&_eventId=profile"/>
	<c:set var="url_flow_forum" value="${flowExecutionUrl}&_eventId=forum"/>
	<c:set var="url_flow_browseevents" value="${flowExecutionUrl}&_eventId=events"/>
	<c:set var="url_flow_blog" value="${flowExecutionUrl}&_eventId=blog"/>
	<c:set var="url_flow_video" value="${flowExecutionUrl}&_eventId=video"/>
</c:if>

<meet:currentUserInSession profileId="${user.profile.id}" />
<tiles:importAttribute name="tab_selector"/>
<meet:tabSelector value="${tab_selector}"/>
<div >
	<div style="width:250px;z-index:2;position:relative;left:20px;float:left;height:150px;">	   
		<a href="${url_flow_home}"><img src="http://images.meetforeal.com/images/logo_mfr.jpg" /></a>
	</div>
	<div style="float:left;width:250px;">	
		<div class="tabPosition">	
			<div class="outerTab">	
											
			</div>
		</div>
	</div>	
</div>
<c:if test="${hometab eq 'selectedtab'}" >
	<div style="padding:0px 0px 0px 20px;">
		<a href="viewpageaboutus.do"><img src="http://images.meetforeal.com/images/mfr_banner.jpg" /></a>
	</div>
</c:if>


<script language='javascript'>

function activatePlaceholders() {

	var inputs = document.getElementById("input_type_log");	
	if (inputs != null) {		
			
		inputs.setAttribute("class", "input_box_header input_placeholder");
		inputs.setAttribute("className", "input_box_header input_placeholder");			
		inputs.value = inputs.getAttribute("title");				
		inputs.onclick = function() {		
			
			if (this.value == this.getAttribute("title")) {
				
	 			this.value = "";
	  			this.setAttribute("className", "input_box_header");
	  			this.setAttribute("class", "input_box_header");
	  			var theId = this.getAttribute("id");  						  			
			}
		return false;
		}
		inputs.onblur = function() {
			
		 if (this.value.length < 1) {
			 	
			 	this.setAttribute("className", "input_box_header input_placeholder");
			 	this.setAttribute("class", "input_box_header input_placeholder");						 
		  		this.value = this.getAttribute("title");
		  		var theId = this.getAttribute("id");
		  		
			}
		}
	}		
}

function switchto(q){
  
  if (q){
	  	
  	document.getElementById('passwordtext').style.display ="none";
    document.getElementById('password').style.display = "inline";
    document.getElementById('password').focus();
    
   } else {
	   
       document.getElementById('password').style.display ="none";
       document.getElementById('passwordtext').style.display ="inline";
   }
}



</script> 

