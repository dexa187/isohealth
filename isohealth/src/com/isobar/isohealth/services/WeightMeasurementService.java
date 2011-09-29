package com.isobar.isohealth.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.NewWeightMeasurement;
import com.isobar.isohealth.models.User;
import com.isobar.isohealth.models.WeightMeasurement;
import com.isobar.isohealth.models.WeightMeasurementFeed;

public class WeightMeasurementService {
	public static WeightMeasurementFeed getWeightMeasurementFeed(String code) throws Exception {
		User user = UserService.getUser(code);
		ObjectMapper mapper = new ObjectMapper(); 
		String url = GraphConstants.REST_URL + user.getWeight();
		HttpURLConnection conn  = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Accept", GraphConstants.MEDIA_WEIGHT_MEASUREMENT_FEED);
		conn.setRequestProperty("Authorization", "Bearer " + code);

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		WeightMeasurementFeed weightMeasurementFeed = mapper.readValue(rd, WeightMeasurementFeed.class);
		conn.disconnect();
		return weightMeasurementFeed;
	}

	public static WeightMeasurement getWeightMeasurement(String url, String code) throws Exception {
		ObjectMapper mapper = new ObjectMapper(); 
		url = GraphConstants.REST_URL + url;
		HttpURLConnection conn  = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Accept", GraphConstants.MEDIA_WEIGHT_MEASUREMENT);
		conn.setRequestProperty("Authorization", "Bearer " + code);

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		WeightMeasurement weightMeasurement = mapper.readValue(rd, WeightMeasurement.class);
		conn.disconnect();
		return weightMeasurement;
	}	

	public static WeightMeasurement updateWeightMeasurement(WeightMeasurement weightMeasurement, String code) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String url = GraphConstants.REST_URL + weightMeasurement.getUri();
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type",
				GraphConstants.MEDIA_WEIGHT_MEASUREMENT);
		conn.setRequestProperty("Authorization", "Bearer " + code);
		conn.setRequestProperty("Content-Length", "nnn");
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		mapper.writeValue(conn.getOutputStream(), weightMeasurement);
		
		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		weightMeasurement = mapper.readValue(rd, WeightMeasurement.class);
		conn.disconnect();
		return weightMeasurement;
	}
	
	public static void createWeightMeasurement(
			NewWeightMeasurement weightMeasurement, String code) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = UserService.getUser(code);
		String url = GraphConstants.REST_URL
				+ user.getWeight();
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				GraphConstants.MEDIA_WEIGHT_MEASUREMENT);
		conn.setRequestProperty("Authorization", "Bearer " + code);
		conn.setRequestProperty("Content-Length", "nnn");
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		mapper.writeValue(conn.getOutputStream(), weightMeasurement);
		
		if (conn.getResponseCode() != 204) {
			throw new IOException(conn.getResponseMessage());
		}
		conn.disconnect();
	}
}