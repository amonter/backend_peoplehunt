<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">

	$(document).ready(function() {
		$("input[name=delete]").click(function(){
			var choice = confirm("Do you want to remove the profile");
			if (choice) {
				$("#message").text("Deleting profile").show();
				var profileId = $(this).parent().find("> input[name=profileId]").val();
				var profileDiv = $(this).parent();
				 $.post("/crowdmodule/event/${event.id}/profile/"+profileId+"?action=delete", {},
						   function(id){
					  			$("#message").text("Deleted successfully");
					  			$("#message").fadeOut('slow');
					  			profileDiv.fadeOut('slow',function(){
					  				$(this).remove();
					  			})
				  			});
			}
		});
	});
	
</script>

<c:if test="${fn:length(profiles) > 0}">
<div class="placing-1 events">
	<h1 id="titleOfCreate">Statistics for ${event.description}</h1>
	<h2 class="form-extra-details" style="margin:0px 0px 60px 0px;text-align:center;">People who have answered ${event.description}</h2>

	<div id="message"></div>
<c:forEach var="entry" items="${profiles}">
	<div class="statistics" style="height:120px;">
	<div class="profile">
		<div>
		<img src="${entry.value}" style="float:left;width:80px; margin-top:-25px;padding-left:20px"/> 
		<div style="float:left;margin:5px 20px 0px 20px;"> ${entry.key.user.firstName} ${entry.key.user.lastName} ${entry.key.user.email}</div>
		<input type="hidden" name="profileId" value="${entry.key.id}"/>
	       <input type="submit" name="delete" class="form-boxes-buttons" value="Delete"/> 
		<br><br>
		</div>
	</div>
	</div>
</c:forEach>

<br><br> 
<form action="<c:url value='event/${event.id}/satistics'><c:param name='type' value='csv'/></c:url>"><button type="submit" class="form-boxes-buttons">Export as .csv file</button> </form>
</c:if>

<c:if test="${fn:length(profiles) == 0}">
<br><br> 
<h2 class="form-extra-details" style="margin-left:0px;">Nobody has answered <span style="background-color:#FF0094">&quot;${event.description}&quot;</span> yet</h2>
</c:if>
<br><br> 
<form action="/crowdmodule/dashboard"><button type="submit" class="form-boxes-buttons">Back</button> </form>

</div>
