<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
   <script type="text/javascript" src="http://images.crowdscanner.com/jquery-1.4.2.min.js"></script>
   <script src="http://images.crowdscanner.com/processing-1.1.0.min.js"></script>    
   <title>Crowd Visualisation</title>

   <style type="text/css">
	#geronimo {
		padding:0px 10px 0px 10px;
		font-size:14px;
		text-align:center;
		font-family:Helvetica;
		background-color:white;
	}
	.colours {
		display:inline-block;
		padding:10px 10px 10px 10px;
		width:100px;
		max-width:100px;
	}
	.is_selected {
		display:inline-block;
		padding:10px 10px 10px 10px;
		width:200px;
		max-width:200px;
		font-size:20px;
		background:#333333;
	}

   </style>
	
</head>
<body style="margin:0px;">
<div style="float:left;margin-left:50px;padding-top:20px;">
<c:choose>
	<c:when test="${bundle_id == 84}">
	<span style="font-weight:bold;">http://crowdscanner.com/suffolktweet</span>
	</c:when>
	<c:when test="${bundle_id== 85}">
	<span style="font-weight:bold;">http://crowdscanner.com/galwaytweet</span>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
</div>
<div style="margin:10px 0px 0px 150px;color:#333333;background-color:white;font-size:35px;text-align:left;float:left;font-family:helvetica;">${name}</div>

<div style=" padding:55px 0px 20px 0px;text-align:center;background-color:white;">
                <form action="http://crowdscanner.com/crowdmodule/public/${bundle_id}/questions"/><button type="submit" class="form-boxes-buttons"style="background-color:#FF0066;padding:10px 10px 10px 10px; height:50px;color:#FFFFFF;font-size:20px;">Answer the questions!</button></form>
</div>
<div id="geronimo"></div>
<script type="application/processing" target="theCanvas">
${preload_images}

numOfAnswers = theData.allanswers.length;
float[] x = new float[numOfAnswers]; 
float[] y = new float[numOfAnswers];
float[] d = new float[theData.totalusers];
float[] refX= new float[numOfAnswers];
float[] refY = new float[numOfAnswers];
float[] widthGrid = new float[];
float[] heightGrid = new float[];
HashMap losImage = new HashMap(120);

int maxSize = 60;
int minSize = 60;
float [][] e = new float [numOfAnswers][106];
float[] textLength = new float[5];
float[] xpos = new float[5];
float spring = 0.01;
int numOfUsers = theData.totalusers; 

HashMap colours = new HashMap();
HashMap questionDescriptions = new HashMap();
// Selected mode switch
int sel = 0;

// Set drag switch to false
boolean dragging=false;

// If use drags mouse...
void mouseDragged(){
  
  // Set drag switch to true
  dragging=true;
}
  
// If user releases mouse...
void mouseReleased(){
  
  // ..user is no-longer dragging
  dragging=false;
}
	
