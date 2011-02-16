package de.akuz.android.smsalarm.util.test;

import de.akuz.android.smsalarm.util.NumberUtils;
import junit.framework.Assert;

public class NumberUtilsTestCase {
	
	private final static String internationalFormat = "+491791791798";
	private final static String internationalFormat2 = "00491791791798";
	private final static String localFormat = "01791791798";
	private final static String notANumber = "NotaNumber";
	
	public void testIsValidMobileNumber() throws Exception {
		Assert.assertTrue(NumberUtils.isValidMobileNumber(internationalFormat));
		Assert.assertTrue(NumberUtils.isValidMobileNumber(internationalFormat2));
		Assert.assertTrue(NumberUtils.isValidMobileNumber(localFormat));
		Assert.assertFalse(NumberUtils.isValidMobileNumber(notANumber));
	}
	
	public void testIsInternationalNumber() throws Exception {
		Assert.assertTrue(NumberUtils.isInternationalNumber(internationalFormat));
		Assert.assertTrue(NumberUtils.isInternationalNumber(internationalFormat2));
		Assert.assertFalse(NumberUtils.isInternationalNumber(localFormat));
		boolean exceptionThrown = false;
		try{
			NumberUtils.isInternationalNumber(notANumber);
		} catch(NumberFormatException e){
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}
	
	public void testConvertNumberToInternationalFormat() throws Exception {
		Assert.assertEquals(internationalFormat, 
				NumberUtils.convertNumberToInternationalFormat(localFormat));
		Assert.assertEquals(internationalFormat, 
				NumberUtils.convertNumberToInternationalFormat(internationalFormat2));
		boolean exceptionThrown = false;
		try{
			NumberUtils.convertNumberToInternationalFormat(notANumber);
		} catch (NumberFormatException e){
			exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}

}