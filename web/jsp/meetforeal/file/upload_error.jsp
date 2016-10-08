<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="java.util.*" %>


<div class="standardSpacing">				
	<div class="msg">
		<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
	 	<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>Your Image file exceeds the allowed size (4Mb).</h3></div></div></div>
		<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
	</div>
	<a href="${path}">Please Try again</a>
</div>				
			