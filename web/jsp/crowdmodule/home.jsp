<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<html>
<head profile="http://www.w3.org/2005/10/profile">
	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src='http://images.crowdscanner.com/jquery-1.4.2.min.js' ></script>
	<script type="text/javascript" src='http://images.crowdscanner.com/jquery.form.js' ></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript">
$(document).ready(function() { 
	
	 $("input[name=file]").change(function(){
	    	$("#status").text("Uploading image").show();
	    	$('#uploadPhoto').submit();
	 });
	

	$('#login').ajaxForm({
    	'dataType' :  'json', 
    	'error' : function() {alert("error");},
    	beforeSubmit : function() {
    		$("#errors").text("");
    	},
    	'success' : function(data) {
   			if (data.result == 'success') {
   				window.location = data.url;
   			} else {
   				$("#errors").text(data.errors);
   			}
    	}
    });
	
	$("#signin").attr ('href', '#');

	$("#signin").click(function(){
		$("#loginDialog").dialog();
	});
	
});
</script>
</head>
<body>	
<c:if test="${authContext != null}">
	Hello ${authContext.userName}
</c:if>	
				
<c:if test="${authContext == null}">
	<a id="signin" href="<c:url value='/crowdmodule/auth/login'/>">Sign In</a> 
	<a href="<c:url value='/crowdmodule/auth/register'/>">Sign Up</a>
</c:if>

<div id="loginDialog" style="display:none">
<div id="errors"></div>
<c:url var="loginUrl" value="/crowdmodule/auth/login_ajax"/>
<form id="login" method="POST" action="${loginUrl}"> 
	<div align="left"> 
		Username:<br> 
		<input name="userName"  size="25"/>
		</div>  
	<div align="left"> 
		Password :
		<br> 
		<input type="password" name="password"  size="25"/>
	</div> 	
	<br><br> 
	<input type="submit" value="Submit"/>
</form> 
</div>
</body>
</html>