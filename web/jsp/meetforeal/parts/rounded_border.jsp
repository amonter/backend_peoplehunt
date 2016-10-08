<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<table class="rounded" cellspacing="0" cellpadding="0">
	<tr>
		<td class="round_tl"/>
		<td class="round_t"/>
		<td class="round_tr"/>
	</tr>
	<tr>
		<td class="round_l"/>
		<td>
			<tiles:insertAttribute name="content" />
		</td>
	  		<td class="round_r"/>
	</tr>
	<tr>
		<td class="round_bl"/>
		<td class="round_b"/>
		<td class="round_br"/>
	</tr>
</table>	