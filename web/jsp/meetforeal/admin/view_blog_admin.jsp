<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div>
	<a href="newblogpost.htm">New post</a>
	<a href="viewblogposts.htm">Edit post</a>
</div>
<div>
	<tiles:insertAttribute name="the_blog" />
</div>