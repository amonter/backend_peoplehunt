<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<tiles:insertTemplate template="/jsp/crowdscanner/blog/theblog_template.jsp">   	
	<tiles:putAttribute name="the_page">	
			<div style="padding:0px 15px 0px 15px;">
				<c:forEach items="${blogPosts}" var="post">	
					<h2 class="header_blog" style="line-height:110%;">
						<a style="color:#87098A;" href="blogpost/${post.id}">${post.title}</a>				
					</h2>
					<div style="padding-bottom:5px;">
		
			
				
						${post.content}		
					</div>
					<div style="padding:20px 0px 20px 0px;">
						<div style="float:left;">Posted on <fmt:formatDate  pattern="MMMM dd, yyyy" value="${post.publishedDate}" /></div>
						<c:set var="comment_size" value="${fn:length(post.comments)}" />
						<div style="padding-left:410px;"><a href="blogpost/${post.id}" >${comment_size} comments</a></div>			
					</div>
				</c:forEach>
			</div>						
	</tiles:putAttribute>		
</tiles:insertTemplate>	

