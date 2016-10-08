<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="java.util.*" %>

<%   
	Map countryMap = (Map)request.getAttribute("countryList");
	Collection countryList = countryMap.values();
	pageContext.setAttribute("countryCollection", countryList);
%>

<!-- localhost public key: 6LdcfAQAAAAAAGxdFlBW9-ho93O0ysd8wWteDMvF-->
<!-- meetforeal public key: 6Lc2yQQAAAAAAEKAmUvlL0FTdu7IrdlInFV01CTG-->
<c:set var="url_flow" value="createuser.do"/>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow" value="${flowExecutionUrl}&_eventId=submituser"/>
</c:if>

<div class="create_user_div">	
	<div  style="margin:10px 0px 10px 0px">		
		<form:form commandName="user" action="${url_flow}" onsubmit="return openProgress();" method="POST" enctype="multipart/form-data">
			<spring:hasBindErrors name="user">
				<div id="OuterErrorLayer">
					<div id="ErrorLayer">
						<form:errors path="*" />
					</div>
				</div>
			</spring:hasBindErrors>
			<c:if test="${!empty fileException}">
				<div id="OuterErrorLayer">
					<div id="ErrorLayer">
						<c:out value= "${fileException}" />
					</div>
				</div>
			</c:if>
			<table width="400">
				<tr>
					<td class="register_form">
						Full Name
					 </td>
					<td class="register_form_boxes" >
						<form:input	path="lastName" cssClass="input_type_new" /> 
					</td>
				</tr>
				<tr>
	    			<td class="register_form">
	    				Username
	    			</td>
	    			<td class="register_form_boxes">
	    				<form:input path="userName"  onfocus="document.getElementById('userTip').style.display='inline'" cssClass="input_type_new"/> 
	    				<span id="userTip" style="display: none;">Minimum 6 characters, A-Z, 0-9</span>
	    			</td>
	    		</tr>							
				<tr>
	    			<td class="register_form">
	    				Password
	    			</td>
	    			<td class="register_form_boxes">
	    				<form:password  onfocus="document.getElementById('pwTip').style.display='inline'" showPassword="true"	path="password" cssClass="input_type_new"/> 
	    				<span id="pwTip" style="display: none;">Minimum 6 characters</span>
	    			</td>
	    		</tr>							
				<tr>
	    			<td class="register_form">
	    				<nobr>Your Email</nobr> 
	    			</td>
	    			<td class="register_form_boxes" style="font-size:11px;">
	    				<form:input path="email" cssClass="input_type_new" /> 
