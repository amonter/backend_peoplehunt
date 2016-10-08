<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div class="top_header" style="height:30px">
	<span id="niceFont" style="padding:2px 0px 2px 7px;color:#008ECF;"><b>${forum.topic}</b></span>
</div>
<div style="min-width:600px;min-height:200px;">
	<display:table list="${forum.post}" id="post" style="max-width:610px;" pagesize="10" requestURI="retrieveforumposts.do" class="forumtable">
		<display:column title="&nbsp;">
			<div style="padding:10px;">
				<div style="padding:0px 0px 3px 15px;">					
					<c:set var="imageId" value="${post.postUserProfile.imageID}" />
					<c:if test="${post.postUserProfile== null}" >
						<c:set var="imageId" value="" />
					</c:if>
					<c:if test="${post.postUserProfile.id == null}">
						<img src="picture_library/profile/${imageId}.jpg" width="50" height="50" />
					</c:if>
					<c:if test="${post.postUserProfile.id != null}">						
						<a href="retrieveuserhome.do?profileid=${post.postUserProfile.profile.id}">
							<img src="picture_library/profile/${imageId}.jpg" width="50" height="50" />
						</a>				
					</c:if>											
				</div>
				<div style="line-height:75%;text-align:center;">
					<c:set var="the_username" value="" />
					<c:if test="${post.postUserProfile != null}" >					
						<c:set var="the_username" value="${post.postUserProfile.userName}" />					 
					</c:if>
					<c:if test="${post.postUserProfile == null}" >
						<c:set var="the_username" value="Anonymous User" /> 				
					</c:if>										
					<span class="nameofmember">
						<span style="display:block;font-weight:bold;"> Created by </span>
						<span style="display:block;">
							<a href="retrieveuserhome.do?profileid=${post.postUserProfile.profile.id}">
								<c:out value="${the_username}" />
							</a>	
						</span>
					</span>
					<span class="nameofmember">
						<span style="display:block;font-weight:bold;padding:0px 0px 3px 0px;">last reply</span>
						<span><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${post.creationDate}" timeZone="Europe/Dublin" /></span>
						<span style="display:block;">
							<c:set var="thread_size" value="${(fn:length(post.thread)) - 1}" />	
							<c:set var="reply_id" value="${post.sortedThreads[thread_size].profile.id}" />
							by: 								
							<a href="retrieveuserhome.do?profileid=${reply_id}">
								<c:out value="${post.sortedThreads[thread_size].profile.user.userName}" />
							</a>	
						</span>
					</span>
				</div>				
			</div>				
		</display:column>
		<display:column  title="Thread" class="post_content" style="padding:18px 5px 18px 5px;" >
			<p>
				<a href="retrieveforumthreads.do?postpk=${post.id}">
					<c:out value="${post.title}" />
				</a>
			</p>
		</display:column>		
		<display:column  title="Replies" class="post_content"  >
			<c:out value="${post.numberReplies}" />
		</display:column>
		<display:column  title="Views" class="post_content" >
			<c:out value="${post.views}" />							
		</display:column>													
	</display:table>		
</div>
