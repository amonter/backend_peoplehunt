<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src='http://images.crowdscanner.com/jquery.form.js' ></script>
<div class="placing-1 events">
	<h1 id = "titleOfCreate">Sign Up</h1>
<div id="twitter_form"> 
<c:set var="pathinfo" value="${fn:split(pageContext.request.requestURL, '/')}" />

<c:url value="/crowdmodule/auth/registerattendeetwitterdev" var="submitRegister"/>
<form:form name="register" commandName="user" action="${submitRegister}" method="POST" >
        <form:hidden path="lastName"/>
        <input type="hidden" name="email" value=""/>
	<input type="hidden" name="avatarImage" value=""/>
	<input type="hidden" name="newPassword" value="password"/>
	<div id="profile"></div> 
	<div class="form">Twitter handle:</div> 
	<div class="form-boxes-padding">
		<form:input id="handle" path="userName" class="form-boxes"  size="45"/>
		 <br/><span class="status_handle" style="color:#FF0066; ">
		 <form:errors path="userName"/></span>
	</div> 
	<div class = "form-extra-details">
        format: @twitterhandle
    </div>
	<!--
	<c:if test="${event.id != 88}">
		<div class="form">Email:</div> 
		<div class="form-boxes-padding">
			<form:input path="email" class="form-boxes" size="45"/> <br/><span style="color:#FF0066; "><form:errors path="email"/></span>
		</div> 
	</c:if> -->
	<!-- <div class="form">Create a password:
	</div>
	<div class="form-boxes-padding">
		<form:password path="newPassword" class="form-boxes" size="45"/> <br/><span style="color:#FF0066; "><form:errors path="newPassword"/></span> 
	</div> --> 	
	<div class="form-extra-details">
		<input style="display:none" type="submit" id="submit" class="form-boxes-buttons" value="Submit"/>
                <a><button type="button">Submit</button></a>
 	</div> 
 
</form:form>
<c:if test="${pathinfo[1] != 'mobile.crowdscanner.com'}" >
	<span id=""><a href="#"id="hide_twitter">Sign up without Twitter</a></span>	
</c:if> 
</div>
<div id="normal_form" style="display:none">
	<c:url var="photo_upload" value="/crowdmodule/auth/upload_photo"/>
	<form id="uploadPhoto" method="POST" action="${photo_upload}" enctype="multipart/form-data"> 
	<div class="form">Upload Image:</div>
        <div class="form-boxes-padding">
		<input type="file" name="file" class = "form-boxes" size="35" value="Profile Image"/><span id="status" style="color:#FF0066"></span>
	</div>
	<div id="preview"></div>
	</form>
	<c:url var="submitPhoto" value="/crowdmodule/auth/registerattendeedev"/>
	<form:form name="register" commandName="user" action="${submitPhoto}" enctype="multipart/form-data" method="POST" >
	<input type="hidden" name="newPassword" value="password"/>
        <div class="form">Full Name:</div>
        <div class="form-boxes-padding">
                <form:input path="lastName" class="form-boxes"  size="45"/> <br/><span style="color:#FF0066; "><form:errors path="lastName"/></span>
        </div>
        <div class="form">Email:</div>
        <div class="form-boxes-padding">
                <form:input path="email" class="form-boxes" size="45"/> <br/><span style="color:#FF0066; "><form:errors path="email"/></span>
        </div>
        <!-- <div class="form">Password:
        </div>
        <div class="form-boxes-padding">
                <form:password path="newPassword" class="form-boxes" size="45"/> <br/><span style="color:#FF0066; "><form:errors path="newPassword"/></span>
        </div> -->
	<div class="form-extra-details">
              	<input type="submit" id="submit" class="form-boxes-buttons" value="Submit"/>
		
        </div>
</form:form>
	<span><a href="#" id="show_twitter">Cancel</a></span>
</div>
</div> 
<div id="theevent_id" style="display:none;">${event.id}</div>
<script type="text/javascript">
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
                $("#hide_twitter").click(function(){
                        $("#twitter_form").hide();
                        $("#normal_form").show();
                });

                $("#show_twitter").click(function(){
                        $("#twitter_form").show();
                        $("#normal_form").hide();
                });

                $("#handle").live('blur',function(eventObj) {
                        $("span[class=status_handle]").text("Loading").show();
                        var requestURL = "http://twitter.com/users/show/" +$(this).val()+ ".json?callback=?";
                        $.getJSON(requestURL, function(theData){
                                $("span[class=status_handle]").fadeOut('slow', function(){
                                        $("span[class=status_handle]").text("");
                                });
				var encodedName = encodeURIComponent(theData.name.replace(/ /g,'_'));
				var createdEmail = encodedName+"_"+(Math.random()*1000000)+"@meetfoeal.com";
				$("[name=email]").val(createdEmail);
				$("[name=lastName]").val(encodeURIComponent(theData.name));
                                $("[name=avatarImage]").val(theData.profile_image_url);
                                $("#profile").html('<img src="' + theData.profile_image_url + '"/>');
                                $("#profile").fadeIn("slow");
				$("#submit").click();
                        });
                });

        });
</script>


