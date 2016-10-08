<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	
	var questionSaveUrl = '<c:url value="/crowdmodule/event/${questionBundle}/questions/" />';
	$(document).ready(function() {
		
		var questionTemplate = $("#question_template");
		var newQuestion = questionTemplate.clone();
		newQuestion.show();
		questionTemplate.after(newQuestion);
		
	 	$("#submit").hide();	
		$("#msg").ajaxError(function(event, request, settings){
			   $(this).append("<li>Error requesting page " + settings.url + "</li>");
		});
		
		$("div.question > input[name=question]").live('blur',function(eventObj) {
			  var desc = $(this).val();
			  if (!desc) {
				  return;
			  }
			  var idElement =  $(this).parent().find("> input[name=id]");
			  var messageElement = $(this).parent().find("> span[class=status]")
			  messageElement.text("Saving...");
			  messageElement.show();
			  var id = idElement.val();
			  var url = questionSaveUrl;
			  if (id) {
			   url += id + "/";
			  }  
			  $.post(url, { description: desc},
					   function(id){
				  				idElement.val(id);
				  			messageElement.text("Saved");
				  			messageElement.fadeOut('slow',function(){
				  				messageElement.text("");
				  			});
			  			});
			});
		
		$("div.answer > input[name=answer]").live('blur',function(eventObj) {
			  var desc = $(this).val();
			  if (!desc) {
				  return;
			  }
			  var questionIdElement =  $(this).parent().parent().parent().find("div.question > input[name=id]");
 			  if(questionIdElement.val()=="") {
                            var messageElement = $(this).parent().find("> span[class=status]")
			    messageElement.text("Please insert a question first");
			    messageElement.show();

			  } else { 	
			  var idElement =  $(this).parent().find("> input[name=id]");
			  var messageElement = $(this).parent().find("> span[class=status]")
			  messageElement.text("Saving...");
			  messageElement.show();
			  var answerId = idElement.val();
			  var questionId = questionIdElement.val();
			  var url = questionSaveUrl + questionId + "/answers/";
			  
			  if (answerId) {
				   url += answerId + "/";
			  }
			  
			  $.post(url, { description: desc},
					   function(id){
				  				idElement.val(id);
				  			messageElement.text("Saved");
				  			messageElement.fadeOut('slow',function(){
				  				messageElement.text("");
				  			});
			  			});
                        }
		});
		
		
		$("#nextQuestion").click(function() {
			
			var lastQuestion = $("div.top_div").last();
			var newQuestion = $("#question_template").clone();
			newQuestion.id = "";
			var questionNo = newQuestion.find("div.question > span[class=questionNo]");
			questionNo.text(increment(lastQuestion.find("div.question > span[class=questionNo]").text()).toString());
			lastQuestion.after(newQuestion);
			newQuestion.fadeIn('slow');
			$('html,body').animate({scrollTop:newQuestion.offset().top},{duration:'slow',easing:'swing'});
			var count = incrementAndUpdate($("#questionCount"));
			if (count == 5) {
				$("#nextQuestion").hide();
				$("#submit").show();
			}
			
		});
	 });
	
	
	$("input.removeAnswer").live("click",function() {
		var lastAnswer = $(this).parent().find("> div.answer").last();
		var questionIdElement =  $(this).parent().find("> input[name=id]");
		var idElement =  lastAnswer.find("> input[name=id]");
		var messageElement = lastAnswer.find("> span[class=status]")
		messageElement.text("Deleting...");
		messageElement.show();
		var answerId = idElement.val();
		var questionId = questionIdElement.val();
		var url = questionSaveUrl + questionId + "/answers/" +  answerId + "/";
		
		if (!questionId || !answerId) {
 				var count = decrementAndUpdate($(this).parent().find("> input[name=answersCount]"));
 				if (count <= 9) {
 					$(this).parent().find("> input.addAnswer").show();
 				}
 				if (count <= 2) {
 					$(this).hide();
 				}
				lastAnswer.remove();
 			return;
		} else {
			$.post(url, { action: 'delete'},
			   function(){
			 				messageElement.text("Deleted successfully");
			 				messageElement.fadeOut('slow',function(){
				 				var count = decrementAndUpdate(lastAnswer.parent().find("> input[name=answersCount]"));
				 				if (count <= 9) {
				 					lastAnswer.parent().find("> input.addAnswer").show();
				 				}
				 				if (count <= 2) {
				 					lastAnswer.parent().find("> input.removeAnswer").hide();
				 				}
			 					lastAnswer.remove();
				 			});
					});
		}	
	});
	
	$("input.addAnswer").live("click",function() {
		var lastAnswer = $(this).parent().find("> div.answer").last();
		var newAnswer = lastAnswer.clone();
		newAnswer.find("> input[name=id]").val("");
		newAnswer.find("> input[name=answer]").val("");
		newAnswer.find("> span[class=status]").val("").hide();
		var count = incrementAndUpdate($(this).parent().find("> input[name=answersCount]"));
		if (count > 9) {
			$(this).hide();
		}
		if (count > 2) {
			$(this).parent().find("> input.removeAnswer").show();
		}
		
		lastAnswer.after(newAnswer);
		
	});
	
	$("input.addAnswer").css("background-color","red");
	
	function increment(input) {
		return parseInt(input) + 1;
	}
	
	function incrementAndUpdate(input) {
		var count = parseInt(input.val()) + 1;
		input.val(count.toString());
		return count;
	}
	
	
	function decrementAndUpdate(input) {
		var count = parseInt(input.val()) - 1;
		input.val(count.toString());
		return count;
	}
