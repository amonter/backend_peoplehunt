<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div>
	<a href="newcrowdblogpost">New post</a>
	<a href="vieweditcrowdblog">Edit post</a>
</div>

<div class="standardSpacing">				
	<c:forEach items="${posts}" begin="0" end="4" var="post">
		<div class="blog_box">				
			<h2>					
				${post.title}		
			</h2>				
			<div>								
				<div class="blog_content">${post.content}</div>					
			</div>			
		</div>
	</c:forEach>	
</div>