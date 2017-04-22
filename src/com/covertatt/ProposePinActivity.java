package com.covertatt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
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
import android.widget.Toast;

public class ProposePinActivity extends Activity
{
	ProposedDrawView drawView;
	static Vector vproposepin= new Vector();
	String username="",accno="",ucheck="";
	String lat="",lng="";
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
	        super.onCreate(savedInstanceState);
	        Bundle extras=this.getIntent().getExtras();
	        username=extras.getString("username");
	        accno=extras.getString("accno");
	        ucheck=extras.getString("ucheck");
	        Display display = getWindowManager().getDefaultDisplay();
	        int width=display.getWidth();
	        int height=display.getHeight();
	        if(vproposepin.size()<=3)
	        {
		        drawView = new ProposedDrawView(this,width,height,username,accno,ucheck);
		        drawView.setBackgroundColor(Color.BLACK);
		        setContentView(drawView);
	        }
	        else
	    	if(vproposepin.size()==4)
	        {
	    		if(ProposedDrawView.finalStatus)
	    		{
		    		Vector pinset1=new Vector((HashSet)vproposepin.get(0));
		    		Vector pinset2=new Vector((HashSet)vproposepin.get(1));
		    		Vector pinset3=new Vector((HashSet)vproposepin.get(2));
		    		Vector pinset4=new Vector((HashSet)vproposepin.get(3));
		    		
		    		Vector vv=new Vector();
		    		int tot=1,couu=0;
		    		for(int i=0;i<vproposepin.size();i++)
		    		{
		    			HashSet set=(HashSet)vproposepin.get(i);
		    			int size=set.size();
		    			vv.add(size);
		    			tot=tot*size;
		    		}
		    		
		    		Collections.sort(vv);
		    		int si=(Integer)vv.lastElement();
		    		
		    		int size1=pinset1.size();
		    		int size2=pinset2.size();
		    		int size3=pinset3.size();
		    		int size4=pinset4.size();
		    		
		    		String arr[]=new String[tot];
		    		int co=0;
		    		Vector temp=new Vector();
		    		Vector temp1=new Vector();
		    		
	    			for(int i=0;i<size1;i++)
	    			{
	    				String fir=String.valueOf(pinset1.get(i));
	    				for(int j=0;j<size2;j++)
	    				{
	    					String fir1=String.valueOf(pinset2.get(j));
	    					arr[co]=fir+fir1;
	    					co++;
	    					temp.add(fir+fir1);
	    				}
	    			}
	    			couu=co;
	    			co=0;
	    			
	    			for(int i=0;i<couu;i++)
	    			{
	    				String fir=temp.get(i).toString();
	    				for(int j=0;j<size3;j++)
	    				{
	    					String fir1=String.valueOf(pinset3.get(j));
	    					String fir2=fir+fir1;
	    					arr[co]="";
	    					arr[co]=fir2;
	    					co++;
	    					temp1.add(fir2);
	    				}
	    			}
	    			couu=co;
	    			co=0;
	    			for(int i=0;i<couu;i++)
	    			{
	    				String fir=temp1.get(i).toString();
	    				for(int j=0;j<size4;j++)
	    				{
	    					String fir1=String.valueOf(pinset4.get(j));
	    					String fir2=fir+fir1;
	    					arr[co]="";
	    					arr[co]=fir2;
	    					co++;
	    				}
	    			}
		    		Vector vfin=new Vector();
	    			for(int i=0;i<arr.length;i++)
	    			{
	    				vfin.add(arr[i]);
	    			}
	    			
		    		String strmac=CovertAttentionalActivity.db.getRecord(username);
		    		hMac hm=new hMac();
		    		boolean st=false;
		    		for(int i=0;i<vfin.size();i++)
		    		{
		    			String k=vfin.get(i).toString();
		    			hm.Sourcemac1(username,k);
		    			String mac=hm.hm;
		    			if(mac.equals(strmac))
		    			{
		    				st=true;
		    				SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    	    		HttpClient httpClient = new DefaultHttpClient();
		        			HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"Register?hidden=loginpincheck&username="+username+"&hashpin="+mac);
		        			
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
		    				break;
		    			}
		    		}
		    		if(st)
		    		{
		    			Toast.makeText(getApplicationContext(), "Congratulation",Toast.LENGTH_LONG).show();
		    			Intent i=new Intent(getApplicationContext(),Temps.class);
		    			startActivity(i);
//						i.putExtra("username",username);
//						i.putExtra("accno",accno);
//						i.putExtra("status","");
//						i.putExtra("statusw","");
//						i.putExtra("statusfund","");
//						startActivity(i);
		    		}
		    		else
		    		{
		    			String value=comFail();
						String strr="";
						if(value.replace("\n","").trim().equalsIgnoreCase("Done"))
						{
							strr="Invalid Pass Code !";
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
								Toast.makeText(getApplicationContext(),"SMS faild, please try again later!",Toast.LENGTH_LONG).show();
							}
							strr="Your Account has been\nblocked !";
						}
						Intent i=new Intent(getApplicationContext(),LoginRegisActivity.class);
						i.putExtra("regis",strr);
						startActivity(i);
		    		}
		        }
	    		else
	    		{
	    			String value=comFail();
					String strr="";
					if(value.replace("\n","").trim().equalsIgnoreCase("Done"))
					{
						strr="Invalid Pass Code !";
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
						strr="Your Account has been\nblocked !";
					}
					Intent i=new Intent(getApplicationContext(),LoginRegisActivity.class);
					i.putExtra("regis",strr);
					startActivity(i);
	    		}
	    		vproposepin.clear();
	    		ProposedDrawView.finalStatus=true;
	        }
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(),"Proposed Pin Activity->"+e,Toast.LENGTH_LONG).show();
		}
    }
	
	public String comFail()
	{
		String value="";
		try
		{
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String provider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(provider);
	
			if(location != null)
			{
				lat=String.valueOf(location.getLatitude());
				lng=String.valueOf(location.getLongitude());
			}
			SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"Register?hidden=loginfailed&username="+username+"&lati="+lat+"&longti="+lng);
			
			HttpResponse res= httpClient.execute(getRequest);
			InputStream is= res.getEntity().getContent();
	        byte[] b=null;
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        int ch=0;
			while((ch = is.read()) != -1)
			{
				bos.write(ch);
			}
			b=bos.toByteArray();
			value=new String(b);
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(),"Com Fail Method Proposed Pin Activity->"+e,Toast.LENGTH_LONG).show();
		}
		return value;
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