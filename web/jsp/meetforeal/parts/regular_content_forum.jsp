<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="standardSpacing">		
	<div style="float:left;width:630px;">		
		<div style="clear:both;padding:0px 0px 25px 0px;">
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
		<div style="clear:both;">
			<tiles:insertAttribute name="the_page"/>											
		</div>	
	</div>
	<div style="float:left;height:550px;">
		<div style="margin:15px 0px 15px 0px;">
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content">
					<div class="recent_main">
						<div id="niceFont" style="color:#276596;font-size:24px;padding:10px 0px 10px 5px;">Recent Posts</div>
						<div id="niceFont">
							<c:forEach items="${recentPosts}" var="post">							
								<a href="retrieveforumthreads.do?postpk=${post.id}" class="recent_content">${post.title}</a>							
							</c:forEach>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>			 						 	
		</div>		
		<div style="margin:15px 0px 15px 0px;">	
			<div id="collapsible_user">			
			</div>
		</div>	
                 <div style="padding:15px 0px 15px 0px;">
                   <object type='application/x-shockwave-flash' data='images/RiseCreatives_ad.swf' width="300" height="240" allowfullscreen='true' id='showplayer'><param name='movie' value='images/RiseCreatives_ad.swf' /><param name='quality' value='best' /><param name='wmode' value='transparent' /></object>
                </div>
				
		<div>
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
	<div style="clear:both;width:336;height:280;padding-top:10px;">
		<script type="text/javascript"><!--
		google_ad_client = "pub-7717112231624442";
		/* 336x280, Bottom of page */
		google_ad_slot = "2123959065";
		google_ad_width = 336;
		google_ad_height = 280;
		//-->
		</script>
		<script type="text/javascript"
		src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
		</script>
	</div>	
	
</div>
		
