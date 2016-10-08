<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>   
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div style="padding:10px;">
	<form:form action="editeventhtml.htm" commandName="event" method="POST">
		<form:hidden path="id" />
		<div style="clear:both;padding-bottom:10px;">	
			${event.content}
		</div>
		<div>
			<form:textarea path="html" cssStyle="width: 800px; height: 550px;" />	
		</div>
		<input type="Submit" value="Edit Html" />	
	</form:form>
</div>