void setup(){
	
	size(screen.width,2000);
	smooth();
	frameRate(60);
	strokeWeight(1);
/*	for (int i = 0; i<numOfAnswers; i++){
                float radius = 2.0;
                for (int p = 0; p<numOfAnswers; p ++){
                        float angle = radians(p*100);
                        
			int numOfMatches = theData.allanswers[p].matchProfiles.length;
                        //one layer
                        if(numOfMatches<9){
                                float f = 460 + (cos(angle)*radius)*160;
                                float g = 460 + (sin(angle)*radius)*160;

                        }

                        if(numOfMatches>=9 && numOfMatches<23){
                                float f = 460 + (cos(angle)*radius)*260;
                                float g = 460 + (sin(angle)*radius)*260;
                        }
                        //three layers
			 if(numOfMatches >=23 && numOfMatches<43){
                                float f = 460 + (cos(angle)*radius)*360;
                                float g = 460 + (sin(angle)*radius)*360;
			}
			//four layers
			 if(numOfMatches>=43 && numOfMatches<69){
                               float f = 460 + (cos(angle)*radius)*460;
                                float g = 460 + (sin(angle)*radius)*460;
			}
			//five layers
			 if(numOfMatches >=69 && numOfMatches<101){
                                float f = 460 + (cos(angle)*radius)*560;
                                float g = 460 + (sin(angle)*radius)*560;
			}
			//ellipse (f, g, 6, 6);
                        radius = radius+0.2;
                        x [p]= f;
                        y [p]= g;
                }
        }
*/
	//positions
	
	int i = 0;
	for (int i = 0;i<numOfAnswers; i++){
		int u = 0;
		int domain = 0;
		int yCount = 0;
		for (int p = 0; p<numOfAnswers; p++){
			int numOfMatches = theData.allanswers[p].matchProfiles.length;
			//one layer
			if(numOfMatches<9){
				float referenceX = 140.0;
				float referenceY = 150.0;
				
			}
			//two layers
			if(numOfMatches>=9 && numOfMatches<23) {
                                float referenceX = 220.0;
			        float referenceY = 220.0;
                        	
			}
			//three layers
			 if(numOfMatches >=23 && numOfMatches<43){
                                float referenceX = 310.0;
				float referenceY = 310.0;
			}
			//four layers
			 if(numOfMatches>=43 && numOfMatches<69){
                                float referenceX = 390.0;
				float referenceY = 390.0;
			}
			//five layers
			 if(numOfMatches >=69 && numOfMatches<101){
                                float referenceX = 480.0;
 				float referenceY = 480.0;

			}
			refX[p]= int(referenceX);
                	refY[p]= int(referenceY);
			if(p==0){
				x[p]=refX[p];
			}else{
				x[p]=x[p-1] +refX[p] + refX[p-1];
						
			}
			if(x[p]>screen.width-130){
				x[p]=refX[p];
				yCount = 1;
				domain =domain+450;
			}
			if(yCount==0){
				y[p]= refY[p];
			}else{
				y[p]=refY[p] + domain;
						
			}
		}	
	}
	
	for(int f = 0; f<theData.allanswers.length; f++){
		colours.put(theData.allanswers[f].questionPhoneId,f);
	 	questionDescriptions.put(theData.allanswers[f].questionPhoneId,theData.allanswers[f].questionDescription);	
	}
	Iterator i = colours.entrySet().iterator();
	int counter = 0;
	while(i.hasNext()){
		Map.Entry me = (Map.Entry)i.next();
		me.setValue(counter);
		counter++;
	}
	Iterator p = questionDescriptions.entrySet().iterator();
	while(p.hasNext()){
		Map.Entry me = (Map.Entry)p.next();
	}


	//initialise the circles - as many as the data length, and their sizes
	for (int j = 0; j<numOfAnswers; j++){
		e[j][0] = x[j]; //X
		e[j][1] = y[j]; //Y
		e[j][2] = ((theData.allanswers[j].matchProfiles.length*50)/(2*PI)); //radius
		//e[j][3] = random(-0.2, 0.2); //x speed
		//e[j][4] = random(-0.2, 0.2); //y speed
		int d=5;
		//depending on the number of people, it goes into a loop, per answer, and contructs the coordinates of the circles around the answer
		for(int r=0; r<theData.allanswers[j].matchProfiles.length; r++){
			if(d<13){
				PImage anImage = loadImage(theData.allanswers[j].matchProfiles[r].url);
                		losImage.put(theData.allanswers[j].matchProfiles[r].email, anImage);
				//the angle is the whole circle cut into the amount of slices that there are users, times the user we are on now, to add onwards	
 				float rawAngle = ((360*r)/8);
				//println(rawAngle);
				float angle = radians(rawAngle);
                	        //the x coordinate is calculated cos we know the hypotenuse which is ej2 + radium of face of 35 plue 5 for space (and we have the angle)
				float ycord = (cos(angle) * (90));
                	        float xcord = (sin(angle) * (90));
				//so we fill these into an x and y coordinate that is used later on...
	                        e[j][r+d]= e[j][0] +xcord;
                       		e[j][r+d+1]= e[j][1]+ ycord;
				e[j][2] = ((12*50)/(2*PI));
			}
			if(d>=13 && d<27){
				PImage anImage = loadImage(theData.allanswers[j].matchProfiles[r].url);
	                        losImage.put(theData.allanswers[j].matchProfiles[r].email, anImage);
       		                 //the angle is the whole circle cut into the amount of slices that there are users, times the user we are on now, to add onwards
                	        float rawAngle = ((360*r)/14);
                        	//println(rawAngle);
	                        float angle = radians(rawAngle);
        	                //the x coordinate is calculated cos we know the hypotenuse which is ej2 + radium of face of 35 plue 5 for space (and we have the angle)
                	        float ycord = (cos(angle) * (160));
                        	float xcord = (sin(angle) * (160));
	                        //so we fill these into an x and y coordinate that is used later on...
        	                e[j][r+d]= e[j][0] +xcord;
                	        e[j][r+d+1]= e[j][1]+ ycord;
				e[j][2] = ((17*50)/(2*PI));
			}
			if(d>=27 && d<47){
                                PImage anImage = loadImage(theData.allanswers[j].matchProfiles[r].url);
                                losImage.put(theData.allanswers[j].matchProfiles[r].email, anImage);
                                 //the angle is the whole circle cut into the amount of slices that there are users, times the user we are on now, to add onwards
                                float rawAngle = ((360*r)/20);
                                //println(rawAngle);
                                float angle = radians(rawAngle);
                                //the x coordinate is calculated cos we know the hypotenuse which is ej2 + radium of face of 35 plue 5 for space (and we have the angle)
                                float ycord = (cos(angle) * (235));
                                float xcord = (sin(angle) * (235));
                                //so we fill these into an x and y coordinate that is used later on...
                                e[j][r+d]= e[j][0] +xcord;
                                e[j][r+d+1]= e[j][1]+ ycord;
				e[j][2] = ((24*50)/(2*PI));
                        }
			if(d>=47 && d<73){
                                PImage anImage = loadImage(theData.allanswers[j].matchProfiles[r].url);
                                losImage.put(theData.allanswers[j].matchProfiles[r].email, anImage);
                                 //the angle is the whole circle cut into the amount of slices that there are users, times the user we are on now, to add onwards
                                float rawAngle = ((360*r)/26);
                                //println(rawAngle);
                                float angle = radians(rawAngle);
                                //the x coordinate is calculated cos we know the hypotenuse which is ej2 + radium of face of 35 plue 5 for space (and we have the angle)
                                float ycord = (cos(angle) * (310));
                                float xcord = (sin(angle) * (310));
                                //so we fill these into an x and y coordinate that is used later on...
                                e[j][r+d]= e[j][0] +xcord;
                                e[j][r+d+1]= e[j][1]+ ycord;
				e[j][2] = ((30*50)/(2*PI));
                        }
			if(d>=73 && d<105){
                                PImage anImage = loadImage(theData.allanswers[j].matchProfiles[r].url);
                                losImage.put(theData.allanswers[j].matchProfiles[r].email, anImage);
                                 //the angle is the whole circle cut into the amount of slices that there are users, times the user we are on now, to add onwards
                                float rawAngle = ((360*r)/32);
                                //println(rawAngle);
                                float angle = radians(rawAngle);
                                //the x coordinate is calculated cos we know the hypotenuse which is ej2 + radium of face of 35 plue 5 for space (and we have the angle)
                                float ycord = (cos(angle) * (370));
                                float xcord = (sin(angle) * (370));
                                //so we fill these into an x and y coordinate that is used later on...
                                e[j][r+d]= e[j][0] +xcord;
                                e[j][r+d+1]= e[j][1]+ ycord;
                                e[j][2] = ((36*50)/(2*PI));
                        }
	
		d = d+1;
		}
	}
	PFont font1;
	font1 = loadFont("Helvetica");
	textFont(font1);
	textSize(27);
      
	
       Iterator itr = losImage.entrySet().iterator();
       int n = 50;
	 while(itr.hasNext()){
              Map.Entry me = (Map.Entry)itr.next();
                n+=120;
	}
}

