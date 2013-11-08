package com.bsrhack.responsenetwork;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
						String category = ((Button)v).getText().toString();
						//TODO: show the dialog with customization ability
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext.get());
						View dialogView = inflater.inflate(R.layout.request_dialog, null);
						Dialog dialog = builder.setView(dialogView)
								.setTitle(category)
								.setPositiveButton("Send", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Toast.makeText(mContext.get(), "Request sent", Toast.LENGTH_SHORT).show();
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