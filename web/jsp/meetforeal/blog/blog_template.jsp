<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 


<div class="standardSpacing">		
	<div style="float:left;width:630px;">		
		<div style="padding-bottom:15px;">

		</div>

		<div style="clear:both;">
			<tiles:insertAttribute name="the_page"/>											
		</div>	
	</div>
	<div style="float:left;min-height:1500px;min-width:300px;">		
	
		<div style="margin:0px 0px 15px 0px;">	
			<div id="collapsible_user">			
			</div>
		</div>

		
		<div style="max-width=280px;margin-top:15px;margin-bottom:15px;" class="main_test">
                        Check out our projects:
                </div>
                <tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">
                        <tiles:putAttribute name="content" >
                                <div style="max-width:295px;width:288px;height:260px;">
                                        <div style="height:350px;background:#FFFFFF;">
                                                <a href="http://www.crowdscanner.com" style="padding:2px;"><img width="285px" height="258px" src="http://images.meetforeal.com/images/CrowdLink.png" alt="crowdscanner iphone app for networking, ice-breaker, conversation starter, meet new people, approach, talk to strangers" /></a>
                                        </div>
                                </div>
                        </tiles:putAttribute>
                </tiles:insertTemplate>
		
<a target="itunes_store" href="http://itunes.apple.com/us/app/crowdscanner/id356256266?mt=8&amp;uo=6" style="padding: 0px 0px 0px 35px;"><img style="padding:10px 0px 10px 0px;" src="http://images.crowdscanner.com/app_store.png" alt="CrowdScanner iphone app for networking, meet new people, ice-breaker, conversation starter, talk to strangers, real life social interaction"></a>

 <tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">
                        <tiles:putAttribute name="content" >
                                <div style="max-width:295px;width:288px;height:375px;">
                                        <div style="height:465px;background:#FFFFFF;">
                                                <a href="http://faces.crowdscanner.com" style="padding:2px;"><img width="285px" height="373px" src="http://images.meetforeal.com/images/CrowdFacesLink.png" alt="crowdscanner faces iphone app for clones, find people in common with you, social discovery, meet new people" /></a>
                                        </div>
                                </div>
                        </tiles:putAttribute>
                </tiles:insertTemplate>

<a target="itunes_store" href="http://itunes.apple.com/ie/app/crowdscannerfaces/id400739223?mt=8" style="padding: 0px 0px 0px 35px;"><img style="padding:10px 0px 10px 0px;" src="http://images.crowdscanner.com/app_store.png" alt="CrowdScanner Faces iphone app for finding people with things in common, clones, social discovery, quiz"></a>


