<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertTemplate template="/jsp/crowdscanner/blog/theblog_template.jsp">   	
	<tiles:putAttribute name="the_page">	
			<div style="padding:0px 15px 0px 15px;">
				<h2 class="blog_title">
					${theBlogPost.title}				
				</h2>
				<div style="padding-bottom:5px;">
					${theBlogPost.content}		
				</div>
				<div style="font-size:1.2em;padding-top:25px;"><b>Comments</b></div>
				<c:forEach items="${theBlogPost.sortedComments}" var="theComment">
					<div style="padding:10px 0px 10px 0px;">
						<span style="font-size:12pt;">${theComment.content}</span>					
					</div>
					<div class="comment_footer">
						<span style="color:#666666;">Posted by <a href="${theComment.website}">${theComment.name}</a> on <fmt:formatDate  pattern="MMMM dd, yyyy, hh:mm a" value="${comment.date}" /> </span>
					</div>			
				</c:forEach>
				<form:form action="/postcomment.do" commandName="comment" method="POST">	
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
						<script type="text/javascript" src="http://api.recaptcha.net/challenge?k=6LcLhwsAAAAAAITKpDHaFEu-7jgO0F6RHVpaJg2f">					
						</script>						
						<noscript>
						    <iframe src="http://api.recaptcha.net/noscript?k=6LcLhwsAAAAAAITKpDHaFEu-7jgO0F6RHVpaJg2f"
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

