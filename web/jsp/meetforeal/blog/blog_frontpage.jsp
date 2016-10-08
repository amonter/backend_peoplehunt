<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.myownmotivator.model.blog.Blog" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>


<%
		Blog theBlog = (Blog) request.getAttribute("blog");
		if(!theBlog.getBlogPosts().isEmpty()) {
			
			String content = theBlog.getBlogPosts().get(0).getContent();			
			pageContext.setAttribute("theContent", StringUtils.abbreviate(content, 1000));	
		}	
		
%>



<div style="padding:15px;width:530px;">	
	<div>
		<div class="blog_frame_fp">
			<a style="color:#666666;" href="retrieveblog.do"><b>BLOG</b></a>
			<a href="createblogfeeds.do" style="margin:0px 0px 0px 450px;">
				<img src="images/feed_icon.png" />
			</a>
		</div>
		<div style="background-color:#FFFFFF;padding:0px 8px 5px 10px;">	
			<h2 class="header_blog">
				<a style="color:#CA090C;" href="retrieveblog.do">${blog.blogPosts[0].title}</a>				
			</h2>
			<div style="padding-bottom:5px;">
				<div class="blog_footer_published"><fmt:formatDate  pattern="yyyy/MM/dd hh:mm a" value="${blog.blogPosts[0].publishedDate}" /></div>
				${theContent} <a href="retrieveblog.do">more</a>			
			</div>
		</div>
		<div class="blog_footer">
			<span class="blog_footer_published">Posted by ${blog.blogPosts[0].publishedBy.user.userName}</span>
			<c:set var="comment_size" value="${fn:length(blog.blogPosts[0].comments)}" />
			<span><a href="postcomment.do?blogPostId=${blog.blogPosts[0].id}">${comment_size} comments</a></span>			
		</div>
	</div>	
</div>
