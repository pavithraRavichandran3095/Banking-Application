package com.covertatt;

import java.io.File;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserBankListView extends Activity
{
	String username,accno;
	TextView nativetext;
	
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.userbanklistview);
			Bundle extras=this.getIntent().getExtras();
	        username=extras.getString("username");
	        accno=extras.getString("accno");
	        nativetext=(TextView)findViewById(R.id.userlistnativetext);
	        
	        nativetext.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		Intent i=new Intent(getApplicationContext(),UserBankServiceActivity.class);
	        		i.putExtra("username",username);
	        		i.putExtra("accno",accno);
	        		i.putExtra("status","");
	        		i.putExtra("statusw","");
	        		i.putExtra("statusfund","");
	        		startActivity(i);
				}
			});
	        
			ArrayAdapter<String> holder = new ArrayAdapter<String>(this,R.layout.textvv);
			String dir=Environment.getExternalStorageDirectory()+"/CovertFiles";
			File f=new File(dir);
			File farr[]=f.listFiles();
			for(int i=0;i<farr.length;i++)
			{
				holder.add(farr[i].getName().toString());
			}
			ListView apps=(ListView) findViewById(R.id.listviewid);
			apps.setAdapter(holder);
			
			apps.setOnItemClickListener(new AdapterView.OnItemClickListener()
	        {
	            public void onItemClick(AdapterView<?> parent, final View view,int position, long id)
	            {
	            	final String item=(String) parent.getItemAtPosition(position);
	            	Intent i=new Intent(getApplicationContext(),UserImageView.class);
					i.putExtra("image",item);
					i.putExtra("username",username);
	        		i.putExtra("accno",accno);
					startActivity(i);
	            }
	        });
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(),"UserBankListView Activity->"+e,Toast.LENGTH_LONG).show();
		}
    }
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
         if (keyCode == KeyEvent.KEYCODE_BACK)
         {
        	 return true;
         }
         return false;
    }
}