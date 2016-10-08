<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="com.myownmotivator.model.blog.*" %>


<tiles:insertTemplate template="/jsp/blog/blog_template.jsp">
    <tiles:putAttribute name="title_style" value="main_title"/>				
	<tiles:putAttribute name="sub_title" value="Meetforeal Blog" />	
	<tiles:putAttribute name="the_page">
		<div class="standardSpacing">	
			<div class="blog_frame_fp">
				<a style="color:#666666;" href="retrieveblog.do"><b>BLOG</b></a>
				<a href="createblogfeeds.do" style="margin:0px 0px 0px 300px;">
					<img src="images/feed_icon.png" />
				</a>
			</div>
			<div style="background-color:#FFFFFF;padding:0px 8px 5px 10px;">	
				<h2 class="header_blog">
					<a style="color:#CA090C;" href="retrieveblog.do">${blog.blogPosts[0].title}</a>				
				</h2>
				<div style="padding-bottom:5px;">
					<div class="blog_footer_published">
						<fmt:formatDate  pattern="yyyy/MM/dd hh:mm a" value="${blog.blogPosts[0].publishedDate}" />
					</div>
					${theContent}			
				</div>
			</div>			
		</div>		
		<div class="standardSpacing">
			<form:form action="${action}" commandName="blogPost" method="POST">
				<input type="hidden" name="publishedBy.id" value="3">	
				<form:hidden path="id" />
				<div style="clear:both;padding-bottom:10px;">	
					<form:input path="title"  cssStyle="width: 350px" />
				</div>
				<div>
					<form:textarea path="content" cssStyle="width: 700px; height: 250px;" />	
				</div>
				<input type="Submit" value="Submit Post" />	
			</form:form>
		</div>
	</tiles:putAttribute>		
</tiles:insertTemplate>	

