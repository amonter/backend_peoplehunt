<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="question" align="left">
		Question ${questionNo} (max characters for questions = 170)
		<br><br> 
		<input type="hidden" name="id" value="${question.id}"/>
		<input type="text" size="25" name="description" value="${question.questionDescription}"/> 
		<span class="status"></span>
</div>
