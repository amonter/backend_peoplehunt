<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="com.myownmotivator.web.utils.session.*" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div class="standardSpacing">
<div style="float:left;">
	<img src="images/de_challenge.jpg" />
	<span style="display:block;max-width:605px;font-size:85%;padding-top:8px;color:#666666;">

	Do you notice bad design?
<br><br>
The Design Challenge is open for you guys to share creatively captured bad designs, so we can all become more aware, and design better for humans. <br><br>The most popular entries will win tickets to the <a href="retrieveevent.do?eid=27" style="color:#008ECF;">meetforeal style workshop</a>. Send us your ideas <a href="aboutdesignchallenge.do" style="color:#008ECF">here</a>!
		
	</span>
</div>
<div style="padding:10px 0px 10px 40px">
<a href="retrieveevent.do?eid=27"><img style="padding-left:30px;" src="images/design/balloon.jpg"></a>
</div>
</div>

<div class="standardSpacing" style="min-height:700px;">	
	<div style="float:left;padding-right:15px;height:500px;" >	
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute name="content">
				<div style="width:610px;">
					
					<c:forEach items="${eventSuggestions}" var="event">				
						<div class="event-main" style="padding-top:15px;">
							<div class="event-date" style="width:100px;background-color:#8CC83C;">
								<h4>
									<b>${event.speaker}</b>					
								</h4>
							</div>	
							<div style="margin-top:5px;">							
								<div style="float:left;padding-left:250px;" id="vote_anchor_${event.id}" >
									<a href="javascript:saveVote(${event.id},'vote_anchor_${event.id}');">
										<img style="padding-left:10px;" src="images/ilikethis.jpg" />
									</a>
								</div>
							</div>							
							<div class="event-content" style="font-size:13px;">
								${event.comment}				
							</div>										       
						</div>
					</c:forEach>
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>	
	</div>
	<div style="float:left;">
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">
			<tiles:putAttribute name="content" >
				<div style="max-width:295px;width:280px;height:600px;">
					<div style="height:380px;background:#FFFFFF;">
						<div class="top_header">
							<img src="images/next_event.png" style="padding:2px 0px 2px 4px;" />
						</div>
                         <div>

                          	<a href="retrieveevent.do?eid=25"><img width="280px" src="images/new_tastesmell.jpg" /></a>
						</div>						
                        <div style="margin-top:6px;">
                        	<a href="retrieveevent.do?eid=25" ><img src="images/more.jpg" /></a>  
                        </div>  																	
					</div>					
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>
	</div>
</div>

<script language='javascript'>

function saveVote(vote, anchor) {

	doVote(vote, anchor);
	
}

</script>
