<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div class="placing-1 events">
	<h1 id = "titleOfCreate">Sign Up</h1>
 <c:set var="registerUrl" value="/crowdmodule/auth/registerattendee"/>
 
<form:form name="register" commandName="user" action="${registerUrl}" method="POST" >
       <!-- <form:errors path="*" />--> 
	<input type="hidden" id="avatarImage" name="avatarImage" value="${profileImage}"/>
	<div><img style="width:180px;margin:20px 0px 20px 20px;" src="${profileImageDisplay}"/></div>
	<div class="form">Full Name:</div> 
	<div class="form-boxes-padding">
		<form:input path="lastName" class="form-boxes"  size="45"/> <br/><span style="color:#FF0066; "><form:errors path="lastName"/></span>
	</div> 
	<div class="form">Email:</div> 
	<div class="form-boxes-padding">
		<form:input path="email" class="form-boxes" size="45"/> <br/><span style="color:#FF0066; "><form:errors path="email"/></span>
	</div> 
	<div class="form">Create Password:
	</div>
	<div class="form-boxes-padding">
		<form:password path="newPassword" class="form-boxes" size="45"/> <br/><span style="color:#FF0066; "><form:errors path="newPassword"/></span> 
	</div> 	
	<div class="form-extra-details">
		<a><input type="submit" class="form-boxes-buttons" value="Submit"/></a> 
 	</div> 
 
</form:form> 
 
</div> 


