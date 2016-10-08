<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="meet" uri="meetforealtags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>



<meet:currentUserInSession profileId="${user.profile.id}">
	<div style="margin:10px 0px 0px 15px;clear:both;" id="innertabs">
		<div id="tabselector" style="display:none;">${tab_selector}</div>
	</div>
</meet:currentUserInSession>
<c:if test="${empty userLogged || userLogged == 'loggedAsOther'}">
	<div id="InfoBoxLayer">
		<div style="float:left;margin-right:10px;height:auto;">	
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>My website</h3>
						</div>
						<div>
							<ul>
								<li><a style="color:#008ECF" href="${user.profile.profileInfo.website}">${user.profile.profileInfo.website}</a></li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>Languages I speak/am learning/wish to learn</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.languages}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content">
					<div class="info_box">
						<div>
							<h3>Amazing places you have travelled</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.amazingThings}</li>	
							</ul>
						</div>
					<div>
					
				</tiles:putAttribute>
			</tiles:insertTemplate>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content">
					<div class="info_box">
						<div>
							<h3>Interests & Passions</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.interests}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>	
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>Best places to meet new people?</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.meetingLocation}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>	
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content"  >
					<div class="info_box">
						<div>
							<h3>Types of people I like to hang with</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.typesPeople}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>						
		</div>
		<div  style="float:left;height:auto;">
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>Follow me on twitter</h3>
						</div>
						<div>
							<ul>
								<li><a style="color:#008ECF;" href="${user.profile.profileInfo.twitterId}">${user.profile.profileInfo.twitterId}</a></li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>About me</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.selfPerception}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>			
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>My occupation</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.reasons}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>
			
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>People describe me as</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.othersPerception}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>	
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>People I like to hang out with are</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.appreciateOthers}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content" >
					<div class="info_box">
						<div>
							<h3>My purpose in life</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.emotions}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
				<tiles:putAttribute name="content">
					<div class="info_box">
						<div>
							<h3>What makes me proud to be me?</h3>
						</div>
						<div>
							<ul>
								<li>${user.profile.profileInfo.different}</li>	
							</ul>
						</div>
					<div>
				</tiles:putAttribute>
			</tiles:insertTemplate>					
		</div>
	<div>
</c:if>