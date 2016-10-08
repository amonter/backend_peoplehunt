<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%
	pageContext.setAttribute("contextPath",request.getContextPath());	
%>

<div class="standardSpacing">		
	<div style="float:left;width:630px;">		
		<div style="clear:both;padding:0px 0px 25px 0px;">
				<script type="text/javascript"><!--
				google_ad_client = "pub-7717112231624442";
				/* 468x60, created 27/06/09 */
				google_ad_slot = "6219966643";
				google_ad_width = 468;
				google_ad_height = 60;
				//-->
				</script>
				<script type="text/javascript"
				src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
				</script>		
		</div>
		<div style="clear:both;">
			<tiles:insertAttribute name="the_page"/>					
		</div>	
	</div>
	<div style="float:left;height:550px;">
		<div style="margin:15px 0px 15px 0px;">	
			<div id="collapsible_user">			
			</div>
		</div>					
		<div>
			<script type="text/javascript"><!--
			google_ad_client = "pub-7717112231624442";
			/* 300x250, created 1/20/09 */
			google_ad_slot = "2939597014";
			google_ad_width = 300;
			google_ad_height = 250;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
		</div>					
	</div>	
	<div style="clear:both;width:336;height:280;padding-top:10px;">
		<script type="text/javascript"><!--
		google_ad_client = "pub-7717112231624442";
		/* 336x280, Bottom of page */
		google_ad_slot = "2123959065";
		google_ad_width = 336;
		google_ad_height = 280;
		//-->
		</script>
		<script type="text/javascript"
		src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
		</script>
	</div>	
	
</div>

<script language='javascript'>

function saveEvent(reference, eventName, url) {
	
	saveMyEventFlow(reference,eventName, url);
}

function showMeTheMap(location,latitude, longitude) {
	
	mapPopup(location,latitude, longitude);	
}

</script> 
	