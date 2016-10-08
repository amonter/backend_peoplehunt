	 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="create_user_div">
	<div  background="images/middle1.jpg" width="700px" height="20px">
		<p class = "create_user" >Email Address Confirmation</p>
	</div>	
	<div class="conf">
		<p>Cool! <b>Just one more step</b> - please click on the link in the email we have just sent to you at <b>${email}</b></p>
		<p>Don't forget to check your <b>junk folder!</b></p>
	</div>
</div>