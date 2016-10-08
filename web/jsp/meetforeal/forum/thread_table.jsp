<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div class="top_header" style="height:30px">
	<span id="niceFont" style="padding:2px 0px 2px 7px;color:#008ECF;"><b>${post.title}</b></span>
</div>
<div id="here_man" style="max-width:620px;min-height:200px;">	
	<display:table list="${post.sortedThreads}" id="the_thread" style="max-width:610px;" pagesize="10" requestURI="retrieveforumthreads.do" class="forumtable">
		<display:column title="${the_thread.post.title}">			
			<table class="my_thread">				
				<tr>
					<td>
						<span class="thread_date">
							<fmt:formatDate pattern="dd-MMM-yyyy, HH:mm" value="${the_thread.creationDate}" />
						</span>
						<div style="padding:10px;">
							<div>
								<c:set var="imageId" value="${the_thread.profile.user.imageID}" />
								<c:if test="${the_thread.profile == null}" >
									<c:set var="imageId" value="" />
								</c:if>
								<c:if test="${the_thread.profile.id == null}">
									<img src="picture_library/profile/${imageId}.jpg" width="50" height="50" />
								</c:if>
								<c:if test="${the_thread.profile.id != null}">
									<a href="retrieveuserhome.do?profileid=${the_thread.profile.id}">
										<img src="picture_library/profile/${imageId}.jpg" width="50" height="50" />
									</a>				
								</c:if>																		
							</div>
							<div>
								<span class="nameofmember"><c:out value="${the_thread.profile.user.userName}" /></span>
							</div>															
						</div>					
					</td>	
				</tr>
				<tr>
					<td class="content" valign="top">					
						${the_thread.content}	
					</td>
				</tr>
			</table>								
		</display:column>											
	</display:table>
</div>