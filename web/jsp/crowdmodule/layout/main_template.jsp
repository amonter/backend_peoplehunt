<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
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
	<script type="text/javascript" src='http://images.crowdscanner.com/jquery-1.4.2.min.js' ></script>
	
<link rel="stylesheet" type="text/css" href="http://images.crowdscanner.com/facescss.css" />

</head>
	
<body>	
<div class="container header dashed-line-header">
  <header id="header" class="placing-1">
    <h1 id="home"><a href="/crowdmodule/dashboard">CrowdScanner</a></h1>

<div >
				<c:if test="${authContext != null}">
				<div style="margin:20px 20px 0px 0px; float:right;">Hello ${authContext.userName}</div>
				</c:if>
</div>	
    <nav id="nav" style="float:none; margin-left:800px;">
    	<ul class="nav-items" id="yw0"><li><form action="<c:url value='/crowdmodule/logout'/>" method="POST"><input type="submit" class="form-boxes-buttons" value="Log out"></form></li>
	</ul>   
 </nav>
 </header>
</div>
<tiles:insertAttribute name="main_content"/>  


<footer id="footer" style="margin-top:0px;padding-top:20px;height:130px" class="placing-3">
   <nav id="footer-nav">
    <ul>
      <li id="footer-nav-home"><a href="/">HOME</a></li>
 <!--     <li id="footer-nav-signin"><a href="#/signin">SIGN OUT</a></li>
      <li id="footer-nav-terms"><a href="/terms/">TERMS</a></li>
    <li id="footer-nav-privacy"><a href="/privacy/">PRIVACY</a></li>-->
    </ul>

  </nav>
</footer>

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




