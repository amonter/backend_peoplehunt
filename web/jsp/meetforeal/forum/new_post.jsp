<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

  			
<div id ="PostNewThread">  			   			
	<form:form commandName="post" action="" method="post">
		<form:hidden path="forum.id"/>
		<table>	
			<tr>
				<td class = "NewPostHeading">
					Title of Post: 	&nbsp;<br><form:input size="60" path="title" />
				</td>
			</tr>
			<tr>
				<td class = "post_error" colspan="2"><nobr>
					<form:errors path="title"/></nobr>
				</td>
			</tr>		
			<tr>
				<td class = "NewPostHeading">
					Post your Comment: &nbsp;<br><form:textarea cols="52" rows="12" path="newThread.content" />
				</td>
			</tr>
			<tr>
				<td class = "post_error" colspan="2"><nobr>
					<form:errors path="newThread.content"/></nobr>
				</td>
			</tr>			
			<tr>
				<td>
					<input name="Post Message" class="Post_button" type="submit" value="Create Post" />
				</td>
			</tr>		
		</table>
	</form:form>	
</div>


	