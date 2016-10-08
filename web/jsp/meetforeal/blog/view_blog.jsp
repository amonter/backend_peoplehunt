<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>


<tiles:insertTemplate template="/jsp/blog/blog_template.jsp">
    <tiles:putAttribute name="title_style" value="main_title"/>				
	<tiles:putAttribute name="sub_title" value="Meetforeal Blog" />	
	<tiles:putAttribute name="the_page">
		
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute name="content" >
				<div class="top_header">
					<a href="retrieveblog.do"><img src="http://images.meetforeal.com/images/the_blo.png" style="padding-left:10px;" /></a>
					<a href="createblogfeeds.do" style="margin:0px 0px 0px 504px;">
						<img src="http://images.meetforeal.com/images/rssfeeds.jpg" />
					</a>
				</div>
				<div class="front_page_blog">
					<div>				
						<c:forEach items="${blog.blogPosts}" begin="0" end="4" var="post">
							<div class="blog_box">				
								<h2>					
									<a href="postcomment.do?blogPostId=${post.id}" style="padding-left:0px;color:#008ecf;">${post.title}</a>			
								</h2>				
								<div>								
									 <div class="blog_content">${post.content}</div>					
								</div>
								<div class="blog_footer" style="height:30px;">
									
									<div class="blog_date" style="float:left;">Posted on <fmt:formatDate  pattern="MMMM dd, yyyy" value="${post.publishedDate}" /></div>
									<c:set var="comment_size" value="${fn:length(post.comments)}" />
									<div class="blog_date" style="float:right;"><a href="postcomment.do?blogPostId=${post.id}" >${comment_size} comments</a></div>
									<c:set var="blog_link" value="http://www.meetforeal.com/postcomment.do?blogPostId=${post.id}" />                                   	                                           	
								
								</div>
								<div style="height:30px;">
														
									   
																	  
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>			
	</tiles:putAttribute>		
</tiles:insertTemplate>	


