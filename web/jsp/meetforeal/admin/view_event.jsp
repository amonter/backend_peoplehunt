<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    

<div class="standardSpacing">
	<a href="editeventhtml.htm">Edit Html</a>
</div>

<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
	<div style="margin:6px;width:610px;">			
		<div class="event-main">
			<div style="margin-top:5px;">
				<div class="event-date">
					<h4><fmt:formatDate pattern="MMM" value="${event.startDate}" /><br/><fmt:formatDate pattern="dd" value="${event.startDate}" />
					</h4>
				</div>
				<div class="event-header" style="margin-right:115px;">
					<a style="padding:0px;" href="">${event.name}</a><br/>
					<span style="font-size:14px;letter-spacing:-0.5px;"><fmt:formatDate pattern="HH:mm" value="${event.startDate}" />: ${event.location}</span>
				</div>
				<div style="float:left;">
					<div style="float:left;margin-top:5px;">
						<a id="${event.googleId}" style="" href="#" onclick="saveEvent('${event.googleId}','${event.name}','${contextPath}/addToCart.do?itemId=${event.googleId}');"><img src="images/save_mycart.png" /></a>
					</div>
					<div style="float:left;margin:12px 3px 0px 3px;">
						or
					</div>
					<div style="float:left;margin-top:5px;">
						<a href="booknow.do?eventId=${event.googleId}"><img src="images/book_now.jpg"></a>
					</div>
				</div>
			</div>
			<div class="event-content">
				${event.content}		
			</div>
			<div class="event-content" style="margin:5px;">
				${event.html}
			</div>		
		</div>													
	</div>
</tiles:insertTemplate>		