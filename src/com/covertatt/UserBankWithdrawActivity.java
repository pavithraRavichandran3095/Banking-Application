package com.covertatt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserBankWithdrawActivity extends Activity
{
	String username="",accno="";
	EditText userwithd;
	TextView logout,usernametext,home;
	ImageView withdimg;
	
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.userbankwithd);
	        Bundle extras=this.getIntent().getExtras();
	        username=extras.getString("username");
	        accno=extras.getString("accno");
	        logout=(TextView)findViewById(R.id.userbankwithlogout);
	        usernametext=(TextView)findViewById(R.id.userbankdepuser);
	        home=(TextView)findViewById(R.id.userbankwithdhome);
	        userwithd=(EditText)findViewById(R.id.userwithamount);
	        withdimg=(ImageView)findViewById(R.id.userbankwithsub);
	        usernametext.setText(username);
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
	        withdimg.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		try
	        		{
	        			if(!userwithd.getText().toString().trim().equals(""))
	        			{
		        			HttpClient httpClient = new DefaultHttpClient();
							HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"BankServer?hidden=bankwithdraw&username="+username.trim()+"&accno="+accno+"&amnt="+userwithd.getText().toString().trim());
							
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
							String spl[]=value.split("\\-");
							if(spl[0].trim().equals("Success"))
							{
								try
						 		{
									String MY_URL = FstIpAddress.ipaddstr+"epay/"+username+".png";
									URL url = new URL(MY_URL);
									HttpURLConnection c = (HttpURLConnection) url.openConnection();
									c.setRequestMethod("GET");
									c.setDoOutput(true);
									c.connect();
								
									String PATH=Environment.getExternalStorageDirectory()+"/CovertFiles/";
									File file = new File(PATH);
									File outputFile = new File(file,spl[1]+".png");
									FileOutputStream fos = new FileOutputStream(outputFile);
									InputStream iss = c.getInputStream();
									byte[] buffer = new byte[1024];
									int len1 = 0;
									while ((len1 = iss.read(buffer)) != -1)
									{
										fos.write(buffer, 0, len1);
									}
									fos.flush();
									fos.close();
									iss.close();
						       	}
								catch (Exception e)
								{
									Toast.makeText(UserBankWithdrawActivity.this, "Download=>"+e, Toast.LENGTH_LONG).show();
								}
								
								Intent i=new Intent(getApplicationContext(),UserBankWithSuccess.class);
				        		i.putExtra("username",username);
				        		i.putExtra("accno",accno);
				        		i.putExtra("status","");
				        		i.putExtra("statusw","suc");
				        		i.putExtra("statusfund","");
				        		startActivity(i);
							}
							else
							{
								Intent i=new Intent(getApplicationContext(),UserBankServiceActivity.class);
				        		i.putExtra("username",username);
				        		i.putExtra("accno",accno);
				        		i.putExtra("status","");
				        		i.putExtra("statusw","err");
				        		i.putExtra("statusfund","");
				        		startActivity(i);
							}
	        			}
	        		}
		    		catch(Exception e)
		    		{
		    			Toast.makeText(getApplicationContext(),"UserBankWith Activity->"+e,Toast.LENGTH_LONG).show();
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
			Toast.makeText(getApplicationContext(),"UserBankWith Activity->"+e,Toast.LENGTH_LONG).show();
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