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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	EditText usertext,passtext;
	ImageView submit,home;
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.loginactivity);
			usertext=(EditText) findViewById(R.id.logactusertext);
			passtext=(EditText) findViewById(R.id.logactpasstext);
			submit=(ImageView) findViewById(R.id.subimg);
			home=(ImageView) findViewById(R.id.homeimg);
			
			home.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					Intent i=new Intent(getApplicationContext(),LoginRegisActivity.class);
					i.putExtra("regis","");
					startActivity(i);
				}
			});
			
			submit.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					try
					{
						if(!usertext.getText().toString().equals("") && !passtext.getText().toString().equals(""))
		        		{
							TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			    	        String imeid=telephonyManager.getDeviceId();
							SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				    		HttpClient httpClient = new DefaultHttpClient();
							HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"Register?hidden=loginNameCheck&username="+usertext.getText().toString().trim()+"&password="+passtext.getText().toString().trim()+"&imei="+imeid.trim());
							
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
							String value = new String(b).replace("\n","").trim();
							String spl[]=value.split("\\=");
							if(spl[0].equalsIgnoreCase("Success"))
							{
								Intent i=new Intent(getApplicationContext(),UserActivity.class);
								i.putExtra("username",usertext.getText().toString());
								i.putExtra("accno",spl[1]);
								startActivity(i);
							}
							else
							{
								Toast.makeText(getApplicationContext(),"Login Failed !",Toast.LENGTH_SHORT).show();
								passtext.setText("");
							}
		        		}
					}
					catch (Exception e)
					{
						Toast.makeText(getApplicationContext(),"Login Activity->"+e,Toast.LENGTH_LONG).show();
					}
				}
			});
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),"Login Activity->"+e,Toast.LENGTH_LONG).show();
		}
    }
}