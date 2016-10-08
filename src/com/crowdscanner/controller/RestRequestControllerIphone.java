package com.crowdscanner.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.TwitterUserAuthentication;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.service.email.EmailMessageServiceDelegate;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Controller
public class RestRequestControllerIphone {
		
	final static Logger logger = Logger.getLogger(RestRequestControllerIphone.class);
	
	@Resource
	private QuestionDao questionService;
	
	@Resource
	private RestControllerUtil restControllerUtil;
	
	
	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	
	@Autowired
    private EmailMessageServiceDelegate emailMessageServiceDelegate;
	
	
	@RequestMapping(value="/authenticateuser/{deviceId}", method=RequestMethod.POST)
	public void authenticateUser(@PathVariable("deviceId") String deviceID, @RequestParam(value="username", required= true ) String username,
			@RequestParam(value="password", required= true ) String password, HttpServletResponse response) { 
		
		User user = userService.getByUserNameAndPassword(username, password);
		String authentication = "succeed";
		if (user == null) {
			
			authentication = "failed";
		} 
		
		try {
			
			response.getOutputStream().print(authentication);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Authenticate User Ex", e);
		}				
	}	
	
	@RequestMapping(value="/linkusertwitter/{deviceId}", method=RequestMethod.POST)
	public void createUserTwitter(@PathVariable("deviceId") String deviceID, @RequestParam(value="username", required= true ) String username,
		@RequestParam(value="twitterusername", required= true ) String twitterUsername,	
		 HttpServletRequest request, @RequestParam(value="key", required= true ) String key,
		 @RequestParam(value="secret", required= true ) String secret, HttpServletResponse response) {
		
		try {						
			
			User user = null;
			if (StringUtils.contains(username, "facebook_" )) {				
				user = userService.getByFacebookUserName(username);				
			} else  {				
				user = userService.getByUserName(username);
			}	
			
			TwitterUserAuthentication twitterUserAuthentication = new TwitterUserAuthentication();
			twitterUserAuthentication.setTheKey(key);
			twitterUserAuthentication.setSecret(secret);
			twitterUserAuthentication.setUsername(twitterUsername);
			twitterUserAuthentication.setProfile(user.getProfile());
			
			user.getProfile().setTwitterUserAuthentication(twitterUserAuthentication);
			
			//Twitter  twitter = new Tw();
			//twitter4j.User twitterUser = twitter.getUserDetail(twitterUsername);
			File theFile = new File();
			theFile.setFileName("twitterProfilePhoto_url");
			//String image_url  = twitterUser.getProfileImageURL().toString();
			//theFile.setFileContent(image_url.getBytes());
			user.getProfile().getFiles().add(theFile);
			
			user.getProfile().setSource("twitter");
			//user.setBirthDate(new CustomDateFormat(Calendar.getInstance()));		
			profileService.updateProfile(user.getProfile());								
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().print("succeed");
		
		} catch (Exception ex) {
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("linkusertwitter User Ex", ex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}


	private void parseTwitterXML(String twiterXml, Map<String, String> theXMLMap) {
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			Document dom  = db.parse(new InputSource(new StringReader(twiterXml.trim())));
			
			NodeList nodes_i = dom.getDocumentElement().getChildNodes();			
			if(nodes_i != null && nodes_i.getLength() > 0) {
				for(int i = 0 ; i < nodes_i.getLength();i++) {
					
					String tagName = nodes_i.item(i).getNodeName();
					String tagValue = nodes_i.item(i).getTextContent();					
					theXMLMap.put(tagName, tagValue);
				}
			}

		}	catch(ParserConfigurationException pce) {
				pce.printStackTrace();
		}	catch(SAXException se) {
				se.printStackTrace();
		}	catch(IOException ioe) {
				ioe.printStackTrace();
		}
}	
	
	
	@RequestMapping(value="/createuser/{deviceId}", method=RequestMethod.POST)
	public void createUser(@PathVariable("deviceId") String deviceID, @RequestParam(value="username", required= true ) String username,
			 HttpServletRequest request, @RequestParam(value="password", required= true ) String password,
			 @RequestParam(value="name", required= true ) String name, @RequestParam(value="email", required= true ) String email, 
			 HttpServletResponse response) {				
		
		try {			
				
				User user = userService.getByUserName(username);
				if (user != null) {
				
					response.getOutputStream().println("error," + messageSource.getMessage("message.iphone.invalidUsername", new Object[] {username}, Locale.ENGLISH));
					
			}	else {		
			
				user = new User();		
				user.setUserName(username);
				user.setPassword(password);
				user.setLastName(name);
				user.setEmail(email);
				
				String location = "to_be_set";		
				user.setState(location);
				user.setCountry(location);			
			
				user.getProfile().setIphoneUser(true);				
				//user.setBirthDate(new CustomDateFormat(Calendar.getInstance()));	
				user.getProfile().setSource("not_linked");
				final User theUser = userService.saveUser(user);
				response.getOutputStream().println("saved_ok"+"");
				new Thread(new Runnable(){
					@Override
					public void run() {
				
						emailMessageServiceDelegate.getCrowdscannerUserCreated().send(theUser);
					}
				}).start();	
			}			
			
			response.setStatus(HttpServletResponse.SC_OK);	
				
		} catch (Exception ex) { 
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("createuser User Ex", ex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}	
	
	
	
	
	@RequestMapping(value="/createuserfacebook", method=RequestMethod.POST)
	public void createUserFacebook(@RequestParam(value="username", required= true ) String username,
			 HttpServletRequest request, @RequestParam(value="name", required= true ) String name,
			 @RequestParam(value="country", required= true ) String country, @RequestParam(value="city", required= true ) String city, 
			 @RequestParam(value="email", required= true ) String email, @RequestParam(value="pic", required= true ) String pic, 
			 HttpServletResponse response) {				
		
		try {			
				
				User user = userService.getByFacebookUserName(username);
				
				if (user != null) {
				
					user.setEmail(email);
					user.setCountry(country);
					user.setState(city);
					userService.updateUser(user);
					response.getOutputStream().println("saved_ok"+"");
					response.setStatus(HttpServletResponse.SC_OK);	
					
				}	else {		
			
					user = new User();		
					user.setUserName(username);
					user.setPassword("facebook_password");
					user.setLastName(name);
					user.setEmail(email);
						
					user.setState(city);
					user.setCountry(country);		
					user.getProfile().setSource("facebook");
				
					user.getProfile().setIphoneUser(true);				
					//user.setBirthDate(new CustomDateFormat(Calendar.getInstance()));		
					
					File theFile = new File();
					theFile.setFileName("facebookProfilePhoto_url");				
					theFile.setFileContent(pic.getBytes());
					user.getProfile().getFiles().add(theFile);					
					
					userService.saveUser(user);
					
					response.getOutputStream().println("saved_ok"+"");
					response.setStatus(HttpServletResponse.SC_OK);					
			}						
				
		} catch (Exception ex) { 
			
			try {
				//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getOutputStream().println("error," +"An authentication error happened. Please try to create a user normally." );
				response.setStatus(HttpServletResponse.SC_OK);	
				logger.info("createuserfacebook User Ex", ex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}		
	
	
	
	@RequestMapping(value="/sendtweet/{username}/{questionPhoneId}", method=RequestMethod.POST)
	public void sendTweePost(@PathVariable("username") String username, @PathVariable("questionPhoneId") Integer questionPhoneId,
			@RequestParam("question") String question, @RequestParam("key") String key, @RequestParam("secret") String secret, HttpServletResponse response) {			
		
		
		//String reqURL = String.format("http://crowdscanner.com/user/%1$d/%2$s",	new Object[]{questionPhoneId,username} );
		String reqURL = "http://faces.crowdscanner.com";
		StringBuffer buffer = new StringBuffer();
		
		try {

			String urlString = String.format("http://api.bit.ly/shorten?version=2.0.1&longUrl=%1$s&login=meetforeal&apiKey=R_ab02bb7494ee1fbab034921edc8047d4",
					new Object[]{reqURL});
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());		
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {

				buffer.append(line);
				buffer.append("\n");
			}
			wr.close();
			rd.close();			
			

			Map<String,Object> queryResults = new JSONDeserializer<HashMap<String, Object>>().use(null, HashMap.class).deserialize(buffer.toString()); 		
			String shortenedURL = reqURL;
			if (queryResults.get("statusCode").equals("OK")) {
				
				Map<String,Object> results = (Map<String, Object>) queryResults.get("results");
				Map<String,Object> URLShort = (Map<String, Object>) results.get(reqURL);
				shortenedURL =(String) URLShort.get("shortUrl");
			}			
		
			Twitter twitter = new TwitterFactory().getInstance();
			//twitter.setOAuthConsumer(configService.retrieveConfig("cons_key").getValue(), configService.retrieveConfig("cons_secret").getValue());
			twitter.setOAuthAccessToken(new AccessToken(key, secret));			
			
			/*
			if (StringUtils.isBlank(theQuestion.getCountry())) {
				
				theMessage = messageSource.getMessage("message.twitter.status.noLocation", new Object[] {theQuestion.getTotalAnswerCount(),
				theQuestion.getQuestionDescription(),shortenedURL	}, Locale.ENGLISH);
			}
			else {
				
				theMessage= messageSource.getMessage("message.twitter.status", new Object[] {theQuestion.getTotalAnswerCount(),
				theQuestion.getCountry() ,theQuestion.getQuestionDescription(),shortenedURL}, Locale.ENGLISH);
				
			}
			*/
			
			if(StringUtils.contains(question, "http://bit.ly")) {
				
				question =  StringUtils.replace(question, "http://bit.ly/aUN1OR", shortenedURL);			
			}
			
			twitter.updateStatus(question);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().print("posted_ok");
			
		} catch (Exception e) {

			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("sendtweet User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
		
	}
	
	@RequestMapping(value="/userstats/{username}/{questionPhoneId}", method=RequestMethod.GET)
	public void retrieveUsernameStats(@PathVariable("username") String username, @PathVariable("questionPhoneId") Integer questionId,
			HttpServletResponse response) {			
		
		
	try {
			
			Question theQuestion = questionService.findBackendQuestionWithUsername(username, questionId);
			String theResult = new JSONSerializer().exclude("*.class").include("answers").serialize("question", theQuestion);			
			
			response.setHeader("Content-Encoding", "gzip");
			OutputStream out = response.getOutputStream();
			GZIPOutputStream theGzip = new GZIPOutputStream(out);
			theGzip.write(theResult.getBytes());			
			
			theGzip.flush();
			theGzip.close();
			out.close();				
			
		} catch (Exception e) {
			
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("userstats", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
	}
	
	
	@RequestMapping(value="/addtokenpush/{token}/{username}", method=RequestMethod.GET)
	public void retrieve(@PathVariable("token") String token, @PathVariable("username") String username, HttpServletResponse response){
		
		
		try {
				
			User user = null;
			if (StringUtils.contains(username, "facebook_" )) {				
				user = userService.getByFacebookUserName(username);				
			} else  {				
				user = userService.getByUserName(username);
			}	
			
			user.getProfile().setIphoneUDID(token);
			profileService.updateProfile(user.getProfile());
			response.setStatus(HttpServletResponse.SC_OK);
			
			} catch (Exception e) {
				
				try {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					logger.info("addtokenpush", e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}		
		
		}
	
	
}
