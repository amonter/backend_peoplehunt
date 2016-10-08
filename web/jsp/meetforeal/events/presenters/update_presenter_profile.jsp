<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ page import="java.util.*" %>


<div style="margin:20px 5px 5px 20px;"> 
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content"> 
			<div class="create_user_div">
				<div  background="images/middle1.jpg" width="750px" height="20px">
					<p class = "create_user" >Show My Stuff</p>
				</div>	
				<div style="margin:10px 0px 10px 0px">	
					<form:form commandName="profile" action="updateprofilepresenter.do"  method="POST">
						<form:hidden path="id"/>																	
						<spring:hasBindErrors name="user">
							<div id="OuterErrorLayer">
								<div id="ErrorLayer">
									<form:errors path="*" />
								</div>
							</div>
						</spring:hasBindErrors>
						<table width="400">
							<tr>
								<td class="register_form">
									Title of what you are sharing:
								 </td>
								<td class="register_form_boxes" >
									<form:input	path="presentationTitle" id="input_type" cssClass="createUserField" /> 
								</td>
							</tr>
							<tr>
								<td class="register_form">
									Tell us more about it:
								 </td>
								<td class="register_form_boxes" >
									<form:textarea cols="50" rows="10"	path="presentationContent" id="input_type" cssClass="createUserField" /> 
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input style="margin:5px 0px 0px 220px;" class="login_button" type="submit"  value="Send" />
								</td>
							</tr>							
						</table>
					</form:form>
				</div>
			</div>
		</tiles:putAttribute>
	</tiles:insertTemplate>
</div>				