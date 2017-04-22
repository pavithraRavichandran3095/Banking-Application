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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserBankServiceActivity extends Activity
{
	TextView logout,textstatdep,textstatwith,textstatfund;
	String username="",accno="";
	ImageButton depimg,withimg,fundimg,listbut,balance;
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.userbankactivity);
			Bundle extras=this.getIntent().getExtras();
	        username=extras.getString("username");
	        accno=extras.getString("accno");
	        String status=extras.getString("status");
	        String statusw=extras.getString("statusw");
	        String statusfund=extras.getString("statusfund");
			logout=(TextView)findViewById(R.id.userbankactlogout);
			depimg=(ImageButton)findViewById(R.id.userbankactdeposit);
			withimg=(ImageButton)findViewById(R.id.userbankactwith);
			fundimg=(ImageButton)findViewById(R.id.userbankacttrans);
			listbut=(ImageButton)findViewById(R.id.userbankactlist);
			balance=(ImageButton)findViewById(R.id.userbankactmoney);
			textstatdep=(TextView)findViewById(R.id.userbankactdepsuc);
			textstatwith=(TextView)findViewById(R.id.userbankactwithdsuc);
			textstatfund=(TextView)findViewById(R.id.userbankactfundsuc);
			
			textstatdep.setText("");
			textstatwith.setText("");
			textstatfund.setText("");
			
			if(status.equals("suc"))
	        {
				textstatdep.setText("Deposited Successfully");
	        }
			else
			if(status.equals("err"))
			{
				textstatdep.setText("Process Failed");
			}

			if(statusw.equals("suc"))
	        {
				textstatwith.setText("Debited Successfully");
	        }
			else
			if(statusw.equals("err"))
			{
				textstatwith.setText("Process Failed");
			}
			
			if(statusfund.equals("suc"))
	        {
				textstatfund.setText("Transaction Successfull");
	        }
			else
			if(statusfund.equals("err"))
			{
				textstatfund.setText("Transaction Failed");
			}
			
			depimg.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		Intent i=new Intent(getApplicationContext(),UserBankDepositActivity.class);
	        		i.putExtra("username",username);
	        		i.putExtra("accno",accno);
	        		startActivity(i);
				}
			});
			
			withimg.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		Intent i=new Intent(getApplicationContext(),UserBankWithdrawActivity.class);
	        		i.putExtra("username",username);
	        		i.putExtra("accno",accno);
	        		startActivity(i);
				}
			});
			
			fundimg.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		Intent i=new Intent(getApplicationContext(),UserBankFundActivity.class);
	        		i.putExtra("username",username);
	        		i.putExtra("accno",accno);
	        		startActivity(i);        		
	        	}
			});
			
			listbut.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		Intent i=new Intent(getApplicationContext(),UserBankListView.class);
	        		i.putExtra("username",username);
	        		i.putExtra("accno",accno);
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
			
			balance.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		try
	        		{
		        		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			    		HttpClient httpClient = new DefaultHttpClient();
						HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"BankServer?hidden=balCheck&username="+username.trim()+"&accno="+accno);
						
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
						String value = new String(b).trim();
						String spl[]=value.split("\\=");
						if(spl[0].replace("\n","").trim().equals("Success"));
						{
							Intent i=new Intent(getApplicationContext(),UserBankBalance.class);
							i.putExtra("bal",spl[1]);
							i.putExtra("username",username);
							i.putExtra("accno",accno);
							startActivity(i);
						}		        		
	        		}
	        		catch(Exception e)
	        		{
	        			Toast.makeText(getApplicationContext(),"UserBankService Activity->"+e,Toast.LENGTH_LONG).show();
	        		}
				}
			});
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(),"UserBankService Activity outside->"+e,Toast.LENGTH_LONG).show();
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