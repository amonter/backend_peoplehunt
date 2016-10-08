<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 


<div class="standardSpacing" >
	<div style="padding:5px 10px 0px 0px;float:left;">
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute name="content" value="/jsp/events/event_participants_full.jsp" />
		</tiles:insertTemplate>
	</div> 
	<div style="float:left;">
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute name="content" value="/jsp/events/event_flat_list.jsp" />
		</tiles:insertTemplate>	
	</div>
	<div  style="float:left;padding-left:45px;">
		<div id="maps">	
			<div id="latitude" style="display:none">${events[0].mapLatitude}</div>
			<div id="longitude" style="display:none">${events[0].mapLongitude}</div>	
		</div>
	</div>
</div>