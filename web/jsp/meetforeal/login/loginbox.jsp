<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:set var="url_flow" value="navigateprofile.do"/>
<c:if test="${!empty flowExecutionUrl}">
	<c:set var="url_flow" value="${flowExecutionUrl}&_eventId=authenticate"/>
</c:if>

<table border="0" class="loginTable" cellspacing="0" cellpadding="0" >
	<form:form commandName="userLogin" action="${url_flow}" method="POST">			
		<tr>			
			<td class="labels">Username</td>  
		</tr>
		<tr>			
			<td class="login_boxes">
				<form:input  cssClass="input_type_new" path="userName"/>
			</td>
		</tr>
		<tr>
			<td class="login_error" colspan="2">
				<nobr><form:errors path="userName"/></nobr>
		   	</td>
		</tr>
		<tr>
			<td class="labels">Password</td>
		</tr>
		<tr>
		   	<td class="login_boxes">
				<form:password  cssClass="input_type_new" path="password"/>	
			</td>
		</tr>
		<tr>
			<td class = "login_error" colspan="2" >
				<nobr><form:errors path="password"/></nobr>
				<form:errors path="authentication"/>	
			</td>
		</tr>
		<tr>
			<td colspan="2" class="rememberMe">
				<div style="padding:11px 52px 0px 0px;float:left;">
					<form:checkbox path="rememberMe" /><span style="padding-left:6px;font-size:90%;">Remember Me</span>
				</div>
				<div style="float:left;">
					<input name="Submit" type="image" src="http://images.meetforeal.com/images/Login.jpg" />
				</div>
			</td>
		</tr>		
		<tr>
			<td colspan="2" class="forgotPassword"><a href="presentforgotpassword.do">Forgotten password?</a></td>
		</tr>			  		
	</form:form>		
</table>

