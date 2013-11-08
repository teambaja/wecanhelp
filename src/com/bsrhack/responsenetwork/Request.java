package com.bsrhack.responsenetwork;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Request {
	public Skill skill;
	public String message;
	public LatLng location;
	
	public Request(JSONObject json) throws JSONException {
		skill = new Skill(json.optString("skill"));
		message = json.optString("message");
		JSONObject location = json.optJSONObject("location");
		if (location != null) {
			double lat = location.optDouble("lat");
			double lng = location.optDouble("lng");
			this.location = new LatLng(lat, lng);
		}
	}
	
	public Request(String skill, String message, LatLng loc) {
		this.skill = new Skill(skill);
		this.message = message;
		location = loc;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("skill", skill.name);
		json.put("message", message);
		JSONObject loc = new JSONObject();
		loc.put("lat", location.latitude);
		loc.put("lng", location.longitude);
		json.put("location", loc);
		return json;
	}
}