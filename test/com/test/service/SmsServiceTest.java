package com.test.service;

import java.util.ArrayList;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"ApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class SmsServiceTest {

	private class AvailablePhoneNumber
	{
		public String FriendlyName;
		public String PhoneNumber;
		public String Latitude;
		public String Longtitude;
		public String Region;
		public String PostalCode;
		public String IsoCountry;
		public String Lata;
		public String RateCenter;
		
		public AvailablePhoneNumber(
				String FriendlyName,
				String PhoneNumber,
				String Latitude,
				String Longtitude,
				String Region,
				String PostalCode,
				String IsoCountry,
				String Lata,
				String RateCenter)
		{
			this.FriendlyName = FriendlyName;
			this.PhoneNumber  = PhoneNumber;
			this.Latitude     = Latitude;
			this.Longtitude   = Longtitude;
			this.Region       = Region;
			this.PostalCode   = PostalCode;
			this.IsoCountry   = IsoCountry;
			this.Lata         = Lata;
			this.RateCenter   = RateCenter;
		}
	}
	private ArrayList<AvailablePhoneNumber> availablePhoneNumbers;
}
