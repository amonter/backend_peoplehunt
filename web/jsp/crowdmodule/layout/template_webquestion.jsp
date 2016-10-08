<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<div style="display:block;height:200px;">
	<c:if test="${event.id == 88}">
		<a href=""><img style="float:left;padding:20px 0px 0px 40px;" src='http://images.crowdscanner.com/peopleHunt.png'/></a>
                <a href=""><img style="float:right;padding:20px 0px 0px 0px" src="http://images.crowdscanner.com/CrowdScanner_mindfield.png"></a>

	</c:if>
	<c:if test="${event.id != 88}">
		<a href=""><img style="height:160px;float:left;padding:20px 0px 0px 40px;" src='${event.imageURL}'/></a>
		<a href=""><img style="height:110px;float:right;padding:20px 0px 0px 0px" src="http://images.crowdscanner.com/power_logo_cubes.png"></a>         
	</c:if>
	</div>
	<div>
				<c:if test="${authContext != null}">
				<div style="margin:20px 20px 0px 0px; float:right;">Hello ${authContext.userName}</div>
				</c:if>
	</div>	
 </header>
</div>

<tiles:insertAttribute name="main_content"/>	

<footer id="footer" style="margin-top:0px;padding-top:20px;height:130px" class="placing-3">
   <nav id="footer-nav">
    <ul>
      <!--<li id="footer-nav-terms"><a href="/terms/">TERMS</a></li>-->
      <!--<li id="footer-nav-privacy"><a href="/privacy/">PRIVACY</a></li>-->
      <!--<li id="footer-nav-home"><a href="/">FAQ</a></li>-->
      <li id="footer-nav-home">
	<c:if test="${event.id == 88}">
		<a style="padding-bottom:20px;" href="http://itunes.apple.com/ie/app/mindfield/id431285693?mt=8&ls=1#"><img src="http://distillery.s3.amazonaws.com/static/images/appStoreBadge.png" /></a><span style="color:#333333;font-size:18px;">iPhone Users</span>
       </c:if> 
       <c:if test="${event.id != 88}">
       		<a style="padding-bottom:20px;" href="http://itunes.apple.com/us/app/crowdscanner/id356256266?mt=8"><img src="http://distillery.s3.amazonaws.com/static/images/appStoreBadge.png" /></a><span style="color:#333333;font-size:18px;">iPhone/iPad Users</span>
	</c:if>
	</li>
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




