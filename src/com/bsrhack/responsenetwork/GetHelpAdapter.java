package com.bsrhack.responsenetwork;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class GetHelpAdapter extends BaseAdapter {
	
	private List<Skill> mSkills = new ArrayList<Skill>();
	private WeakReference<Context> mContext;
	
	public GetHelpAdapter(Context context) {
		mContext = new WeakReference<Context>(context);
	}
	
	public void setSkillsList(List<Skill> skills) {
		mSkills = skills;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mSkills.size();
	}

	@Override
	public Object getItem(int position) {
		return mSkills.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) mContext.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_gethelp, null);
			ViewHolder holder = new ViewHolder();
			holder.button = (Button) convertView.findViewById(R.id.button);
			holder.button.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					if (v instanceof Button) {
						final String category = ((Button)v).getText().toString();
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext.get());
						View dialogView = inflater.inflate(R.layout.request_dialog, null);
						final EditText editDesc = (EditText) dialogView.findViewById(R.id.message);
						Dialog dialog = builder.setView(dialogView)
								.setTitle(category)
								.setPositiveButton("Send", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Toast.makeText(mContext.get(), "Request sent", Toast.LENGTH_SHORT).show();
										String description = editDesc.getText().toString();
										// Take description + category + location and save locally
										
										String locationProvider = LocationManager.NETWORK_PROVIDER;
										// String locationProvider = LocationManager.GPS_PROVIDER
										LocationManager locationManager = (LocationManager) mContext.get().getSystemService(Context.LOCATION_SERVICE);
										Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
										LatLng location = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
										
										Request request = new Request(category, description, location);
										LocalRequestStore.putRequest(mContext.get(), request);
									}
								})
								.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								})								
						.create();
						dialog.show();
					}
				}
			});
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		final Skill skill = mSkills.get(position);
		holder.button.setText(skill.name);
		return convertView;
	}

	private static class ViewHolder {
		Button button;
	}
}