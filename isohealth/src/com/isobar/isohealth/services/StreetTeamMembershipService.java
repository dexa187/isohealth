package com.isobar.isohealth.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

import com.isobar.isohealth.GraphConstants;
import com.isobar.isohealth.models.StreetTeamInvite;
import com.isobar.isohealth.models.StreetTeamInviteReply;
import com.isobar.isohealth.models.StreetTeamMember;
import com.isobar.isohealth.models.StreetTeamMembershipFeed;
import com.isobar.isohealth.models.User;

public class StreetTeamMembershipService {
	
	public static StreetTeamMembershipFeed getStreetTeamMembershipFeed(String code) throws Exception {
		User user = UserService.getUser(code);
		ObjectMapper mapper = new ObjectMapper(); 
		String url = GraphConstants.REST_URL + user.getTeam();
		HttpURLConnection conn  = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Accept", GraphConstants.MEDIA_TEAM_FEED);
		conn.setRequestProperty("Authorization", "Bearer " + code);

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		StreetTeamMembershipFeed streetTeamMembershipFeed = mapper.readValue(rd, StreetTeamMembershipFeed.class);
		conn.disconnect();
		return streetTeamMembershipFeed;
	}
	
	public static void inviteUser(String code, StreetTeamInvite invitation) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = UserService.getUser(code);
		String url = GraphConstants.REST_URL + user.getTeam();
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				GraphConstants.MEDIA_TEAM_INVITATION);
		conn.setRequestProperty("Authorization", "Bearer " + code);
		conn.setRequestProperty("Content-Length", "nnn");
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		mapper.writeValue(conn.getOutputStream(), invitation);
		
		if (conn.getResponseCode() != 201) {
			throw new IOException(conn.getResponseMessage());
		}
//		BufferedReader rd = new BufferedReader(new InputStreamReader(
//				conn.getInputStream()));
//		StreetTeamMembershipFeed streetTeamMembershipFeed = mapper.readValue(rd, StreetTeamMembershipFeed.class);
//		conn.disconnect();
//		return streetTeamMembershipFeed;
	}
	
	public static StreetTeamMember replyToInvite(String code, String teamId, StreetTeamInviteReply reply) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = UserService.getUser(code);
		String url = GraphConstants.REST_URL + user.getTeam() + "/"+teamId;
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				GraphConstants.MEDIA_TEAM_INVITE_REPLY);
		conn.setRequestProperty("Authorization", "Bearer " + code);
		conn.setRequestProperty("Content-Length", "nnn");
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		mapper.writeValue(conn.getOutputStream(), reply);
		
		if (conn.getResponseCode() != 200) {
			throw new Exception(conn.getResponseMessage());
		}
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		StreetTeamMember streetTeamMember = mapper.readValue(rd, StreetTeamMember.class);
		conn.disconnect();
		return streetTeamMember;
	}
	
	public static boolean removeUser(String code, String teamId) throws Exception {
		boolean response = false;
		User user = UserService.getUser(code);
		String url = GraphConstants.REST_URL + user.getTeam() + "/" + teamId;
		HttpURLConnection conn  = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Authorization", "Bearer " + code);

		if (conn.getResponseCode() == 204) {
			response = true;
		}
		System.out.println(conn.getResponseCode()+"#"+conn.getResponseMessage());
		conn.disconnect();
		return response;
	}
}
