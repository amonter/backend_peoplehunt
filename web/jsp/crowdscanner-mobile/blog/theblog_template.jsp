<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<div class="container-mobile banner">
  <section class="placing-1-mobile">

	<div style="margin:0px 0px 20px 20px;">	
		<div style="float:left;width:480px;">
			<div style="clear:both;">
				<tiles:insertAttribute name="the_page"/>											
			</div>	
		</div>
		<div style="float:left;margin:15px 0px 0px 115px;max-width:500px;">
            		<div style="padding:10px 0px 15px 0px;">
             			 <a href="http://feedburner.google.com/fb/a/mailverify?uri=CrowdscannerBlog&amp;loc=en_US">Subscribe to The CrowdScanner Blog</a>
            		</div>		
	</div>
</section>
</div>
