package com.covertatt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginRegisActivity extends Activity
{
	ImageView loginimg,regimg;
	TextView statusText;
	String status="";
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginregis);
        Bundle extras=this.getIntent().getExtras();
        status=extras.getString("regis");
        
        loginimg=(ImageView) findViewById(R.id.loginregL);
        regimg=(ImageView) findViewById(R.id.loginregR);
        statusText=(TextView) findViewById(R.id.loginregstatustext);
        statusText.setTypeface(null,Typeface.BOLD);
        
        if(status.equalsIgnoreCase(""))
        {
        	statusText.setTextSize(25.0f);
        	statusText.setText("Banking Application");
        }
        else
    	if(status.equalsIgnoreCase("log"))
        {
        	statusText.setTextSize(25.0f);
        	statusText.setText("Successfully Logged out!");
        }
    	else
		if(status.equalsIgnoreCase("logupd"))
        {
        	statusText.setTextSize(25.0f);
        	statusText.setText("Successfully Updated");
        }
        else
        {
        	statusText.setTextSize(25.0f);
        	statusText.setText(status);
        }
        
        loginimg.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		try
        		{
	        		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	    	        String imeid=telephonyManager.getDeviceId();
	        		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    		HttpClient httpClient = new DefaultHttpClient();
	    			HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"Register?hidden=loginblockcheck&imei="+imeid);
	    			
					HttpResponse res= httpClient.execute(getRequest);
					InputStream is= res.getEntity().getContent();
			        byte[] b=null;
			        ByteArrayOutputStream bos = new ByteArrayOutputStream();
			        int ch;  
					while((ch = is.read()) != -1)
					{
						bos.write(ch);
					}
					b=bos.toByteArray();
					String value = new String(b);
					String st="";
					if(value.replace("\n","").trim().equalsIgnoreCase("Blocked"))
					{
						st="Your Account has been\nblocked !";
						Intent i=new Intent(getApplicationContext(),LoginRegisActivity.class);
						i.putExtra("regis",st);
						startActivity(i);
					}
					else
					{
						Intent i=new Intent(getApplicationContext(),LoginActivity.class);
						startActivity(i);
					}	        		
        		}
        		catch(Exception e)
				{
					Toast.makeText(getApplicationContext(),"LoginRegis Activity->"+e,Toast.LENGTH_LONG).show();
				}
        	}
		});
        
        regimg.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		Intent i=new Intent(getApplicationContext(),RegisterActivity.class);
				startActivity(i);
        	}
		});
    }
}