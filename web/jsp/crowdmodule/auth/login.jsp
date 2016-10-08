<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 


	<div class="placing-1 events">

		<c:url var="loginUrl" value="crowdmodule/auth/login"/>
		<form:form name="register" commandName="loginCmd" action="${loginUrl}" method="POST" > 
		<form:errors/>
			<div class="form"> 
				Username:
			</div>
			<div class = "form-boxes-padding">
				 <form:input path="userName" class="form-boxes" size="25"/> <form:errors path="userName"/>
			</div> 

			<div class="form"> 
				Password: 
			</div>
			<div class = "form-boxes-padding">
				<form:password path="password" class="form-boxes"size="25"/> <form:errors path="password"/> 
			</div> 
			<div class="form-extra-details">
			<a><input type="submit" class="form-boxes-buttons" value="Submit"/></a> 
 </div>
		</form:form> 
 
</div>
