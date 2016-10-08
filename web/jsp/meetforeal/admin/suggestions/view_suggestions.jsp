<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div class="standardSpacing" style="width:610px;">	
	<c:forEach items="${suggestions}"  var="event" >				
		<div class="event-main">
			<div>
				<h4>
					${event.country}, ${event.state}					
				</h4>
			</div>	
			<div style="margin-top:5px;">					
				<div class="event-header" style="margin-right:5px;">
					${event.speaker}
				</div>
				<div style="float:left;">
					<a href="javascript:saveVote(${event.id},'vote_anchor_${event.id}');">
						<img src="images/add_events.jpg" />
					</a>
				</div>
			</div>
			<div class="event-content" style="font-size:13px;">
				${event.email}				
			</div>
			<div class="event-content" style="font-size:13px;">
				${event.comment}				
			</div>				
			<div class="pricing">
				Price: &#8364; ${event.fee} <br>				
				<form  action="updateSuggestion.htm" method="post">
					<input type="hidden" name="suggestionId" value="${event.id}"/>									
					<c:set var="checked" value="" />
					<c:if test="${event.confirm eq true}">
						<c:set var="checked" value="checked" />
					</c:if>					
					Confirm <input type="checkbox" name="blogpostid" value="true"  ${checked} /><br>
					<input type="submit" value="Update"  />
				</form>
			</div>        
		</div>
	</c:forEach>
<div>
  