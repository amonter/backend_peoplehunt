<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<c:set var="url_prefix" value="do" />
<c:if test="${public == 'false'}">
	 <c:set var="url_prefix" value="htm" />
</c:if>


<div class="standardSpacing" style="min-height:200px;">	
	<div style="float:left;padding-right:15px;">
		<div style="padding-bottom:10px;">
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
		<div>	
			<div style="padding-bottom:15px;">
				<div style="display:inline;padding-right:50px;">
					<a href="listvideos.${url_prefix}?isPublic=${public}">All Categories </a>
				</div>	
				<c:forEach items="${category}" var="cat">	
					<c:if test="${selectedCategory == cat.id}">
						<c:set var="the_style" value="font-weight:bold;"/>
					</c:if>	
					<c:if test="${selectedCategory != cat.id}">
						<c:set var="the_style" value=""/>
					</c:if>		
					<div style="border-right:1px solid #CCCCCC;display:inline;padding:0 10px;">		
						<a style="${the_style}" class="selectVideo" href="listvideosbycategory.${url_prefix}?categoryId=${cat.id}&isPublic=${public}">${cat.categoryDescription}</a> 
					</div>			
				</c:forEach>
			</div>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content">
					<div class="top_header">
						<img style="padding-left:5px;" src="images/videos.png">
					<div>
					<table id="forum" style="width:610px;background-color:white;padding:6px;">
						<tr>							
							<c:if test="${public == 'false'}">
								<th>Action</th>
							</c:if>	
						</tr>		
						<c:forEach items="${videoList}" var="video">				
							<tr height="150px;">
								<td class="video_image">
								   <div class="image_section">
									   <c:if test="${public == 'false'}"> 
										    <a href="displayvideo.htm?videoId=${video.id}">
										     	<img src="picture_library/profile/${video.file.id}.jpg" width="200"/>
										    </a>
									   </c:if>	
									   <c:if test="${public == 'true'}"> 
										    <a href="displayvideo.do?videoId=${video.id}">
										     	<img src="picture_library/profile/${video.file.id}.jpg" width="280"/>
										    </a>
									   </c:if>	    
								   </div>
								   <div class="image_description"> 
								     <div id="niceFont" ><a style="color:#008ECF;font-size:15pt;" href="displayvideo.do?videoId=${video.id}">${video.videoTitle}</a></div>
								     <div>
								       <span style=""><fmt:formatDate pattern="MMM dd, yyyy" value="${video.creationDate}" /></span>
								     </div>  
								   </div>
								</td>
								<c:if test="${public == 'false'}">
									<td>
										<a href="deletevideos.htm?videoId=${video.id}">Delete</a>|
										<a href="editvideos.htm?videoId=${video.id}">Edit</a>
									</td>			  		
								</c:if>
							</tr>
						</c:forEach>			
					</table>
				</tiles:putAttribute>
			</tiles:insertTemplate>								
		</div>
	</div>
	<div style="float:left;">
		<div style="margin:0px 0px 15px 0px;width:300px;">	 
			<div id="collapsible_user">			
			</div>
		</div>		
<!--		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">
			<tiles:putAttribute name="content" >
	                  <div style="max-width:295px;width:288px;height:343px;">
					<div style="height:343px;background:#FFFFFF;">
						
                        <div>
                          <a href="http://www.meetforeal.com/browseevents.do?execution=e1s1"><img width="288px" height="343px" src="http://images.meetforeal.com/images/brunch3.png" alt="meet people at meetforeal brunch in galway and dublin in march " /></a>


</div>
											
                       																	
					</div>		
			</tiles:putAttribute>
		</tiles:insertTemplate>
-->
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">
				<tiles:putAttribute name="content" >
				<div style="width:288px" >		
					<div class="top_header" style="height:34px;">
							<div style="float:left;font-size:84%;padding:5px 0px 2px 5px;"><b>Follow<a style="color:#008ECF;" target="_blank" href="http://www.twitter.com/meetforeal">@meetforeal</a> on Twitter</b></div>
							<div><a target="_blank" href="http://www.twitter.com/meetforeal"><img style="padding:2px 3px 2px 5px;float:right;"src="images/TwitterIcon.jpg" width="30px" height="30px" /></a></div>
							
						</div>
						<div style="max-width:288px;width:288px;padding:12px 0px 6px 5px;background:#f1f1f1;">
							<c:forEach items="${tweets}" var="tweet" begin="0" end="3">
								<div>
									<img style="float:left;padding-right:16px;" src="${tweet.profileImageUrl}" />
									<div class="twitter_div">
										<a target="_blank" href="http://www.twitter.com/${tweet.fromUser}"><b>${tweet.fromUser}:</b></a>
										<span>${tweet.text}</span>
									</div>						
								</div>
							</c:forEach>													
						</div>
					</div>
				</tiles:putAttribute>
		</tiles:insertTemplate>		
		<div style="padding-top:10px;">
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
