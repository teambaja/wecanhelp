package com.bsrhack.responsenetwork;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class GetHelpActivity extends Activity {

	private ListView mSkillsList;
	private GetHelpAdapter mSkillsListAdapter;
	private List<Skill> mSkills;
	private Button mBtnGiveHelp;
	private Button mBtnGetHelp;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSkills = generateSkillsList();
        
        setContentView(R.layout.activity_gethelp);
        mSkillsList = (ListView) findViewById(R.id.skills_list);
        mSkillsListAdapter = new GetHelpAdapter(this);
        mSkillsList.setAdapter(mSkillsListAdapter);
        mSkillsListAdapter.setSkillsList(mSkills);
               
    }
    
    public static List<Skill> generateSkillsList() {
    	ArrayList<Skill> list = new ArrayList<Skill>();
    	for( String name : Skill.SKILLS_LIST) {
    		Skill s = new Skill(name);
    		list.add(s);
    	}
    	return list;
    }
    
}
