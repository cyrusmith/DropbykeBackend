package com.dropbyke

import org.codehaus.groovy.grails.web.json.JSONObject;

import grails.transaction.Transactional

@Transactional
class FacebookService {

    def getUserInfo(String token) {
		
		URL html = new URL("https://graph.facebook.com/me?access_token=" + token);
		URLConnection urlConnection = html.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		StringBuffer response = new StringBuffer();
		String inputLine;
		while ((inputLine = reader.readLine()) != null)
		{
			response.append(inputLine)
		}
		reader.close();
		
		log.debug("Got from facebook " + response.toString())
		def result = [:]
		JSONObject json = new JSONObject(response.toString())
		for(String key in json.keys()) {
			result.put(key, json.get(key))
		}
		
		return result
		
    }
}
