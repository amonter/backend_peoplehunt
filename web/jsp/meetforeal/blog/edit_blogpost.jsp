<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="com.myownmotivator.model.blog.*" %>

		
<div class="standardSpacing">
	<form:form action="updateblogpost" commandName="blogPost" method="POST">
		<input type="hidden" name="publishedBy.id" value="3">	
		<form:hidden path="id" />
		<div style="clear:both;padding-bottom:10px;">	
			<form:input path="title"  cssStyle="width: 350px" />
		</div>
		<div>
			<form:textarea path="content" cssStyle="width: 800px; height: 550px;" />	
		</div>
		<div>
			<form:input path="description" cssStyle="width: 350px"/>	
		</div>
		<input type="Submit" value="Submit Post" />	
	</form:form>
</div>


