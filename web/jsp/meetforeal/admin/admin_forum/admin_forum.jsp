<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="standardSpacing">	
	<div style="padding:5px 0 5px 0;">	
		<div>
			Search in Forum
		<div>
		<table class="searchbox" border="0" cellspacing="0" cellpadding="0">	
			<form method="post" action="searchforums.do">	
				<tr>
		 			<td class="alignment">					 					
		 				<input class="search_box" type="text" name="criteria" />
		 				<input  type="hidden" name="searchadmin" value="true" /> 			
		 			</td>
		 			<td class="alignment">
		 				<input name="Submit" type="submit" class="search_button" value="Search" />		 			
		 			</td>
				</tr>  	
			</form>		  			
		</table>	
	<div>
	<div style="padding:5px 0 5px 0;">
		<div>
			Create New Forum
		<div>
		<table class="searchbox" border="0" cellspacing="0" cellpadding="0">	
			<form method="post" action="createnewforum.htm">	
				<tr>
		 			<td class="alignment">					 					
		 				<input class="search_box" type="text" name="forumname" />	 						
		 			</td>
		 			<td class="alignment">					 					
		 				<input class="search_box" type="text" name="subtitle" />	 						
		 			</td>		 			
		 			<td class="alignment">
		 				<input name="Submit" type="submit" class="search_button" value="Create" />		 			
		 			</td>
				</tr>  	
			</form>		  			
		</table>	
	</div>
</div>
<div class="standardSpacing">
	<table class="forumtable" id="forum">
		<tr>
			<th></th>
			<th>Forum</th>
			<th>Posts</th>
			<th>Replies</th>
		</tr>		
		<c:forEach items="${listforums}" var="forum">				
			<tr>
				<td class="icon">
					<img src="images/forum_icon.png" />
				</td>
				<td class="content">
					<b><a href="retrievepostadmin.htm?forumpk=${forum.id}" ><c:out value="${forum.topic}" /></a></b><br>
					<span style="margin-left:10px;font-size:10pt;">${forum.description}</span>
				</td>
				<td class="count">
					<c:out value="${forum.postCount}" />
				</td>
			  	<td class="count">
					<c:out value="${forum.calculateTotalReplies}" />
				</td>			  		
			</tr>
			<tr>
				<td>
					<a href="deleteforum.htm?forumId=${forum.id}">Delete Forum</a>
				</td>
			</tr>
		</c:forEach>			
	</table>								
</div>
<div id="PostLayerBrowse">
	<c:if test="${fn:length(threads) != 0}">	
		<display:table list="${threads}" id="thread" pagesize="20" requestURI="searchforums.do" class="forumtable">	
			<display:column>
				<table width="100" height="100">
					<tr>
						<td>
							<div id="PhotoOuterBorder">
								<div id="Photo">
									<c:set var="imageId" value="${thread.profile.user.imageID}" />
									<c:if test="${thread.profile == null}" >
										<c:set var="imageId" value="male" />
									</c:if>
									<img src="getimage.do?id=${imageId}" width="70" height="70" />
									<span><c:out value="${thread.profile.user.userName}" /></span>									
								</div>								
							</div>
						</td>
						<td style="padding-left:3px;padding-bottom:36px;">
							<c:if test="${!empty thread.profile.user.age}" >
								<nobr>Age: <span><c:out value="${thread.profile.user.age}" /></span></nobr>
							</c:if>
							<span><c:out value="${thread.profile.user.country}" /></span>
						</td>
					<tr>	
				</table>			
			</display:column>
			<display:column>
				<a href="retrieveforumthreads.do?postpk=${thread.post.id}">
					<nobr><c:out value="${thread.content}" /></nobr>
				</a>
			</display:column>
			<display:column  title="Last Post" class="alignment" >
				<c:if test="${thread.post.postUserProfile != null}" >
					<fmt:formatDate pattern="dd/MM/yyyy" value="${thread.post.creationDate}" />
					<br class = "LastPostedBy2">by <c:out value="${thread.post.postUserProfile.userName}" />  
				</c:if>
				<c:if test="${thread.post.postUserProfile == null}" >
					<br class = "LastPostedBy2">Anonimous User  
				</c:if>
			</display:column>
			<display:column  title="Thread Id" class="alignment">
				<c:out value="${thread.id}" />
			</display:column>
			<display:column title="actions">
				<a href="admindeletethread.htm?threadpk=${thread.id}">Delete Thread</a>
			</display:column>										
		</display:table>
	</c:if>
</div> 	
			