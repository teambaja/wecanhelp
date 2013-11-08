package com.bsrhack.responsenetwork;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Skill {
	public String name;
	
	public Skill(String name) {
		this.name = name;
	}

	// Dynamically check if the skill is selected
	public boolean isSelected(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getBoolean(name, false);
	}
	
	public void setSelected(Context context, boolean value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = preferences.edit();
		edit.putBoolean(name, value);
		edit.commit();
	}
}
