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

<div class="standardSpacing" >
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content">
			<form action="savetestimony.do" method="POST">
				<div style="margin:10px;">				
					<div class="comment_box">	
						<div style="clear:both;padding-bottom:10px;color:#333333;" id="niceFont">Event you attended:</div>
						<div>
							<input id="input_type"  class="createUserField" name="event" type="text" />
						</div>
						<div style="padding-top:10px;">	
							<span style="color:#333333;" id="niceFont">What was your experience? Why should others go?</span>		
						</div>
					</div>
					<div>
						<textarea  id="input_type" name="content" cols=80 rows=6></textarea>
					</div>
					<div style="padding-top:10px;">	
						<input type="Submit" style="margin-left:300px;" value="Submit" />	
					</div>
				</div>
			</form>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</div>	