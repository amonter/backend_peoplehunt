<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
					
<div class="question_top" >
	<c:out value="${main_question.questionDescription}" />				
</div>	
<div class="question_frame" >
	<div style="margin-bottom:80px;">		
		<div class="content_top">			
			<div style="display:block;padding:0px 0px 10px 10px;">
				<c:forEach items="${main_question.answers}" var="the_answer">
					<div style="font-size:15px;">
						<span style="display:inline;color:#666666;">${the_answer.textualAnswer}</span>
						<span style="display:inline;color:#666666;">${the_answer.answerNumber}</span>
					</div>					
				</c:forEach>
			</div>
			<div class="quiz_statement">
			<b>	${main_question.totalAnswerCount} people answered in total</b> 
			</div>									
		</div>		
		<img style="padding:10px 0px 0px 0px;" alt="graph of aggregated answers from approaching and talking to new people" src="http://chart.apis.google.com/chart?cht=p3&chd=t:${main_question.formatedAnswers}&chs=550x180&chdl=${main_question.formatedAnswerLabels}&chl=${main_question.formatedAnswerLabelsBar}&chco=${palette}" />	
	</div>	
	<c:forEach items="${answered_questions}" var="the_question">
		<div class="inner_question">
			<div class="inner_photo">
				<img src="http://images.crowdscanner.com/anon_nosmile.png" alt="profile picture" >							
			</div>
			<div class="inner_question_cont">	
				<span class="theusername">${the_question.profiles[0].user.userName} </span>					
				<span class="quiz_statement">
					Asked ${the_question.totalAnswerCount} people <span style="font-weight:bold;font-size:1.1em;">${the_question.state} ${the_question.country}</span>
				</span>																	
			</div>
			<img alt="graph of answers from individual user from approaching and talking to new people" src="http://chart.apis.google.com/chart?cht=p3&chd=t:${the_question.formatedAnswers}&chs=250x100&chl=${the_question.formatedAnswerLabelsBar}&chf=bg,s,F3F2F6&chco=${palette}" />		
		</div>	
	</c:forEach>	
</div>
<div>	
	<div style="color:#333333;margin-top:30px;" >	
		<div style="float:left;width:180px;text-align:center;padding:0px 0px 25px 65px;">
			<span style="background-color:#FFF200; font-size:17px;line-height:120%;">"it was simply one of those apps that I have long awaited"</span> Micah, Canada
		</div>

		<div style="float:left;width:180px;text-align:center;padding:0px 20px 25px 45px; ">
			<span style="background-color:#FFF200;font-size:17px;line-height:120%;">"I took your app to a party, and everyone wanted this app for their phone. Very impressed"</span> Travis, US
		</div>
	</div>
	<a style="padding-left:45px;" href="http://itunes.apple.com/us/app/crowdscanner/id356256266?mt=8&uo=6" target="itunes_store">
	<img alt="CrowdScanner, iphone app for having conversations with new people, serendipitously" src="http://images.crowdscanner.com/app_store.png" /></a>
</div>
<div style="margin:60px 0px 20px 150px;color:#FF0074;">If you would like to add more answers to this question, download Crowdscanner
</div>
	







