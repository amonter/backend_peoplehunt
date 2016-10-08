<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
<head>
  <link rel="shortcut icon" href="http://images.crowdscanner.com/favicon.ico" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="google-site-verification" content="D_E7KSHlo9bHBPirSHRyNgwntYA8ncHPY5kzzvPd1iU" />	
	 <meta name="language" content="en" />
  <!--[if IE]>
    <script src="https://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <link type="text/css" rel="stylesheet" media="all" href="http://images.crowdscanner.com/facescss.css" />
  <![endif]-->
  <!--[if lt IE 8]>
    <link type="text/css" rel="stylesheet" media="all" href="http://images.crowdscanner.com/facescss.css" />
  <![endif]-->
<script type="text/javascript" src="http://images.crowdscanner.com/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="http://images.crowdscanner.com/jquery.fancybox-1.3.4.pack.js"></script>
<title><tiles:insertAttribute name="title"/></title>
<script type="text/javascript" src="http://use.typekit.com/hzn8glz.js"></script>
<script type="text/javascript">try{Typekit.load();}catch(e){}</script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
	
<link rel="stylesheet" type="text/css" href="http://images.crowdscanner.com/facescss.css" />
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src='http://images.crowdscanner.com/jquery.form.js' ></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link href="http://images.crowdscanner.com/widgets_crowd.css" rel="stylesheet" type="text/css" />

</head>

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
                $("#message").text("Fetching answer stat...").show();
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
                var maskHeight = $(window).height();
                var maskWidth = $(window).width();

                 // calculate the values for center alignment
                var dialogTop =  (maskHeight/2) - ($('#dialog-box').height());
                var dialogLeft = (maskWidth/2) - ($('#dialog-box').width()/2);

                $('#dialog-overlay').css({height:docHeight, width:maskWidth}).show();
                $('#dialog-box').css({top:dialogTop, left:dialogLeft}).show();

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


	
<body>	
<div class="container header dashed-line-header">
  <header id="header" class="placing-1">
    <h1 id="home"><a href="faces2.html">CrowdScanner</a></h1>

		<div >
				<c:if test="${authContext != null}">
				<div style="margin:20px 20px 0px 0px; float:right;">Hello ${authContext.userName}</div>
				</c:if>
		</div>	
    <nav id="nav">
    	<ul class="nav-items" id="yw0">
		<li><a href="">LOGOUT</a></li>
	</ul>   
 </nav>
 </header>
</div>
Aloha¤
<div class="placing-1 events">
        <h1 id="titleOfCreate">${event.description}</h1>
<c:set value='/crowdmodule/public/authtwitter/${event.id}' var="submitResponseUrl"/>
        <form:form commandName="questionResponse" action="${submitResponseUrl}" method="POST">
                <div>
                <div id="message"></div>
                <div id="progress" class="question-number"></div>
                <c:forEach items="${questions}" var="question" varStatus="qstatus">
                        <div id="${qstatus.count}" style="display:none; margin-left:40px;">
                        <span id="question_pid_${qstatus.count}" style="display:none; ">${question.questionPhoneId}</span>
                        <h2>${qstatus.count}) ${question.questionDescription}</h2>
                                <ul style="list-style:none;padding:10px;">
                                <c:forEach      items="${question.answers}" var="answer" varStatus="astatus">
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


<footer id="footer" style="margin-top:0px;padding-top:20px;height:130px" class="placing-3">
   <nav id="footer-nav">
    <ul>
      <li id="footer-nav-home"><a href="/">HOME</a></li>
      <li id="footer-nav-signin"><a href="#/signin">SIGN OUT</a></li>
      <li id="footer-nav-terms"><a href="/terms/">TERMS</a></li>
      <li id="footer-nav-privacy"><a href="/privacy/">PRIVACY</a></li>
    </ul>

  </nav>
</footer>


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
    <span>Sign in with your twitter account</span>
    <a href="#" id="twitter_btn"><img style="margin-top:10px;" src="http://si0.twimg.com/images/dev/buttons/sign-in-with-twitter-d.png"/></a>
</div>
<div id="answer-stat"></div>

<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-1869004-4");
pageTracker._trackPageview();
} catch(err) {}</script>	
</body>
</html>

<script type="text/javascript">
var uservoiceOptions = {
  /* required */
  key: 'crowdscanner',
  host: 'crowdscanner.uservoice.com', 
  forum: '39996',
  showTab: true,  
  /* optional */
  alignment: 'left',
  background_color:'#f00', 
  text_color: 'white',
  hover_color: '#06C',
  lang: 'en'
};

function _loadUserVoice() {
  var s = document.createElement('script');
  s.setAttribute('type', 'text/javascript');
  s.setAttribute('src', ("https:" == document.location.protocol ? "https://" : "http://") + "cdn.uservoice.com/javascripts/widgets/tab.js");
  document.getElementsByTagName('head')[0].appendChild(s);
}
_loadSuper = window.onload;
window.onload = (typeof window.onload != 'function') ? _loadUserVoice : function() { _loadSuper(); _loadUserVoice(); };
</script>

