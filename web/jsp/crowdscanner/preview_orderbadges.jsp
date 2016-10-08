<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="placing-1 events">
<div id="leftcol" style="float:left;width:590px;padding:0px 25px 0px 25px;">
        <h1 id="titleOfCreate" style="margin-top:10px;">ALRIGHTY!</h1>
        <h2 style="padding-top:5px;text-align: center">You're a few short steps from making your event more awesome!</h2>

        <div style="padding:25px;width:590px;float:left;">
                <span style="font-style:normal; font-size:16px;text-align:center;">Enter the number of people attending your event:</span><br><br>
                <form name="register" action="/crowdmodule/auth/placebatchorder" method="GET" >
                <div style="border-radius:12px 12px 12px 12px; width:505px;height:52px;background-color:#DEFBD2;padding:20px;">
                        <input type="text" style="float:left;width:170px;height:45px;font-size:40px;"  class = "form-boxes" name="totalbadges" value=""/>
                        <div style="line-height:15px;font-weight:bold;font-size:13px; margin:12px 0px 0px 220px;">
                                <i>Number of button badges you need for your event</i>

                        </div>
                </div>
                <div style="margin-top:40px;height:70px;">
                        <span id="total_amount" style="float:left;width:100px;margin:-15px 0px 0px 20px; font-size:40px;"></span>
                <span id="total_text" style="font-weight:bold;font-size:13px; margin:20px 0px 20px 120px;"></span>
                </div>
                <div style="margin:0px auto;display:block; width:220px;">
                        <a><input type="submit" class="form-boxes-buttons"style="width:220px;height:50px;" value="Continue to Next Step"/></a>
                </div>
                </form>
        </div>
</div>
<div id="rightcol" style="font-size:13px;color:#666666;padding:10px 20px 20px 20px;width:260px;float:right;">
        <span><b>Instructions</b></span><br><br>
        <span>1. Tell your attendees to download People Hunt FREE for iPhone and Android smartphones. <br><br>2. Give them a cool button badge to identify them as players. <br><br></span>
        <img src = "http://images.crowdscanner.com/Badge.png" style="width:200px;padding:10px 0px 0px 20px;">
<br><br><span><b>Why do I need button badges?</b></span><br><br>
        <span>People Hunt is available to download, free, for iPhone and Android smartphones. To pay for the service, you just need to tell us how many people are playing, and we will provide you with button badges for each attendee. That way, players can easily identify one another.<span>
        <span style="font-size:14px;color:#333333;text-align:center;"><br><br>Want a customized button badge for your event? <a href="mailto:ellen@crowdscanner.com">Contact us</a></span>
</div>
</div>


<script type="text/javascript">
         $(document).ready(function() {
                 $("input[name=totalbadges]").live('keyup',function(event) {
                                var theVal = $(this).val();
                                if (!isNaN(theVal)){
                                        var totalCost = 3 * theVal;
                                        $("#total_amount").text('$'+totalCost);
                                        $("#total_text").text('Total cost including VAT and shipping costs');
                                }
                });

        });
</script>


