	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<script type="text/javascript"  src="http://api.recaptcha.net/js/recaptcha_ajax.js"></script>
<script type="text/javascript" src="js/recaptchaimpl.js"></script> 

<div class="navigationLayer">
	<ul>
		<li>
			<a href="browseforums.do">meetforeal forum</a> >>
		</li>
 		<li> 
 			<h1>
 				<a  href="#"><c:out value="${forum.topic}" /></a>
 			</h1>
 		</li>
	</ul>
</div>
<meet:format length='36' value='${forum.topic}' printValue='false'/>

<tiles:insertTemplate template="/jsp/parts/regular_content_forum.jsp">
	<tiles:putAttribute name="title_style" value="forum_title"/>	
	<tiles:putAttribute name="sub_title" value="${formatted_value}"/>	
	<tiles:putAttribute name="the_page">	
		<div>								  				  			
			<div>		
				<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
					<tiles:putAttribute name="content" value="/jsp/forum/post_table.jsp" />
				</tiles:insertTemplate>			
			</div>	
			<div style="margin:10px 0px 0px 500px;" >	
				<a href="javascript:callNewThread('${forum.id}','${(fn:length(forum.post) + 1 ) / 10}');">
					<img style="vertical-align:middle;" src="images/New_thread.png"/>&nbsp;New Thread													
				</a>
			</div>
		</div>
	</tiles:putAttribute>		
</tiles:insertTemplate>	
		
<script language='javascript'>

function callNewThread (a, b) {
		
	newThread(a,b);	
} 

function callme () {
	
	var attributes =document.getElementById("lastPageRef").attributes;
	for (i=0; i < attributes.length; i++) {
	
		if (attributes[i].name == 'href')
		{
			var regex = new RegExp("-p=[0-9]*"); 
			var replaced = attributes[i].value.replace(regex,'-p='+pageNumber);
			location.href = replaced;			
			
		}		
	}

}
</script> 