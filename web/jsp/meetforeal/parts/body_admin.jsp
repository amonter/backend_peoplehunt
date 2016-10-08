<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  

<div>
	<tiles:insertAttribute name="searchprofile_admin" /> 		  					  		
</div>			
<div>
	<display:table list="${profile}" id="userprofile" pagesize="20" requestURI="adminsearchusers.htm" class="forumtable">
		<display:column>
			<table width="100" height="100">
				<tr>
					<td>
						<div>							
							<c:if test="${userprofile.user.imageID != -1}" >
								<c:set var="imageId" value="${userprofile.user.imageID}" />
							</c:if>
							<c:if test="${userprofile.user.imageID == -1}" >
								<c:set var="imageId" value="${userprofile.user.gender}" />										
							</c:if>										
							<a alt="${userprofile.user.firstName} ${userprofile.user.lastName}"  title="${userprofile.user.firstName} ${userprofile.user.lastName}" href="retrieveuserhome.do?profileid=${userprofile.id}">
								<img src="picture_library/profile/${imageId}.jpg" width="70" height="70" />
							</a>									
							<span><c:out value="${userprofile.user.userName}" /></span>								
						</div>
					</td>
					<td>
						<c:if test="${!empty userprofile.user.age}" >
							<nobr>Age: <span><c:out value="${userprofile.user.age}" /></span></nobr>
						</c:if>
						<span>
							<c:out value="${userprofile.user.country}" />
						</span>									
						<div class="messagepopup" id="sendmessagewidget_${userprofile.id}"><div id="param" style="visibility:hidden;">${userprofile.id},${userprofile.user.userName}</div></div>																								
						
					</td>
				<tr>
			</table>							
		</display:column>
		<display:column title="Actions">
			<a href="admindeleteprofile.htm?profileid=${userprofile.id}">Delete me</a>
		</display:column>																							
	</display:table>			
</div>				