<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="standardSpacing" style="margin-left:80px;">
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content">
			<div class="create_user_div" style="max-width:740px;">				
				<div class="conf">
					<p><b>Thanks a lot!</b> - We really appreciate your input. We will review it and get back to you as soon as possible</p>					
				</div>
			</div>
		</tiles:putAttribute>
	</tiles:insertTemplate>  
</div>