<!--	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">
			<tiles:putAttribute name="content" >
				<div style="max-width:295px;width:288px;height:343px;">
					<div style="height:343px;background:#FFFFFF;">
						<a href="http://www.meetforeal.com/browseevents.do?execution=e1s1" style="padding-left:2px;"><img width="288px" height="343px" src="http://images.meetforeal.com/images/brunch3.png" alt="brunch, meet new people in Galway, meet new people in Dublin, meet new people, conversation starters, interesting conversations, networking event, make new friends" /></a>
					</div>					
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>  
-->			
		<div style="margin:15px 0px 15px 0px;">			
			<div style="max-width:305px;">
				<div  class="main_test" style="padding-bottom:10px;">Recent Posts</div>
				<c:forEach items="${blog.blogPosts}" var="post" >				
					<span><a style="padding:3px 0px 3px 10px;display:block;color:#000000;font-size:15px;" href="postcomment.do?blogPostId=${post.id}&page=${oldPageNumber}"><b>${post.title}</b></a></span>
				</c:forEach>
				<div style="padding: 10px 0px 8px 0px;">					
				     <c:set var="old_page" value="${oldPageNumber + 1}" />
				     <c:if test="${oldPageNumber != 1}">				      	 
					 	 <a style="display:inline;color:#008ecf;" href="retrieveblog.do?page=${oldPageNumber - 1}">&#60;</a>
					 	 <a style="display:inline;" class="sidetitle" href="retrieveblog.do?page=${oldPageNumber - 1}">fresher posts!</a>
					 </c:if>
					 <c:if test="${oldPageNumber == 1}">				      	 
					 	 <span style="padding-right:70px;">&nbsp;</span>
					 </c:if>				     
					 <c:if test="${maxPage != oldPageNumber}">
					 	 <a style="display:inline;padding-left:60px;"class="sidetitle" href="retrieveblog.do?page=${old_page}">older posts</a> 	
					 	 <a style="display:inline;color:#008ecf;" href="retrieveblog.do?page=${old_page}">&#62;</a>
					 </c:if>					 					 
				</div> 		 															
			</div>									 	
		</div>
		
		<div style="max-width:280px;" class="main_test">
			About the blogger
		</div>
		<div style="padding:5px 10px 10px 10px;width:285px" >			
			<div style="padding:15px 5px 0px 0px;float:left;"><img src="http://images.meetforeal.com/images/ellen.jpg" width="100px" alt="ellen dudley co-founder of meetforeal, technology for meeting new people, ice-breakers, conversation starters, interesting conversations">
			</div>
			<div style="padding:10px 0px 5px 0px;font-size:10px;font-spacing:0.1em;line-height:120%;">
			<b>Ellen</b> is currently following her dream of doing what she loves 24/7 instead of just 3/7. <br><br>Knowing some about health and engineering, she is discovering daily about everything else, and hopes her insatiable curiosity won't kill her as it did the cat. <br><br>Inspired by those eager to share what they love about the world, she finds meeting new people consistently rewarding, hence the creation of <a href="viewpageaboutus.do" style="color:#008ecf;text-decoration:underline;padding-left:0px;">meetforeal</a>.
			</div>
		</div>
		<div style="max-width:280px;color:#008ECF;padding:5px 0px 5px 0px;" >
		  <a style="color:#008ECF;font-size:0.9em;"  href="http://feedburner.google.com/fb/a/mailverify?uri=MeetforealBlog&amp;loc=en_US">Subscribe to The Meetforeal Blog by Email</a>
		</div>			
		<div><object width="288" height="216"> <param name="flashvars" value="offsite=true&lang=en-us&page_show_url=%2Fphotos%2F47078247%40N08%2Fsets%2F72157623304147950%2Fshow%2F&page_show_back_url=%2Fphotos%2F47078247%40N08%2Fsets%2F72157623304147950%2F&set_id=72157623304147950&jump_to="></param> <param name="movie" value="http://www.flickr.com/apps/slideshow/show.swf?v=71649"></param> <param name="allowFullScreen" value="true"></param><embed type="application/x-shockwave-flash" src="http://www.flickr.com/apps/slideshow/show.swf?v=71649" allowFullScreen="true" flashvars="offsite=true&lang=en-us&page_show_url=%2Fphotos%2F47078247%40N08%2Fsets%2F72157623304147950%2Fshow%2F&page_show_back_url=%2Fphotos%2F47078247%40N08%2Fsets%2F72157623304147950%2F&set_id=72157623304147950&jump_to=" width="288" height="216" style="margin-top:20px; margin-left:4px;"></embed></object>
	</div>	
		<div style="margin-top:25px;">
		
		
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
		</div> 
                <div style="max-width:280px;margin-top:15px;" class="main_test">
			Stuff I like to read
		</div>
		<div style="padding:10px 10px 10px 10px;width:285px;font-size:11px;font-spacing:0.1em;line-height:140%;" >			
                 	<a href="http://theladylovesbooks.blogspot.com/" style="color:#008ecf;">A Word Of One's Own</a><br>
			<a href="http://blogs.discovermagazine.com/badastronomy/" style="color:#008ecf;">Bad Astronomy</a><br>
			<a href="http://www.consequentialstrangers.com/" style="color:#008ecf;">Consequential Strangers</a><br>
			<a href="http://www.contrast.ie/blog//" style="color:#008ecf;">Contrast: The Blog</a><br>
			<a href="http://www.eventcoup.com/" style="color:#008ecf;" >Event Managers Blog</a><br>
			<a href="http://garvangallagher.wordpress.com/" style="color:#008ecf;">Garvan Gallagher</a><br>
			<a href="http://www.jamesgallagher.ie/" style="color:#008ecf;" >James Gallagher</a><br>
			<a href="http://www.jameskennedy.ie" style="color:#008ecf;" >James Kennedy: The Goose</a><br>
			<a href="http://scienceblogs.com/cortex/" style="color:#008ecf;">Jonah Lehrer</a><br>
			<a href="http://www.verticalbones.com" style="color:#008ecf;">Justin Knecht</a><br>
			<a href="http://www.adaringadventure.com/blog/wordpress/" style="color:#008ecf;">Life Coach Blog: The Discomfort Zone</a><br>
			<a href="http://madebymany.co.uk/" style="color:#008ecf;">Made by Many</a><br>
			<a href="http://patphelan.net/ " style="color:#008ecf;">Pat Phelan</a><br>
			<a href="http://www.penhire.blogspot.com/" style="color:#008ecf;">Penhire</a><br>
			<a href="http://richardconroy.blogspot.com/" style="color:#008ecf;" >Richard Conroy</a><br>
			<a href="http://robots.ie/" style="color:#008ecf;" >Robots.ie</a><br>
			<a href="http://www.sabrinadent.com/" style="color:#008ecf;">Sabrina Dent</a><br>
			<a href="http://sethgodin.typepad.com/" style="color:#008ecf;" >Seth's Blog</a><br>					
			<a href="http://stancarey.wordpress.com/" style="color:#008ecf;">Stan Carey</a><br>
			<a href="http://www.rolfnelson.com/" style="color:#008ecf;" >The Rational Entrepreneur</a><br>
			<a href="http://theschooloflife.typepad.com/" style="color:#008ecf;">The School of Life</a><br>
									
		</div>            	
			
			 			
	</div>	
</div>
