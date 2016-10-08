<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div class="standardSpacing">
	<div>
		<span style="font-size:1.8em;font-weight:bold;color:#008ecf;">
			<c:out value="${userName}" />
		</span>	
	</div>
	<div>
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute  name="content">
				<div>
					<c:forEach items="${questions}" var="question" varStatus="iter">									
						<div style="padding:10px;background-color:white;height:180px;border:1px solid #CCCCCC;margin-bottom:2px;width:700px;">
							<div class="quiz_question">
								${question.questionDescription}
							</div>					
							<div>							
								<div class="quiz_statement">
									<span style="color:#666666;">${user}</span>
									<a href="http://www.twitter.com/brucebueno" style="color:#0083cf;">@bruceBdM</a>
									<br>								
								</div>
								<div>
									<img src="http://chart.apis.google.com/chart?cht=p3&chd=t:${question.formatedAnswers}&chs=250x100&chl=${question.formatedAnswerLabels}" style="padding-left:60px;margin-top:-10px;"/>
								</div>
							</div>
						</div>					
					</c:forEach>	
				</div>
			</tiles:putAttribute>
		</tiles:insertTemplate>
	<div>
</div>