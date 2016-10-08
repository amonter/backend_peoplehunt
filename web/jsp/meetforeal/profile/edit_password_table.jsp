<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="OuterEditLayer">
	<div background="images/middle1.jpg" style="padding-left:6px;"width="800" height="10">
		<p class="Title">Change Password</p>
	</div>
	<form:form commandName="user" action="" method="post">
	<spring:hasBindErrors name="user">
		<div id="OuterErrorLayer">
			<div id="ErrorLayer">
				<form:errors path="*" />														
			</div>
		</div>
	</spring:hasBindErrors>	
	<form:hidden path="profile.id" />
		<table class="loginTable" style="width:617px;padding-top:20px;" >							
			<tr>
   				<td class="register_form" style="padding-top:20px;">Old Password</td>
   				<td class="register_form_boxes" style="width:350px;padding-top:20px;"  >
   					<form:password cssClass="input_type_new" path="password"/> 			    					
   				</td>
   			</tr>
   			<tr>
   				<td class="register_form" style="padding-top:20px;">New Password</td>
   				<td class="register_form_boxes" style="width:350px;padding-top:20px;" >
   					<form:password cssClass="input_type_new" path="newPassword"/> 			    					
   				</td>
   			</tr>			
			<tr>
    			<td class="register_form" style="padding-top:20px;"><nobr>Confirm Password</nobr></td>
    			<td class="register_form_boxes" style="width:350px;padding-top:20px;" >
    				<form:password cssClass="input_type_new" path="newPasswordConfirm"/> 				    				
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2" style="padding:20px 0px 0px 115px;">
    				<div style="padding-left:50px;">
    					<input name="save" src="images/Save.jpg" type="image" id="save" value="Save" />    				
    					<input name="cancel" src="images/cancel.jpg" type="image" id="cancel" value="Cancel" />
    				</div>
    			</td>
    		</tr>
		</table>
	</form:form>
</div>