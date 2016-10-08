<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div class="top_header" style="height:30px">
	<img style="padding:2px 0px 0px 5px;" src="images/discussideashere.png"/>
</div>
<table class="forumtable" style="margin:0px;" id="forum">
	<tr>		
		<th>Forum</th>
		<th>Threads</th>
		<th>Posts</th>
	</tr>	
	<c:forEach items="${listforums}" var="forum">				
		<tr>			
			<td class="content">
				<div>
					<a href="retrieveforumposts.do?goal=${forum.topic}"><b><c:out value="${forum.topic}" /></b></a><br>
					<p style="margin-left:10px;font-size:10pt;">${forum.description}</p>
				</div>
			</td>
			<td class="count">
				<c:out value="${forum.postCount}" />
			</td>
		  	<td class="count">
				<c:out value="${forum.calculateTotalReplies}" />
			</td>			  		
		</tr>
	</c:forEach>			
</table>				