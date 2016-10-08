 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="java.util.*" %>

<%   
	Map countryMap = (Map)request.getAttribute("countryList");
	Collection countryList = countryMap.values();	
	pageContext.setAttribute("countryCollection", countryList);
%>

<div class="create_user_div">
	<div  background="images/middle1.jpg" width="800" height="10">
		<p class = "Title">Edit Profile</p>
	</div>
	<div>				
		
	</div>					
	<div style="margin:20px 0px 10px 0px">
		<form:form commandName="profile" action="" method="post">
		<form:hidden path="user.email" />
		<spring:hasBindErrors name="profile">
			<div id="OuterErrorLayer">
				<div id="ErrorLayer">
					<form:errors path="*" />														
				</div>
			</div>
		</spring:hasBindErrors>	
		<form:hidden path="user.imageID" />
			<table width="600">	   			
	   			<tr>
	   				<td class="register_form">Name </td>
	   				<td class="register_form_boxes" colspan="2">
	   					<form:input cssClass="input_type_new"  path="user.lastName"/> 			    					
	   				</td>
	   			</tr>				
	    		<tr>
	    			<td class="register_form">Birthday</td>
	    			<td class="register_form_boxes" colspan="2"><nobr>
						<form:select path="user.birthDate.day" cssClass="input_type_day">
							<form:option value="" label="day" />
							<form:options items="${dayList}" itemLabel="label" itemValue="code" />
						</form:select> 
						<form:select path="user.birthDate.month" cssClass="input_type_month" >
							<form:option value="" label="month" />
							<form:options items="${monthList}" itemLabel="label" itemValue="code" />
						</form:select> 
						<form:select path="user.birthDate.year" cssClass="input_type_year">
							<form:option value="" label="year" />
							<form:options items="${yearList}" itemLabel="label" itemValue="code" />
						</form:select> </nobr>
					</td>
					<td>
						<form:checkbox path="publicProfile" value="true"/> Make my age private.
					</td>
				</tr>
				<tr>
	    			<td class="register_form" style="padding:0px 20px 12px 0px;">Gender</td>
	    			<td class="register_form_boxes" colspan="2" style="padding-bottom:15px;">	    				
						<form:radiobutton path="user.gender" cssStyle="vertical-align:middle;" value="male" /><span style="padding:0px 5px 0px 3px;">Male</span>
						<form:radiobutton path="user.gender" cssStyle="vertical-align:middle;" value="female"/><span style="padding:0px 5px 0px 3px;">Female </span>
						<form:radiobutton path="user.gender" cssStyle="vertical-align:middle;" value="couple"/><span style="padding:0px 5px 0px 3px;">Couple </span>
					</td>
				</tr>									
				<tr>
	    			<td class="register_form">Country</td>
	    			<td class="register_form_boxes" colspan="2">
			    		<form:select  path="user.country" onchange="hideSelect(this);" cssClass="input_type_new">
							<form:option value="" label="country" />
							<form:options items="${countryCollection}" itemLabel="label" itemValue="code" />
						</form:select> 
					</td>
					<td id="countryStates">	
						<c:if test="${profile.user.country == 'Ireland'}">		
							<form:select path="user.state"  cssClass="input_type_new">										
								<form:options items="${countryList['Ireland'].states}" itemLabel="label" itemValue="code" />
							</form:select>
						</c:if>	
					</td>
				</tr>				
	    		<tr>    			
	    			<td colspan="3">
	    				<div style="padding:20px 0px 20px 134px;">
	    					<input name="save"  type="image" src="images/Save.jpg" id="save" value="Save" />
	    					<input name="cancel"  type="image" src="images/cancel.jpg" id="cancel" value="Cancel" />		    										
						</div>
					</td>
				</tr>			
			</table>
		</form:form>	
	</div>
</div>