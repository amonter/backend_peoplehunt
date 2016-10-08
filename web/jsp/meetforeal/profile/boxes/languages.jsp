<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

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