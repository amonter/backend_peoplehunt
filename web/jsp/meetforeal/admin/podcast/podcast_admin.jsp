<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div style="margin:10px 0px 20px 20px;">	
	<a href="publishpodcast.htm"> Publish Podcast</a>
</div>

<div style="margin:10px 0px 20px 20px;">
	<display:table list="${podlist}" id="podcast" pagesize="20" requestURI="retrieveadminpodcast.htm"  class="forumtable">					 
		<display:column title="Title">				
			<c:out value="${podcast.title}" />
		</display:column>				
		<display:column title="Published Date">				
			<c:out value="${podcast.publishedDate}" />
		</display:column>				
		<display:column title="Description">				
			<c:out value="${podcast.description}" />	
		</display:column>
		<display:column >				
			<a href="publishpodcast.htm?podcastid=${podcast.id}">Edit</a>	
		</display:column>
	</display:table>
</div>