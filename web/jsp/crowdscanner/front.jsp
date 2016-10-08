<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<script type="text/javascript" src="http://images.crowdscanner.com/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="http://images.crowdscanner.com/jquery.fancybox-1.3.4.pack.js"></script>



<div class="container banner">
  <section id="banner" class="placing-1">
        <div class="banner-image">

	<h2 id="home-strapline" style="z-index:2;">Turning networking into people discovery:<br> a fun, serendipitous, interactive experience</h2>
                <div class="green-button large-signup-button" ><a class="tour-video" href="mailto:ellen@crowdscanner.com">Get in Touch</a></div>
        </div>
  </section>

</div>
<section id="content" class="placing-1">
  <section id="screens" class="placing-2" style="padding-bottom:0px;height:170px;">
    <ul id="screens-nav">

      <li id="screens-nav-1""><a href="http://images.crowdscanner.com/graph_trends.png" rel="images-gallery" title="Let attendees sign up before the event to see what are common themes of interest"><span class="screen-text">Create Anticipation</span><img title="Let attendees sign up before the event to see what are common themes of interest" alt="Let attendees sign up before the event to see what are common themes of interest" src="http://images.crowdscanner.com/build-1.png" /></a><div class="screen-text-content"></div></li>
      <li id="screens-nav-2"><a href="http://images.crowdscanner.com/Screen-Shot-4.png" rel="images-gallery" title="Create a social environment at the event"><span class="screen-text">Create Social Hotspots</span><img title="Create a social environment at the event" alt="Create a social environment at the event" src="http://images.crowdscanner.com/nav-screen-4.png"/></a><div class="screen-text-content"></div></li>
      <li id="screens-nav-3"><a href="http://images.crowdscanner.com/gamingBig-1.png" rel="images-gallery" title="Reward connections with fun gaming mechanics"><span class="screen-text">Reward Connections</span><img title="Reward Connections with Gaming Mechanics" alt="Rewards Connections with Gaming Mechanics" src="http://images.crowdscanner.com/game-3.png"/></a><div class="screen-text-content"></div></li>

      <li id="screens-nav-4"><a href="http://images.crowdscanner.com/how_we_all_connect.png" rel="images-gallery" title="Give attendees visuals of who they connected with!"><span class="screen-text">Visuals to Takeaway</span><img title="Give people visuals of who they connected with" alt="Visuals to takeway" src="http://images.crowdscanner.com/visuals-4.png" /></a><div class="screen-text-content"></div></li>

<!--<li id="screens-nav-1""><a href="http://images.crowdscanner.com/Screen-Shot-1.png" rel="images-gallery" title="Customise 5 multiple choice questions: Tailor them for your event"><span class="screen-text">Set up in 20 minutes</span><img title="Customise questions for your event" alt="Customise questions for your event" src="http://images.crowdscanner.com/nav-screen-1.png" /></a></li>
      <li id="screens-nav-2"><a href="http://images.crowdscanner.com/Screen-Shot-2.png" rel="images-gallery" title="Share with your event attendees to get them involved"><span class="screen-text">Share with Attendees</span><img title="Share with Attendees" alt="Share with Attendees" src="http://images.crowdscanner.com/nav-screen-2.png"/></a></li>
      <li id="screens-nav-3"><a href="http://images.crowdscanner.com/Screen-Shot-3.png" rel="images-gallery" title="Display visualisations of results on screens at your event"><span class="screen-text">Display at event</span><img title="Display at event" alt="Display at event" src="http://images.crowdscanner.com/nav-screen-3.png"/></a></li>

      <li id="screens-nav-4"><a href="http://images.crowdscanner.com/Screen-Shot-4.png" rel="images-gallery" title="Let people talk to the right people!"><span class="screen-text">Get people talking</span><img title="Get people talking" alt="Get people talking" src="http://images.crowdscanner.com/nav-screen-4.png" /></a></li>-->
    </ul>
  </section>
  <div>
  <section class="placing-2" style="background:#FFFFFF;padding: 5px 0 5px; margin:0 auto; height:220px;" >
        
	<h2 style="padding-left:20px;font-size:30px;">Products</h2>
	<div style="float:left;padding:0px 0 0 20px;">
           <a href="howitworks"> <img src="http://images.crowdscanner.com/Icon-512x512.png" style="width:120px;"></a>
        </div>
        <div style="float: left; width: 260px; padding-top: 5px; padding-left: 30px;">
            <p><b>CrowdScanner Social Objects</b> show attendee information, real-time, to let people discover who they need to be talking to, and what to talk to them about. </p>
        <p style="float:right;padding-right:20px;"><a href="/howitworks">Learn more</a>
	</p>
	</div>
        <div style="float:left;background:#FFFFFF;padding:0px 0 0 20px;">
            <a href="/peoplehunt"><img src="http://images.crowdscanner.com/512x512PeopleHunt.png" style="width:120px;"></a>
        </div>
        <div style="float: left; width: 260px; padding-top: 5px; padding-left: 30px;">
            <p><b>People Hunt</b> is a fun, interactive networking game, motivating people to hunt for face to face conversations with interesting people around them. </p>
        <p style="float:right;padding-right:20px;"><a href="/peoplehunt">Learn more</a>
	</p>
	</div>  

   </section>
   </div>
</section>

<script type="text/javascript">
$(document).ready(function() {

        $("a[rel=images-gallery]").fancybox({

                'hideOnContentClick': true,
                'transitionIn'  :       'elastic',
                'transitionOut' :       'elastic',
                'speedIn'               :       600,
                'speedOut'              :       200,
                'overlayShow'   :       false,
		'width':'inline',
		'height':'inline'
});

function formatTitle(title, currentArray, currentIndex, currentOpts) {
    return '<div id="tip7-title"><span><a href="javascript:;" onclick="$.fancybox.close();"><img src="/data/closelabel.gif" /></a></span>' + (title && title.length ? '<b>' + title + '</b>' : '' ) + 'Image ' + (currentIndex + 1) + ' of ' + currentArray.length + '</div>';
}

$(".tip7").fancybox({
        'showCloseButton'       : false,
        'titlePosition'                 : 'inside',
        'titleFormat'           : formatTitle
});

});
</script>
