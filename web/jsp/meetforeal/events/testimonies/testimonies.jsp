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


<tiles:insertTemplate template="/jsp/parts/regular_content.jsp">
    <tiles:putAttribute name="title_style" value="main_title"/>				
	<tiles:putAttribute name="sub_title" value="What others are saying" />	
	<tiles:putAttribute name="the_page">
		<div>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content">
					<ul class="testimony">
						<c:forEach items="${testimonies}" var="testimony">
							<li>
								<div style="float:left;float:left;overflow:hidden;text-align:center;">
									<c:if test="${profile.user.imageID != -1}" >
										<c:set var="imageId" value="${testimony.profile.user.imageID}" />
									</c:if>
									<c:if test="${user.imageID == -1}" >
										<c:set var="imageId" value="${testimony.profile.user.gender}" />										
									</c:if>	
									<a href="retrieveuserhome.do?profileid=${testimony.profile.id}"><img src="picture_library/profile/${imageId}.jpg" width="100" height="100" /></a>									
								</div>
								<div style="margin:">
									<p style="margin-left:120px;">
										<span class="quote">&#8220;</span>
										<span style="color:black;font-size:16px;">${testimony.content}</span>	
										<span class="quote">&#8221;</span>
									</p>
									<p style="margin:10px 0px 0px 120px;font-size:14px;">
										<span><b>${testimony.profile.user.lastName}</b>,</span>
										<span>&nbsp;<fmt:formatDate pattern="MMM dd, yyyy" value="${testimony.date}" /></span>
									</p>
								</div>
							</li>
						</c:forEach>
					</ul>						
				</tiles:putAttribute>
			</tiles:insertTemplate>
		</div>
	</tiles:putAttribute>		
</tiles:insertTemplate>	


