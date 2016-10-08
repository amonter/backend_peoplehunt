<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<display:table list="${post.sortedThreads}" id="thread" pagesize="10" requestURI="retrieveforumthreads.do" class="forumtable">
	<display:column title="${thread.post.title}">
		<table class="thread">
			<tr>
				<th><fmt:formatDate pattern="yyyy-MM-dd, hh:ssa" value="${thread.creationDate}" /></th>
			</tr>
			<tr>
				<td>
					<div style="padding:10px;">
						<div>
							<c:set var="imageId" value="${thread.profile.user.imageID}" />
							<c:if test="${thread.profile == null}" >
								<c:set var="imageId" value="" />
							</c:if>								
							<a href="retrieveuserhome.do?profileid=${thread.profile.id}"><img src="picture_library/profile/${imageId}.jpg" width="50" height="50" /></a>													
						</div>
						<div>
							<span class="nameofmember"><c:out value="${thread.profile.user.userName}" /></span>
						</div>															
					</div>					
				</td>	
			</tr>
			<tr>
				<td class="content" valign="top">					
					<c:out value="${thread.content}" />	
				</td>
			</tr>
			<tr>
				<td>
					<a href="admindeletethread.htm?threadpk=${thread.id}">Delete Thread</a>
				</td>
			</tr>
		</table>								
	</display:column>											
</display:table>