<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="standardSpacing">
	<c:if test="${!empty message}">
		<div id="OuterErrorLayer">
			<div id="ErrorLayer">
				<c:out value="${message}" />
			</div>
		</div>
	</c:if>
	<c:if test="${active}">
		<div id="account_active" style="display:none;"><spring:message code="message.account.active" /></div>
	</c:if>
</div>	
	
<div style="margin:20px 0px 20px 30px;width:auto;height:230px;">	
	<div style="float:left;">
		<table border = "0" cellpadding="3" cellspacing="0">				
			<tr>
				<td width="150px" valign="top">
					<div id="PhotoOuterBorder">
						<c:if test="${user.imageID != -1}" >
							<c:set var="imageId" value="${user.imageID}" />
						</c:if>
						<c:if test="${user.imageID == -1}" >
							<c:set var="imageId" value="${user.gender}" />										
						</c:if>								
						<img src="picture_library/profile/${imageId}.jpg" alt="profile" />										
					</div>
					<meet:currentUserInSession profileId="${user.profile.id}">
						<div style="clear:both;">	
							<span class="edit_profile">
								<img src="http://images.meetforeal.com/images/arrow.png">
								<a href="uploadFile.do">change photo</a>
							</span>
						</div>
						
						
					</meet:currentUserInSession>
					<c:if test="${userLogged == 'loggedAsOther'}">
						<div style="clear:both;">
							<span class="edit_profile">
								<nobr><img src="images/arrow.png">								
								<a href="javascript:sendtheMessage('${user.profile.id}','${user.userName}');" id="popupPosition" style="margin-right:5px;color:blue;">Send ${user.userName} a Message</a></nobr>
							</span>
						</div>
					</c:if>					
					<c:if test="${empty userLogged}" >
						<div style="clear:both;">
							<span class="edit_profile">
								<nobr><img src="images/arrow.png">		
								<a href="javascript:notLoggedIn();" id="popupPosition" style="margin-right:5px;"> Send ${user.userName} Message</a>									
							</span>
						</div>
					</c:if>																						
				</td>
				<td  width="150px;" valign="top" style="padding-right:10px;">
					<div style="padding-bottom:6px;">
						<span style="font-size:1.8em;font-weight:bold;color:#008ecf;">
							<c:out value="${user.userName}" />
						</span>							  			  																																				
					</div>
					<div style="width:200px;">										
						<table style="font-family:'Trebuchet MS',Helvetica,Arial,sans-serif">
							<c:if test="${user.profile.publicProfile eq false}">
                                <tr>
                                    <td>
                                        <span style="color:#606060;">Age:</span>
                                    </td>
                                    <td>
                                        <span style="margin-left:5px;">${user.age}</span>
                                    </td>
                                </tr>
                            </c:if>
							<tr>
								<td>
									<span style="color:#606060;">Living in:</span>
								</td>
								<td>
									<span style="margin-left:5px;">
										<c:if test="${!empty user.state}">
											${user.state},
										</c:if>
										${user.country}
									</span>
								</td>
							</tr>
							<tr>
								
								<td>
									<span style="margin-left:0px;">${user.gender}</span>
								</td>
							</tr>														
						</table>					
					</div>
				</td>
				<meet:currentUserInSession profileId="${user.profile.id}">
					<td valign="top">
						<div style="padding-top:0px;">
							<div id="settings_button"></div>
						</div>
					</td>
				</meet:currentUserInSession>									
			</tr>
		</table>
	</div>	
	<div style="float:left;margin-left:158px;">
		<div style="margin:0px 0px 15px 0px;">	
			<div id="collapsible_user">			
			</div>
		</div>
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute name="content" value="/jsp/events/event_participants.jsp" />
		</tiles:insertTemplate>	
	</div>	
</div>				


<meet:currentUserInSession profileId="${user.profile.id}">				

</meet:currentUserInSession>
		
<script language='javascript'>

function loadTheInbox() {

	loadUserInbox();
	
}

function showNextElement(element1, element2) {
	
	questionaireEffect(element1, element2);			
}

function sendtheMessage(profileId,name) {

	sendMessage(profileId,name);	
}

function notLoggedIn() {

	notLoggedPopup();
}

</script>





	
