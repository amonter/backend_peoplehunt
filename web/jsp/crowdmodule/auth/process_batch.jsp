<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<div class="placing-1 events">
	<h1 id = "titleOfCreate">Payment Details</h1>
	<h2 style="padding-top:5px;text-align: center">Just fill in your details and we'll ship them to you!</h2>
 <c:set var="registerUrl" value="/crowdmodule/auth/placebatchorder"/>
<div id="leftcol" style="padding-right:20px;float:left;"> 
<form:form name="register" commandName="registerCmd" action="${registerUrl}" method="POST" > 
	<form:errors path="*"/>
	<input type="hidden" name="user.newPassword" value="password"/> 
	<input type="hidden" name="user.newPasswordConfirm" value="password"/>
	<input type="hidden" name="cardDetails.expiryDate" value=""/>
	
	<div class="form">Email:</div> 
	<div class="form-boxes-padding" >
		<form:input path="user.email" class="form-boxes" size="35"/> 
		<br/><span style="color:#FF0066; "><form:errors path="user.email"/></span>
	</div>
	<div style="font-size:12px;margin:-35px 0px 0px 680px;">So we can contact you- we will never share your details</div> 


	<div class="form" style="text-align:center;">Shipping Address</div> 
	<div class="form">Street Address:</div> 
	<div class="form-boxes-padding">
		<form:input path="cardDetails.streetAddress" class="form-boxes"  size="25"/>
		<br/><span style="color:#FF0066; "><form:errors path="cardDetails.streetAddress"/></span>
	</div>	
	<div style="font-size:12px;margin:-35px 0px 20px 680px;">This is where we will ship your button badges</div>		
	<div class="form" style="height:35px;"> </div> 
	<div class="form-boxes-padding">
		<form:input path="cardDetails.streetAddressTwo" class="form-boxes"  size="25"/>
		<br/><span style="color:#FF0066; "><form:errors path="cardDetails.streetAddressTwo"/></span>
	</div>	
	<div class="form">Town/City: </div> 
	<div class="form-boxes-padding">
		<form:input path="cardDetails.city" class="form-boxes"  size="25"/>
		<br/><span style="color:#FF0066; "><form:errors path="cardDetails.city"/></span>
	</div>
	<div class="form">Post/Zip Code: </div> 
	<div class="form-boxes-padding">
		<form:input path="cardDetails.zipCode" class="form-boxes"  size="25"/>
		<br/><span style="color:#FF0066; "><form:errors path="cardDetails.zipCode"/></span>
	</div>	
	<div class="form">Country: </div> 
	<div class="form-boxes-padding">
		<form:input path="cardDetails.country" class="form-boxes"  size="25"/>
		<br/><span style="color:#FF0066; "><form:errors path="cardDetails.country"/>
		</span>
	</div>
	<div class="form" style="text-align:center;">Cardholder Details</div> 
	
	<div class="form">First Name:</div> 
	<div class="form-boxes-padding">
		<form:input path="user.firstName" class="form-boxes" size="25"/> 	
	<br/><span style="color:#FF0066; "><form:errors path="user.firstName"/></span>
	</div> 
	<div class="form">Last Name:</div> 
	<div class="form-boxes-padding">
		<form:input path="user.lastName" class="form-boxes"  size="25"/>
		<br/><span style="color:#FF0066; "><form:errors path="user.lastName"/></span>
	</div>	
        <div class="form">Card Number:</div> 
	<div class="form-boxes-padding">
		<form:input path="cardDetails.number" class="form-boxes" size="25"/> 
		 <br/><span style="color:#FF0066;"><form:errors path="cardDetails.number"/></span>
	</div> 
	<div class="form">Expires on:</div>
	<div class="form-boxes-padding" style="margin-top:-30px;">
		<select name="date_month">
			<option>01</option>
<option>02</option>
<option>03</option>
<option>04</option>
<option>05</option>
<option>06</option>
<option>07</option>
<option>08</option>
<option>09</option>
<option>10</option>
<option>11</option>
<option>12</option>
		</select>
		<select name="date_year">
	<option>2011</option>
<option>2012</option>
<option>2013</option>
<option>2014</option>
<option>2015</option>
<option>2016</option>
<option>2017</option>
<option>2019</option>
<option>2020</option>
<option>2021</option>
<option>2022</option>
<option>2023</option>
<option>2024</option>
<option>2025</option>
<option>2026</option>
<option>2027</option>
<option>2028</option>
<option>2029</option>
</select>
	</div> 
	<div class="form">CVN:</div> 
	<div class="form-boxes-padding">
		<form:password path="cardDetails.cvn" class="form-boxes"  size="10"/> 
		 <br/><span style="color:#FF0066;"><form:errors path="cardDetails.cvn"/></span>
	</div> 
	<div style="font-size:12px;margin:-25px 0px 40px 680px;">If applicable</div>		
    
<div class="form-extra-details">
		<a><input id="commit" type="submit" class="form-boxes-buttons" value="Submit"/></a>
		<div style="display:none" id="loading">
			<span>Please Wait..</span>
			<img src="http://images.crowdscanner.com/vc_loading_anim.gif" />
		</div> 
 	</div> 
 
</form:form> 
</div>

</div> 
<script type="text/javascript">
$(document).ready(function () {
	var cardDate = "";
	var month = "01";
	var year = "11";
 	$("input[name='cardDetails.expiryDate']").val(month+""+year);	
    	$("select[name='date_month']").change(function () {
		 $("select[name='date_month'] option:selected").each(function () {
			month = $(this).text();
			cardDate += month;
			if (year.lenght != 0){
				cardDate = month + year;
			}
			$("input[name='cardDetails.expiryDate']").val(cardDate);	
		});
	});
	$("select[name='date_year']").change(function () {
		$("select[name='date_year'] option:selected").each(function () {
                	year = $(this).text().substring(2,4);
			cardDate += year;
		  	if (month.length != 0){
				cardDate = month + year; 
			}
        	});
		$("input[name='cardDetails.expiryDate']").val(cardDate); 	
	});
	
	$("#commit").click(function () {
		$(this).hide();
		$("#loading").show();
	});
});

</script>



