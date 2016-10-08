<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
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
<div>
  <section id="blog" class="placing-1">


	<div style="float:left;width:670px;">
		<div style="clear:both;">
			<tiles:insertAttribute name="the_page"/>											
		</div>
		<div style="padding: 10px 0px 8px 0px;">					
			<c:set var="old_page" value="${oldPageNumber + 1}" />
			<c:if test="${oldPageNumber != 1}">				      	 
				<a style="display:inline;color:#008ecf;" href="/theblog/${oldPageNumber - 1}">&#60;</a>
				<a style="display:inline;" class="sidetitle" href="/theblog/${oldPageNumber - 1}">fresher posts!</a>
			</c:if>
			<c:if test="${oldPageNumber == 1}">				      	 
				<span style="padding-right:70px;">&nbsp;</span>
			</c:if>				     
			<c:if test="${maxPage == oldPageNumber}">
				<a style="display:inline;padding-left:460px;"class="sidetitle" href="/theblog/${old_page}">older posts</a> 	
				<a style="display:inline;color:#008ecf;" href="/theblog/${old_page}">&#62;</a>
			</c:if>					 					 
		</div> 	
	</div>

	<div style="float:left;margin:15px 0px 0px 40px;max-width:230px;">

		<div style="padding:10px 0px 15px 0px;text-align:center;">
			<a  href="http://feedburner.google.com/fb/a/mailverify?uri=CrowdscannerBlog&amp;loc=en_US">Subscribe to The CrowdScanner Blog</a>
		</div>
				
		<div>
			<a href="http://www.flickr.com/photos/52207392@N04/galleries/72157626286077492/#photo_5534443499"><img src="http://images.crowdscanner.com/crowdscanner-flickr.png" width="210"style="padding:10px 10px 10px 10px;" height="178" alt="crowdscanner photos at events on flickr"></a>
		</div>

<div style="width:225px" id="__ss_7030630"> <strong style="display:block;margin:12px 0 4px"><a href="http://www.slideshare.net/Meetforeal/crowdscanner-slides" title="CrowdScanner slides">CrowdScanner slides</a></strong> <object id="__sse7030630" width="225" height="188"> <param name="movie" value="http://static.slidesharecdn.com/swf/ssplayer2.swf?doc=crowdscanner-slides-110223075028-phpapp01&stripped_title=crowdscanner-slides&userName=Meetforeal" /> <param name="allowFullScreen" value="true"/> <param name="allowScriptAccess" value="always"/> <embed name="__sse7030630" src="http://static.slidesharecdn.com/swf/ssplayer2.swf?doc=crowdscanner-slides-110223075028-phpapp01&stripped_title=crowdscanner-slides&userName=Meetforeal" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="225" height="188"></embed> </object> <div style="padding:5px 0 12px"> View more <a href="http://www.slideshare.net/">presentations</a> from <a href="http://www.slideshare.net/Meetforeal">CrowdScanner</a> </div> </div>	
	
	</div>
</section>
</div>
