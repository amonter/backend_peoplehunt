<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<html>
<head profile="http://www.w3.org/2005/10/profile">
	<title><tiles:getAsString name="title" ignore="true"/></title>
	<link rel="icon" type="image/x-icon" href="http://images.meetforeal.com/images/smallericon2.ico">	
	<link rel="shortcut icon" href="images/smallericon2.ico"> 
	<meta name="verify-v1" content="mh1MmdpMHYWT6PKtICNCMUtPH/RC+1if+ORwedgK3Kw=" />
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<meta name="title" content="Meetforeal" />
	<meta name="description" content="<tiles:getAsString name='description' ignore='true'/>" />
	<link rel="image_src" href="http://images.meetforeal.com/images/meetforeal_logo.jpg" />	
	<meta name="medium" content="news" />
	<c:if test="${!empty blog}">
		<c:set var="init"  value="init"/>     
	</c:if>
	<c:if test="${empty init}">
		<script language='javascript' src='${pageContext.request.contextPath}/tabs/gwt3/gwt3.nocache.js'></script>
	</c:if>
	<!--
		localhost key:ABQIAAAAqOLU1ZmjoAJhpAUMXWu3fBQ1vZi2fP7a8vQ3GB0adS-AD6ARUBSKrJlnh6XQOl_4PVM1YXjDRhqhpA
		meetforeal key:ABQIAAAAqOLU1ZmjoAJhpAUMXWu3fBTTib4Hydy0wq4MxfvJ2obqaysWGBQVQ9uzd6jz0_TA1bCFVmA_ZRr_Ng
	  -->
		
	<link href="css/index.css" rel="stylesheet" type="text/css" />
</head>

<body onload="activatePlaceholders();">
	<table width="800" align="center" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<  name="top_header"/>
			</td>
		</tr>
		<tr>
			<td style="background: url(http://images.meetforeal.com/images/new_header.jpg);"  height="19">
				&nbsp;
			</td>			
		</tr>
		<tr>
			<td class="site_background" height = "137"  valign="bottom">				
				<tiles:insertAttribute name="header"/>
			</td>
		</tr>	
				
		<tr>
			<td class="site_background" height = "380" valign="top">		
				<tiles:insertAttribute name="body-tile"/>
			</td>
		</tr>	
		<tr>
			<td>
				<tiles:insertAttribute name="foot-tile"/>
			</td>
		</tr>
		<tr>
			<td>
				<tiles:insertAttribute name="page-footer"/>
			</td>	
		</tr>		
	</table>
	<script type="text/javascript">
		var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
		document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
		</script>
		<script type="text/javascript">
		var pageTracker = _gat._getTracker("UA-1869004-3");
		pageTracker._trackPageview();
	</script>
</body>

</html>
