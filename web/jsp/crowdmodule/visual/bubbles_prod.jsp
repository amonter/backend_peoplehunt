<!DOCTYPE html>
<html>
<head>
   <script type="text/javascript" src="http://images.crowdscanner.com/jquery-1.4.2.min.js"></script>
   <script src="http://images.crowdscanner.com/processing-0.9.7.min.js"></script>    
   <title>Crowd Visualisation</title>
</head>
<body style="margin:0px;">
<div style="width:screen.width;padding:10px 0px 10px 0px;color:#333333;background-color:white;font-size:35px;text-align:center; font-family:helvetica;">${name}</div>

<div style=" padding:20px 0px 20px 0px;text-align:center;background-color:white;">
                <form action="http://crowdscanner.com/crowdmodule/public/${bundle_id}/questions"/><button type="submit" class="form-boxes-buttons"style="background-color:#FF0066;padding:10px 10px 10px 10px; height:50px;color:#FFFFFF;font-size:20px;">Answer the questions!</button></form>
        </div>
<script type="application/processing" target="theCanvas">

${preload_images}

Connectors l1;
float radius = 30;
float[] x = new float[]; 
float[] y = new float[];
float[] d = new float[];
float[] refX= new float[];
float[] refY = new float[];
float[] widthGrid = new float[];
float[] heightGrid = new float[];
HashMap losImage = new HashMap(120);

int maxSize = 60;
int minSize = 50;
numOfAnswers = theData.allanswers.length;
float [][] e = new float [numOfAnswers][106];
float ds = 2;
int sel = 0;
int change = 0;
int num = 0;
float[] textLength = new float[5];
float[] xpos = new float[5];
float spring = 0.01;
boolean dragging = false;
int numOfUsers = theData.totalusers; 

HashMap colours = new HashMap();
HashMap questionDescriptions = new HashMap();


void mouseDragged(){
	dragging = true;
}

void mouseReleased(){
	dragging = false;
	}
	
void setup(){
	
	size(screen.width, 3800);
	frameRate(10);
	smooth();
	strokeWeight(1);
	//positions
	int i = 0;
	//	println(widthGrid[0]);
	for (int i = 0;i<numOfAnswers; i++){
		int u = 0;
		int domain = 0;
		for (int p = 0; p<numOfAnswers; p++){
			float referenceX=200+( (theData.allanswers[p].matchProfiles.length*80)/(PI));
			refX[p]= int(referenceX);
			float referenceY=220+( (theData.allanswers[p].matchProfiles.length*100)/(PI));
                	refY[p]= int(referenceY);
			if(p==0){
				x[p]=refX[p];
			}else{
				x[p]=x[p-1] +refX[p];
						
			}
			if(x[p]>screen.width-200){
				x[p]=refX[0];
				domain = domain + refY[u];
				u = p;
			}
			if(p==0){
				y[p]= refY[p]/2;
			}else{
				y[p]=refY[p] + domain;
						
			}
		}	
	}
	//println(x+" "+y);	
	//fill hashmap with colours


	for(int f = 0; f<theData.allanswers.length; f++){
		colours.put(theData.allanswers[f].questionPhoneId,f);
	 	questionDescriptions.put(theData.allanswers[f].questionPhoneId,theData.allanswers[f].questionDescription);	
		//println(theData.allanswers[f].questionPhoneId);
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
              	//println(me.getValue());
	}


	//println(colours.values());	
	//initialise the circles - as many as the data length, and their sizes
	for (int j = 0; j<numOfAnswers; j++){
		//y[j]=heightGrid[j];
                //x[j]=widthGrid[j];
		e[j][0] = x[j]; //X
		e[j][1] = y[j]; //Y
		e[j][2] = ((theData.allanswers[j].matchProfiles.length*80)/(2*PI)); //radius
		//println(e[j][2]);
		e[j][3] = random(-0.2, 0.2); //x speed
		e[j][4] = random(-0.2, 0.2); //y speed
		int d=5;
		//depending on the number of people, it goes into a loop, per answer, and contructs the coordinates of the circles around the answer
		for(int r=0; r<theData.allanswers[j].matchProfiles.length; r++){
			//println(theData.allanswers[j].matchProfiles[r].url);
			PImage anImage = loadImage(theData.allanswers[j].matchProfiles[r].url);
                	losImage.put(theData.allanswers[j].matchProfiles[r].email, anImage);
			//the angle is the whole circle cut into the amount of slices that there are users, times the user we are on now, to add onwards	
 			float rawAngle = ((360*r)/theData.allanswers[j].matchProfiles.length);
			//println(rawAngle);
			float angle = radians(rawAngle);
                        //the x coordinate is calculated cos we know the hypotenuse which is ej2 + radium of face of 35 plue 5 for space (and we have the angle)
			float ycord = (cos(angle) * (e[j][2]+30));
                        float xcord = (sin(angle) * (e[j][2]+30));
                        //ellipse (f, g, 6, 6);
			//so we fill these into an x and y coordinate that is used later on...
                        e[j][r+d]= e[j][0] +xcord;
                        e[j][r+d+1]= e[j][1]+ ycord;
			d = d+1;
		}
	}
	PFont font1;
	font1 = loadFont("Arial");
	textFont(font1);
	textSize(27);
       
       Iterator is = losImage.entrySet().iterator();
        while(is.hasNext()){
                Map.Entry me = (Map.Entry)is.next();
  		//println(me.getKey());
        }

}

void draw(){
	background(51, 51, 51);

		HashMap aHashMap = new HashMap();
		for (int b = 0; b<numOfAnswers; b++){
			aHashMap = insertToHash(x,y,theData,d);
			//println(e[b][0]);
		}
		drawConnections(theData);
		}	

