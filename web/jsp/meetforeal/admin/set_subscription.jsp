<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 

<div style="margin:5px;">
	
	<form:form commandName="profile" action="setsubscription.htm" method="POST" enctype="multipart/form-data">
		<form:hidden path="id" /> 	
		
		<span style="display:inline;margin:5px;">${profile.user.userName}</span> 
		<span style="display:inline;margin:5px;">${profile.user.email}</span> 	
		<span style="display:inline;margin:5px;"><form:checkbox path="subscribed"/></span> 
		<input type="Submit" value="Unsubscribe" />
						
	</form:form>
</div>