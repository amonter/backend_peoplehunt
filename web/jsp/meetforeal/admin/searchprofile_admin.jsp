<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>

<%   
	Map countryMap = (Map)request.getAttribute("countryList");
	if (countryMap != null) {
		
		Collection countryList = countryMap.values();
		pageContext.setAttribute("countryCollection", countryList);
	}
%>

<form action="adminsearchusers.htm" method="get">			
	<table height="45" class="usersearchbox" style="border:1px solid #CCCCCC;padding:10px;" border="0" cellspacing="0" cellpadding="0">
		<tr>											
			<td class="alignment">
				Criteria											
 			</td>	
 			<td>
 				 <input type="text" name="criteria" style="width:184px"/>
 			</td>
 		</tr>			 			
		<tr >
			<td class="alignment">
				Gender
			</td>
 			<td class="alignmentGender">						 									 								 				
				<input type="radio" name="gender" value="both" checked>Both <br>					 					
 				<input type="radio" name="gender" value="male">Male <br>
 				<input type="radio" name="gender" value="female">Female <br>											 									 				
 			</td>
		</tr>
		<tr>
			<td class="alignment">
				Location
			</td>
			<td>
				<select name="country">
 					<c:forEach var="country" items="${countryCollection}">
				  		<option value ="${country.code}">${country.label}</option>											 
				  	</c:forEach>	
				</select>	
			</td>
		</tr>
		<tr>											
			<td class="alignment">
				County											
 			</td>	
 			<td>
 				 <input type="text" name="state" style="width:184px"/>
 			</td>
 		</tr>	
		<tr>
			<td colspan="2">
				<input name="Submit" type="submit" class="search_button" value="Search" style="margin-left:110px;margin-top:15px;" />	
			</td>
		</tr>
	</table>						
</form>			  		