<!DOCTYPE html>
<html>
<head>
   
   <script type="text/javascript" src="http://images.crowdscanner.com/jquery-1.4.2.min.js"></script>
   <script src="http://images.crowdscanner.com/processing-1.1.0.min.js"></script>    
   <script src="http://images.crowdscanner.com/mindfield_bubbles.js"></script>    
   <title>Crowd Visualisation</title>
   <link href="http://images.crowdscanner.com/widgets_crowd.css" rel="stylesheet" type="text/css" />
   <style type="text/css">
	#geronimo {
		margin-top:0px;
		width:200px;
		float:right;
		padding:0px 10px 0px 10px;
		font-size:14px;
		text-align:center;
		font-family:Helvetica;
		background-color:#FFFFFF;	
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
		background:#FFFFFF;
		border:solid #FFFFFF 4px;
	}


   </style>
	
</head>
<body style="background:#FFFFFF;margin:0px;">
<div style="padding:10px 0px 10px 0px;">
	<div >
	<a href="http://crowdscanner.com/mindfield"><img src="http://images.crowdscanner.com/CrowdScanner_mindfield.png" style="border:none;padding-left:20px;width:200px;float:left;"></a>
	</div>
	<div style="width:900px;padding:0px 0px 0px 0px;margin-right:180px;color:#333333;font-size:35px;text-align:center; font-family:helvetica;"><img src="http://images.crowdscanner.com/peopleHunt.png" ></div>
</div>
<div style="margin-top:-135px; padding:0px 60px 20px 0px;text-align:right;background-color:#FFFFFF;">
               <a href="http://crowdscanner.com/crowdmodule/public/${bundle_id}/questions"/><img src="http://images.crowdscanner.com/Join.png" style="width:100px;border:none;"></a>
        </div>
<div id="geronimo">
<a href="http://crowdscanner.com/mindfield#artist"><img src="http://images.crowdscanner.com/artist.png" width="120px"></a>
 <a href="http://crowdscanner.com/mindfield#social"><img src="http://images.crowdscanner.com/social.png"width="120px"></a>
 <a href="http://crowdscanner.com/mindfield#cybergeek"><img src="http://images.crowdscanner.com/nerd.png"width="120px"></a>
 <a href="http://crowdscanner.com/mindfield#scientist"><img src="http://images.crowdscanner.com/scientist.png"width="120px"></a>
 <a href="http://crowdscanner.com/mindfield#politician"><img src="http://images.crowdscanner.com/politician.png"width="120px"></a>

</div>

<div id="widget">
	<canvas style="float:left;" width="500"  height="685" id ="theCanvas"></canvas></p>
</div>
<div style="height:0px;width:0px;overflow:hidden;"></div>
<span id="show_character">${display_character}</span>
<span id="bundle_id" style="display:none">5</span>
<div id="dialog-box">
    <div class="dialog-content">
        <span style="font-size:20px; text-align:center;display:block;">Your secret identity:<br><b> ${display_character["nerd_type"]}</b></span>
	<img style="padding:10px 0px 10px 55px;" src="${display_character['nerd_image']}"/><span style="text-align:center;font-size:14px;">Download the iPhone app to play people hunt on Saturday and Sunday at MindField!</span>
	<a href="#" class="button">Close</a>
    </div>
</div>
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
	var theURL = "/rest/charectersgraph/";
        $('a.btn-ok,#dialog-overlay,#dialog-box').click(function () {     
       		$('#dialog-box').hide();       
        	return false;
    	});
	if($("#show_character").html() != ''){
    		 var maskHeight = $(window).height();  
    		 var maskWidth = $(window).width();
 
    		// calculate the values for center alignment
    		var dialogTop =  (maskHeight/2) - ($('#dialog-box').height());  
    		var dialogLeft = (maskWidth/2) - ($('#dialog-box').width()/2); 
     
    		$('#dialog-box').css({top:dialogTop, left:dialogLeft}).show();
	}
	$.getJSON(theURL, function(data) {
		theData = data;
 	        Crowd.init('theCanvas', theData);		 	
	});
	
	
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