void draw(){
	background(51, 51, 51);

		HashMap aHashMap = new HashMap();
		for (int b = 0; b<numOfAnswers; b++){
			aHashMap = insertToHash(x,y,theData,d);
		}
		drawConnections(theData);
		}	



HashMap insertToHash(float  x,float y , Array theArray, float d) {
	
	HashMap theHash = new HashMap();
	for(i=0;i < theArray.length; i++) {
		String theKey = theArray[i].profileId;
		String theName = theArray[i].name;
		Array anArray = new Array[4];
		anArray[0] = x[i];
		anArray[1] = y[i];
		anArray[2] = theName;
		anArray[3] = d[i]
		//println(' x equals: '+x[i]+' y equals:'+y[i]);
		theHash.put(theKey, anArray);			
	}
	return theHash;	
 		
}

void drawConnections(Array theData){
	for (int j = 0; j<numOfAnswers; j++){
		float radi = e[j][2];
		float diam = radi;
				
		chooseColour(selected_colour);	
		ellipse(e[j][0], e[j][1], radi*3, radi*3);

		//write questions
		String status=decodeURIComponent(theData.allanswers[j].encodedTextualAnswer);
		textSize(18);
		status = status.replaceAll("\\+", " ");
		float boxLength = textWidth(status);
                //println(theOtherArray[k].questionDescription);
                rectMode (CENTER);
                noStroke();
                fill(51,51,51);
	        ellipse(e[j][0], e[j][1], 140, 140);
		fill(255);
		textAlign(CENTER,CENTER);
               
                text(status, e[j][0]-60, e[j][1]-60, 120, 120);
				//move circles
	//	e[j][0]+=e[j][3];
        //	e[j][1]+=e[j][4];
	


		imageMode (CENTER);
		int d = 5;
		//goes through each user for each answer and populates their image on the location based on d and e[j]
		for(int r=0; r<theData.allanswers[j].matchProfiles.length; r++){
		
			//println(theData.allanswers[j].matchProfiles[r].email);
			PImage theImage = losImage.get(theData.allanswers[j].matchProfiles[r].email);
			image(theImage, e[j][r+d], e[j][r+d+1],50,50);
			noFill();
			strokeWeight(2);
			stroke(255,176);
			strokeJoin(ROUND);
			rectMode(CENTER);
			rect(e[j][r+d], e[j][r+d+1],50,50);
			noStroke();
			
				
			if (dist(e[j][r+d], e[j][r+d+1], mouseX, mouseY)<25){
				if(mousePressed){
				PImage theImage = losImage.get(theData.allanswers[j].matchProfiles[r].email);
	                        image(theImage, e[j][r+d], e[j][r+d+1],70,70);
        	                noFill();
                	        strokeWeight(2);
                        	stroke(255);
                       		strokeJoin(ROUND);
                        	rectMode(CENTER);
                        	rect(e[j][r+d], e[j][r+d+1],70,70);
				//get data
				textSize(12);
                	        String theName= (theData.allanswers[j].matchProfiles[r].name);
                        	float smallBoxLength = textWidth(theName);
				//println(theName);
        	                rectMode (CENTER);
                	        smooth();
				fill(51,51,51);
				strokeWeight(4);
				stroke(51,51,51);
				strokeJoin(ROUND);
	                	rect(e[j][r+d], e[j][r+d+1]+27, smallBoxLength+6, 15);
				//strokeWeight(12);
                	        //stroke(255);
                        	//strokeWeight(2);
	                        fill(255);
				textAlign(CENTER,CENTER);
                		textSize(12);
                		text(theName, e[j][r+d], e[j][r+d+1]+27);
				String theTwitterName= (theData.allanswers[j].matchProfiles[r].username);
                        	float smallBoxLength = textWidth(theTwitterName);
        	                rectMode (CENTER);
                	        smooth();
				fill(51,51,51);
				strokeWeight(4);
				stroke(51,51,51);
				strokeJoin(ROUND);
	                	rect(e[j][r+d], e[j][r+d+1]+45, smallBoxLength+6, 15);
	                        fill(255);
				textAlign(CENTER,CENTER);
                		textSize(12);
                		text(theTwitterName, e[j][r+d], e[j][r+d+1]+45);
}
			}

			//so the location of the next one is about to be calculated
			
			//e[j][r+d]+=e[j][3];
                        //e[j][r+d+1]+= e[j][4];

			d=d+1;
			
		}
		//this is for bouncing
		if((e[j][0] > width-radi*2)||(e[j][0] < radi*2)){
		 	e[j][3] = e[j][3]*-1;
		}
		if((e[j][1] > height-radi*2)||(e[j][1] < radi*2)){
		  	e[j][4] = e[j][4]*-1;
		}
		//this is to calculate a distance to move
		for (int i = j+1; i < numOfAnswers; i++){
		      	float dx = e[i][0] - e[j][0];
		      	float dy = e[i][1] - e[j][1];
		      	float distance = sqrt(dx*dx + dy*dy);
		      	float minDist = e[i][2] + diam*2+40;
			//println(minDist);
			if (distance < minDist) { 
				float angle = atan2(dy, dx);
				float targetX = e[j][0] + cos(angle) * minDist;
				float targetY = e[j][1] + sin(angle) * minDist;
				float ax = (targetX - e[i][0]) * spring;
			        float ay = (targetY - e[i][1]) * spring;
			     	e[j][3] -= ax;
			       	e[j][4] -= ay;
			        e[i][3] += ax;
			        e[i][4] += ay;    
	  	        }
		} 
			}
		
			
}


