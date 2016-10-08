<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div style="padding-left:25px;max-width:470px;">
		<div style = "width:470px;text-align:left;color:#333333;font-size:30px;font-family:helvetica;line-height:110%;padding:20px 0px 10px 0px;"	>
			
			
			Start conversations by discovering cool facts about the "strangers" around you, face to face, and break the status quo... <br><br><span style="color:#ffffff;background-color:#FF0074;">because serendipitous encounters make you feel good, and change the way you see the world</span>
			
			
			<br><br><span style="color:#999999;font-size:20px;">Designed based on sociological & anthropological research*</span>			
		</div>
</div>		
		<div style="padding-left:10px;max-width:450px;" >
		<div style="padding:10px 10px 10px 130px;">
				<a  href="http://itunes.apple.com/us/app/crowdscanner/id356256266?mt=8&uo=6" target="itunes_store">
				<img style="padding:0px 0px 0px 0px;" width="220" alt="talking to strangers, meet new people, have cool conversations, crowdscanner iphone app, networking" src="http://images.crowdscanner.com/app_store.png" /></a>
			</div>
		<div style="clear:both;max-width:450px;">
			<img style="padding:0px 0px 0px 15px;" width="450" height="823"  src="http://images.crowdscanner.com/iphone_front.png" alt="talking to strangers, meet new people, approach, pick up, have cool conversations, crowdscanner iphone app, technology for networking">	
		</div>
		<div style="color:#333333;width:500px;" class="textlinks">	
			<div style="float:left;width:200px;text-align:center;padding:0px 0px 30px 25px;">
				<span style="background-color:#FFF200;">"It was simply one of those apps that I have long awaited"</span> Micah, Canada
			</div>
			
			<div style="float:left;width:200px;text-align:center;padding:0px 0px 30px 30px; ">
				<span style="background-color:#FFF200;">"I took your app to a party, and everyone wanted this app for their phone. Very impressed"</span> Travis, US
			</div>
			
		</div>
</div>
	<div style="clear:both;max-width:470px;">
		<div style="float:left;width:470px;" >
			<h2 class="header_blog" style="line-height:110%;padding:30px 0px 20px 20px;">
				<span style="color:#ff0074;">Conversations happening now:</span>				
			</h2>
			<c:forEach items="${theList}" var="the_question">
				<div class="question_top" >
					<c:out value="${the_question.questionDescription}" />				
				</div>
				<div class="question_frame" style="height:65px;margin-bottom:10px;">						
					<div class="inner_photo">					
						<img width="42px;" src="http://images.crowdscanner.com/anon_42x42.png">								
					</div>
					<div class="content_top">
						<span class="theusername"><c:out value="${the_question.profiles[0].user.userName}" /></span>								
						<span class="quiz_statement">
						asked ${the_question.totalAnswerCount} people in <span style="font-weight:bold;font-size:1.1em;">${the_question.country}, ${the_question.state}</span>
						</span>									
					</div>
					<div style="float:right;padding:30px 10px 0px 0px;font-size:12px;">
						<a href="user/${the_question.questionPhoneId}/${the_question.profiles[0].user.userName}">View Graphs</a>
					</div>			
				</div>	
			</c:forEach>	

		</div>	
		<div style="padding:40px 10px 30px 140px;">
				<a  href="http://itunes.apple.com/us/app/crowdscanner/id356256266?mt=8&uo=6" target="itunes_store">
				<img width="220" alt="talking to strangers, meet new people, have cool conversations, crowdscanner iphone app, networking" src="http://images.crowdscanner.com/app_store.png" /></a>
		</div>		

	<!--<div style="float:left;width:300px;">
			<h2 class="header_blog" style="line-height:110%;padding:30px 0px 20px 20px;">
				<span style="color:#87098A;">How to use it:</span>				
			</h2>		
			<div >
				<img style="padding:0px 20px 10px 40px;" src="http://images.crowdscanner.com/how_to4.png" width="250" height="1069" alt="talking to strangers, meet new people, approach, pick up, have cool conversations, crowdscanner iphone app, technology for networking">	
			</div>
			<div style="padding-left:30px;" >
				<a href="http://www.youtube.com/watch?v=uoyQo7y6Vb0" style="line-height:110%;font-size:18px;padding-left:15px;font-weight:bold;">Watch a clip of the app in action</a>
			</div>	
	</div>-->
	<div>
		<span style="color:#999999;font-size:12px;">&nbsp *check out our presentation on slideshare <a href="http://www.slideshare.net/Meetforeal/designing-for-socialisation">here</a></span>			
	</div>
</div>
<!--<div>
	<img style="padding:60px 10px 0px 0px;" src="http://images.crowdscanner.com/graph_final.png" width="1200" height="683" alt="talking to strangers, meet new people, approach, pick up, have cool conversations, crowdscanner iphone app, technology for networking">	
</div>
-->


 


   









