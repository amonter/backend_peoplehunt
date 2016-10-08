package com.crowdscanner.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.crowdscanner.controller.utils.ImageUtils;
import com.crowdscanner.controller.utils.ImageUtilsNewVersion;
import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.profile.ProfileInfo;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.service.FileDao;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONSerializer;

@Controller
public class RestRequestControllerProfile {

	
	final static Logger logger = Logger.getLogger(RestRequestControllerProfile.class);
	
	@Autowired
	private UserDao userService;	
	
	@Autowired
	private ProfileService profileService;;	
	
	@Autowired
	private QuestionDao questionService;	
	
	@Autowired
	private FileDao fileService;
	
	
	
	@RequestMapping(value="/sendprofileinfo/", method=RequestMethod.POST)
	public void sendProfileInfo(@RequestParam("aboutme") String aboutMe, @RequestParam("username") String username, 
			@RequestParam("placelive") String placeLive, @RequestParam("website") String website, @RequestParam("gender") String gender,
			HttpServletResponse response){			
		
		try {
			
			User theUser = null;
			if (StringUtils.contains(username, "facebook_" )) {				
				theUser = userService.getByFacebookUserName(username);
				
			} else  {				
				theUser = userService.getByUserName(username);
			}	
			theUser.setGender(gender);
			theUser.setCountry(placeLive);
			ProfileInfo theProfileInfo = null;
			
			if (theUser.getProfile().getProfileInfo() != null) {		
				theProfileInfo = theUser.getProfile().getProfileInfo();	
			}else {
			
			 theProfileInfo = new ProfileInfo();			 
			 theProfileInfo.setProfile(theUser.getProfile());						
			}
			
			theProfileInfo.setWebsite(website);
			theProfileInfo.setSelfPerception(aboutMe);	
		
			
			profileService.saveProfileInfo(theProfileInfo);
			response.setStatus(HttpServletResponse.SC_OK);	
							
			
		} catch (Exception e) {
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("sendprofileinfo User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	@RequestMapping(value="/retrieveprofileinfo/{username}", method=RequestMethod.GET)
	public void retrieveProfileInfo(@PathVariable("username") String username, HttpServletResponse response){			
		
		try {
			
			User theUser = null;
			if (StringUtils.contains(username, "facebook_" )) {				
				theUser = userService.getByFacebookUserName(username);
				
			} else  {				
				theUser = userService.getByUserName(username);
			}			
		
			String gender = theUser.getGender();
			if (gender == null) {
				gender = "my gender";
			}
			String placeLive = theUser.getCountry();
			String website = "my website";
			String aboutMe = "some details about me";	
			
			Map<String, Object> mapOfParameters = new HashMap<String, Object>();
			if (theUser.getProfile().getProfileInfo() != null) {
				
				website = theUser.getProfile().getProfileInfo().getWebsite();
				aboutMe = theUser.getProfile().getProfileInfo().getSelfPerception();	
				mapOfParameters.put("website", website);
				mapOfParameters.put("aboutMe", aboutMe);
			}		
			
			List <Question> questionPhysical = questionService.findQuestionsByUsername(username);
			int answersPhysical = 0;
			int questionsCreated = 0;
			
			for  (Question question : questionPhysical) {
				
				int answers = question.getTotalAnswerCount();								
				answersPhysical = answersPhysical + answers;
				if (question.getParent()) {
					questionsCreated = questionsCreated + 1;
				}
			}
			
			List <Question> questionVirtual = questionService.findVirtualInfluence(username);
			int answersVirtual = 0;
			
			for  (Question question : questionVirtual) {
				
				int answers = question.getTotalAnswerCount();								
				answersVirtual = answersVirtual + answers;				
			}
			
			
			mapOfParameters.put("username", username);
			mapOfParameters.put("gender", gender);
			mapOfParameters.put("placeLive", placeLive);
			mapOfParameters.put("website", website);
			mapOfParameters.put("aboutMe", aboutMe);			
			mapOfParameters.put("answersPhysical", answersPhysical);
			mapOfParameters.put("answersVirtual", answersVirtual);
			mapOfParameters.put("scansCreated", questionsCreated);			
			mapOfParameters.put("name", theUser.getLastName());
			
			String theFile ="http://images.crowdscanner.com/anon_nosmile.png";
			if (theUser.getProfile().getFiles().size() > 0) {
				
				Map<String, File> mapFiles = new HashMap<String, File>();
				for (File aFile : theUser.getProfile().getFiles()) {
					
					mapFiles.put(aFile.getFileName(), aFile);
				}
				
				if (mapFiles.containsKey("profilePhoto")){
					
					File storedFile= mapFiles.get("profilePhoto");
					theFile = "http://www.crowdscanner.com/picture_library/profile/" +storedFile.getId()+".jpg";
					
				} else if (mapFiles.containsKey("facebookProfilePhoto_url") || mapFiles.containsKey("twitterProfilePhoto_url")) {
					
					if (mapFiles.containsKey("facebookProfilePhoto_url")) {
						
						File storedFile= mapFiles.get("facebookProfilePhoto_url");
						theFile = new String(storedFile.getFileContent());		
					} else {
						
						File storedFile= mapFiles.get("twitterProfilePhoto_url");
						theFile = new String(storedFile.getFileContent());						
					}				
				}					
			}		
			
			logger.info("file URL " +theFile);
			mapOfParameters.put("profile_url", theFile);			
			String theResult = new JSONSerializer().serialize(mapOfParameters);		
			
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
				logger.info("retrieveprofileinfo User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	
	@RequestMapping(value="/retrieveprofilephotomatch/{uid}",method=RequestMethod.GET)
	public void retrieveAnonymousPhoto(@PathVariable("uid") String uid, HttpServletResponse response){
		

		try {
			File theFile = fileService.retrieveFileByName("profilePhoto_"+uid);
			String theUrl = "http://www.crowdscanner.com/picture_library/profile/" +theFile.getId()+".jpg";
			
			String theResult = new JSONSerializer().serialize("profile_url",theUrl);		
			
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
				logger.info("uploadprofilephoto User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}  	
	}	
		
	
	
	@RequestMapping(value="/uploadprofilephotomatch/",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void uploadMatchPhoto(@RequestParam("file")  MultipartFile multipartFile, @RequestParam("username") String username, @RequestParam("deviceid") String deviceId, HttpServletResponse response){	
		
		try {
			
			java.io.File dirFile = new java.io.File("/usr/local/tomcat/upload_files/");
			if (username.equals("no_user")) {				
				java.io.File theImageFile = java.io.File.createTempFile("profile_image", ".jpg", dirFile);
				multipartFile.transferTo(theImageFile);	
				byte[] theBuffer = null;						
				if (theImageFile.canRead()) {								
					doImageProcessing(deviceId, theImageFile);					
				}
				
			} else {				
				
				java.io.File theImageFile = java.io.File.createTempFile("profile_image", ".jpg", dirFile);
				multipartFile.transferTo(theImageFile);					
				doImageProcessingWithUsermame(username, theImageFile);
			}
		
		} catch (Exception e) {
		
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("uploadprofilephoto User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}     		
}
	
	
	@RequestMapping(value="/uploadprofilephotomatchbase64/",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void uploadMatchPhotoBase64(@RequestParam("username") String username, @RequestParam("deviceid") String deviceId,
			@RequestParam("image") String image, HttpServletResponse response){	
		
		try {
			
			if (username.equals("no_user")) {
				
				java.io.File theImageFile = java.io.File.createTempFile("profile_image", ".jpg");
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] decodedBytes = decoder.decodeBuffer(image);
				FileOutputStream fos = new FileOutputStream(theImageFile);
				fos.write(decodedBytes);						
				if (theImageFile.canRead()) {								
					doImageProcessing(deviceId, theImageFile);					
				}
				
			} else {				
				
				java.io.File theImageFile = java.io.File.createTempFile("profile_image", ".jpg");
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] decodedBytes = decoder.decodeBuffer(image);		
				FileOutputStream fos = new FileOutputStream(theImageFile);
				fos.write(decodedBytes);	
				doImageProcessingWithUsermame(username, theImageFile);
			}
		
		} catch (Exception e) {
		
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("uploadprofilephoto User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}     		
}

	private void doImageProcessingWithUsermame(String username, java.io.File theImageFile) throws UnsupportedEncodingException {
				
		User theUser = userService.getByUserName(username.trim());		
		byte[] theBuffer;		
		Profile theProfile = theUser.getProfile();
		
		theBuffer =  ImageUtilsNewVersion.doRemoteImageProcessing(theImageFile, "http://127.0.0.1:8080/processor/compress/index.go", 130,130);				
		List<File> updatedSet = ImageUtils.updateFileContent(theBuffer, theProfile.getFiles());	
		for (File file : updatedSet) {
				file.setProfile(theProfile);
		}
		logger.info("update Set size " +updatedSet.size());
		theProfile.setFiles(updatedSet);					
		
		profileService.updateProfile(theProfile);						
		
	}
	

	private void doImageProcessing(String deviceId, java.io.File theImageFile) 	throws UnsupportedEncodingException {
		
		byte[] theBuffer;
		com.myownmotivator.model.profile.File  updateFile = new com.myownmotivator.model.profile.File();
		theBuffer =  ImageUtilsNewVersion.doRemoteImageProcessing(theImageFile, "http://127.0.0.1:8080/processor/compress/index.go", 130, 130);
		
		File theFile =  fileService.retrieveFileByName("profilePhoto_"+deviceId);
		if (theFile != null) {
			updateFile.setId(theFile.getId());
		}
		updateFile.setProfile(null);
		updateFile.setFileName("profilePhoto_"+deviceId);
		updateFile.setFileContent(theBuffer);
		fileService.updateFile(updateFile);
	}
	
	
	@RequestMapping(value="/uploadprofilephoto/",method=RequestMethod.POST)
	public void upload(@RequestParam("file")  MultipartFile multipartFile, @RequestParam("username") String username, HttpServletResponse response){		
		
	
		try {
		
			User theUser = null;
			if (StringUtils.contains(username, "facebook_" )) {				
				theUser = userService.getByFacebookUserName(username.trim());
				
			} else  {				
				theUser = userService.getByUserName(username.trim());
			}		
			
			java.io.File dirFile = new java.io.File("/usr/local/tomcat/upload_files/");
			java.io.File theImageFile = java.io.File.createTempFile("profile_image", ".jpg", dirFile);
			multipartFile.transferTo(theImageFile);			
			
			byte[] theBuffer = null;		
			Profile theProfile =theUser.getProfile();			
			if (theImageFile.canRead()) {			
				
				theBuffer =  ImageUtilsNewVersion.doRemoteImageProcessing(theImageFile, "http://127.0.0.1:8080/processor/compress/index.go", 70, 70);				
				List<File> updatedSet = ImageUtils.updateFileContent(theBuffer, theProfile.getFiles());	
				for (File file : updatedSet) {
					file.setProfile(theProfile);
				}
				logger.info("update Set size " +updatedSet.size());
				theProfile.setFiles(updatedSet);
			}			
		
			profileService.updateProfile(theProfile);
			response.setStatus(HttpServletResponse.SC_OK);
			theImageFile.delete();
		
		} catch (Exception e) {
		
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("uploadprofilephoto User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}     
	}
	
	
	
	@RequestMapping(value="/uploadprofilephoto/",method=RequestMethod.POST)	
	public void uploadPhotoEncode64(@RequestParam("image") String image, HttpServletResponse response){		
		
	
		try {
		
			
		
		} catch (Exception e) {
		
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("uploadprofilephoto User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}     
	}
	
	
	
	@RequestMapping(value="/sendaccesstoken/",method=RequestMethod.POST)
	public void sendAccessToken(@RequestParam("token")  String token, HttpServletResponse response){		
		
	
		try {
		
			logger.info("token "+ token);
			FacebookBatcher batcher = new FacebookBatcher(token);
			Later<JsonNode> friends = batcher.graph("me/friends");
			for (JsonNode theNode : friends.get()) {
				
				System.out.println(theNode.getValueAsText());
				
			}		
			
			response.setStatus(HttpServletResponse.SC_OK);
			
		
		} catch (Exception e) {
		
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("sendAccessToken User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}     
	}
	
	
	@RequestMapping(value="/retrievemapinfluence/{username}", method=RequestMethod.GET)
	public void retrieveUserMapInfluence(@PathVariable("username") String username, HttpServletResponse response){			
		
		try {
			
			List <Question> quVirtual = questionService.findVirtualInfluence(username);
			List <Question> quAnswered = questionService.findQuestionsByUsername(username);
			
			Map<String, Object> mapOfParameters = new HashMap<String, Object>();
			mapOfParameters.put("quVirtual", quVirtual);
			mapOfParameters.put("quAnswered", quAnswered);
			
			String theResult = new JSONSerializer().serialize(mapOfParameters);		
			
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
				logger.info("retrievemapinfluence User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	
}
