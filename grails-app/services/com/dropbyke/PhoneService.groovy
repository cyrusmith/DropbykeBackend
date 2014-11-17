package com.dropbyke

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.groovy.grails.web.json.JSONException;
import org.codehaus.groovy.grails.web.json.JSONObject;

import grails.transaction.Transactional
import groovy.util.logging.Log4j;

@Log4j
class PhoneService {

	def grailsApplication

	class MyAuthenticator extends Authenticator {

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(grailsApplication.config.com.dropbyke.getproveApiKey,
			"password_no_important".toCharArray());
		}
	}

	private String sendSMSRingcaptcha(phone) throws Exception {

		try {
			def apiKey = grailsApplication.config.com.dropbyke.ringcaptcha.apiKey
			def appKey = grailsApplication.config.com.dropbyke.ringcaptcha.appKey
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			URL u = new URL("https://api.ringcaptcha.com/${appKey}/code/sms");
			HttpsURLConnection http = (HttpsURLConnection) u.openConnection();
			http.setConnectTimeout(10000);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setInstanceFollowRedirects(false);
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			final DataOutputStream wr = new DataOutputStream(http.getOutputStream());
			wr.writeBytes("app_key=${appKey}&secret_key=${apiKey}&phone=${phone}&locale=en_us");
			wr.flush();
			wr.close();

			log.debug "sendSMS response code " + http.getResponseCode();

			http.connect();
			InputStream is = http.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			if(log.isDebugEnabled()) {
				log.debug "received: " + stringBuilder.toString()
			}

			JSONObject json = new JSONObject(stringBuilder.toString());

			if(!json || !json.has("status")) {
				throw new Exception("Failed to get response")
			}
			
			if(json.getString("status") == "ERROR" && json.getString("message")=="ERROR_WAIT_TO_RETRY") {
				log.error "Need to wait a while"
				throw new Exception("Please wait about a minute and try again")
			}
			
			if(!json.has("token")  || !json.getString("token")) {
				throw new Exception("Failed to get verification key")
			}

			return json.getString("token")

		}
		catch(JSONException e) {
			log.error "Send SMS JSONException: "  +e.message
			throw new Exception("Error in response. Please try again.")
		}
		catch(IOException e) {
			log.error "Send SMS IOException: "  +e.message
			throw new Exception("Error sending sms")
		}
	}

	private boolean verifySMSRingcaptcha(String phone, String code, String requestKey) throws Exception {
		try {

			def apiKey = grailsApplication.config.com.dropbyke.ringcaptcha.apiKey
			def appKey = grailsApplication.config.com.dropbyke.ringcaptcha.appKey

			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			URL u = new URL("https://api.ringcaptcha.com/${appKey}/verify");
			HttpsURLConnection http = (HttpsURLConnection) u.openConnection();
			http.setConnectTimeout(10000);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setInstanceFollowRedirects(false);
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			final DataOutputStream wr = new DataOutputStream(http.getOutputStream());
			wr.writeBytes("app_key=${appKey}&secret_key=${apiKey}&phone=${phone}&code=${code}");
			wr.flush();
			wr.close();

			if(log.isDebugEnabled()) {
				log.debug "verifySMSCode response code " + http.getResponseCode()
			}

			http.connect();
			InputStream is = http.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			if(log.isDebugEnabled()) {
				log.debug "verifySMSCode received: " + stringBuilder.toString()
			}

			JSONObject json = new JSONObject(stringBuilder.toString());

			if(!json || !json.has("status")) {
				throw new Exception("Failed to receive verification response")
			}

			if(json.getString("status") != "SUCCESS") {
				return false
			}

			return true
		}
		catch(JSONException e) {
			log.error "Verify SMS JSONException:" + e.message
			throw new Exception("Failed to parse verification response")
		}
		catch(IOException e) {
			log.error "Verify SMS IOException:" + e.message
			throw new Exception("Failed to send verification code")
		}
	}

	private String sendSMSGetprove(String phone) throws Exception {
		try {

			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			URL u = new URL("https://getprove.com/api/v1/verify");
			HttpsURLConnection http = (HttpsURLConnection) u.openConnection();
			http.setConnectTimeout(10000);
			http.setInstanceFollowRedirects(false);
			Authenticator.setDefault(new MyAuthenticator());
			http.setAllowUserInteraction(true);
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			final DataOutputStream wr = new DataOutputStream(http.getOutputStream());
			wr.writeBytes("tel=" + phone);
			wr.flush();
			wr.close();

			if(log.isDebugEnabled()) {
				log.debug "sendSMS response code " + http.getResponseCode();
			}

			http.connect();
			InputStream is = http.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			if(log.isDebugEnabled()) {
				log.debug "sendSMS received: " + stringBuilder.toString()
			}

			JSONObject json = new JSONObject(stringBuilder.toString());

			if(!json || !json.has("id")) {
				throw new Exception("Failed to get response from sms service")
			}

			return json.getString("id")

		}
		catch(JSONException e) {
			log.error "SendSMS JSONException:" + e.message
			throw new Exception("Failed to parse response from sms service")
		}
		catch(IOException e) {
			log.error "SendSMS IOException:" + e.message
			throw new Exception("Failed to send response to sms service")
		}
	}

	private boolean verifySMSGetprove(String phone, String code, String requestKey) throws Exception {
		try {

			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			URL u = new URL("https://getprove.com/api/v1/verify/" + requestKey + "/pin");
			HttpsURLConnection http = (HttpsURLConnection) u.openConnection();
			http.setConnectTimeout(10000);
			http.setInstanceFollowRedirects(false);
			Authenticator.setDefault(new MyAuthenticator());
			http.setAllowUserInteraction(true);
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			final DataOutputStream wr = new DataOutputStream(http.getOutputStream());
			wr.writeBytes("pin=" + code);
			wr.flush();
			wr.close();

			if(log.isDebugEnabled()) {
				log.debug "verifySMSCode response code " + http.getResponseCode();
			}

			http.connect();
			InputStream is = http.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			if(log.isDebugEnabled()) {
				log.debug "verifySMSCode received: " + stringBuilder.toString()
			}

			JSONObject json = new JSONObject(stringBuilder.toString());
			
			if(!json || !json.has("verified")) {
				throw new Exception("Failed to get response from sms service")
			}
			
			if(json.getBoolean("verified")) {
				return true
			}
			
			return false
		}
		catch(JSONException e) {
			log.error "SendSMS JSONException:" + e.message
			throw new Exception("Failed to parse response from sms service")
		}
		catch(IOException e) {
			log.error "SendSMS IOException:" + e.message
			throw new Exception("Failed to send response to sms service")
		}
	}

	def sendSMS(phone) throws Exception {

		log.debug "sendSMS to " + phone + " with " + grailsApplication.config.com.dropbyke.smsService

		if(grailsApplication.config.com.dropbyke.smsService == "ringcaptcha") {
			return this.sendSMSRingcaptcha(phone)
		}
		else if (grailsApplication.config.com.dropbyke.smsService == "getprove") {
			return this.sendSMSGetprove(phone)
		}
		else {
			throw new Exception("No sms service defined")
		}
	}

	def verifySMSCode(String phone, String code, String verificationId) throws Exception {

		if(log.isDebugEnabled()) {
			log.debug "verifySMSCode to " + code
		}

		if(grailsApplication.config.com.dropbyke.smsService == "ringcaptcha") {
			return this.verifySMSRingcaptcha(phone, code, verificationId)
		}
		else if(grailsApplication.config.com.dropbyke.smsService == "getprove") {
			return this.verifySMSGetprove(phone, code, verificationId)
		}
		else {
			throw new Exception("No sms service defined")
		}
	}
}
