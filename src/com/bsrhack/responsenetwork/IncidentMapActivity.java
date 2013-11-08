package com.bsrhack.responsenetwork;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;

public class IncidentMapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		setDataPoints();
		
	}
	
	private void setDataPoints() {
		// Get a handle to the Map Fragment
		GoogleMap map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		List<Skill> initskills = AccountActivity.generateSkillsList();
		ArrayList<Skill> skills = new ArrayList<Skill>();
		for( int i = 0; i < initskills.size(); i++) {
			if (initskills.get(i).isSelected(this)) {
				skills.add(initskills.get(i));
			}
		}
		List<Request> requests = LocalRequestStore.getRequests(this);
		requests.addAll(getPreloads());
		for(Request request : requests) {
			if (hasSkill(skills,request.skill)) {
				// add to map
				offsetIfNeeded(requests, request);
				MarkerOptions marker = new MarkerOptions()
				.title(request.skill.name)
				.snippet(request.message)
				.position(request.location);
				map.addMarker(marker);
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
			}
		}
		
	}
	
	private void offsetIfNeeded(List<Request> allRequests, Request request) {
		for (Request cur : allRequests) {
			if (!cur.equals(request) &&
					cur.location.latitude == request.location.latitude && 
					cur.location.longitude == request.location.longitude) {
				request.location = new LatLng(request.location.latitude + 0.00005,
						request.location.longitude + 0.00005);
				offsetIfNeeded(allRequests, request);
			}
		}
	}
	
	private boolean hasSkill(List<Skill> skills, Skill skill) {
		for (Skill s : skills) {
			if (s.name.equalsIgnoreCase(skill.name)) {
				return true;
			}
		}
		return false;
	}

	private List<Request> getPreloads() {
		List<Request> preloads = new ArrayList<Request>();
		preloads.add(new Request("First Aid & CPR", "My grandma has fallen and is hurt", new LatLng(37.7900, -122.4000)));
		preloads.add(new Request("Transportation", "need truck to take food & water to disaster shelter", new LatLng(37.7988, -122.3976)));
		preloads.add(new Request("Doctor", "Need a doctor over here!\nPlease help", new LatLng(37.7978, -122.4015)));
		preloads.add(new Request("Shelter", "Our home was knocked down in the earthquake\nwe need a place to stay tonight", new LatLng(37.7938, -122.3989)));
		preloads.add(new Request("Water", "3 people, out of water", new LatLng(37.7960, -122.4007)));
		preloads.add(new Request("Plumber", "drains are backing up", new LatLng(37.7921, -122.3938)));
		preloads.add(new Request("Food", "ran out of food, can grill whatever you bring", new LatLng(37.7896, -122.3900)));
		preloads.add(new Request("Electrician", "power was knocked out at our home", new LatLng(37.7945, -122.3954)));
		return preloads;
	}

}
