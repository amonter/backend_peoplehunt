<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
	


<div>
	<!--
	<div class="standardSpacing" style="clear:both;width:919px;">					
		<div style="float:left;">
			<img style="padding-left:80px;" src="http://images.meetforeal.com/images/signup_btn1_mod.png" />
		<div>			
		</div>
	</div>
	-->
	<div class="standardSpacing" style="float:left;override:hidden;">			
		<div style="clear:both;">
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" value="/jsp/events/event_flat_list.jsp" />				
			</tiles:insertTemplate>			
		 </div>			 			
	</div>	
	<div style="float:left;override:hidden;">		
		<div class="standardSpacing" style="clear:both; margin:20px 5px 10px 20px;width:255px;">		
		 	<a  href="createuser.do" class="main_test" style="padding-left:0px;"><b>New to meetforeal?</b></a>
			<div style="font-size:13px;line-height:120%;padding:12px 0px 10px 10px;"  >
				Joining the community is free and it means you'll never miss a meetforeal event happening near you!
				<br><a  href="createuser.do"><img  style="padding:18px 0px 3px 9px;" src="http://images.meetforeal.com/images/sign_me_up.jpg" alt="sign up for meetforeal to receive newsletter"/></a>
				<br><a  href="viewpageaboutus.do"><img  style="padding:5px 3px 3px 3px;" src="http://images.meetforeal.com/images/here.jpg"/></a>
			</div>	
		</div>		
		<div class="standardSpacing" style="width:255px;margin-top:0px;float:left;override:hidden;">		
			<tiles:insertTemplate template="/jsp/profile/new_members.jsp" />
		</div>			
		<div class="standardSpacing" style="clear:both; margin:20px 5px 10px 20px;width:255px;">
			<a href="viewpastevents.do" class="main_test" style="padding-left:0px;"><b>Recent Events</b></a>

			 	
		</div>		
	</div>		
</div>

<script language='javascript'>

function showMeetforeal() {
	
	return showtheVideo();	
}

</script>
