<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>    
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<div class="legaldoc">
	
	<h1 style="color:#008ECF;font-size:31px;font-weight:normal;">What are meetforeal events?</h1>
	<p style="font-size:16px;margin:">		
		Meetforeal events are interactive, low cost, inclusive events where you can meet like-minded people and have conversations based 
		around topics that you are most passionate about, regardless of your age, relationship status or otherwise, all in real life.
	</p>	
	<div style="height:220px;margin:15px 0px 0px 0px;width:700px;">
		<div style="float:left;">
			<img src="images/expert.jpg" />
		</div>
		<div class="expert_div" style="font-weight:normal;font-size:20px;">
			Events typically include expert talks and an interactive discussion where you can participate.
		</div>
	</div>		
	<h1 style="color:#008ECF;font-size:31px;font-weight:normal;">Why should I come to a meetforeal event?</h1>
	<p style="font-size:16px;margin:0px 0px 40px 0px;">		
		You should come if you are interested in the topic, want to meet like-minded people, share your ideas, and learn something new. 
	</p>
	<h1 style="color:#008ECF;font-size:31px;font-weight:normal;">Who are the events for?</h1>
	<p style="font-size:16px;margin:0px;">		
		Meetforeal events are for you if:
	</p>
	<div style="margin:15px 0px 40px 0px;">
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute name="content">
				<ul style="font-size:20px;padding:40px;color:#333333;font-weight:bold;list-style-type:disc;">
					<li >You are an academic, an entrepreneur, a geek, an artist, a filmmaker, a scientist, a writer, a teacher, a doctor, a lawyer, an accountant, an engineer, 
					all or none of the above and you're interested in the topic of the event</li>
					<li style="padding-top:17px;">You just want to experience something different, with some interesting conversations with interesting people</li>		
				</ul>		
			</tiles:putAttribute>
		</tiles:insertTemplate>	
	</div>
	
	<h1 style="color:#008ECF;font-size:31px;font-weight:normal;">Do I need to be an expert to come?</h1>
	<p style="font-size:16px;margin:0px 0px 40px 0px;">
		It helps have an interest in the topic, but even if you know nothing about the topic, 
		but are enthusiastic to learn something new, and meet new people, then you are welcome. 
	</p>
	<h1 style="color:#008ECF;font-size:31px;font-weight:normal;">How can I sign up to come?</h1>
	<p style="font-size:16px;margin:0px 0px 40px 0px;">
		Become a user by signing up. Its free, and we will not share your details with anyone. 
		Once you are logged in, choose the event you wish to go to and click on "Add to my Events". 
		This adds the event to your list, and you can click on your event basket (top right) to proceed through to payment. Alternatively, you can confirm your place by sending an email to admin@meetforeal.com. 
	</p>
	<h1 style="color:#008ECF;font-size:31px;font-weight:normal;">Can I just turn up on the night?</h1>
	<p style="font-size:16px;margin:0px 0px 40px 0px;">
		It is recommended that you sign up to secure your place so let us know that you're coming by clicking "Add to My Events". Then your face appears in the event window and people can see that you are coming. 
	</p>
<h1 style="color:#008ECF;font-size:31px;font-weight:normal;">Why do I need to upload a photo when I create an account?</h1>
<p style="font-size:16px;margin:0px 0px 40px 0px;">
We felt there were too many websites with blank empty profiles, and we didn't want that happening on meetforeal.com. It makes meetforeal.com better, and there's no pressure to upload a picture of you personally, anything that represents an aspect of your personality is perfect.<br><br> 
We are currently working on functionality that allows you to choose from a variety of profile pics if you don't have one lying around, but we are a small IT team with a LONG to do list of improvements so please be patient with us.</p>
	<h1 style="color:#008ECF;font-size:31px;font-weight:normal;">Ways to enjoy the event</h1>
	<div style="margin-top:15px;">
		<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">	
			<tiles:putAttribute name="content">
				<ul style="font-size:20px;padding:40px;color:#333333;font-weight:bold;list-style-type:disc;">
					<li ><span style="color:#008ECF;"><b>Relax and have fun:<b></span> You've probably had a long day and you have a lot to do tomorrow - just enjoy yourself. 
					Stay as long as you can or as long as you want to. Come alone or bring your friends, whatever makes you most comfortable</li>
					<li style="padding-top:17px;"><span style="color:#008ECF;"><b>Share:</b></span> The event is all about sharing your ideas and interacting with a diverse crowd of people. Ask questions, 
					share your knowledge and learn a little something to take home with you</li>
					<li style="padding-top:17px;"><span style="color:#008ECF;"><b>Don't be shy:</b></span> It's easy to spot a familiar face and rush to their side to be rescued. 
					You probably have a super group of friends, and meetforeal events are not about replacing them. 
					It's just about having conversations with different people for a change and we create an environment where it's easier to do it, so stepping outside of your comfort zone is.... well.... as comfortable as possible.
					</li>						
				</ul>
			</tiles:putAttribute>
		</tiles:insertTemplate>
	</div>	
<p>More information <a href="http://www.meetforeal.com/viewpageaboutus.do" style="font-weight:bold;color:blue;">here</a>
</div>

<script type="text/javascript">

function sendthefeedback () {
	
	sendFeedback();
}
  

</script>


   



    

   

   
