<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

  

<div style="width:300px;padding:20px;">				
	<div class="msg">
		<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
	 	<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>An Error has occurred please try again or <a href="#" onclick="sendthefeedback();">contact meetforeal</a></h3></div></div></div>
		<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
	</div>					
</div>				

<script type="text/javascript">

function sendthefeedback () {
	
	sendFeedback();
}
  
</script>	
			