<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src='http://images.crowdscanner.com/jquery.form.js' ></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link href="http://images.crowdscanner.com/widgets_crowd.css" rel="stylesheet" type="text/css" />
	
<script type="text/javascript">
var totalQuestions = ${fn:length(questions)};
var currentQuestion = 1;
var loggedIn = false;

<c:if test="${authContext != null}">
	loggedIn = true;
</c:if>
$(document).ready(function(){

        $("#twitter_btn").click(function(){
          $("#submit").click();   	
	});
	$("#"+currentQuestion).show();
	updateProgress();
	$("input.answer").click(function(){
                var answerPercentUrl = $(this).parent().find("> a").attr("href");
                var theId = "#"+ $(this).val();
                var textualAnswerHtml = $(theId).html().replace('&amp;', '&');
		var theTextualAns = encodeURIComponent(textualAnswerHtml);
		var theQuestionId = $("#question_pid_"+currentQuestion).html();

                nextQuestion();
                /* 
		$.post(answerPercentUrl, {answerStr:theTextualAns,questionPhoneId:theQuestionId},
				   function(result){
						$("#message").fadeOut("slow");
						$("#answer-stat").text(result.percentage + " other users have choosen the same answer as you");
						$( "#answer-stat" ).dialog({
							resizable: false,
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
									nextQuestion();	
								}
							}
						});
		  			},"json");*/
	});
	
	$('#login').ajaxForm({
    	'dataType' :  'json', 
    	'error' : function() {alert("error");},
    	beforeSubmit : function() {
    		$("#errors").text("");
    	},
    	'success' : function(data) {
   			if (data.result == 'success') {
   				loggedIn = true;
   				$("#submit").click();
   			} else {
   				$("#errors").text(data.errors);
   			}
    	}
    });
});

function updateProgress() {
	$("#progress").text("Question " + currentQuestion + " of " + totalQuestions);
}

function nextQuestion() {
	if (currentQuestion == totalQuestions) {
		if (loggedIn) return true;
		//$("#loginDialog").dialog();
                var docHeight = $(document).height();  
    		var maskHeight = window.innerHeight ? window.innerHeight : $(window).height()   
    		var maskWidth = window.innerWidth ? window.innerWidth: $(window).width();
                //alert(maskHeight+' '+maskWidth); 
   		 // calculate the values for center alignment
		var theMaskWidth = maskWidth/2;
                //if (theMaskWidth < 320) {
                  // theMaskWidth = maskWidth;
                   //maskWidth = maskWidth * 2;      
		//}	   	
 		
     		$("#submit").click();
    		//var dialogTop =  (maskHeight/2) - ($('#dialog-box').height());  
    		//var dialogLeft = (theMaskWidth) - ($('#dialog-box').width()/2); 
	
    		//$('#dialog-overlay').css({height:docHeight, width:maskWidth}).show();
    		//$('#dialog-box').css({top:dialogTop, left:dialogLeft}).show();
     
		return false;
	} else {
		$("#"+currentQuestion).fadeOut("slow");
		currentQuestion++;
		$("#"+currentQuestion).fadeIn("slow");
		updateProgress();
		return false;
	}
}
</script>

<div class="placing-1 events">
	 <div style=" margin:0px 10px 0px 0px;float:right;">
                <a href="/crowdmodule/perma-link/${event.permaLink}"><button type="button" class="form-boxes-buttons" onclick="">Skip to Visuals</button></a>
	</div>
	<h1 id="titleOfCreate">${event.description}</h1>
	<c:url value="/crowdmodule/auth/prepareregisterattendeedev/${event.id}" var="submitResponseUrl"/>
	<form:form commandName="questionResponse" action="${submitResponseUrl}" method="POST">
		<div>
		<div id="message"></div>
		<div id="progress" class="question-number"></div>
		<c:forEach items="${questions}" var="question" varStatus="qstatus">
			<div id="${qstatus.count}" style="display:none; margin-left:40px;">
			<span id="question_pid_${qstatus.count}" style="display:none; ">${question.questionPhoneId}</span>
			<h2>${qstatus.count}) ${question.questionDescription}</h2> 
				<ul class = "question-list" style="list-style:none;padding:10px;">
				<c:forEach 	items="${question.sortedAnswers}" var="answer" varStatus="astatus">
						<c:url var="answerStatsurl" value='/crowdmodule/public/answerStat'/>
						<li>
							<a href="${answerStatsurl}" style="display:none"></a>
							<input class="answer radio" type="radio" name="choice[${question.id}]" value="${answer.id}"/>
							<span id="${answer.id}">${answer.textualAnswer}</span>
						</li>
				</c:forEach>
				</ul>
			</div>
		</c:forEach>
		</div>
</div>
<input style="display:none" id="submit" type="submit" value="Submit" />
</form:form>


<div id="loginDialog" style="display:none">
<div id="errors"></div>
<c:url var="loginUrl" value="/crowdmodule/auth/login_ajax"/>
<form id="login" method="POST" action="${loginUrl}"> 
	<div align="left"> 
		Username:<br> 
		<input name="userName"  size="25"/>
		</div>  
	<div align="left"> 
		Password :
		<br> 
		<input type="password" name="password"  size="25"/>
	</div> 	
	<br><br> 
	<input type="submit" value="Submit"/>
</form> 
</div>
<div id="dialog-overlay"></div>
<div id="dialog-box" style="padding:20px;display:none;text-align:center;">
    <span>Grab your photo from your twitter account</span>
    <a href="#" id="twitter_btn"><img style="margin-top:10px;" src="http://si0.twimg.com/images/dev/buttons/sign-in-with-twitter-d.png"/></a> 
</div>







<div id="answer-stat"></div>
