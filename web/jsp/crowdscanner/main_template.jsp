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

<script type="text/javascript" src="http://use.typekit.com/hzn8glz.js"></script>
<script type="text/javascript">try{Typekit.load();}catch(e){}</script>
<script type="text/javascript" src='http://images.crowdscanner.com/jquery-1.4.2.min.js' ></script>
<link rel="stylesheet" type="text/css" href="http://images.crowdscanner.com/facescss.css" />
<title><tiles:insertAttribute name="title"/></title>
</head>
<body>	

<div class="not-used"></div>
<div class="container header dashed-line-header">  <header id="header" class="placing-1">    <h1 id="home"><a href="/">CrowdScanner</a></h1>

    <nav id="nav">
    <ul class="nav-items" id="yw0">
<li><a href="/crowdmodule/auth/login">SIGN IN</a></li>
</ul>    </nav>
  </header>
</div>

<div>
			<tiles:insertAttribute name="main_content"/>				
</div>

<footer id="footer" class="placing-3">
  <div class="cta-text" style="text-align:center;">Contact <a href="mailto:ellen@crowdscanner.com">ellen@crowdscanner.com</a> for more information</div>
 <!-- <div class="green-button small-green-button cta-button"><a href="crowdmodule/auth/register">Sign Up Now</a></div>-->
<div id="cta" class="stripey">
</div>
 
  <section id="social">

    <ul>
       <li id="social-twitter-follow" class="social-item social-item-follow">
        <div>
          Follow <a href="http://twitter.com/crowdscanner">CrowdScanner on Twitter</a> 
        </div>
      </li>
    </ul>
  </section>


  <nav id="footer-nav">
    <ul>
	<li id="footer-nav-terms"><a href="/jobs">JOBS</a></li>
  	<li id="footer-nav-signup"><a href="aboutus">ABOUT US</a></li>
	<li id="footer-nav-blog"><a href="/theblog">BLOG</a></li>
      	<li id="footer-nav-home"><a href="/">HOME</a></li>
   <!--   <li id="footer-nav-signin"><a href="#/signin">SIGN IN</a></li>
      <li id="footer-nav-support"><a href="/">SUPPORT</a></li>
      <li id="footer-nav-terms"><a href="/terms/">TERMS</a></li>
      <li id="footer-nav-privacy"><a href="/privacy/">PRIVACY</a></li>-->
    </ul>
</nav>
<div style="padding-top:10px; font-size:14px;">&#169; 2011 DudleyMont Ltd
</div>
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




