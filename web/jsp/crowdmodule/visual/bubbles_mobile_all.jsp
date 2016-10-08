<!DOCTYPE html>
<html>
<head>
   <script type="text/javascript" src="http://images.crowdscanner.com/jquery-1.4.2.min.js"></script>
   <script src="http://images.crowdscanner.com/processing-1.1.0.min.js"></script>    
   <title>Crowd Visualisation</title>
</head>
<body style="margin:0px;">
<div style="width:500px;padding:10px 10px 10px 20px;color:white;background-color:#333333;font-size:35px;text-align:center; font-family:helvetica;">${name}</div>

<div style="width:500px; padding:20px 0px 20px 0px;text-align:center;background-color:#333333;">
                <form action="http://crowdscanner.com/crowdmodule/public/${bundle_id}/questions"/><button type="submit" class="form-boxes-buttons"style="background-color:#FF0066;padding:10px 10px 10px 10px; height:50px;color:#FFFFFF;font-size:20px;">Answer the questions!</button></form>
        </div>
<script type="application/processing" target="theCanvas">

${preload_images}

numOfAnswers = theData.allanswers.length;

Connectors l1;
float radius = 30;
float[] x = new float[numOfAnswers]; 
float[] y = new float[numOfAnswers];
float[] d = new float[theData.totalusers];
float[] refX= new float[numOfAnswers];
float[] refY = new float[numOfAnswers];
float[] widthGrid = new float[];
float[] heightGrid = new float[];
HashMap losImage = new HashMap(120);

int maxSize = 50;
int minSize = 50;
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
	
	size(1000,2400);
	frameRate(10);
	smooth();
	strokeWeight(1);
	//positions
	int i = 0;
	//	println(widthGrid[0]);
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
                        if(x[p]>500-130){
                                x[p]=refX[0];
                                yCount = 1;
                                domain =domain+750;
                        }
                        if(yCount==0){
                                y[p]= refY[p];
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
                                }
                        }


		
		//so the location of the next one is about to be calculated
			

			d=d+1;
			
		}
		sel = 0;
		Integer colour = (Integer)colours.get(theData.allanswers[j].questionPhoneId);
			
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



</script><canvas width="600" height="600" id ="theCanvas"></canvas></p>
<div style="height:0px;width:0px;overflow:hidden;"></div>

<span id="bundle_id" style="display:none">${bundle_id}</span>
<script type="text/javascript">
var theData;

$(document).ready(function(){ 
	
	var theURL = "/rest/bubblebyanswers/"+$("#bundle_id").html(); 
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