<td colspan="2" ><span style="font-size:11px;">we won't share your details with anyone</span></td>	    			
</td>
	    		</tr>						
				<tr>
	    			<td class="register_form">
	    				Birthday
	    			</td>
	    			<td class="register_form_boxes" colspan="2"><nobr>
	    				<form:select path="birthDate.day" cssClass="input_type_day">
							<form:option value="" label="day" />
							<form:options items="${dayList}" itemLabel="label" itemValue="code" />
						</form:select> 
						<form:select path="birthDate.month" cssClass="input_type_month">
							<form:option value="" label="month" />
							<form:options items="${monthList}" itemLabel="label" itemValue="code" />
						</form:select>
						<form:select path="birthDate.year" cssClass="input_type_year">
							<form:option value="" label="year" />
							<form:options items="${yearList}" itemLabel="label" itemValue="code" />
						</form:select> </nobr>
					</td>
					<td>
						<nobr><form:checkbox path="profile.publicProfile" cssStyle="vertical-align:middle;" value="true"/><span style="font-size:11px;padding:0px 0px 0px 4px;">Make my age private.</span></nobr>
					</td>
				</tr>
				<tr>
	    			<td class="register_form">Living In</td>
	    			<td class="register_form_boxes" colspan="2" >
			    		<form:select path="country" onchange="hideSelect(this);" cssClass="input_type_new">
							<form:option value="" label="country" />
							<form:options items="${countryCollection}" itemLabel="label" itemValue="code" />
						</form:select> 
					</td>
					<td id="countryStates">
						<c:if test="${user.country == 'Ireland'}">
							<form:select path="state"  id="input_type_state" cssClass="input_type_new">										
								<form:options items="${countryList['Ireland'].states}" itemLabel="label" itemValue="code" />
							</form:select>										 
						</c:if>
					</td>
				</tr>																			
				<tr>
	    			<td class="register_form" style="padding-bottom:0px;">
	    				Gender
	    			</td>
	    			<td class="register_form_boxes" style="padding:6px 0px 6px 0px;">
						<form:radiobutton path="gender" cssStyle="vertical-align:middle;" value="male" /><span style="padding:0px 5px 0px 3px;">Male</span>
						<form:radiobutton path="gender" cssStyle="vertical-align:middle;" value="female"/><span style="padding:0px 5px 0px 3px;">Female </span>
						<form:radiobutton path="gender" cssStyle="vertical-align:middle;" value="couple"/><span style="padding:0px 5px 0px 3px;">Couple </span>
					</td>
				</tr>
				<tr>
	    			<td class="register_form" >
	    				How did you get here?
	    			</td>
	    			<td class="register_form_boxes" style="padding:6px 0px 6px 0px;">
						<form:select path="profile.publicity" cssClass="input_type_new">
							<form:option value="" label="Please help us out.." />							
							<form:options items="${publicityList}" itemLabel="label" itemValue="code" />
						</form:select>
					</td>
				</tr>
				<c:if test="${empty flowExecutionUrl}">
					<tr>
						<td class="register_form" style="padding-top:6px;">
							<img src="http://images.meetforeal.com/images/upload_photo.jpg" />
						 </td>
						<td class="register_form_boxes" colspan="2" style="padding-top:5px;font-size:11px;">
							<input type="file" id="input_type" name="profile.files[0].multipartFile"/>							
								2MB max <br>we accept jpg, png, bmp, gif 							
						</td>
					<td><img src="http://images.meetforeal.com/images/hassle_image.png"></td>						
					</tr>
				</c:if>
				<c:if test="${isPresenter}">
					<tr>
						<td class="register_form" style="padding-top:6px;">
							Title of what you are sharing:
						 </td>
						<td  colspan="3" class="register_form_boxes" style="padding-top:5px;">
							<form:textarea rows="5" cols="40" path="profile.presentationTitle" id="input_type" cssClass="createUserField"/> 				
						</td>										
					</tr>
				</c:if>	
				<c:if test="${isPresenter}">
					<tr>
						<td class="register_form" style="padding-top:6px;">
							Tell us more about it:
						 </td>
						<td  colspan="3" class="register_form_boxes" style="padding-top:5px;">
							<form:textarea rows="5" cols="40" path="profile.presentationContent" id="input_type" cssClass="createUserField"/> 				
						</td>										
					</tr>
				</c:if>	
				<tr>
					<td></td>
					<td colspan="3" style="padding-top:5px;">
						<script>
							var RecaptchaOptions = {
						    theme : 'clean'
						 };						
						</script>					
						<script type="text/javascript" src="http://api.recaptcha.net/challenge?k=6LdcfAQAAAAAAGxdFlBW9-ho93O0ysd8wWteDMvF">					
						</script>						
						<noscript>
						    <iframe src="http://api.recaptcha.net/noscript?k=6LdcfAQAAAAAAGxdFlBW9-ho93O0ysd8wWteDMvF"
						        height="300" width="500" frameborder="0"></iframe><br>
						    <textarea  id="input_type" name="recaptcha_challenge_field" rows="3" cols="40">
						    </textarea>
						    <input type="hidden" name="recaptcha_response_field" value="manual_challenge">
						</noscript>
					</td>
				</tr>									
	    		<tr>	    			
	    			<td colspan="4">
	    				<div class="termsDiv">
	    					<div>
	    					<input type="image" src="http://images.meetforeal.com/images/sign_up_grey.jpg"  />
	    					</div>
	    					<div class="terms_statement">
	    						By Signing Up you agree to our <a href="viewpageterms.do">Terms of Use</a> and  <a href="viewpageprivacy.do">Privacy Policy</a>
	    					</div>
	    				</div>
	    			</td>
	    		</tr>										
			</table>
		</form:form>	
	</div>	
</div>

<script language='javascript'>



</script>
