<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/generalScripts.js"></script>

<div id="expand">
  <input class="login_button" type="button"  value="Create video" onclick="expandAndColapseAll('expand','create_video_area');"/> 
</div>

<div id="collapse" style="padding: 10px 0pt 0pt 50px;display:none;">
  <div style="float:left;">
	  <input class="login_button" type="button"  value="Collapse" onclick="expandAndColapseAll('collapse','create_video_area');"/>
  </div>
</div>
 <p/>	
  <form:form commandName="video" action="createVideo.do" method="POST" enctype="multipart/form-data">
    <div style="padding:.5% 0 0 3%;">
	    <spring:hasBindErrors name="video">
				<div id="OuterErrorLayer">
					<div id="ErrorLayer">
						<form:errors path="*" />
					</div>
				</div>
		</spring:hasBindErrors>
	</div>	
    <div class="create_video_area" id="create_video_area">
	    <table width="610px;">			
			<tr>
				<td class="create_video_label">Video Path:</td>
				<td class="create_video_value"><input type="text" name="videoPath" value="${video.videoPath}" id="input_type"/></td>
				<td class="create_video_label">Video Title:</td>
				<td class="create_video_value"><input type="text" name="videoTitle" value="${video.videoTitle}" id="input_type"/></td>    
		    </tr>		    
		    <tr>
				<td class="create_video_label">Image File:</td>
				<td class="create_video_value"><input type="file" id="input_type" name="multipartFile" size="10" value="${video.multipartFile}"/></td>
				<td class="create_video_label">Category:</td>
				<td class="create_video_value">
				   <select name="categoryId">
				        <c:forEach items="${category}" var="category">
				          <c:choose>
				           <c:when test="${category.id == video.categoryId}">
				             <option value="${category.id}" selected="selected">${category.categoryName}</option>
				           </c:when>
				           <c:otherwise>
				             <option value="${category.id}">${category.categoryName}</option>
				           </c:otherwise>
				          </c:choose>  
				        </c:forEach>
					</select>
				</td>    
		    </tr>		    
		    <tr>
				<td class="create_video_label">Description:</td>
				<td class="create_video_value" colspan="3">
				  <input type="text" name="videoDescription" id="input_type" size="72" value="${video.videoDescription}"/>
				</td>
		    </tr>
		    <tr>
				<td class="create_video_label">Short Link:</td>
				<td class="create_video_value" colspan="3">
				  <input type="text" name="shortLink" id="input_type" size="72" value="${video.shortLink}"/>
				</td>
		    </tr>
		    <tr>
				<td class="create_video_label">Update thumbnail:</td>
				<td class="create_video_value" colspan="3">
				  <form:checkbox path="updateThumbnail"  value="false"/>
				</td>
		    </tr>					   
		   <tr>
				<td colspan="4" style="text-align: center; height: 20px;">
				   <div>
						<input class="login_button" type="submit"  value="Save" />
				   </div>
				</td>
		   </tr>				 
	  </table>
 </div>	
 <c:set var="fileId" value="${video.file.id}"/>
 <c:if test="${video.file == null}">
   <c:set var="fileId" value="${video.fileId}"/>
 </c:if>
  <c:set var="fileThumbId" value="${video.thumbnailFile.id}"/>
 <c:if test="${video.thumbnailFile == null}">
   <c:set var="fileThumbId" value="${video.fileIdThumbnail}"/>
 </c:if>
 <input type="hidden" name="fileId" value="${fileId}"/>
 <input type="hidden" name="fileThumbId" value="${fileThumbId}"/>
 <input type="hidden" name="id" value="${video.id}"/>  
 </form:form>
 
 <jsp:include page="/jsp/video/videoList.jsp"/>
 
 <c:if test="${error == 'true' || expandEditVideo == 'true'}">
 <script>
   expandAndColapseAll('expand','create_video_area');
 </script>
</c:if>
