package com.covertatt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserBankDepositActivity extends Activity
{
	String username="",accno="";
	EditText userdep;
	TextView logout,usernametext,home;
	ImageView depimg;
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.userbankdep);
	        Bundle extras=this.getIntent().getExtras();
	        username=extras.getString("username");
	        accno=extras.getString("accno");
	        logout=(TextView)findViewById(R.id.userbankdeplogout);
	        usernametext=(TextView)findViewById(R.id.userbankdepuser);
	        userdep=(EditText)findViewById(R.id.userdepactamount);
	        depimg=(ImageView)findViewById(R.id.userbankdepsub);
	        usernametext.setText(username);
	        home=(TextView)findViewById(R.id.userbankdephome);
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
	        depimg.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		try
	        		{
	        			if(!userdep.getText().toString().trim().equals(""))
	        			{
		        			SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				    		HttpClient httpClient = new DefaultHttpClient();
							HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"BankServer?hidden=bankdeposit&username="+username.trim()+"&accno="+userdep.getText().toString().trim());
							
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
							if(value.equals("Success"))
							{
								Intent i=new Intent(getApplicationContext(),UserBankServiceActivity.class);
				        		i.putExtra("username",username);
				        		i.putExtra("accno",accno);
				        		i.putExtra("status","suc");
				        		i.putExtra("statusw","");
				        		i.putExtra("statusfund","");
				        		startActivity(i);
							}
							else
							{
								Intent i=new Intent(getApplicationContext(),UserBankServiceActivity.class);
				        		i.putExtra("username",username);
				        		i.putExtra("accno",accno);
				        		i.putExtra("status","err");
				        		i.putExtra("statusw","");
				        		i.putExtra("statusfund","");
				        		startActivity(i);
							}
	        			}
	        		}
		    		catch(Exception e)
		    		{
		    			Toast.makeText(getApplicationContext(),"UserBankDep Activity->"+e,Toast.LENGTH_LONG).show();
		    		}
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
			Toast.makeText(getApplicationContext(),"UserBankDep Activity->"+e,Toast.LENGTH_LONG).show();
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