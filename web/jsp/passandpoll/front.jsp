<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div style="max-width:850px; width:850px;">
	<div style="float:left;padding-left:0px;width:350px;" >
		<img style="padding:0px 0px 0px 15px;" width="350" src="http://images.crowdscanner.com/iphone_front.png" alt="talking to strangers, meet new people, approach, pick up, have cool conversations, crowdscanner iphone app, technology for networking">	
	</div>

	<div style="float:left;margin:20px 0px 0px 40px;width:440px;">
		<div style = "text-align:left;font-family:helvetica;padding:20px 0px 10px 0px;">
			<span style="color:#333333;line-height:110%;font-size:25px;">start conversations by discovering cool facts about the "strangers" around you, face to face, and break the status quo </span>
			<br><br><br><span style="color:#ffffff;line-height:110%;background-color:#FF0074;font-size:25px;">because serendipitous encounters make you feel good, and change the way you see the world</span>
			<br><br><br><span style="color:#999999;line-height:110%;font-size:16px;">designed based on sociological & anthropological research*</span>			
			<div style="color:#333333;margin-top:30px;" >	
				<div style="float:left;width:180px;text-align:center;padding:0px 0px 25px 25px;">
					<span style="background-color:#FFF200; font-size:17px;line-height:120%;">"it was simply one of those apps that I have long awaited"</span> Micah, Canada
				</div>
		
				<div style="float:left;width:180px;text-align:center;padding:0px 0px 25px 30px; ">
					<span style="background-color:#FFF200;font-size:17px;line-height:120%;">"I took your app to a party, and everyone wanted this app for their phone. Very impressed"</span> Travis, US
				</div>
			</div>
			<div style="padding:20px 10px 10px 30px;">
				<a  href="http://itunes.apple.com/us/app/crowdscanner/id356256266?mt=8&uo=6" target="itunes_store">
				<img style="padding:0px 0px 0px 0px;margin-left:80px;" width="220" alt="talking to strangers, meet new people, have cool conversations, crowdscanner iphone app, networking" src="http://images.crowdscanner.com/app_store.png" /></a>
			</div>
		</div>
	</div>		
		
</div>
<div style="clear:both;max-width:1200px;">
	
		<h2 class="header_blog" style="line-height:110%;padding:30px 0px 20px 20px;">
			<span style="color:#FF0074;">what people are answering:</span>				
		</h2>
		<c:forEach items="${theList}" var="the_question">
			<div class="question_top" >
				<c:out value="${the_question.questionDescription}" />				
			</div>
			<div class="question_frame" style="height:65px;margin-bottom:10px;">						
				<div class="inner_photo">					
					<img width="38px;" src="http://images.crowdscanner.com/anon_nosmile.png">								
				</div>
				<div class="content_top">
					<span class="theusername"><c:out value="${the_question.profiles[0].user.userName}" /></span>								
					<span class="quiz_statement">
					asked ${the_question.totalAnswerCount} people <span style="font-weight:bold;font-size:1.1em;">${the_question.state} ${the_question.country}</span>
					</span>									
				</div>
				<div style="float:right;padding:30px 30px 0px 0px;">
					<a href="user/${the_question.questionPhoneId}/${the_question.profiles[0].user.userName}">View Graphs</a>
				</div>		
			</div>	
		</c:forEach>	

			
	
	<div style="margin-top:40px;">
		<span style="color:#999999;font-size:12px;">&nbsp *check out our presentation on slideshare <a href="http://www.slideshare.net/Meetforeal/designing-for-socialisation">here</a></span>			
	</div>
</div>
