package com.covertatt;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;
import android.preference.PreferenceManager;

public class MainActivity extends Activity implements CollectionsDataInterface
{
	public static Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
	Random rand=new Random();
	static String Servervalue="";
	static String username="",accno="";
	DefaultHttpClient httpClient=null;
	HttpGet getRequest=null;
	HttpResponse res=null;
	InputStream is=null;
    byte[] b=null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    static Boolean bool=true;
    int ch;  
//	static Context con=null; 
//	private Button pinb0,pinb1,pinb2,pinb3,pinb4,pinb5,pinb6,pinb7,pinb8,pinb9;
//	private Button conformbutton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		con=this;
		setContentView(R.layout.activity_main);
	try
	{
		Bundle extras=this.getIntent().getExtras();
		username=extras.getString("username");
        accno=extras.getString("accno");
//        urlcall("username:"+username+"accno:"+accno);
        bool=true;
        Servervalue="";
		suffleKeypadGenereate();
		b1=(Button) findViewById(R.id.button2);
		b2=(Button) findViewById(R.id.button3);
		b3=(Button) findViewById(R.id.button4);
		b4=(Button) findViewById(R.id.button5);
		b5=(Button) findViewById(R.id.button6);
		b6=(Button) findViewById(R.id.button7);
		b7=(Button) findViewById(R.id.button8);
		b8=(Button) findViewById(R.id.button9);
		b9=(Button) findViewById(R.id.button10);
		b0=(Button) findViewById(R.id.button11);

		
		
		b1.setText(" "+String.valueOf(checkNum.get(1))+" ");
		b2.setText(" "+String.valueOf(checkNum.get(2))+" ");
		b3.setText(" "+String.valueOf(checkNum.get(3))+" ");
		b4.setText(" "+String.valueOf(checkNum.get(4))+" ");
		b5.setText(" "+String.valueOf(checkNum.get(5))+" ");
		b6.setText(" "+String.valueOf(checkNum.get(6))+" ");
		b7.setText(" "+String.valueOf(checkNum.get(7))+" ");
		b8.setText(" "+String.valueOf(checkNum.get(8))+" ");
		b9.setText(" "+String.valueOf(checkNum.get(9))+" ");
		b0.setText(" "+String.valueOf(checkNum.get(0))+" ");
		Thread t = new Thread() 
	    {
	        @Override
	        public void run() {
	            try {
	                while (!isInterrupted()) {
	                    Thread.sleep(2000);
	                    runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() 
	                        {
	                        	if(bool)
	                        	{
	                        		
	                			if(Servervalue.replace("\n","").trim().equals("Success"))
	                			{
//	                				urlcall("Inside if:"+Servervalue);	
	                				bool=false;
	                				Intent i=new Intent(getApplicationContext(),UserBankServiceActivity.class);
	                				i.putExtra("username",username);
	                				i.putExtra("accno",accno);
	                				i.putExtra("status","");
	                				i.putExtra("statusw","");
	                				i.putExtra("statusfund","");
	                				 startActivity(i);
	                				//startActivity(i);
	                				 
	                			}
	                			else if(Servervalue.replace("\n","").trim().equals("Error"))
	                			{
	                				try
	                				{
//		                				urlcall("Inside else:"+Servervalue);
		                				Intent i=new Intent(getApplicationContext(),LoginActivity.class);
		                				i.putExtra("regis","hai");
		                				startActivity(i);
		                				String lat="",lng="";
		                				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		                    			Criteria criteria = new Criteria();
		                    			String provider = locationManager.getBestProvider(criteria, true);
		                    			Location location = locationManager.getLastKnownLocation(provider);
		                    			bool=false;
		                    			if(location != null)
		                    			{
		                    				lat=String.valueOf(location.getLatitude());
		                    				lng=String.valueOf(location.getLongitude());
		                    			}
		                    			SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		                				pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		                	    		httpClient = new DefaultHttpClient();
		                    			getRequest=new HttpGet(FstIpAddress.ipaddstr+"/Register?hidden=loginfailed&username="+username+"&lati="+lat+"&longti="+lng);
		                    			
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
		                				Servervalue = new String(b);
		                				String st="";
		                				if(Servervalue.replace("\n","").trim().equalsIgnoreCase("Done"))
		                				{
		                					st="Invalid Pass Code !";
		                				}
		                				else
		                				{
		                					String spp[]=Servervalue.replace("\n","").trim().split("\\-");
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
		                				
		                			}
	                				catch (Exception e) {
//	    								urlcall("insideThread"+e);
	    							}
		                        }
	                			else if(Servervalue.equalsIgnoreCase("WrongPIN"))
	                			{
	                				Toast.makeText(getApplicationContext(), "Enter Your 4 Digit PIN Correctly", Toast.LENGTH_LONG);
	                			}
	                        }
	                        }
	                    });
	                    
	                }
	            } catch (InterruptedException e) 
	            {
	            }
	        }
	    };
	    t.start();

	}
	catch(Exception e)
	{
//		urlcall("exceptioninmain==="+e);
	}
	}

	public void suffleKeypadGenereate()
	{
		int array[]={0,1,2,3,4,5,6,7,8,9};
		try
		{
			for (int i=0; i<array.length; i++) 
			{
			    int randomPosition = rand.nextInt(array.length);
			    int temp = array[i];
			    array[i] = array[randomPosition];
			    array[randomPosition] = temp;
			}
			for (int i=0; i<array.length; i++) 
			{
				checkNum.add(array[i]);
			}
//			urlcall("thevectorvalue"+checkNum.toString());
		}
		catch(Exception e)
		{
//			urlcall("ERRORAtSuffleKeyGenerate"+e);
		}
	}
	public void PINChecking(String checkPINEntered,Context conn)
	{	
		try
		{
		if(checkPINEntered.length()==4)
		{	
    		hMac hm=new hMac();
    		
    		hm.Sourcemac1(username,checkPINEntered);
    		String mac=hm.hm;
    		//urlcall("hmacDoneSuccessfully"+mac);
    		
    		
    		//urlcall("Ipadrr:"+FstIpAddress.ipaddstr);
    		httpClient = new DefaultHttpClient();
			getRequest=new HttpGet(FstIpAddress.ipaddstr+"/Register?hidden=loginpincheck&username="+username+"&hashpin="+mac);
			
//			urlcall("urlcallsuccess");
			res= httpClient.execute(getRequest);
			is= res.getEntity().getContent();
	        byte[] b=null;
	        bos = new ByteArrayOutputStream();
	        int ch;  
			while((ch = is.read()) != -1)
			{
				bos.write(ch);
			}
			b=bos.toByteArray();
			Servervalue = new String(b);
		}
		else
		{
			checkPINEntered="";
			Servervalue="WrongPIN";
		}
		}
		catch(Exception e)
		{
//			urlcall("exceptionInpinChecking"+e);
		}
	}
//	public static void urlcall(String value)//deleted static type
//	 {
//		 try
//		 {
//			 value=value.replace(" ", "%20");
//			 URL u=new
//			 URL("http://10.0.0.17:9999/PSOEMandroid/Myservicecheck?value="+value);
//			 BufferedReader br=new BufferedReader(new
//			 InputStreamReader(u.openStream()));
//			 String str=br.readLine();
//		 }
//		 catch(Exception e)
//		 {
//		 	e.printStackTrace();
//		 }
//	 }

}
