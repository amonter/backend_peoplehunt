<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<display:table list="${forum.post}" id="post" pagesize="10" requestURI="retrieveforumposts.do" class="forumtable">
	<display:column>
		<div style="padding:10px;">
			<div>
				<c:set var="imageId" value="${post.postUserProfile.imageID}" />
				<c:if test="${post.postUserProfile== null}" >
					<c:set var="imageId" value="" />
				</c:if>
				<a href="retrieveuserhome.do?profileid=${post.postUserProfile.id}"><img src="picture_library/profile/${imageId}.jpg" width="50" height="50" /></a>				
			</div>
			<div>
				<span class="nameofmember"><c:out value="${post.postUserProfile.userName}" /></span>
			</div>
		</div>				
	</display:column>
	<display:column  title="Thread" class="content" >
		<a href="retrieveadminthreads.htm?postpk=${post.id}">
			<nobr><c:out value="${post.title}" /></nobr>
		</a>
	</display:column>
	<display:column  title="Last Post" class="alignment" >
		<c:if test="${post.postUserProfile != null}" >
			<fmt:formatDate pattern="dd/MM/yyyy" value="${post.creationDate}" />
			<br class = "LastPostedBy2">by <c:out value="${post.postUserProfile.userName}" />  
		</c:if>
		<c:if test="${post.postUserProfile == null}" >
			<br class = "LastPostedBy2">Anonimous User  
		</c:if>
	</display:column>
	<display:column  title="Replies" class="alignment" >
		<c:out value="${post.numberReplies}" />
	</display:column>
	<display:column  title="Action" class="alignment" >
		<a href="deleteforumpost.htm?postpk=${post.id}">Delete Post</a>							
	</display:column>													
</display:table>		