<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="standardSpacing" style="padding:5px 10px 0px 0px;float:left;">
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content">
			<div>
				<p>
					Your transaction has failed for the following status <b>${paymentStatus}</b> because <b>${reason}</b>.
				</p>
				<p>
					Please contact us <a href="javascript:sendthefeedback();"><b>here</b></a>.
				</p>					
			</div>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</div>

<script type="text/javascript">

function sendthefeedback () {
	
	sendFeedback();
}
  
</script>	