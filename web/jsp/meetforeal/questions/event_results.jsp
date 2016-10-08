<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="standardSpacing">
    <span class="main_title">
        Events that might suit you:
    </span>
</div>
<div class="standardSpacing">
      <div style="padding:6px 6px 6px 0px;">
	        <script type="text/javascript"><!--
	        google_ad_client = "pub-7717112231624442";
	        /* 468x60, created 1/10/09 */
	        google_ad_slot = "7147468951";
	        google_ad_width = 468;
	        google_ad_height = 60;
	        //-->
	       </script>
	       <script type="text/javascript"
	       src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	       </script>
      </div>
      <div>
          <tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">   
                <tiles:putAttribute name="content" value="/jsp/questions/event_results_table.jsp" />
          </tiles:insertTemplate>   
      </div>
</div>
<div class="standardSpacing">
    <span class="main_title">
       What other people have answered:
    </span>
</div>
<div class="standardSpacing" >
	<div style="float:left;">
		<div class="graph">
			<tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">   
		         <tiles:putAttribute name="content">
		            <div class="results">
		                <div  background="images/middle1.jpg" width="750px" height="20px">
		                    <p class = "create_user" >Question 1</p>
		                </div>
		                <div class="question_text">
		                    <p>
		                        What type of groups do you like?           
		                    </p>
		                </div>
		                <div style="float:left;margin:10px 0px 10px 0px;">   
		                    <img src="http://chart.apis.google.com/chart?cht=bvg&chd=t:${questionMap['question_1'][0].answerCountPositive},${questionMap['question_1'][1].answerCountPositive},${questionMap['question_1'][2].answerCountPositive}&chs=450x200&chco=FF0000|00FF00|FFD364&chxt=y,y&chxr=0,0,${questionMap['question_1'][0].maxCount}&chdl=${questionMap['question_1'][0].name}|${questionMap['question_1'][1].name}|${questionMap['question_1'][2].name}&chbh=45,10,20&chf=bg,s,EFEFEF&chds=0,${questionMap['question_1'][0].maxCount}&chxl=1:| |Responses| ">
		                </div>
		            </div>
		        </tiles:putAttribute>
		    </tiles:insertTemplate>
		</div>
		<div >
		    <tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">   
		        <tiles:putAttribute name="content">
		            <div class="results">
		                <div  background="images/middle1.jpg" width="750px" height="20px">
		                    <p class = "create_user" >Question 2</p>
		                </div>
		                <div class="question_text">
		                    <p>
		                        Do you exercise on the regular basis?               
		                    </p>
		                </div>
		                <div style="float:left;margin:10px 0px 10px 0px;">   
		                    <img src="http://chart.apis.google.com/chart?cht=bvg&chd=t:${questionMap['question_2'][0].answerCountPositive},${questionMap['question_2'][0].answerCountNegative}&chf=bg,s,EFEFEF&chs=450x200&chco=FF0000|00FF00&chxt=y,y&chxr=0,0,${questionMap['question_2'][0].maxCount}&chdl=Yes|No&chbh=45,10,20&chds=0,${questionMap['question_2'][0].maxCount}&chxl=1:| |Responses| ">
		                </div>
		            </div>               
		        </tiles:putAttribute>
		    </tiles:insertTemplate>
		</div>
		<div>
		    <tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">   
		        <tiles:putAttribute name="content">
		            <div class="results">
		                <div  background="images/middle1.jpg" width="750px" height="20px">
		                    <p class = "create_user" >Question 3</p>
		                </div>
		                <div class="question_text">
		                    <p>
		                        Do you enjoy exchanging ideas about the world?               
		                    </p>
		                </div>
		                <div style="float:left;margin:10px 0px 10px 0px;">   
		                    <img src="http://chart.apis.google.com/chart?cht=bvg&chd=t:${questionMap['question_3'][0].answerCountPositive},${questionMap['question_3'][0].answerCountNegative}&chf=bg,s,EFEFEF&chs=450x200&chco=FF0000|00FF00&chxt=y,y&chxr=0,0,${questionMap['question_3'][0].maxCount}&chdl=Yes|No&chbh=45,10,20&chds=0,${questionMap['question_3'][0].maxCount}&chxl=1:| |Responses| ">
		                </div>
		            </div>               
		        </tiles:putAttribute>
		    </tiles:insertTemplate>
		</div>
		<div>
		    <tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">   
		        <tiles:putAttribute name="content">
		            <div class="results">
		                <div  background="images/middle1.jpg" width="750px" height="20px">
		                    <p class = "create_user" >Question 4</p>
		                </div>
		                <div class="question_text">
		                    <p>
		                        What's your comfort level around new people?           
		                    </p>
		                </div>
		                <div style="float:left;margin:10px 0px 10px 0px;">   
		                    <img src="http://chart.apis.google.com/chart?cht=bvg&chd=t:${questionMap['question_4'][0].answerCountPositive},${questionMap['question_4'][1].answerCountPositive},${questionMap['question_4'][2].answerCountPositive}&chf=bg,s,EFEFEF&chs=450x200&chco=FF0000|00FF00|FFD364&chxt=y,y&chxr=0,0,${questionMap['question_4'][0].maxCount}&chdl=${questionMap['question_4'][0].name}|${questionMap['question_4'][1].name}|${questionMap['question_4'][2].name}&chbh=45,10,20&chds=0,${questionMap['question_4'][0].maxCount}&chxl=1:| |Responses| ">
		                </div>
		            </div>                   
		        </tiles:putAttribute>
		    </tiles:insertTemplate>
		</div>
		<div>
		   <tiles:insertTemplate template="/jsp/parts/rounded_border.jsp">   
		       <tiles:putAttribute name="content">
		           <div class="results">
		               <div  background="images/middle1.jpg" width="750px" height="20px">
		                   <p class = "create_user" >Question 5</p>
		               </div>
		               <div class="question_text">
		                   <p>
		                       Do you like to be outside your comfort zone?           
		                   </p>
		               </div>
		               <div style="float:left;margin:10px 0px 10px 0px;">   
		                   <img src="http://chart.apis.google.com/chart?cht=bvg&chd=t:${questionMap['question_5'][0].answerCountPositive},${questionMap['question_5'][0].answerCountNegative}&chs=450x200&chco=FF0000|00FF00&chxt=y,y&chf=bg,s,EFEFEF&chxr=0,0,${questionMap['question_5'][0].maxCount}&chdl=Yes|No&chbh=45,10,20&chds=0,${questionMap['question_5'][0].maxCount}&chxl=1:| |Responses| ">
		               </div>
		           </div>               
		    	</tiles:putAttribute>
		   </tiles:insertTemplate>
		</div>
	</div>     
    <div style="float:left;margin:10px 0px 0px 10px">
    	<div>
           <script type="text/javascript"><!--
            google_ad_client = "pub-7717112231624442";
            /* 336x280, Bottom of page */
            google_ad_slot = "2123959065";
            google_ad_width = 336;
            google_ad_height = 280;
            //-->
            </script>
            <script type="text/javascript"
             src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
             </script>   
        </div>
        <div>
              <script type="text/javascript"><!--
              google_ad_client = "pub-7717112231624442";
              /* 300x250, created 1/20/09 */
              google_ad_slot = "2939597014";
              google_ad_width = 300;
              google_ad_height = 250;
               //-->
              </script>
              <script type="text/javascript"
             src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
              </script>
         </div>
	</div>
</div>		