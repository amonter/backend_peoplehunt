<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<%   
	SessionContextObject sessionContext = (SessionContextObject)request.getSession().getAttribute("userSessionContext");
	if (sessionContext != null) {
	
		pageContext.setAttribute("email_sender", sessionContext.getEmail());	
	}	
%>



<tiles:insertTemplate template="/jsp/parts/regular_content.jsp">    
	<tiles:putAttribute name="the_page">
		<div>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute  name="content" value="/jsp/events/event_list_table.jsp" />
			</tiles:insertTemplate>
		</div>
	</tiles:putAttribute>		
</tiles:insertTemplate>	



