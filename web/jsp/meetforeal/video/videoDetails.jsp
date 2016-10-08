<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>

<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/tabs/login/login.nocache.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/generalScripts.js"></script>

<meet:currentUserInSession profileId="${sessionScope.userSessionContext.profileID}" />


<div class="standardSpacing" style="min-height:200px;">	
	<div style="float:left;padding-right:15px;">
		<div style="padding-bottom:15px;">
			<script type="text/javascript"><!--
			google_ad_client = "pub-7717112231624442";
			/* 468x60, created 27/06/09 */
			google_ad_slot = "6219966643";
			google_ad_width = 468;
			google_ad_height = 60;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>		
		</div>
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		     <tiles:putAttribute name="content" >
		     	<div class="top_header">
		     		<img src="images/video.png" style="padding:2px 0px 2px 4px;" />
		     	</div>		
				<div class="video_details_div" style="padding-bottom:30px;">						
					<table>
						<tr>
							<td colspan="2">
								<div class="video_box_title" >				
									<h2 id="niceFont" style="color:#008ECF;font-size:18pt;font-weight:bold;max-width:550px;">					
										${video.videoTitle}		
									</h2>
								</div>									
								<div style="padding:20px 0px 20px 0px;">
								   ${video.videoPath}
								</div>
							 </td>
						</tr>
						<tr>
							<td colspan="2">
								<div  style="height:60px;width:530px;">
									<div style="font-size:9pt;float:left;padding:14px 85px 0px 0px;">
									  <fmt:formatDate pattern="MMMM dd, yyyy" value="${video.creationDate}" />									  
									</div>
									<div style="float:left;padding-right:8px;">	
										<a style="display:inline;display:block;" target="_blank" href="http://www.twitter.com/home?status=@meetforeal+${video.shortLink}"><img style="vertical-align:middle;" src="images/TwitterIcon.jpg" /></a>
										<a style="display:inline;display:block;font-size:59%;line-height:82%;" target="_blank" href="http://www.twitter.com/home?status=@meetforeal+${video.shortLink}"><b>Retweet</b></a>															
									</div>
									<div style="float:left;margin-left:0px;">									    
							      		<a href="javascript:doIlikeVideos();"><img src="images/ilikethisvideo.jpg" /></a>										    
									</div>
								</div>
								<div  style="height:90px;width:420px;padding-top:15px;">
									 <div>${video.videoDescription}</div>	
								</div>									
							<td>
						</tr>					            
			            <c:forEach items="${video.videoCommentCollection}" var="comment">    			
							<tr>
								<td colspan="2">
									  <div class="video_comment_box">
										  <div style="padding-bottom:10px;">${comment.content}</div>	
										  <div class="comment_footer">											  
											  <span style="color:#666666;">
													Posted by <a href="retrieveuserhome.do?profileid=${comment.profile.id}" >${comment.profile.user.userName}</a> on <fmt:formatDate  pattern="yyyy/MM/dd hh:mm a" value="${comment.creationDate}" />
											  </span>
										  </div>
									  </div>										  
								 </td>
							</tr>
						</c:forEach>								
						<tr>
							<td colspan="2" style="text-align:center;">
							  <div id="comment_button" style="height:40px;padding-top:40px;">
							    <c:choose>
							      <c:when test="${empty userLogged}">
							        <div id="login_2" style="display:block;">
							        	<a href="javascript:doAddComments();"><img src="images/addcomment.jpg" /></a>
							        </div>
							        <div id="user_logged_in_add_comment" style="display:none;">
							           <input type="image" 
							           		onclick="expandAndCollapse('add_comment_section');" src="images/addcomment.jpg"/>
							         </div>
							      </c:when> 
							      <c:otherwise>
							         <div id="user_logged_in_add_comment" style="padding-top:20px;" >
							           <input type="image" src="images/addcomment.jpg"
							           		onclick="expandAndCollapse('add_comment_section');" />
							         </div>
							      </c:otherwise>
							    </c:choose>  
							  </div> 		
							</td>
						</tr>
					</table>
					<form:form commandName="video" action="addcommenttovideo.do" method="POST">
						<div style="display: none; padding-left: 2%;" id="add_comment_section">
							<table>
								<tr>
									<td>Comment :</td>
									<td><textarea rows="" cols="50" name="comment" id="commentId"></textarea></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
									   <input src="images/Save.jpg" type="image" style="display:inline;" value="Save" />
									   <a href="javascript:expandAndCollapse('add_comment_section');"><img src="images/cancel.jpg" /></a	>							  
									</td>
								</tr>
							</table>
						</div>
					   <input type="hidden" name="videoId" value="${video.id}">
					   <input type="hidden" name="type" value="${public}">
			       </form:form>				 
				   <div id="VIDEO_ID_IS" style="display: none">${video.id}</div>
				   </div>
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>		
		<c:if test="${comment == 'true'}">
			<script>
				document.getElementById('add_comment_section').style.display = "block";
				document.getElementById('command_button').style.display = "none";
			</script>
		</c:if>
	</div>
	<div style="float:left;">
		<div style="margin:0px 0px 15px 0px;width:300px;">	 
			<div id="collapsible_user">			
			</div>
		</div>	
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		     <tiles:putAttribute name="content" >
				<div class="top_header"><img  style="padding:2px 0px 2px 4px;" src="images/peoplewholiked.png"></div>	
				<div style="width: 36%;">
					<div class="topPannel" style="width:288px;">
					   <c:forEach items="${iLikeVideoList}" var="iLikeVideo">   
						   <c:if test="${iLikeVideo.profileId.user.imageID != -1}" >
						   		<c:set var="imageId" value="${iLikeVideo.profileId.user.imageID}" />
						   </c:if>
						   <c:if test="${iLikeVideo.profileId.user.imageID == -1}" >
						   		<c:set var="imageId" value="${eventHistory.profileId.user.gender}" />										
						   </c:if>	
					       <div class="fullCell">
					           <div id="profieleImage">
						           <a href="retrieveuserhome.do?profileid=${iLikeVideo.profileId.id}">
						           		<img src="picture_library/profile/${imageId}.jpg" width="40" height="40"/>
						           </a>		
					           </div>
					           <div id="profileLabels">
					          	   <div>
						               <a href="retrieveuserhome.do?profileid=${iLikeVideo.profileId.id}" style="color:#336699;padding-left:0;">${iLikeVideo.profileId.user.userName}</a>  
							           <span style="color:#999999">
							                on <fmt:formatDate pattern="MMM dd" value="${iLikeVideo.createDate}" />
							           </span>
					          	   </div>
					          	   <div>
					          	   		${iLikeVideo.profileId.user.state}, ${iLikeVideo.profileId.user.country}
					          	   </div>
					           </div>
					       </div>
					   </c:forEach>   
					   <div class="fullCell" id="newILikeVideo" style="display:none;">
					   </div>	     
					</div> 
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>
		<div style="padding-top:10px;">
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		     <tiles:putAttribute name="content" >
				<div class="top_header"><img  style="padding:2px 0px 2px 4px;" src="images/Othervideos.png"></div>	
				<div style="width:288px;max-width:288px;">
					<div class="topPannelVideo">
					   <c:forEach items="${videoList}" var="videoItem">   
						   <div class="fullCellVideo">
						   		<a href="displayvideo.do?videoId=${videoItem.id}" style="float:left;padding-right:6px"><img src="picture_library/profile/${videoItem.thumbnailFile.id}.jpg" /></a>
						   		<div  style="float:left;font-size:70%;font-weight:bold;max-width:160px;width:160px;padding-left:0px;line-height:120%;"><a style="padding-left:0px;" href="displayvideo.do?videoId=${videoItem.id}">${videoItem.videoTitle}</a></div>
						   		<div  style="float:left;font-size:55%;font-style:italic;padding-left:0px;">Posted: <fmt:formatDate pattern="MMM yyyy" value="${video.creationDate}" /></div>
						   </div>
					   </c:forEach>   					  	     
					</div> 
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>			
		</div>
                <div style="padding:15px 0px 20px 0px;display:block;">
			<script type="text/javascript"><!--
			google_ad_client = "pub-7717112231624442";
			/* 300x250, created 1/20/09 */
			google_ad_slot = "2939597014";
			google_ad_width = 300;
			google_ad_height = 250;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
		</div>  		
	</div>
</div>
<script language='javascript'>

function doIlikeVideos() {
	
	popupIlikeVideos();	
}

function doAddComments() {
	
	addComment();	
}

</script>	    
