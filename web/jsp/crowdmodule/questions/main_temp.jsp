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
	        $("#done_question").hide();	
                alert('ALOHA');		
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
			  messageElement.text("Saving question...");
			  messageElement.show();
			  var id = idElement.val();
			  var url = questionSaveUrl;
			  if (id) {
			   url += id + "/";
			  }  
			  $.post(url, { description: desc},
					   function(id){
				  				idElement.val(id);
				  			messageElement.text("Saved successfully");
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
			  var questionIdElement =  $(this).parent().parent().find("> input[name=id]");
 			  if(questionIdElement.val()=="") {
                            var messageElement = $(this).parent().find("> span[class=status]")
			    messageElement.text("Please insert a question first");
			    messageElement.show();

			  } else { 	
			  var idElement =  $(this).parent().find("> input[name=id]");
			  var messageElement = $(this).parent().find("> span[class=status]")
			  messageElement.text("Saving answer...");
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
				  			messageElement.text("Saved successfully");
				  			messageElement.fadeOut('slow',function(){
				  				messageElement.text("");
				  			});
			  			});
                        }
		});
		
		
		$("#nextQuestion").click(function() {
			var lastQuestion = $("div.question").last();
			var newQuestion = $("#question_template").clone();
			newQuestion.id = "";
			var questionNo = newQuestion.find("> span[class=questionNo]");
			questionNo.text(increment(lastQuestion.find("> span[class=questionNo]").text()).toString());
			lastQuestion.after(newQuestion);
			newQuestion.fadeIn('slow');

			var count = incrementAndUpdate($("#questionCount"));
			if (count == 5) {
				$("#nextQuestion").hide();
				$("#done_question").show();
			}
			
		});
	 });
	
	
	$("input.removeAnswer").live("click",function() {
		var lastAnswer = $(this).parent().find("> div.answer").last();
		var questionIdElement =  $(this).parent().find("> input[name=id]");
		var idElement =  lastAnswer.find("> input[name=id]");
		var messageElement = lastAnswer.find("> span[class=status]")
		messageElement.text("Deleting answer...");
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

<div class = "placing-1 events"> 
<div id="msg"></div>
<h1 id="titleOfCreate">Create 5 Questions</h1>
<h2 class = "form-extra-details" style="margin-left:0px;text-align:center;">Create and customise 5 multiple choice questions to tailor them to your specific event audience:</h2> 
<input id="questionCount" type="hidden" value="1"/>

<div class="question" id="question_template" style="display:none">
	<div style="padding-bottom:20px;">
		Question <span class="questionNo">1</span> 
		<div class="form-extra-details" style="margin-left:0px;">(max characters for questions = 170)
	 	</div>
		<input type="hidden" name="id" value=""/>
		<input class="form-boxes" type="text" size="45" name="question" value=""/> 
		<span class="status"></span>
	</div>
	<div>
		Answers <div class="form-extra-details" style="margin-left:0px">(max characters for answers = 100)</div>
		<input type="hidden" name="answersCount" value="2"/>
		<div class="answer" align="left"> 
			<input type="hidden" name="id" value=""/>
			<input type="text" name="answer" class = "form-boxes" size="45" value=""> 
			<span class="status"></span>
		</div> 
		<div class="answer" align="left"> 
			<input type="hidden" name="id" value=""/>
			<input type="text" name="answer" size="45" class = "form-boxes" value=""> 
			<span class="status"></span>
		</div> 
		<input class="removeAnswer form-boxes" type="button" value="remove answer" style="display:none"/> 
	 
		<input class="addAnswer form-boxes" type="button" value="add another answer"/> 
	</div>	
	<div class = "form-extra-details" style = "margin-left:0px">
		<input id="nextQuestion" type="button" value="Next" class = "form-boxes"/> 
		<a id="done_question"  href='<c:url value="questions/done/"/>'>Done</a>
	</div>
</div>
</div> 
