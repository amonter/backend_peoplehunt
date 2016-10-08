<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="java.util.*" %>

<%   
	Map countryMap = (Map)request.getAttribute("countryList");
	Collection countryList = countryMap.values();
	pageContext.setAttribute("countryCollection", countryList);
%>

<div class="standardSpacing" style="margin-top: 35px;">
	<img src="images/create_user_msg.jpg"/>
</div>

<div style="margin:20px 5px 5px 20px;"> 
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content">
			<form:form commandName="eventSuggest" action="suggestevent.do"  method="POST" enctype="multipart/form-data">
			<spring:hasBindErrors name="eventSuggest">
				<div id="OuterErrorLayer">
					<div id="ErrorLayer">
						<form:errors path="*" />
					</div>
				</div>
			</spring:hasBindErrors>			
			<table width="450">
				<tr>
					<td class="register_form">
						Tell us about the speaker
					 </td>
					<td class="register_form_boxes" >
						<form:textarea rows="3" cols="10" path="speaker" cssClass="input_type_new" /> 
					</td>
				</tr>
				<tr>
	    			<td class="register_form">
	    				How much would you pay?
	    			</td>
	    			<td class="register_form_boxes">
	    				<form:input path="fee" cssClass="input_type_new"/>	    				
	    			</td>
	    		</tr>									
				<tr>
	    			<td class="register_form">
	    				<nobr>Your Email</nobr> 
	    			</td>
	    			<td class="register_form_boxes">
	    				<form:input path="email" cssClass="input_type_new"/> 
	    			</td>
	    		</tr>				
				<tr>
	    			<td class="register_form">Where?</td>
	    			<td class="register_form_boxes" colspan="2" >
			    		<form:select path="country" onchange="hideSelect(this);" cssClass="input_type_new">
							<form:option value="" label="country" />
							<form:options items="${countryCollection}" itemLabel="label" itemValue="code" />
						</form:select> 
						${countryList.values}
					</td>
					<td id="countryStates">
						<c:if test="${eventSuggest.country == 'Ireland'}">
							<form:select path="state"  id="input_type_state" cssClass="input_type_new">										
								<form:options items="${countryList['Ireland'].states}" itemLabel="label" itemValue="code" />
							</form:select>										 
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="register_form">
						do you want to tell us anything else?
					 </td>
					<td class="register_form_boxes" >
						<form:textarea rows="3" cols="10" path="comment" cssClass="input_type_new" /> 
					</td>
				</tr>				
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
	    			<td colspan="2">
	    				<div class="termsDiv">
	    					<div>
	    						<input type="image" src="images/create_account.jpg"  />
	    					</div>	    					
	    				</div>
	    			</td>
	    		</tr>	    											
			</table>
		</form:form>	
		</tiles:putAttribute>
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

</script>
