<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>   
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  

<display:table list="${tags}" id="tag" pagesize="20" class="forumtable">					 
	<display:column>
		<form method="post" action="saveupdatetag.htm" >  
			<input type="hidden" name="tagId" value="${tag.id}"/>			
	</display:column>				
	<display:column title="Name">				
			<input type="text" name="name" value="${tag.name}"/>
	</display:column>
	<display:column title="Alias Name">				
			<input type="text" name="tag_alias" value="${tag.tagAlias}"/>
	</display:column>
	<display:column title="Question Link">				
			<input type="text" name="question_link" value="${tag.questionLink}"/>
	</display:column>
	<display:column title="Version">				
			<input type="text" name="version" value="${tag.version}"/>
	</display:column>				
	<display:column>				
			<input type="submit" value="Update"/>
		</form>
	</display:column>				
	<display:column>				
		<a onclick="return confirmDelete();" href="deletetag.htm?tagId=${tag.id}">Delete</a>
	</form>	
	</display:column>					
	<display:caption>
		<form method="post" action="saveupdatetag.htm" >               	
            <input type="text" name="name"/>
            <input type="submit" value="Add"/>
       	</form>
	</display:caption>
</display:table>

<script language='javascript'>

function confirmDelete(country, state) {
	
	return confirm('Are you sure you want to delete this.');	
}


</script> 