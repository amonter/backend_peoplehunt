<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<table class="rounded_red" cellspacing="0" cellpadding="0">
	<tr>
		<td class="round_tl_red"/>
		<td class="round_t_red"/>
		<td class="round_tr_red"/>
	</tr>
	<tr>
		<td class="round_l_red"/>
		<td>
			<tiles:insertAttribute name="content" />
		</td>
	  		<td class="round_r_red"/>
	</tr>
	<tr>
		<td class="round_bl_red"/>
		<td class="round_b_red"/>
		<td class="round_br_red"/>
	</tr>
</table>	