class Connectors{
  float x1, y1, x2, y2;
  int thick, gray;
  float rad1;
  float rad2;
  
  Connectors(float xpos1, float ypos1, float xpos2, float ypos2, int t, int g, float radius1, float radius2){
  	x1 = xpos1;
    	y1 = ypos1;
    	x2 = xpos2;
    	y2 = ypos2;
    	thick = t; 
    	gray = g; 
    	rad1=radius1;
    	rad2=radius2;
  }
  
 void update1(){
 
    	float angle = atan2(y2-y1, x2-x1);
    	float distance = dist(x1, y1, x2, y2);
    	pushMatrix();
    	translate(x1, y1);
    	rotate(angle);
    	//strokeWeight(t);
      	stroke(255, 100);
      	line( rad1, 0, distance/2, 0 );
      	//strokeWeight(1);
      	fill(255, 100)
	rectMode (CENTER);
	rect(distance/2, 0, 2, 2 );
    	popMatrix();
    	}
}

function ArraySubtract(ara1,index) {
   	var aRes = new Array();
   	for(var i=0;i<ara1.length;i++) {
     		if(i != index) {
       			aRes.push(ara1[i]) ;
     		}
   	}
   	return aRes;
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
		float diam = radi/2;
			

		imageMode (CENTER);
		int d = 5;
		//goes through each user for each answer and populates their image on the location based on d and e[j]
		for(int r=0; r<theData.allanswers[j].matchProfiles.length; r++){
		
			//println(theData.allanswers[j].matchProfiles[r].email);
			PImage theImage = losImage.get(theData.allanswers[j].matchProfiles[r].email);
			image(theImage, e[j][r+d], e[j][r+d+1],80,80);
			fill(255, 0);				
			strokeWeight(2);
			ellipse(e[j][r+d], e[j][r+d+1], 78, 78);
			stroke(51,51, 51);
			strokeWeight(21);
			ellipse(e[j][r+d], e[j][r+d+1], 93,93);
			stroke(255);
			strokeWeight(3);
			ellipse(e[j][r+d], e[j][r+d+1], 78, 78);
			noStroke();
			
			if (dist(e[j][r+d], e[j][r+d+1], mouseX, mouseY)<radi*2){
				//get data
				textSize(24);
                	        String theName= (theData.allanswers[j].matchProfiles[r].name);
                        	float smallBoxLength = textWidth(theName);
				//println(theName);
        	                rectMode (CENTER);
                	        noStroke();
				fill(255, 255, 255, 176);
	                	rect(e[j][r+d], e[j][r+d+1], smallBoxLength+10, 30);
				//strokeWeight(12);
                	        //stroke(255);
                        	//strokeWeight(2);
	                        fill(0);
				textAlign(CENTER,CENTER);
                		textSize(24);
                		text(theName, e[j][r+d], e[j][r+d+1]);
                	}
			//so the location of the next one is about to be calculated
			
			e[j][r+d]+=e[j][3];
                        e[j][r+d+1]+= e[j][4];

			d=d+1;
			
		}
		sel = 0;
		//this is for bouncing
		if((e[j][0] > width-diam)||(e[j][0] < diam)){
		 	e[j][3] = e[j][3]*-1;
		}
		if((e[j][1] > height-diam)||(e[j][1] < diam)){
		  	e[j][4] = e[j][4]*-1;
		}
		//this is to calculate a distance to move
		for (int i = j+1; i < numOfAnswers; i++){
		      	float dx = e[i][0] - e[j][0];
		      	float dy = e[i][1] - e[j][1];
		      	float distance = sqrt(dx*dx + dy*dy);
		      	float minDist = e[i][2] + diam*2+90+90;
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
		
		Integer colour = (Integer)colours.get(theData.allanswers[j].questionPhoneId);
		chooseColour(colour);	
		//write questions
		String question = (String)questionDescriptions.get(theData.allanswers[j].questionPhoneId);
		textSize(22);
		float questionBoxLength = textWidth(question);
		textAlign(CENTER,TOP);
	     	text (question, 20 +(screen.width/5)*colour,20, screen.width/5-50 , 200);
              	//println(question);
		
		ellipse(e[j][0], e[j][1], radi*2, radi*2);	
		String status=decodeURIComponent(theData.allanswers[j].encodedTextualAnswer);
		textSize(18);
		status = status.replaceAll("\\+", " ");
		float boxLength = textWidth(status);
                //println(theOtherArray[k].questionDescription);
                rectMode (CENTER);
                noStroke();
                chooseColour(colour);
		if (boxLength>230){
	                rect(e[j][0], e[j][1], 230, 40);
        
		}else{
			rect(e[j][0], e[j][1], boxLength+10, 30);
		}
		fill(255);
		textAlign(CENTER,CENTER);
               
                text(status, e[j][0]-115, e[j][1]-115, 230, 230);
				//move circles
		e[j][0]+=e[j][3];
        	e[j][1]+=e[j][4];
	
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



</script><canvas width="800" height="600" id ="theCanvas"></canvas></p>
<div style="height:0px;width:0px;overflow:hidden;"></div>

<span id="bundle_id" style="display:none">${bundle_id}</span>
<script type="text/javascript">
var theData;

$(document).ready(function(){ 
	
	//var theURL = "/rest/bubblebyanswers/"+$("#bundle_id").html(); 
	var theURL = "/rest/bubblebyanswersbyquestion/8536836"; 
 	$.getJSON(theURL, function(data) {
		 	theData = data;
		 
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
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-1869004-4']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</body>
</html>
