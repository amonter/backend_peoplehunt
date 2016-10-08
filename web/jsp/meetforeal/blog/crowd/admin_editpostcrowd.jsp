<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div>
	<c:forEach items="${posts}" var="post">
		<div style="clear:both;">
			<div style="float:left;padding:10px;">				
				<a href="updatecrowdblogpost.htm?blogpostid=${post.id}">Edit Post</a>
			</div>
			<div style="float:left;padding:10px;">				
				<a href="updatecrowdblogpost.htm?blogpostid=${post.id}">Delete Post</a>
			</div>
			<div style="padding:10px;">
				${post.title}
			</div>
		</div>
	</c:forEach>
</div>
