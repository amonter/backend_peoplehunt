<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 

<div style="padding:20px;" width="750px" height="10">
	<p class = "Title">Upload a Profile Photo</p>
</div>
<div style="padding:20px;margin-bottom:30px;">  		
	<form:form method="post" action="uploadFile.do" onsubmit="return setProgressBar('uploading photo');" enctype="multipart/form-data">	
		<div style="float:left;padding-top:5px;">			
			<input type="hidden" name="userProfile" value="${userProfile}">
		  	<input  type="file"  id="input_type"  name="multipartFile"/>				            	        			
			<span class = login_error>
				<spring:hasBindErrors name="file">			
					<c:forEach items="${errors.allErrors}" var="error">
						<spring:message message="${error}" />
					</c:forEach>			
				</spring:hasBindErrors>
				<c:out value= "${fileException}" />				
			</span>	
		</div>
		<div style="float:left;padding-left:10px;">	
			<input name="upload" src="images/upload.jpg" type="image" id="upload" value="Upload!"/>		      				
	    	<input name="cancel" type="image" src="images/cancel.jpg" id="cancel" value="Cancel" />	  
	    </div>
	</form:form>
</div> 

<script language='javascript'>
function setProgressBar (message) {

	setProgress(message);	
	
	return true;
}
</script>
			