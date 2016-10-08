<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>   
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="unselectedtab">
					<a href="retrievetags.htm">Create Tag</a> 
				</div>
			</td>
		    <td>
				<div class="unselectedtab" id="${hometab}" >
					<a id="tab_id" href="taglinking.htm">Link Tag/Event</a>
				</div>
			</td>								
		</tr>
	</table>
</div>
<div>
	<tiles:insertAttribute name="function-tile" />
</div>