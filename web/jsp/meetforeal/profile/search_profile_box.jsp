<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%   
	Map countryMap = (Map)request.getAttribute("countryList");
	Collection countryList = countryMap.values();
	pageContext.setAttribute("countryCollection", countryList);
%>

<form action="searchuserhome.do" method="get">			
	<input type="hidden" name="criteria" value="" />
	<table height="45" class="usersearchbox" border="0" cellspacing="0" cellpadding="0">				 			
		<tr>
			<td class="alignment">
				Gender
			</td>
 			<td class="alignmentGender">
 										 									 								 				
				<input type="radio" name="gender" value="both" checked>&nbsp;All <br>					 					
 				<input type="radio" name="gender" value="male">&nbsp;Male <br>
 				<input type="radio" name="gender" value="female">&nbsp;Female <br>
 				<input type="radio" name="gender" value="couple">&nbsp;Couple <br>												 									 				
 			</td>
		</tr>
		<tr>
			<td class="alignment">
				Location
			</td>
			<td>			
				<select onchange='javascript:hideSelect(this)' name='country' id='input_type' class="input_type_new">
					<option value ="%">All</option>	
 					<c:forEach var="countryVar" items="${countryCollection}">
 						<c:set var="selection" value=""/>
 						<c:if test="${countryVar.code eq country}">
 							<c:set var="selection" value="selected"/>
 						</c:if>	
				  		<option value ="${countryVar.code}" ${selection}>${countryVar.label}</option>				  												 
				  	</c:forEach>	
				</select>	
			</td>
		</tr>
		<tr  id='state_tr' style='display:none'>				
			<td class='alignment' style='padding-top:10px;'>		
				State		
			</td>		
			<td  style='padding-top:10px;'>	
				<select name='state' id='input_type_state' class="input_type_new">						
				    <option value ="%">All</option>		
					<option value ='Carlow'>Carlow</option>	
					<option value ='Cavan'>Cavan</option>	
					<option value ='Clare'>Clare</option>	
					<option value ='Cork'>Cork</option>	
					<option value ='Donegal'>Donegal</option>	
					<option value ='Dublin'>Dublin</option>	
					<option value ='Galway'>Galway</option>	
					<option value ='Kerry'>Kerry</option>	
					<option value ='Kildare'>Kildare</option>	
					<option value ='Kilkenny'>Kilkenny</option>	
					<option value ='Laois'>Laois</option>	
					<option value ='Leitrim'>Leitrim</option>	
					<option value ='Limerick'>Limerick</option>	
					<option value ='Longford'>Longford</option>	
					<option value ='Louth'>Louth</option>	
					<option value ='Mayo'>Mayo</option>	
					<option value ='Meath'>Meath</option>	
					<option value ='Monaghan'>Monaghan</option>	
					<option value ='Offaly'>Offaly</option>	
					<option value ='Roscommon'>Roscommon</option>	
					<option value ='Sligo'>Sligo</option>	
					<option value ='Tipperary'>Tipperary</option>	
					<option value ='Waterford'>Waterford</option>	
					<option value ='Westmeath'>Westmeath</option>	
					<option value ='Wexford'>Wexford</option>	
					<option value ='Wicklow'>Wicklow</option>	
				</select>			
			</td>		
		</tr>		
		<tr>
			<td colspan="2">
				<input name="Submit" type="image" src="images/Search.jpg"  style="margin-left:110px;margin-top:15px;" />	
			</td>
		</tr>
	</table>						
</form>

<script type="text/javascript">

var selectedCountry = document.getElementById("input_type");
var theCountry = selectedCountry.options[selectedCountry.selectedIndex].value;
if (theCountry == 'Ireland') {
	
	document.getElementById("state_tr").style.display = '';	
}	

function hideSelect(name) {
	
	var selectedCountry = name.options[name.selectedIndex].value;
	if (selectedCountry != 'Ireland') {
		
		document.getElementById("state_tr").style.display = 'none';
		document.getElementById("input_type_state").options[0].value = '';		
						
	}
	else if (selectedCountry == 'Ireland') {
	
		document.getElementById("state_tr").style.display = '';	
	}		
} 
  
</script>


 			  		