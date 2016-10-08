<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

Create Visualisation
<br><br>
Here is where we will be able to create new types of visualisations in the database so that they can be chosen by the users in the flow. 
<br><br>
<br><br>
<c:url value="/crowdmodule/visual/" var="visual_url"/>
<form:form commandName="visual" name="visual_create" action="${visual_url}" method="POST">
<form:errors path="*"/>
<div align="left">
Name:
<form:input size="25" path="name"/>
<form:errors path="name"/>
</div>
<div align="left">
Description:
<form:input size="25" path="description"/>
</div>
<div align="left">
Cost:
<form:input size="25" path="cost" />
<form:errors path="cost"/>
</div>
<div align="left">
Give a link to a photo of the visualisation:
<br><br>
<form:input path="image" size="25"/>
<form:errors path="image"/>
</div>
<input type="submit" value="Submit"/>
</form:form>
