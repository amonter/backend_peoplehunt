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


<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
	<tiles:putAttribute name="content">
               <div class="blog_frame_fp" style="margin:8px 0px 8px 8px;" >
			<span style="font-size:18px;"><b>Participants (Show My Stuff)</b></span>
		</div>
		<ul class="testimony">
			<c:forEach items="${presenters}" var="profile">
				<li>
					<div style="float:left;float:left;overflow:hidden;text-align:center;">
						<c:if test="${profile.user.imageID != -1}" >
							<c:set var="imageId" value="${profile.user.imageID}" />
						</c:if>
						<c:if test="${user.imageID == -1}" >
							<c:set var="imageId" value="${profile.user.gender}" />										
						</c:if>	
						<a href="retrieveuserhome.do?profileid=${profile.id}"><img src="picture_library/profile/${imageId}.jpg" width="100" height="100" /></a>									
					</div>
					<div style="margin:">
						<p style="margin-left:120px;">
							<span class="quote">&#8220;</span>
							<span style="color:black;font-size:16px;">${profile.presentationContent}</span>	
							<span class="quote">&#8221;</span>
						</p>
						<p style="margin:10px 0px 0px 120px;font-size:14px;">
							<span><b>${profile.user.lastName}</b></span>							
						</p>
					</div>
				</li>
			</c:forEach>
		</ul>						
	</tiles:putAttribute>
</tiles:insertTemplate>