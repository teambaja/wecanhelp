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
		for(Request request : requests) {
			if (hasSkill(skills,request.skill)) {
				// add to map
				MarkerOptions marker = new MarkerOptions()
				.title(request.skill.name)
				.snippet(request.message)
				.position(request.location);	
				map.addMarker(marker);
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
			}
		}
		
		/*
		List<Skill> skills = AccountActivity.generateSkillsList();
		for( int i = 0; i < skills.size(); i++) {
			if (skills.get(i).isSelected(this)) {
				MarkerOptions marker = getMarkerForSkill(skills.get(i));
				if (marker != null) {
					map.addMarker(marker);
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
				}
			}
		}
		*/
	}
	
	private boolean hasSkill(List<Skill> skills, Skill skill) {
		for (Skill s : skills) {
			if (s.name.equalsIgnoreCase(skill.name)) {
				return true;
			}
		}
		return false;
	}

	//{"First Aid & CPR", "Transportation", "Doctor", "Shelter", "Water", "Food", "Plumber", "Electrician"};
	private MarkerOptions getMarkerForSkill(Skill skill) {
		MarkerOptions marker = null;
		if ("First Aid & CPR".equalsIgnoreCase(skill.name)) {
			LatLng help = new LatLng(37.7900, -122.4000);
			marker = new MarkerOptions()
			.title("First Aid")
			.snippet("This person needs first aid!")
			.position(help);
		} else if ("Transportation".equalsIgnoreCase(skill.name)) {
			LatLng help = new LatLng(37.7988, -122.3976);
			marker = new MarkerOptions()
			.title("Transportation")
			.snippet("This person needs transportation.")
			.position(help);
		} else if ("Doctor".equalsIgnoreCase(skill.name)) {
			LatLng help = new LatLng(37.7978, -122.4015);
			marker = new MarkerOptions()
			.title("Doctor")
			.snippet("This person needs a doctor.")
			.position(help);
		} else if ("Shelter".equalsIgnoreCase(skill.name)) {
			LatLng help = new LatLng(37.7938, -122.3989);
			marker = new MarkerOptions()
			.title("Shelter")
			.snippet("This person needs shelter.")
			.position(help);
		} else if ("Water".equalsIgnoreCase(skill.name)) {
			LatLng help = new LatLng(37.7960, -122.4007);
			marker = new MarkerOptions()
			.title("Water")
			.snippet("This person needs water.")
			.position(help);
		} else if ("Food".equalsIgnoreCase(skill.name)) {
			LatLng help = new LatLng(37.7896, -122.3900);
			marker = new MarkerOptions()
			.title("Food")
			.snippet("This person needs food.")
			.position(help);
		} else if ("Plumber".equalsIgnoreCase(skill.name)) {
			LatLng help = new LatLng(37.7921, -122.3938);
			marker = new MarkerOptions()
			.title("Plumber")
			.snippet("This person needs a plumber.")
			.position(help);
		} else if ("Electrician".equalsIgnoreCase(skill.name)) {
			LatLng help = new LatLng(37.7945, -122.3954);
			marker = new MarkerOptions()
			.title("Electrician")
			.snippet("This person needs an electrician.")
			.position(help);
		}

		return marker;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

}
