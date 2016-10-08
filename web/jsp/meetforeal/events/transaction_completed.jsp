<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>   
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="standardSpacing" style="padding:5px 10px 0px 0px;float:left;">
    <tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">   
        <tiles:putAttribute name="content">
            <div class="create_user_div">
               <div  background="images/middle1.jpg" width="750px" height="20px">               
                     <p class = "create_user" >Transaction Completed!</p>
   				</div>   
               <div class="conf">
                    <p>Cool! Looking forward to seeing you at this event! </p>
                    <p>Thank you for your payment of <b><c:out value="${amount}" /> euro</b>.</p>
                    <p>Your transaction has been <b>completed</b>, and a receipt for your purchase has been emailed to you. </p>
                    <p> Please contact us for any queries <a href="javascript:sendthefeedback();"><b>here</b></a> 
                    or send an email to <b>adrian@meetforeal.com</b> </p>
                </div>
          </div>
        </tiles:putAttribute>
    </tiles:insertTemplate>
</div>

<script type="text/javascript">

function sendthefeedback () {
	
	sendFeedback();
}
  
</script>	