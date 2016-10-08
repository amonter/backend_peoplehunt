<!DOCTYPE html>
<html>
<head>
   
   <script type="text/javascript" src="http://images.crowdscanner.com/jquery-1.4.2.min.js"></script>
   <script src="http://images.crowdscanner.com/processing-1.1.0.min.js"></script>    
   <script src="http://images.crowdscanner.com/crowd_bubbles.js"></script>    
   <title>Crowd Visualisation</title>

   <style type="text/css">
	#geronimo {
		margin-top:-60px;
		padding:0px 10px 0px 10px;
		font-size:14px;
		text-align:center;
		font-family:Helvetica;
		background-color:#EAEFE0;	
	//background-color:#ABC731;
	}
	.colours {
		display:inline-block;
		padding:10px 10px 10px 10px;
		width:150px;
		max-width:250px;
	}
	.is_selected {
		display:inline-block;
		padding:10px 10px 10px 10px;
		width:200px;
		max-width:200px;
		font-size:20px;
		background:#ABC731;
		border:solid #FFFFFF 4px;
	}
.button {
	display: inline-block;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	font: 16px/100% Helvetica, sans-serif;
	padding: .5em 2em .5em;
	text-shadow: 0 1px 1px rgba(0,0,0,.3);
	-webkit-border-radius: .5em; 
	-moz-border-radius: .5em;
	border-radius: .5em;
	-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	box-shadow: 0 1px 2px rgba(0,0,0,.2);
}
.button:hover {
	text-decoration: none;
}
.button:active {
	position: relative;
	top: 1px;
}
.pink {
	color: #feeef5;
	border: solid 1px #d2729e;
	background: #f895c2;
	background: -webkit-gradient(linear, left top, left bottom, from(#feb1d3), to(#f171ab));
	background: -moz-linear-gradient(top,  #feb1d3,  #f171ab);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#feb1d3', endColorstr='#f171ab');
}
.pink:hover {
	background: #d57ea5;
	background: -webkit-gradient(linear, left top, left bottom, from(#f4aacb), to(#e86ca4));
	background: -moz-linear-gradient(top,  #f4aacb,  #e86ca4);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#f4aacb', endColorstr='#e86ca4');
}
.pink:active {
	color: #f3c3d9;
	background: -webkit-gradient(linear, left top, left bottom, from(#f171ab), to(#feb1d3));
	background: -moz-linear-gradient(top,  #f171ab,  #feb1d3);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#f171ab', endColorstr='#feb1d3');
}
   </style>
	
</head>
<body style="background:#EAEFE0;margin:0px;">
<div style="padding:10px 0px 10px 0px;">
	<div >
	<a href="http://crowdscanner.com"><img src="http://images.crowdscanner.com/power_logo_cubes_gray.png" style="border:none;padding-left:20px;width:180px;float:left;"></a>
	</div>
	<div style="padding:10px 0px 10px 0px;margin-right:180px;color:#333333;font-size:35px;text-align:center; font-family:helvetica;">${name}</div>
</div>
<div style=" padding:20px 20px 20px 0px;text-align:right;background-color:#EAEFE0;">
               <form action="http://crowdscanner.com/crowdmodule/public/${bundle_id}/questions"/><button type="submit" class="button pink" >Join</button></form>
        </div>
<div id="geronimo"></div>
<div id="widget">
	<canvas width="500"  height="685" id ="theCanvas"></canvas></p>
</div>
<div style="height:0px;width:0px;overflow:hidden;"></div>

<span id="bundle_id" style="display:none">${bundle_id}</span>
<script type="text/javascript">

$.extend({
          getUrlVars: function(){
            var vars = [], hash;
            var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&'
);            for(var i = 0; i < hashes.length; i++)
            {
              hash = hashes[i].split('=');
              vars.push(hash[0]);
              vars[hash[0]] = hash[1];
            }
            return vars;
          },
          getUrlVar: function(name){
            return $.getUrlVars()[name];
          }
});


var theData;
var question_id;
var selected_colour = 0;

$(document).ready(function(){ 
	var theURL, next, next_questionid;
	question_id = $.getUrlVar('qid');
	if (question_id == null) {
		theURL = "/crowdscanner_dev/rest/bubblebyanswersdev/"+$("#bundle_id").html();
	} else {
		theURL = "/rest/bubblebyanswersbyquestion/"+question_id;
	}

	$.getJSON(theURL, function(data) {
		theData = data;
		var theKeys = [];
	 	var theQuestions = data.questions;
		if (question_id == null){
			for (key in theQuestions){
				question_id = key;
				break;
			}
		}	
		var count = 0;
		for (key in theQuestions){
			theKeys[count] = key;
			count++;
		}
		theKeys.sort(function(a,b) { 
			return parseInt(a) - parseInt(b) 
		});
		for (key in theQuestions){
			var classSelection = "colours";
			if(question_id == key){
				selected_colour = count;
				classSelection = "is_selected";
			}	
			$('#geronimo').append("<div class ='"+classSelection+"' ><a style='text-decoration:none;color:#333333;' href=?qid="+key+" id='"+key+"'>"+theQuestions[key]+"</a></div>");
count++;
		}
		
		next = false;
		for (key in theQuestions){ 
			if (next){
			   next_questionid = key;
			   break;		
			}
			if (question_id != null){
				if (key == question_id){
					next = true;
				}
			}	
			
		}
		
		
 	   Crowd.init('theCanvas', theData);		 	
	});
        
	 window.setInterval(function () {
		var param = "?qid="+next_questionid;
		if (!next){
			var aCount = 0;
			for (key in theQuestions){
				if (aCount == 1){
					next_questionid = key;
				}
			}
		}
		if (next_questionid == null){
			param = "";
		} 
		window.location.href = "http://" + window.location.host + "" + window.location.pathname+""+param;
	},60000);

});


</script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-1869004-4']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</body>
</html>
