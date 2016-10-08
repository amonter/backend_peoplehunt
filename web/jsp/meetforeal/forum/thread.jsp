<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>
<script type="text/javascript"  src="http://api.recaptcha.net/js/recaptcha_ajax.js"></script>
<script type="text/javascript" src="js/recaptchaimpl.js"></script> 


<div class="navigationLayer">
	<ul>
		<li>	
			<a href="browseforums.do">meetforeal Forum</a> >
		</li>
		<li> 
			<a href="retrieveforumposts.do?forumpk=${post.forum.id}"><c:out value="${post.forum.topic}" /></a> >
		</li>
		<li>
			<h1>
				<a href="retrieveforumthreads.do?postpk=${post.id}"><c:out value="${post.title}" /></a>
			</h1>
		</li>
	</ul>
</div>

<meet:format length='36' value='${post.title}' printValue='false'/>

<tiles:insertTemplate template="/jsp/parts/regular_content_forum.jsp">
	<tiles:putAttribute name="title_style" value="forum_title"/>				
	<tiles:putAttribute name="sub_title" value="${formatted_value}"/>	
	<tiles:putAttribute name="the_page">
		<div>								  				  			
			<div>
				<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
					<tiles:putAttribute name="content" value="/jsp/forum/thread_table.jsp" />
				</tiles:insertTemplate>		
			</div>	
			<div style="margin:10px 0px 0px 500px;">
				<a href="javascript:callThreadReply('${post.id}','<meet:format escapedScript='true'  value='${post.title}' />','${(fn:length(post.sortedThreads) +1 ) / 10}');" >
					<img style="vertical-align:middle;" src="images/Post_reply.png"/>Post Reply									
				</a>
			</div>
		</div>
	</tiles:putAttribute>		
</tiles:insertTemplate>	


<script language='javascript'>

function callThreadReply(a,b,c) {
		
	threadReply(a,b,c);	
} 

</script> 
		