void chooseColour(int k){
  switch(k) {
      case 0: 
        //ORANGEblood supply
	strokeWeight(0);
        fill(255,147,30,176);
        //println("Zero");  // Does not execute
        break;
      case 1: 
      	//GREENsections interest you. 
	strokeWeight(0);
        fill(122,201,67,176);
        //println("One");  // Prints "One"
        break;
      case 2: 
        //REDmost excited by
	strokeWeight(0);
        fill(255,29,37,176);
        //println("One");  // Prints "One"
        break;
      case 3: 
        //PINK congress times
	strokeWeight(0);
        fill(255,123,172,176);
        //println("One");  // Prints "One"
        break;
      case 4: 
	//BLUE Osx
        strokeWeight(0);
        fill(63,169,245,176);
       // println("One");  // Prints "One"
        break;
      }
}



</script><canvas width="500" id ="theCanvas"></canvas></p>
<div style="height:0px;width:0px;overflow:hidden;"></div>

<span id="bundle_id" style="display:none">${bundle_id}</span>
<script type="text/javascript">

$.extend({
          getUrlVars: function(){
            var vars = [], hash;
            var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&'
);            for(var i = 0; i < hashes.length; i++)
            {
              hash = hashes[i].split('=');
              vars.push(hash[0]);
              vars[hash[0]] = hash[1];
            }
            return vars;
          },
          getUrlVar: function(name){
            return $.getUrlVars()[name];
          }
});

