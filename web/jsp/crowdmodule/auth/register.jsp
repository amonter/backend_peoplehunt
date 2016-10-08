<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>


<script type="text/javascript">
 var RecaptchaOptions = {
    theme : 'clean'
 };
</script>
<div class="placing-1 events">
	<h1 id = "titleOfCreate">Sign Up</h1>
 <c:set var="registerUrl" value="/crowdmodule/auth/register"/>
 
<form:form name="register" commandName="registerCmd" action="${registerUrl}" method="POST" > 
	<div class="form">Email:</div> 
	<div class="form-boxes-padding" >
		<form:input path="user.email" class="form-boxes" size="35"/> 
		<br/><span style="color:#FF0066; "><form:errors path="user.email"/></span>
	</div> 

	<div class="form">First Name:</div> 
	<div class="form-boxes-padding">
		<form:input path="user.firstName" class="form-boxes" size="35"/> 	
	<br/><span style="color:#FF0066; "><form:errors path="user.firstName"/></span>
	</div> 
	<div class="form">Last Name:</div> 
	<div class="form-boxes-padding">
		<form:input path="user.lastName" class="form-boxes"  size="35"/>
		<br/><span style="color:#FF0066; "><form:errors path="user.lastName"/></span>
	</div> 
	<div class="form">Password:
	</div>
	<div class="form-boxes-padding">
		<form:password path="user.newPassword" class="form-boxes" size="35"/> 		<br/><span style="color:#FF0066; "><form:errors path="user.newPassword"/></span> 
	</div> 
	<div class="form-extra-details"> at least 6 characters, including a number/special character</div> 
	<div class="form">Confirm Password:</div> 
	<div class="form-boxes-padding">
		<form:password path="user.newPasswordConfirm" class="form-boxes" size="35"/> 		<br/><span style="color:#FF0066; "><form:errors path="user.newPasswordConfirm"/></span>
	</div>
	<div class="form"></div>
	<div class="form-boxes-padding" style="padding:40px 0px 0px 0px;">
		<%
          	ReCaptcha c = ReCaptchaFactory.newReCaptcha("6Ldo5MESAAAAAIeeNE7Gt9nBvp-jGaR1SIYMwRSH ", "6Ldo5MESAAAAADUD-Sl0zXcR8w_KsqDUAfmpGl_o", false);
          	out.print(c.createRecaptchaHtml(null, null));
        %>
			<br/><span style="color:#FF0066; "><form:errors path="user.captcha"/></span>	
	</div>
	<!--	 
 	<div class="form">Expires on:</div>
	<div class="form-boxes-padding">
		<form:input path="cardDetails.number" class="form-boxes" size="35"/> <form:errors path="cardDetails.number"/>
	</div> 
 	<div class="form-extra-details">mm/yy</div> 
	<div class="form">Card Number:</div> 
	<div class="form-boxes-padding">
		<form:input path="cardDetails.expiryDate" class="form-boxes" size="35"/> <form:errors path="cardDetails.expiryDate"/>
	</div> 
 	<div class="form">Card Type:</div> 
	<div class="form-boxes-padding">
		<form:input path="cardDetails.type" class="form-boxes" size="35"/> <form:errors path="cardDetails.type"/>
	</div> 
	<div class="form">CVN:</div> 
	<div class="form-boxes-padding">
		<form:password path="cardDetails.cvn" class="form-boxes"  size="35"/> <form:errors path="cardDetails.cvn"/>
	</div> 
	<div class="form-extra-details">if it has one</div>
        --> 
<div class="form-extra-details">
		<a><input type="submit" class="form-boxes-buttons" value="Submit"/></a> 
 	</div> 
 
</form:form> 
 
</div> 

