<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="com.myownmotivator.model.blog.*" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>


<tiles:insertTemplate template="/jsp/blog/blog_template.jsp">
    <tiles:putAttribute name="title_style" value="main_title"/>				
	<tiles:putAttribute name="sub_title" value="Meetforeal Blog" />	
	<tiles:putAttribute name="the_page">		
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">
			<tiles:putAttribute name="content">	
				<div class="top_header">
					<a href="retrieveblog.do"><img src="http://images.meetforeal.com/images/blog.png" style="padding-left:10px;" /></a>
					<a href="createblogfeeds.do" style="margin:0px 0px 0px 504px;">
						<img src="http://images.meetforeal.com/images/rssfeeds.jpg" />
					</a>
				</div>
				<div class="front_page_blog">							
					<div class="blog_box">	
						<h2>
							${blogPost.title}
						</h2>
						<div>
							
							 <div class="blog_content">${blogPost.content}</div>	
						
						</div>
						<div class="blog_footer" style="height:30px;">
							 <div class="blog_date" style="float:left;">Posted on <fmt:formatDate  pattern="MMMM dd, yyyy" value="${blogPost.publishedDate}" /></div>				
						</div>						
						<c:set var="blog_link" value="http://www.meetforeal.com/postcomment.do?blogPostId=${blogPost.id}" />                             
    					<div style="height:30px;">
													
								   <div style="padding:10px 10px 0px 0px;float:left;">
								         <a href="http://www.reddit.com/r/programming/submit" onclick="window.location = 'http://www.reddit.com/r/programming/submit?url=' + encodeURIComponent(window.location); return false"> <img src="http://www.reddit.com/static/spreddit1.gif" alt="submit to reddit" border="0" /> </a>
								   </div>
								   <div style="padding-top:10px;float:left;">
								      <c:url var="the_url" value="http://www.stumbleupon.com/submit">
								          <c:param name="url" value="${blog_link}"/>
								          <c:param name="title" value="${post.title}"/>                                                                              
								      </c:url>
								      <a href="${the_url}"><img border=0 src="http://cdn.stumble-upon.com/images/32x32_su_shadow.gif" alt="stumble upon" width="19"></a>
								   </div>
								
															  
								   <div style="padding:10px 5px 0px 10px;height:30px;float:left;">
								       <script type="text/javascript"
											src="http://d.yimg.com/ds/badge2.js"
											badgetype="logo">
											ARTICLEURL
										</script>
								   </div>
								   <div style="padding-top:8px;float:left;">
								      <script type="text/javascript" src="http://s3.chuug.com/chuug.twitthis.scripts/twitthis.js"></script>
										<script type="text/javascript">
										<!--
										document.write('<a href="javascript:;" onclick="TwitThis.pop();"><img src="http://s3.chuug.com/chuug.twitthis.resources/twitthis_grey_72x22.gif" alt="TwitThis" style="border:none;" /></a>');
										//-->
										</script>
								   </div>
																  
						</div> 			
					</div>
					<div class="main_test"><b>Comments</b></div>
						<c:forEach items="${blogPost.sortedComments}" var="comment">
							<div style="padding:10px 0px 10px 0px;">
								<span style="font-size:12pt;">${comment.HTMLContent}</span>					
							</div>
							<div class="comment_footer">
								<span style="color:#666666;">Posted by <a href="${comment.website}">${comment.name}</a> on <fmt:formatDate  pattern="MMMM dd, yyyy, hh:mm a" value="${comment.date}" /> </span>
							</div>			
						</c:forEach>
						<form:form action="postcomment.do" commandName="comment" method="POST">	
						    <c:if test="${!empty message}">
								<div id="OuterErrorLayer">
									<div id="ErrorLayer">
										${message}
									</div>
								</div>
							</c:if> 
							<form:hidden path="blogPostId" />
							<div class="comment_box">	
								<div style="clear:both;">Your name:</div>
								<div>
									<form:input path="name" cssClass="comment_name_input"/>
								</div>
								
								<div style="clear:both;padding-top:10px;">Your website:</div>
								<div>
									<form:input path="website" value="http://" cssClass="comment_name_input"/>
								</div>								
								<div style="padding-top:10px;">	
									<span>Your comment:</span>		
								</div>
							</div>
							<div>
								<form:textarea path="content" cssClass="textArea_comment"/>	
							</div>
							<div>
								<script>
									var RecaptchaOptions = {
								    theme : 'clean'
								 };						
								</script>					
								<script type="text/javascript" src="http://api.recaptcha.net/challenge?k=6LdcfAQAAAAAAGxdFlBW9-ho93O0ysd8wWteDMvF">					
								</script>						
								<noscript>
								    <iframe src="http://api.recaptcha.net/noscript?k=6LdcfAQAAAAAAGxdFlBW9-ho93O0ysd8wWteDMvF"
								        height="300" width="500" frameborder="0"></iframe><br>
								    <textarea  id="input_type" name="recaptcha_challenge_field" rows="3" cols="40">
								    </textarea>
								    <input type="hidden" name="recaptcha_response_field" value="manual_challenge">
								</noscript>	
							</div>					
							<div style="padding-top:10px;">	
								<input type="Submit"  value="Publish your Comment" />	
							</div>
						</form:form>
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>	
				
	</tiles:putAttribute>		
</tiles:insertTemplate>	
