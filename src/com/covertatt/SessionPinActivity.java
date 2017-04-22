package com.covertatt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class SessionPinActivity extends Activity
{
	int stat=0;
	SessionKeyDrawView drawView;
	public String username="",accno="";	
	public static Vector vecsym=new Vector();
	
	@Override    
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle extras=this.getIntent().getExtras();
		username=extras.getString("username");
		accno=extras.getString("accno");
		Display display = getWindowManager().getDefaultDisplay();
        int width=display.getWidth();
        int height=display.getHeight();
		if(vecsym.size()<=3)
		{
	        drawView = new SessionKeyDrawView(this,this,width,height);
		    drawView.setBackgroundColor(Color.WHITE);
		    setContentView(drawView);
		}
		else
    	if(vecsym.size()==4)
		{
    		try
    		{
    			String bytesEncoded = Base64.encodeBytes(vecsym.toString().getBytes());
	    		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    		HttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"Register?hidden=sessionloginpincheck&username="+username+"&vecsymbls="+bytesEncoded);
				
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
				if(value.replace("\n","").trim().equalsIgnoreCase("Success"))
				{
					Intent i=new Intent(getApplicationContext(),UserBankServiceActivity.class);
					i.putExtra("username",username);
					i.putExtra("accno",accno);
					i.putExtra("status","");
					i.putExtra("statusw","");
					i.putExtra("statusfund","");
					startActivity(i);
				}
				else
				{
					String lat="",lng="";
					LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    			Criteria criteria = new Criteria();
	    			String provider = locationManager.getBestProvider(criteria, true);
	    			Location location = locationManager.getLastKnownLocation(provider);

	    			if(location != null)
	    			{
	    				lat=String.valueOf(location.getLatitude());
	    				lng=String.valueOf(location.getLongitude());
	    			}
					pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    		httpClient = new DefaultHttpClient();
	    			getRequest=new HttpGet(FstIpAddress.ipaddstr+"Register?hidden=loginfailed&username="+username+"&lati="+lat+"&longti="+lng);
	    			
					res= httpClient.execute(getRequest);
					is= res.getEntity().getContent();
			        b=null;
			        bos = new ByteArrayOutputStream();
			        ch=0;
					while((ch = is.read()) != -1)
					{
						bos.write(ch);
					}
					b=bos.toByteArray();
					value = new String(b);
					String st="";
					if(value.replace("\n","").trim().equalsIgnoreCase("Done"))
					{
						st="Invalid Pass Code !";
					}
					else
					{
						String spp[]=value.replace("\n","").trim().split("\\-");
						try
						{
							SmsManager smsManager = SmsManager.getDefault();
							smsManager.sendTextMessage(spp[1], null,"Your ATM Account has been\nblocked !", null, null);
							Toast.makeText(getApplicationContext(), "SMS Sent!",Toast.LENGTH_LONG).show();
						}
						catch(Exception e)
						{
							Toast.makeText(getApplicationContext(),"SMS failed, please try again later!",Toast.LENGTH_LONG).show();
						}
						st="Your Account has been\nblocked !";
					}
					Intent i=new Intent(getApplicationContext(),LoginRegisActivity.class);
					i.putExtra("regis",st);
					startActivity(i);
				}
				vecsym.clear();
    		}
    		catch(Exception e)
    		{
    			Toast.makeText(getApplicationContext(),"Session Pin Activity->"+e,Toast.LENGTH_LONG).show();
    			Intent i=new Intent(getApplicationContext(),UserActivity.class);
				i.putExtra("username",username);
				i.putExtra("accno",accno);
				startActivity(i);
    		}
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