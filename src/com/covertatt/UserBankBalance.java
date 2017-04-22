package com.covertatt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserBankBalance extends Activity
{
	TextView logout,baltext,home;
	String bal="",username="",accno="";
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.userbankbal);
			Bundle extras=this.getIntent().getExtras();
	        bal=extras.getString("bal");
	        username=extras.getString("username");
	        accno=extras.getString("accno");
			logout=(TextView)findViewById(R.id.userbankballogout);
			baltext=(TextView)findViewById(R.id.userbaltext);
			home=(TextView)findViewById(R.id.userbankbalhome);
			baltext.setText("Rs."+bal+"/-");
			
			home.setOnClickListener(new View.OnClickListener()
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
			
			logout.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		Intent i=new Intent(getApplicationContext(),LoginRegisActivity.class);
	        		i.putExtra("regis","log");
					startActivity(i);
				}
			});
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(),"UserBankBalance Activity->"+e,Toast.LENGTH_LONG).show();
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