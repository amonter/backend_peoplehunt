
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    

<div style="margin:5px;">
	<table style="padding:5px;">
		<tr>
			<td align="center" width="50%">
				Do you exercise on the regular basis?
			</td>	
			<td align="center">
				<img src="http://chart.apis.google.com/chart?chs=370x190&chd=t:${stats.sporty_yes},${stats.sporty_no}&cht=p3&chl=Yes|No" title="Yes ${stats.sporty_yes}: No ${stats.sporty_no}" />
			</td>
		<tr>
	</table>
</div>
<div style="margin:5px;">
	<table style="padding:5px;">
		<tr>
			<td align="center" width="50%">
				Are you intellectual?
			</td>	
			<td align="center">
				<img src="http://chart.apis.google.com/chart?chs=370x190&chd=t:${stats.intellectual_yes},${stats.intellectual_no}&cht=p3&chl=Yes|No" title="Yes ${stats.intellectual_yes}: No ${stats.intellectual_no}"  />
			</td>
		<tr>
	</table>	
</div>
<div style="margin:5px;">
	<table style="padding:5px;">
		<tr>
			<td align="center" width="50%">
				Do you like to share emotions?	
			</td>	
			<td align="center">
				<img src="http://chart.apis.google.com/chart?chs=370x190&chd=t:${stats.share_emotions_yes},${stats.share_emotions_no}&cht=p3&chl=Yes|No" title="Yes ${stats.share_emotions_yes}: No ${stats.share_emotions_no}" />
			</td>
		<tr>
	</table>	
</div>
<div style="margin:5px;">
	<table style="padding:5px;">
		<tr>
			<td align="center" width="50%">
				What type of groups do you like?	
			</td>	
			<td align="center">
				<img src="http://chart.apis.google.com/chart?chs=370x190&chd=t:${stats.small_yes},${stats.medium_yes},${stats.large_yes}&cht=p3&chl=Small|Medium|Large" title="Small ${stats.small_yes}: Medium ${stats.medium_yes}: Large${stats.large_yes}" />
			</td>
		<tr>
	</table>	
</div>
<div style="margin:5px;">
	<table style="padding:5px;">
		<tr>
			<td align="center" width="50%">
				How often do you talk to strangers?
			</td>	
			<td align="center">
				<img src="http://chart.apis.google.com/chart?chs=400x190&chd=t:${stats.hard_interaction_yes},${stats.medium_interaction_yes},${stats.some_interaction_yes}&cht=p3&chl=Hard Intr |Medium Intr|Some Intr" title="${stats.hard_interaction_yes},${stats.medium_interaction_yes},${stats.some_interaction_yes}" />
			</td>
		<tr>
	</table>	
</div>
<div style="margin:5px;">	
	<table style="padding:5px;">
		<tr>
			<td align="center" width="50%">
				Trend Graphic
			</td>	
			<td align="center">
				<img src="http://chart.apis.google.com/chart?cht=lc&chxt=x,y&chxl=0:|Q1|Q2|Q3|Q4|Q5|1:||Yes|No|Small|Medium|Large|H Int|M Int|L Int&chs=450x260&chd=s:${trand_chart_values}&chg=25,12.5" title="${stats.hard_interaction_yes},${stats.medium_interaction_yes},${stats.some_interaction_yes}" />
			
			</td>
		<tr>
	</table>	
</div>

<script language='javascript'>

var simpleEncoding = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
 

var result = simpleEncode(new Array(8,38,53,15,61), 61);

//alert(result);



function simpleEncode(valueArray,maxValue) {
	
	var chartData = ['s:'];
	  for (var i = 0; i < valueArray.length; i++) {
	    var currentValue = valueArray[i];
	    if (!isNaN(currentValue) && currentValue >= 0) {
	    chartData.push(simpleEncoding.charAt(Math.round((simpleEncoding.length-1) * currentValue / maxValue)));
	    }
	      else {
	      chartData.push('_');
	      }
	  }
	return chartData.join('');
}

</script> 