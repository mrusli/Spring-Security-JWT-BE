package com.pyramix.security;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.nimbusds.jwt.SignedJWT;

public class TokenParsing {

	public static void main(String[] args) {
		
		TokenParsing obj = new TokenParsing();
		
		String accessToken = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoicnVzbGkiLCJleHAiOjE2NzI1NzAyODgsImlhdCI6MTY3MjU2NjY4OCwic2NvcGUiOiJST0xFX01BTkFHRVIgUk9MRV9VU0VSIn0.SP4QC46vMMnRCrZGk_jlU31-BNnNnSWwxKwJKfZJNJYw61UltGK9pKjoAtp-cxUd8aIcEM9Axeu5-FFkninLfqe4gBeD1RE9Zmj3SW51iLtwbkvLnArErfgOH6u9DNeAxTj-3bqgdP1LnCaRkdlio9qa0k15zm_tCkvntqhzI7mWa7qTCVcoRPuQcvm8ZU0D4lBoIg7Uno2xOUsBNqCaKzkkmWsIt-1_ljZMaIGZAYCaYDrAAZ3TpL33gADcGIlpVtJPzTvblaCvqah5WdV4vDuJzPflN5hRNmdowgJ9tKtC404p6YkwzK_tdN_MnkGdbdScydIG5beCaI5w7vtBPw";

		try {
			obj.parseJWT(accessToken);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	// ref: https://stackoverflow.com/questions/57696416/how-to-decode-jwt-token-to-get-details-of-header-and-payload-using-nimbus-jose-j
	public void parseJWT(String accessToken) throws ParseException {
	    SignedJWT decodedJWT = null;
	    
	    Map<String, String> jsonMap = null;
	    
		try {
			decodedJWT = SignedJWT.parse(accessToken);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
		var header = decodedJWT.getHeader().toString();
		
		System.out.println(header);
		
		var payload = decodedJWT.getPayload().toString();

		System.out.println(payload);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonMap = mapper.readValue(payload, new TypeReference<Map<String, String>>() {});
			
			System.out.println(jsonMap.toString());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		for (String key : jsonMap.keySet()) {
			System.out.println(key + ": " + jsonMap.get(key));
		}
	}
}
