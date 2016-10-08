<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<html>
<head profile="http://www.w3.org/2005/10/profile">
	<title><tiles:insertAttribute name="title"/></title>	
	<link rel="shortcut icon" href="http://images.crowdscanner.com/favicon.ico">
        <meta name="google-site-verification" content="D_E7KSHlo9bHBPirSHRyNgwntYA8ncHPY5kzzvPd1iU" />	
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />	
	<meta name="description" content="<tiles:insertAttribute name='title'/>" />	
	<link href="http://images.crowdscanner.com/crowd_css.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src='http://images.crowdscanner.com/jquery-1.4.2.min.js' ></script>
</head>
<body>	
	<table  align="center" border="0" cellpadding="0" cellspacing="0">		
		<tr>
			<td align="left" class="site_background">
				<a href="http://crowdscanner.com">
					<img style="margin:0px 0px 0px 5px;" alt = "conversation starters, meet new people, curiosity iphone app" width="500" height="145"  src="http://images.crowdscanner.com/logo_mobile.png">
				</a>	
			</td>
		</tr>						
		<tr>
			<td class="site_background" valign="top">		
				<tiles:insertAttribute name="main_content"/>				
			</td>
		</tr>
		<tr>
		<td class="site_background" align="center">
			<div >
				<!--<div style="padding:35px 0px 16px 0px;" >
						<a style="font-size:1.1em;background-color:#ff0074;color:#ffffff;font-weight:bold;" href="/howtouse">&nbsp how to use crowdscanner &nbsp</a>
				</div>
				<div style="padding:35px 0px 35px 0px;" >
						<a style="font-size:1.1em;background-color:#ff0074;color:#ffffff;font-weight:bold;" href="/howyoufeel">&nbsp how crowdscanner makes you feel &nbsp</a>
				</div>-->
			</div>
		</td>
		</tr>
		<tr>
		<td style="background-color:#333333;" align="center">
			<div >	
				
				<div style="float:right;margin:10px 45px 20px 0px;" >
					<a style="display:block;font-size:1.2em;color:#ffffff;font-weight:bold;" href="/aboutus">about the creators</a>
				</div>
				<div style="float:right;margin:10px 30px 20px 0px;">
					<a style="display:block;font-size:1.2em;color:#ffffff;font-weight:bold;" href="/theblog">blog</a>
				</div>
			</div>
		</td>
		</tr>		
	</table>
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