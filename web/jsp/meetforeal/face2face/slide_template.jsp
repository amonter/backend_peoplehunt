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

<div class="standardSpacing" style="font-size:80%;">
	<div style="padding:10px 0px 60px 0px;">
		<span style="font-size:160%;color:#008ECF;"><b>Seedcamp Prototype Presentation</b></span>
	</div>
	<div class="tour-nav">		
		<ul>
			<li ${li_1}><a ${li_1} href="seedcamp.do?slide=1">General overview</a></li>
			<li ${li_2}><a ${li_2} href="seedcamp.do?slide=2">Step 1</a></li>
			<li ${li_3}><a ${li_3} href="seedcamp.do?slide=3">Step 2</a></li>
			<li ${li_4}><a ${li_4} href="seedcamp.do?slide=4">Step 3</a></li>
			<li ${li_5}><a ${li_5} href="seedcamp.do?slide=5">Final Considerations</a></li>			
		</ul>
	</div>
	<div class="tour-nav-content">
		<div class="tour-header">
			<p class="tour-links">
				${slide} of 6 
				<c:if test="${slide != 1}">
					<a href="seedcamp.do?slide=${slide - 1}">
						<img src="images/left_arrow.png" alt="previous"/>
					</a> 
				</c:if>
				<c:if test="${slide != 6}">
					<a href="seedcamp.do?slide=${slide + 1}">
						<img src="images/right_arrow.png" alt="next"/>
					</a>
				</c:if>
			</p>
			<c:if test="${slide == 1}">
				<h2>Meetforeal face2face overview</h2>
			</c:if>
			<c:if test="${slide == 2}">
				<h2>Step 1 on face2face application</h2>
			</c:if>	
			<c:if test="${slide == 3}">
				<h2>Step 2 meetforeal platform processing</h2>
			</c:if>	
			<c:if test="${slide == 4}">
				<h2>Step 3 the resulting landmark will be displayed on real time at the events</h2>
			</c:if>	
			<c:if test="${slide == 5}">
				<h2>Final considerations</h2>
			</c:if>				
		</div>
		<div>
			<tiles:insertTemplate template="/jsp/face2face/${slide_page}.html" />			
		</div>
		<p class="tour-links">
			<c:if test="${slide != 1}">
				<a href="seedcamp.do?slide=${slide - 1}">
					<img src="images/left_arrow.png" alt="previous"/>
				</a> 
			</c:if>
			<c:if test="${slide != 6}">
				<a href="seedcamp.do?slide=${slide + 1}">
					<img src="images/right_arrow.png" alt="next"/>
				</a>
			</c:if>
		</p>		
	</div>
</div>