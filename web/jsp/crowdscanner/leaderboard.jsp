<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>



<c:forEach items="${theProfiles}" var="profile">
			<div class="question_top" >
				<c:out value="${profile.profileInfo.selfPerception}" />				
			</div>
			<div class="question_frame" style="height:65px;margin-bottom:10px;">						
				<div class="inner_photo">					
					<img width="38px;" src="${profile.profileImageUrl}"/>								
				</div>
				<div class="content_top">
					<span class="theusername"><c:out value="${profile.user.lastName}" /></span>													
				</div>
				<div style="float:right;padding:30px 30px 0px 0px;">					
				</div>		
			</div>	
</c:forEach>
