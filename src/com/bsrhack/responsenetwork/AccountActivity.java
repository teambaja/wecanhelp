package com.bsrhack.responsenetwork;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AccountActivity extends Activity {

	private ListView mSkillsList;
	private SkillsListAdapter mSkillsListAdapter;
	private List<Skill> mSkills;
	private Button mBtnGiveHelp;
	private Button mBtnGetHelp;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSkills = generateSkillsList();
        
        setContentView(R.layout.activity_account);
        mSkillsList = (ListView) findViewById(R.id.skills_list);
        mSkillsListAdapter = new SkillsListAdapter(this);
        mSkillsList.setAdapter(mSkillsListAdapter);
        mSkillsListAdapter.setSkillsList(mSkills);
        
        mBtnGiveHelp = (Button) findViewById(R.id.btn_give);
        mBtnGiveHelp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(AccountActivity.this, IncidentMapActivity.class);
				startActivity(i);
			}
		});
        
        mBtnGetHelp = (Button) findViewById(R.id.btn_get);
        mBtnGetHelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(AccountActivity.this, GetHelpActivity.class);
				startActivity(i);
			}
		});
    }
    
    public static List<Skill> generateSkillsList() {
    	ArrayList<Skill> list = new ArrayList<Skill>();
    	for( String name : Skill.SKILLS_LIST) {
    		Skill s = new Skill(name);
    		list.add(s);
    	}
    	return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
    	int id = item.getItemId();
    	if (id == R.id.action_settings) {
    		LocalRequestStore.clearRequests(this);
    		return true;
    	}
    	return false;
    }
    
}
