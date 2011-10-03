package com.isobar.isohealth.wrappers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.DiabetesMeasurement;
import com.isobar.isohealth.models.DiabetesMeasurementFeed;
import com.isobar.isohealth.models.NewDiabetesMeasurement;
import com.isobar.isohealth.models.User;

public class DiabetesMeasurementWrapper {
	private String authToken;
	
	public DiabetesMeasurementWrapper(String authToken) {
		super();
		this.authToken = authToken;
	}

	public DiabetesMeasurementFeed getDiabetesMeasurementFeed() throws Exception {
		User user = new UserWrapper(authToken).getUser();
		ObjectMapper mapper = new ObjectMapper(); 
		String url = GraphConstants.REST_URL + user.getDiabetes();
		HttpURLConnection conn  = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Authorization", "Bearer " + authToken);
		conn.setRequestProperty("Accept", GraphConstants.MEDIA_DIABETES_MEASUREMENT_FEED);

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		DiabetesMeasurementFeed diabetesMeasurementFeed = mapper.readValue(rd, DiabetesMeasurementFeed.class);
		conn.disconnect();
		return diabetesMeasurementFeed;
	}
	
	public DiabetesMeasurement getDiabetesMeasurement(String url) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		url = GraphConstants.REST_URL + url;
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setRequestProperty("Accept",
				GraphConstants.MEDIA_DIABETES_MEASUREMENT);
		conn.setRequestProperty("Authorization", "Bearer " + authToken);

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		DiabetesMeasurement diabetesMeasurement = mapper.readValue(
				rd, DiabetesMeasurement.class);
		conn.disconnect();
		return diabetesMeasurement;
	}

	public DiabetesMeasurement updateDiabetesMeasurement(DiabetesMeasurement diabetesMeasurement) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String url = GraphConstants.REST_URL + diabetesMeasurement.getUri();
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type",
				GraphConstants.MEDIA_DIABETES_MEASUREMENT);
		conn.setRequestProperty("Authorization", "Bearer " + authToken);
		conn.setRequestProperty("Content-Length", "nnn");
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		mapper.writeValue(conn.getOutputStream(), diabetesMeasurement);
		
		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		diabetesMeasurement = mapper.readValue(rd, DiabetesMeasurement.class);
		conn.disconnect();
		return diabetesMeasurement;
	}
	
	public void createDiabetesMeasurement(
			NewDiabetesMeasurement diabetesMeasurement) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = new UserWrapper(authToken).getUser();
		String url = GraphConstants.REST_URL
				+ user.getDiabetes();
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				GraphConstants.MEDIA_NEW_DIABETES_MEASUREMENT);
		conn.setRequestProperty("Authorization", "Bearer " + authToken);
		conn.setRequestProperty("Content-Length", "nnn");
		
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);;

		System.out.println(mapper.writeValueAsString(diabetesMeasurement));
		mapper.writeValue(conn.getOutputStream(), diabetesMeasurement);
		
		if (conn.getResponseCode() != 201) {
			throw new Exception(conn.getResponseMessage());
		}
		conn.disconnect();
	}
}
