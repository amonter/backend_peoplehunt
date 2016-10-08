<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<html>
<head>
	<title>Administration</title>
</head>
<body>
	<table width="800" align="center" border="0" cellspacing="0" cellpadding="0">
		<tr>	
			<td background="images/middle_2.jpg" align="left">
				<tiles:insertAttribute name="admintabs-tile" />				
			</td>	
		<tr>
			<td background="images/middle_2.jpg" width="800" height="400">
				 <tiles:insertAttribute name="body-tile" />
			</td>
		</tr>
		</table>   
</body>
</html>

