 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="standardSpacing">
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content">
			<div style="margin:10px">
				<div  background="images/middle1.jpg" width="750px" height="20px">
					<p class = "create_user" >Trouble Entering Your Account?</p>
				</div>	
				<div style="margin:8px;">
					<span>Please enter your email address below. We will send you an email with your account details.</span>					
					<c:if test="${!empty message}">
						<div id="OuterErrorLayer">
							<div id="ErrorLayer">
								${message}
							</div>
						</div>
					</c:if>	
					<form action="forgotpassword.do" method="post">
						<table width="400" style="margin-top:15px;">
							<tr>
								<td class="register_form">
									Your Email Address
								 </td>
								<td class="register_form_boxes" >
									<input type="text"  id="input_type" class="createUserField" name="email"/> 
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right"> 
									<input  type="image" src="images/send.jpg" value="Send" />
								</td>
							</tr>
						</table>
					</form>	
				</div>
			<div>
		</tiles:putAttribute>
	</tiles:insertTemplate>	
</div>	