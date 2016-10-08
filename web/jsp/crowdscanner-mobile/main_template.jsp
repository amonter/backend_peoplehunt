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
	<link rel="icon" type="image/x-icon" href="images/smallericon2.ico">	
	<link rel="shortcut icon" href="http://images.crowdscanner.com/favicon.ico">
        <meta name="google-site-verification" content="D_E7KSHlo9bHBPirSHRyNgwntYA8ncHPY5kzzvPd1iU" />	
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />	
	<meta name="description" content="<tiles:insertAttribute name='title'/>" />	
	<meta name="viewport" content="width=device-width">
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
	<link href="http://images.crowdscanner.com/facescss.css" rel="stylesheet" type="text/css" />
	
</head>
<body>
<div style="height:170px;"class="container-mobile header dashed-line-header" >
<header id="header-mobile" class="placing-1-mobile">   
<h1 id="home-mobile"><a href="/">CrowdScanner</a></h1>

</header>
</div>
	
<div>
				<tiles:insertAttribute name="main_content"/>				
</div>
<footer id="footer-mobile" class="placing-3-mobile">
  <div class="cta-text-mobile" style="margin-left:0px;margin-top:20px;">Contact <a href="mailto:ellen@crowdscanner.com">ellen@crowdscanner.com</a> for more information.</div>
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


  <nav id="footer-nav-mobile">
    <ul>
      <li id="footer-nav-home"><a href="/">HOME</a></li>
    <li id="footer-nav-signin"><a href="/aboutus">ABOUT US</a></li>
      <li id="footer-nav-blog"><a href="/theblog">BLOG</a></li>
       <li id="footer-nav-tour"><a href="/jobs">JOBS</a></li>
     <!--<li id="footer-nav-terms"><a href="/terms/">TERMS</a></li>-->
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

