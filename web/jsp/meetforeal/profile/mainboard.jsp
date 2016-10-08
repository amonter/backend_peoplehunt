<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<script type="text/javascript"  src="http://api.recaptcha.net/js/recaptcha_ajax.js"></script>
<script type="text/javascript" src="js/recaptchaimpl.js"></script> 
<div>			
	<tiles:insertAttribute name="profile_details" />
</div>
<div style="margin:10px 0px 10px 30px;">	
	<script type="text/javascript"><!--
	google_ad_client = "pub-7717112231624442";
	/* 468x60, created 1/10/09 */
	google_ad_slot = "7147468951";
	google_ad_width = 468;
	google_ad_height = 60;
	//-->
	</script>
	<script type="text/javascript" 	src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>				
</div>
<div style="height:auto;">			
	<tiles:insertAttribute name="profile_tabs" />
</div>

