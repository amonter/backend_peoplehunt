 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div class="standardSpacing">
	<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
		<tiles:putAttribute name="content" >
			<div style="margin:10px;">
				<div  background="images/middle1.jpg" width="750px" height="20px">
					<p class = "create_user" >Presenter Case Update</p>
				</div>	
				<div class="conf">
					<p>Thanks, Your information has been updated if you would like to see the list of presenters go <a style="color:blue;" href="">here</a></p>				
				</div>
			</div>
		</tiles:putAttribute>
	</tiles:insertTemplate>  			  					  		
</div>	