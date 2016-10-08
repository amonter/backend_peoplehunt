<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="meet" uri="meetforealtags" %>


<div style="padding:15px;width:265px;">	
	<div>
		<div class="blog_frame_fp">
			<a style="color:#666666;" href="retrieveforumthreads.do?postpk=${todaysThread.post.id}"><b>News Feed</b></a>			
		</div>
		<div style="background-color:#FFFFFF;padding:0px 8px 5px 10px;">	
			<h2 class="header_blog">
				<a style="color:#CA090C;" href="retrieveforumthreads.do?postpk=${todaysThread.post.id}">${todaysThread.post.title}</a>				
			</h2>
			<div style="padding-bottom:5px;">				
				<meet:format length='80' value='${todaysThread.content}'/><a href="retrieveforumthreads.do?postpk=${todaysThread.post.id}">more</a>			
			</div>
		</div>	
		<div style="background-color:#FFFFFF;padding:0px 8px 5px 10px;">	
			<h2 class="header_blog">
				<a style="color:#CA090C;" href="retrieveforumthreads.do?postpk=${todaysThread.post.id}">${todaysThread.post.title}</a>				
			</h2>
			<div style="padding-bottom:5px;">				
				<meet:format length='80' value='${todaysThread.content}'/><a href="retrieveforumthreads.do?postpk=${todaysThread.post.id}">more</a>			
			</div>
		</div>		
	</div>	
</div>