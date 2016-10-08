<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<script type="text/javascript" src='http://images.crowdscanner.com/jquery.form.js' ></script>

<script type="text/javascript" >
$(document).ready(function() { 
    $('#uploadPhoto').ajaxForm({
    	'dataType' :  'json', 
    	'success' : function(data) {
    		$("#status").text("Uploaded successfully").fadeOut('slow');
    		if (data.url) {
    			$("#preview").html('<img src="' + data.url + '"/>');
    			$("#preview").fadeIn("slow");
    		}
    	}
    });
    
    $("input[name=file]").change(function(){
    	$("#status").text("Uploading... please wait").show();
    	$('#uploadPhoto').submit();
    });
    
}); 
</script>

<div class = "placing-1 events"> 
<h1 id="titleOfCreate">Step 2 of 4: Upload Your Image</h1>
<h2 class = "form-extra-details" style="margin-left:0px; text-align:center;">This square image appears on the iPhone/iPad and on the browser to identify your event questions:</h2>

<c:url value="image" var="uploadPhotoUrl"/>
<form id="uploadPhoto" method="POST" action="${uploadPhotoUrl}" enctype="multipart/form-data"> 
	<div class = "form"> 
		Upload image:
	</div> 
	<div class = "form-boxes-padding">
		<input type="file" name="file" class = "form-boxes" size="35" value="Image identifying your event"/> <span id="status" style="color:#FF0066"></span>
	</div>
	<div class = "form-extra-details">
		<strong>Square images required</strong>. Image can be .png,, .bmp, .jpg. Max size 2MB		
	</div>
</form> 
	<div id="preview"></div>

	<c:url value="choose_visual/flow" var="nextStep"/>
        <form action="${nextStep}">
	<div class = "form-extra-details" style="float:right;margin-right:20px;">
		<button type="submit" class = "form-boxes-buttons" value="Next"/>Next</button>
	</div>
	</form>
	</div>
</div>
