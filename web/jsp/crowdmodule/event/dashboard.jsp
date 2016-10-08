<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<link href="http://images.crowdscanner.com/widgets_crowd.css" rel="stylesheet" type="text/css" />

<div class="placing-1 events">

<h1 id="titleOfCreate" style="margin-bottom:120px;" >Dashboard</h1>


	<div style=" margin:-80px 0px 0px 0px;text-align:center;">
		<c:url value="event/create" var="theURL" />
		<form action="${theURL}"><button type="submit" class="form-boxes-buttons">Create NEW Event Question Bundle</button></form>
	</div>
	
	<div class="banner-sep-panels" ></div>
 	
 	<c:forEach items="${events}" var="event">
	<div class = "eventBundle">
	<div style="display:block;height:100px;">
		<div style="float:left; padding:0 20px 0px 20px;">
			 <img style="width:80px;" src='<c:url value="${event.imageURL}" />'/>
       		</div>
		<div class="form">
			${event.description} 
		</div>
		<div class = "form-extra-details" style = "margin-left:20px;">
			Taking place on the ${event.date} in: ${event.location}
        	</div>
		<div class = "form-extra-details" style="float:right;margin:-67px 20px 0px 0px;">
	        <form action="/crowdmodule/event/${event.id}/satistics/"><button type="submit" class="form-boxes-buttons" >View stats</button></form>
		</div>
	</div>
	<div style="display:block;height:65px;">	
		<div style="float:left; padding: 0px 20px 0px 20px">
			<img src ="${event.visual.image}" style="width:80px;">     
          	</div>
		<div class = "form" style="font-size:20px;">
			${event.visual.name}	
          	</div>
        	<div class="form-extra-details" style="margin:-28px 20px 0 0px;float:right;">
		<form action="/crowdmodule/event/${event.id}/choose_visual/dashboard"><button type="submit" class="form-boxes-buttons" >Change visualisation type</button></form>
	  	</div>
	</div>
		<div class="form" style="font-size:20px;">
			Link to share: <a style="font-size:20px;" href='<c:url  value="/crowdmodule/perma-link/${event.permaLink}"/>'/>http://crowdscanner.com/crowdmodule/perma-link/${event.permaLink}</a>
		</div>
		<div class = "form-extra-details" style="margin-left:20px;">
			This is a link to the visualisation of your attendee information, as well as a route for attendees to participate.Email, tweet and post this link so that users can participate online or through their smart devices
		</div>
		<div class="banner-sep-panels" ></div>

 </c:forEach>
		
</div>
</div>
<div id="notify" style="display:none">${status_now}</div>
<div id="bundle_des" style="display:none">${currentbundle_description}</div>
<div id="dialog-overlay"></div>
<div id="dialog-box">
    <div class="dialog-content">
        <div id="dialog-message"></div>
        <a href="#" class="button">Close</a>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function () {
 
    // if user clicked on button, the overlay layer or the dialogbox, close the dialog  
    $('a.btn-ok,#dialog-overlay,#dialog-box').click(function () {     
        $('#dialog-box, #dialog-overlay').hide();       
        return false;
    });
     
    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center    
    $(window).resize(function () {
         
        //only do it if the dialog box is not hidden
        if (!$('#dialog-box').is(':hidden')) popup();       
    }); 
     
    $(window).load(function () {
	var theStatus = $('#notify').html();
        if(theStatus =='start') {
	   popup("Now you can start creating a bundle!");
        }
        if(theStatus =='added_new'){
	  var bundleDes = $('#bundle_des').html();	 
	  popup('Great! Your questions for '+bundleDes+' are now LIVE and ready to be answered by your attendees.');	
	}		
   });
});


function popup(message) {
         
    // get the screen height and width  
    var docHeight = $(document).height();  
    var maskHeight = $(window).height();  
    var maskWidth = $(window).width();
    
 
    // calculate the values for center alignment
    var dialogTop =  (maskHeight/2) - ($('#dialog-box').height());  
    var dialogLeft = (maskWidth/2) - ($('#dialog-box').width()/2); 
     
    $('#dialog-overlay').css({height:docHeight, width:maskWidth}).show();
    $('#dialog-box').css({top:dialogTop, left:dialogLeft}).show();
     
    // display the message
    $('#dialog-message').html(message);
             
}



</script>







