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

import org.codehaus.groovy.grails.web.json.JSONObject;

import grails.transaction.Transactional
import groovy.util.logging.Log4j;

@Log4j
class PhoneService {

	class MyAuthenticator extends Authenticator {

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("sk_test_JF4z1PFLKRUCettRiiRglvt8",
			"password_no_important".toCharArray());
		}
	}

	def sendSMS(phone) {

		log.debug "sendSMS to " + phone

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

			log.debug "sendSMS response code " + http.getResponseCode();

			http.connect();
			InputStream is = http.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			String resp = stringBuilder.toString()

			log.debug "sendSMS received: " + resp

			if(!resp) {
				return null
			}

			JSONObject json = new JSONObject(resp);

			return json;
		}
		catch(Exception e) {
			log.error "sendSMS Exception " + e
			return null
		}
	}

	def verifySMSCode(String code) {

		log.debug "verifySMSCode to " + code

		def verificationId = "518c4db62602b8fe02000061";

		try {

			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			URL u = new URL("https://getprove.com/api/v1/verify/" + verificationId + "/pin");
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

			log.debug "verifySMSCode response code " + http.getResponseCode();

			http.connect();
			InputStream is = http.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			String resp = stringBuilder.toString()

			log.debug "verifySMSCode received: " + resp

			if(!resp) {
				return null
			}

			JSONObject json = new JSONObject(resp);

			return json;
		}
		catch(Exception e) {
			log.error "sendSMS Exception " + e
			return null
		}
	}
}
