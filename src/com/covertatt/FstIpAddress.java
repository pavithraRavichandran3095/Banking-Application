package com.covertatt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FstIpAddress extends Activity
{
	EditText ipaddtext;
	Button sub;
	public static String ipaddstr="";
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fstipxml);
        ipaddtext=(EditText)findViewById(R.id.fstipeditipid);
        sub=(Button)findViewById(R.id.fstipsubid);
        
        sub.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		ipaddstr=ipaddtext.getText().toString().trim();        		
        		if(ipaddstr.equalsIgnoreCase(""))
        		{
        			Toast.makeText(getApplicationContext(),"Give your System Ip Address",Toast.LENGTH_LONG).show();
        		}
        		else
        		{
        			ipaddstr="";
        			ipaddstr="http://"+ipaddtext.getText().toString().trim()+":9999/Covert-Server/";
        			Intent i=new Intent(getApplicationContext(),CovertAttentionalActivity.class);
					startActivity(i);
        		}
			}
		});
    }
}