<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>   
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  



<form method="post" action="uploadImageAdmin.htm" enctype="multipart/form-data">           
   	<input type="hidden" name="userProfile" value="${userProfile}">
   	File name
   	<input type="text" name="fileName">
       <input type="file" name="multipartFile"/>
       <input type="submit" value="Upload!"/>
</form>     
 <span class = login_error>
  	<spring:hasBindErrors name="file">			
		<c:forEach items="${errors.allErrors}" var="error">
			<spring:message message="${error}" />
		</c:forEach>			
	</spring:hasBindErrors>
 </span>
			