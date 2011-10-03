package com.isobar.isohealth.wrappers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.PersonalRecord;
import com.isobar.isohealth.models.User;

public class PersonalRecordWrapper {
	private String authToken;
	
	public PersonalRecordWrapper(String authToken) {
		super();
		this.authToken = authToken;
	}

	public PersonalRecord[] getPersonalRecord() throws Exception {
		User user = new UserWrapper(authToken).getUser();
		ObjectMapper mapper = new ObjectMapper(); 
		String url = GraphConstants.REST_URL + user.getRecords();
		HttpURLConnection conn  = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Authorization", "Bearer " + authToken);
		conn.setRequestProperty("Accept", GraphConstants.MEDIA_RECORDS);

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));

		PersonalRecord[] personalRecord = mapper.readValue(rd, PersonalRecord[].class);
		conn.disconnect();
		return personalRecord;
	}
}