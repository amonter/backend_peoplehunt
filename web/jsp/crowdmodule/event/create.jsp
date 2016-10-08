<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>

<div class="placing-1 events" >
	<h1 id="titleOfCreate">Step 1 of 4: Details of Your Event</h1>
	<h2 class = "form-extra-details">What's the name, date and location of your event?</h2>
	<c:url value="/crowdmodule/event/create" var="createUrl"/>
	<form:form commandName="event" action='${createUrl}' method="POST" onsubmit="return checkLocation(this);" >
	<div id="form-details">
		<div class = "form">
		       Event Name: 
		</div>
		<div class = "form-boxes-padding">
			<form:input path="description" class = "form-boxes" size="35" value="" />
        		<form:errors path="description"/>
		</div>
		<div class = "form-extra-details">
			Descriptive name, e.g IgniteGalway Jan2011; max 27 characters
		</div>
		
		<div class = "form" >
			Description: 
		</div>
		<div class = "form-boxes-padding">
			<form:input path="topic" class="form-boxes" size="35" value="" />
		</div>
		<div class = "form-extra-details">
		
			Short description of your event	
		</div>
		
		<div class = "form">
			Date: 
		</div>
		<div class = "form-boxes-padding">
			<form:input path="date" class="form-boxes" value="" />
        		<form:errors path="date"/>
		</div>
		<div class = "form-extra-details">MM-DD-YYYY
		</div>
		<div class = "form">
			Location: 
		</div>
		<div class = "form-boxes-padding">
			<input id="address" class="form-boxes" type="textbox" size="35" value="">
			<input type="button" class="form-boxes-buttons" value="find" onclick="codeAddress()">
		</div>
		<div class = "form-extra-details">
			Is this your location? If not, reposition the marker below:
		</div>
		<div id="map_canvas" style="width:400px;left: 350px; margin-top: 10px; margin-bottom: 20px; height:300px"></div>
				<form:hidden path="longitude" />
				<form:hidden path="latitude" />
				<form:hidden path="location" />
				<form:errors path="location"/>
		</div>
		<div class = "form-extra-details" style="margin-right:20px;float:right;">
			<input type="submit" class="form-boxes-buttons" value="Save &amp; Continue">
			</form:form>
		</div>
	</div>
</div>
<script type="text/javascript"charset="utf-8">
 var marker;
 var geocoder;
 var map;
 var theLocation;
 window.onload = function () {
  geocoder = new google.maps.Geocoder();
  var latlng = new google.maps.LatLng(53.344104,-6.2674937);
    var myOptions = {
      zoom: 12,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map_canvas"),
        myOptions);

    marker = new google.maps.Marker({
      map:map,
      draggable:true,
      animation: google.maps.Animation.DROP,
      position:latlng
    });

    google.maps.event.addListener(marker, 'dragend', function(position) {
	  var lat = marker.getPosition().lat();
	  var long = marker.getPosition().lng();

	  var latlng = new google.maps.LatLng(lat, long);
          findTheLocation(latlng);
    });
   findTheLocation(latlng);
 }


 function codeAddress() {
   var address = document.getElementById("address").value;
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        var thePosition = results[0].geometry.location;
        map.setCenter(thePosition);
        marker.setPosition(thePosition);
        var theLatlng = new google.maps.LatLng(thePosition.lat(),thePosition.lng());
        findTheLocation(theLatlng);
      } else {
        alert("The address could not be found. Please try something less specific");
      }
  });
} 

 function findTheLocation(latlng) {
     geocoder.geocode({'latLng': latlng}, function(results, status) {
     	if (status == google.maps.GeocoderStatus.OK) {
		if (results[1]) {
	
	            theLocation = results[1].formatted_address;
                    document.getElementById('address').value = theLocation; 
	        } else {
	          //alert("No results found");
	        }
	      } else {
	        //alert("Geocoder failed due to: " + status);
	      }
	 });
} 


 function checkLocation(form) {
   var lat = marker.getPosition().lat();
   var long = marker.getPosition().lng();
   form.latitude.value = lat;
   form.longitude.value = long;
   form.location.value = theLocation; 
   return true;	
}


</script>





