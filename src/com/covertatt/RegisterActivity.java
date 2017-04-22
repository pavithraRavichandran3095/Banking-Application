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
import android.widget.Toast;

public class RegisterActivity extends Activity
{
	EditText usertext,mobtext,mailtext,passtext,altmobtext;
	Button submit;
	String imeid="";
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.register);
	        
	        usertext=(EditText) findViewById(R.id.regusertext);
	        passtext=(EditText) findViewById(R.id.regpasstext);
	        mobtext=(EditText) findViewById(R.id.regphonetext);
	        altmobtext=(EditText) findViewById(R.id.alternatemobid);
	        mailtext=(EditText) findViewById(R.id.regmailtext);
	        submit=(Button) findViewById(R.id.regbut);
	        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	        imeid=telephonyManager.getDeviceId();
			
	        submit.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		if(usertext.getText().toString().equals("") || passtext.getText().toString().equals("") || mobtext.getText().toString().equals("") || mailtext.getText().toString().equals("") || altmobtext.getText().toString().equals(""))
	        		{
	        			Toast.makeText(getApplicationContext(),"Fill all the details !",Toast.LENGTH_SHORT).show();
	        		}
	        		else
					if(String.valueOf(mobtext.getText()).equals("0000000000") || String.valueOf(mobtext.getText()).length()>10 || String.valueOf(mobtext.getText()).length()<10)
					{
						Toast.makeText(getApplicationContext(),"Give valid Mobile Number !",Toast.LENGTH_SHORT).show();
					}
					else
					if(String.valueOf(altmobtext.getText()).equals("0000000000") || String.valueOf(altmobtext.getText()).length()>10 || String.valueOf(altmobtext.getText()).length()<10)
					{
						Toast.makeText(getApplicationContext(),"Alternate Mobile Number should be valid!",Toast.LENGTH_SHORT).show();
					}
					else
					if(!mailtext.getText().toString().contains("@") || !mailtext.getText().toString().contains("."))
					{
						Toast.makeText(getApplicationContext(),"Give valid Mail Address !",Toast.LENGTH_SHORT).show();
					}
					else
					{
						try
						{
							SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				    		HttpClient httpClient = new DefaultHttpClient();
			    			HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"Register?hidden=register&username="+usertext.getText().toString().trim()+"&password="+passtext.getText().toString().trim()+"&phone="+mobtext.getText().toString().trim()+"&altphone="+altmobtext.getText().toString().trim()+"&mailid="+mailtext.getText().toString().trim()+"&imei="+imeid);
			    			
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
							String spl[]=value.split("\\=");
							if(spl[0].replace("\n","").trim().equalsIgnoreCase("Success"))
							{
								CovertAttentionalActivity.db.addTable(usertext.getText().toString().trim(),spl[1].replace("\n","").trim(),spl[2].replace("\n","").trim(),spl[3].replace("\n","").trim());
								String str=CovertAttentionalActivity.db.getRecord(usertext.getText().toString().trim());
								Intent i=new Intent(getApplicationContext(),LoginRegisActivity.class);
								i.putExtra("regis","Registered Successfully");
								startActivity(i);
							}
							else
							{
								if(spl[1].replace("\n","").trim().equalsIgnoreCase("SameMob"))
								{
									Toast.makeText(getApplicationContext(),"Registration Failed! Already you have registered in this device",Toast.LENGTH_LONG).show();
								}
								else
								if(spl[1].replace("\n","").trim().equalsIgnoreCase("SameName"))								
								{
									Toast.makeText(getApplicationContext(),"Registration Failed! Try a different name",Toast.LENGTH_LONG).show();
								}								
							}
						}
						catch(Exception e)
						{
							Toast.makeText(getApplicationContext(),"Register Activity->"+e,Toast.LENGTH_LONG).show();
						}
					}
	        	}
			});
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),"Register Activity->"+e,Toast.LENGTH_LONG).show();
		}
    }
}