package com.bsrhack.responsenetwork;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SkillsListAdapter extends BaseAdapter {
	
	private List<Skill> mSkills = new ArrayList<Skill>();
	private WeakReference<Context> mContext;
	
	public SkillsListAdapter(Context context) {
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
			LayoutInflater inflater = (LayoutInflater) mContext.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_skill, null);
			ViewHolder holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		
		final Skill skill = mSkills.get(position);
		holder.text.setText(skill.name);
		holder.checkbox.setChecked(skill.isSelected(mContext.get()));
		holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d("SkillsListAdapter", skill.name + " is " + isChecked);
				skill.setSelected(mContext.get(), isChecked);
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		TextView text;
		CheckBox checkbox;
	}
}