function getTheColours(questionId) {
   var theColour;	
     switch (questionId)
	{
	case 0:
  	theColour = "#FF931E";	
	break;
	case 1:
  	theColour = "#7AC925";	
  	break;
	case 2:
  	theColour = "#FF1D25";	
  	break;
	case 3:
  	theColour = "#FF7BAC";	
	break;  
	case 4:
  	theColour = "#3FA9F5";	
	break;
   }
  return theColour;	
	
}
		

var theData;
var question_id;
var selected_colour = 0;

$(document).ready(function(){ 
	var theURL;
	question_id = $.getUrlVar('qid');
        if (question_id == null) {
	   theURL = "/crowdscanner_dev/rest/bubblebyanswersdev/"+$("#bundle_id").html(); 
        } else {
	   theURL = "/rest/bubblebyanswersbyquestion/"+question_id;	
	}

 	$.getJSON(theURL, function(data) {
		 	theData = data;
		        var theQuestions = data.questions;
			if (question_id == null){
				for (key in theQuestions){
					question_id = key;
					break;		
				}
			}
			var count = 0;
			for (key in theQuestions){					
                         	var classSelection = "colours";
				if(question_id == key){
				  selected_colour = count;
				  classSelection = "is_selected";		
				}
				$('#geronimo').append("<div class ='"+classSelection+"' ><a style='text-decoration:none;color:"+getTheColours(count)+";' href=?qid="+key+" id='"+key+"'>"+theQuestions[key]+"</a></div>");
				count++;		
			}
			var scripts = document.getElementsByTagName("script");				    
		    	var canvas;
		    	for (var i = 0, j = 0; i < scripts.length; i++) {
		      	if (scripts[i].type == "application/processing") {
		       	      	var secondCanvas = $('canvas#theCanvas').get(0);
		        	new Processing(secondCanvas, scripts[i].text);          
		        }
		          
		     }			 	
		 	
	}); 
});




</script>

</body>
</html>
