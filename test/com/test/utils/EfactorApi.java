package com.test.utils;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
import org.scribe.services.PlaintextSignatureService;
import org.scribe.services.SignatureService;
import org.scribe.services.TimestampService;

public class EfactorApi extends DefaultApi10a {

	@Override
	public String getAccessTokenEndpoint() {
		// TODO Auto-generated method stub
		return "https://api.efactor.com/oauth/access_token";
	}

	@Override
	public String getAuthorizationUrl(Token arg0) {
		// TODO Auto-generated method stub
		return "https://api.efactor.com/oauth/authorize";
	}

	@Override
	public String getRequestTokenEndpoint() {
		// TODO Auto-generated method stub
		return "https://api.efactor.com/oauth/request_token";
	}
	
	@Override
	public SignatureService getSignatureService() {
		// TODO Auto-generated method stub
		return new PlaintextSignatureService();
	}

	@Override
	public TimestampService getTimestampService() {
		// TODO Auto-generated method stub
		return new MyEfactorTimeStamp();
	}
}