</script>
<script type="text/javascript">
// Popup window code
function newPopup(url) {
	popupWindow = window.open(
		url,'popUpWindow','height=400,width=600,left=650,top=200,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
}
</script>

<div class = "placing-1 events" align="center">
	<div id="msg"></div>
	<h1 id="titleOfCreate">Step 4 of 4: Create 5 Questions</h1>
	<h2 class = "form-extra-details" style="margin-left:0px;text-align:center;">Create and customise 5 multiple choice questions to tailor them to your specific event audience:</h2> 
	<div class="placing-1 events" align="left" style="width:450px;float:left;"> 
	<input id="questionCount" type="hidden" value="1"/>
		<div align="left" id="question_template" class="top_div"  style="display:none;">
			<div class="question">
				Question <span class="questionNo">1</span>
        	        	<div class="form-extra-details" style="margin-left:0px;">(max characters for questions = 170)</div>
				<input type="hidden" name="id" value=""/>
				<input type="text" size="35" name="question" class="form-boxes" value=""/> 
				<span class="status"></span>
			</div>
			<div class="answerTitle">
				Answers <div class="form-extra-details" style="margin-left:0px">(max characters for answers = 100)</div>
				<input type="hidden" name="answersCount" value="2"/>
				<div class="answer" align="left"> 
					<input type="hidden" name="id" value=""/>
					<input type="text" name="answer" size="35" class="form-boxes" value=""> 
					<span class="status"></span>
				</div> 
				<div class="answer" align="left"> 
					<input type="hidden" name="id" value=""/>
					<input type="text" name="answer" size="35" class="form-boxes" value=""> 
					<span class="status"></span>
				</div> 
					<input class="addAnswer" type="button" value="add another answer"/>
					<input class="removeAnswer" type="button" value="remove answer" style="display:none"/> 
		 	</div> 
		</div>

	
		<div class = "form-extra-details" style = "margin-left:0px">
			<input id="nextQuestion" type="button" value="Next"/> 
			<a id="submit" href="<c:url value='/crowdmodule/event/${questionBundle}/questions/done'/>">Done</a>
		</div>
 
	</div>
	<div class ="placing-1 events" style="width:400px;float:left;">
		
		<!--<div class="question_help">
			Click <a href="JavaScript:newPopup('http://www.crowdscanner.com');">here</a> for Question Ideas 
		</div>-->
	</div>	
</div>
