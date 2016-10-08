<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  
  <!--[if lt IE 9]>
  <script src="http://images.crowdscanner.com/html5shim.js"></script>
  <![endif]-->  
  
  <!-- These are some core styles the slideshow app requires -->
  <link rel="stylesheet" href="http://images.crowdscanner.com/libstyles.css" />
  
  <!-- These are the styles you'll add to make the slides look great -->
  <link rel="stylesheet" href="http://images.crowdscanner.com/cssstyles.css" />
  
  <title>Case Study: MindField</title>
</head>
<body>
  <header>
    <h1>SlideShow MindField Festival of Ideas Case Study</h1>
    <nav>
      <ul>
        <li><button id="prev-btn" title="Previous slide">Previous Slide</button></li>
        <li><span id="slide-number"></span>/<span id="slide-total"></span></li>
        <li><button id="next-btn" title="Next Slide">Next Slide</button></li>
      </ul>
    </nav>
  </header>
  <div id="deck">
    
    <!-- Begin slides -->
    <section>
      <hgroup>
        <h1>Hello, and welcome to the MindField Case Study SlideShow.</h1>
        <h2>First, how to use this:</h2>
      </hgroup>
      <p>Press the right arrow, down arrow, or spacebar to advance; press the left arrow or up arrow to move backward.</p>
  		<p>You can also click the left and right arrows in the control bar at the top.</p>
    		
	</section>
    <section>
      <hgroup>
        <h1>Case Study: CrowdScanner People Hunt at MindField Festival, 2011</h1>
        <h2></h2>
      </hgroup>
        <p>People Hunt is an interactive people discovery game where the goal is to meet and bump phones with as many players as possible, to win points for awesome prizes, and have serendipitous conversations with cool people. </p>
  	   	  
	<img style="width:400px;" src="http://images.crowdscanner.com/MindField1.png" alt="CrowdScanner MindField People Hunt">      

    </section>
    <section>
      <p>Cartoon personas are determined based on our scientific algorithms that use 5 questions to measure the psychological traits of conscientiousness, agreeableness, openness, extraversion and stability.<br><br>
This becomes a player's secret identity, which other players have to try to guess through conversation. </p>
    	<img style="width:450px;" src="http://images.crowdscanner.com/MindField11.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      
	<p style="font-size:12px;text-align:center;padding-top:10px;"><b>Figure 2:</b> Cartoon personas can be created according to the style of the event</p>

	</section>
  	<section>
      	<hgroup>
        	<h2>Before the event:</h2>
      	</hgroup>
      	<p>A widget can be placed on relevant webpages to invite people to find their cartoon identity.</p>
  		<img style="width:300px;" src="http://images.crowdscanner.com/MindField2.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      

	</section>
  	<section>
     	<p>People can answer on the web, or through the native app on their iPhone (available soon on Android and Blackberry).</p>
  		<img style="width:300px;" src="http://images.crowdscanner.com/MindField3.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      

    </section>    
    <section>
      	<p>The distribution of identities is displayed on an interactive graph:</p>
  		<img style="width:611px;" src="http://images.crowdscanner.com/MindField4.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      

   </section>
  	<section>
      	<hgroup>
        	<h2>During the event:</h2>
      	</hgroup>
      	<p>People talk to each other to figure out each others secret identities. If players are crafty at figuring other people out, and good at keeping secrets, they will quickly master People Hunt!</p>
  		<img style="width:565px;" src="http://images.crowdscanner.com/MindField5.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      

	</section>
  	<section>
      	<p>People talk and BUMP phones</p>
  		<img style="width:652px;" src="http://images.crowdscanner.com/MindField6.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      

   </section>
  <section>
      	<p>The leaders can be projected on the walls, real-time, showing players how close they are to winning:</p>
  		<img style="width:221px;float:left;padding-left:300px;" src="http://images.crowdscanner.com/MindField10.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      
	<p style="text-align:left;padding:20px;font-size:14px;"><b>Point System:</b><br><br>
	+1 point for BUMPing phones
	<br>
	+1 point for correctly guessing identity 
	<br>+1 point if the correctly guessed identity is "different" from yours
	<br><br>
	Most efficient hunter = most people BUMPed
	<br>Best investigator = most correctly guessed secret identities 
	<br>Best overall hunter = most points
	<br><br>----------
	<br><br>Point system can be altered to suit your event.
	<br><br><b>Examples:</b> 
	<br><br>Reward the user with a free drink/meal when they have reached 5 points
	<br>Reward the user when they BUMP with a particular person 
	<br>Reward certain personality types, combinations, or locations of BUMPs

	</p>
   </section>
   <section>
      	<p>The locations of the BUMPs can be mapped and shown real time to identify "hot spots" or more social areas:</p>
  		<img style="" src="http://images.crowdscanner.com/MindField7.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      

   </section>
   <section>
      	<hgroup>
        	<h2>After the event:</h2>
      	</hgroup>
      	<p>The number of bumps across the day can be measured to identify better times for promoting the application:</p>
  		<img style="" src="http://images.crowdscanner.com/MindField8.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      

	</section>
  	<section>
      	<p>People's connections can be mapped and printed as a reminder of the event:</p>
  		<img style="width:407px;" src="http://images.crowdscanner.com/MindField9.png" alt="CrowdScanner MindField People Hunt, events, conferences, social tool, networking">      

   </section><section>
      <h2>Press:</h2>
<br>
<p>
<a href="http://www.irishtimes.com/newspaper/finance/2011/0429/1224295670943.html">Irish Times: Networking a Game</a>
<br><br>
<a href="http://www.rte.ie/podcasts/2011/pc/pod-v-03051106m08sculturefile-pid0-368064.mp3">RTE Lyric FM: Culture File</a>
<br><br>
<a href="http://crowdscanner.com/blogpost/44">CrowdScanner Blog<a></p>
<br><br>
<h2>Credits:</h2>
<br><br>
<p>Illustrations by Daniel Spencer of <a href="htp://pegbar.ie">http://pegbar.ie</a></p> 
<br>


<h2>Contact Details: </h2>
<p>Adrian Avendano
<br>+353 867826597  |  +1 650 603 0877  
<br><a href="mailto:adrian@crowdscanner.com">adrian@crowdscanner.com</a>
<br><a href="http://crowdscanner.com">http://crowdscanner.com</a>
  
<br><br><b><a href="http://images.crowdscanner.com/CaseStudyMindField.pdf">Download this in pdf</a></b>
  </section>
    <!-- /End slides -->
    
  </div>
  <!-- /deck -->
  <script src="http://images.crowdscanner.com/jquery-1.5.2.min.js"></script>
  <script src="http://images.crowdscanner.com/htmlSlides.js"></script>
  <script>
    //Do our business when the DOM is ready for us
    $(function() {
      
      //You can trigger Javascript based on the slide number like this:
      $('html').bind('newSlide', function(e, id) { 
        switch(id) {
          case 2:
            console.log('This is the second slide.');;
            break;
          case 3:
            console.log('Hello, third slide.');
            break;
        }
      });

      //One little option: hideToolbar (boolean; default = false)
      htmlSlides.init({ hideToolbar: true });
      
    });
  </script>
  </body>
</html>
