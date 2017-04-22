package com.covertatt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserImageView extends Activity
{
	ImageView img;
	ImageView sendimg;
	String imgname;
	EditText acctext;
	String username="",accno="";
	File file;
	TextView nativetext;
	public void onCreate(Bundle savedInstanceState)
    {
		try
		{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.userlistimageview);
	        img=(ImageView)findViewById(R.id.eslipimg);
	        sendimg=(ImageView)findViewById(R.id.usersendimgeslip);
	        acctext=(EditText)findViewById(R.id.acctTextid);
	        Bundle extras=this.getIntent().getExtras();
	        imgname=extras.getString("image");
	        username=extras.getString("username");
	        accno=extras.getString("accno");	        
	        String PATH=Environment.getExternalStorageDirectory()+"/CovertFiles/"+imgname;
	        file=new File(PATH);
	        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			img.setImageBitmap(myBitmap);
			nativetext=(TextView)findViewById(R.id.userlistnativetextimg);
			
	        nativetext.setOnClickListener(new View.OnClickListener()
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
			
			sendimg.setOnClickListener(new View.OnClickListener()
	        {
	        	public void onClick(View v)
	        	{
	        		try
	        		{
	        			if(!acctext.getText().toString().trim().equals(""))
	        			{
			        		HttpClient httpClient = new DefaultHttpClient();
							HttpGet getRequest=new HttpGet(FstIpAddress.ipaddstr+"BankServer?hidden=banksendmail&transaccno="+acctext.getText().toString().trim()+"&transid="+imgname+"&username="+username);
							
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
								file.delete();
								Intent i=new Intent(getApplicationContext(),UserBankServiceActivity.class);
								i.putExtra("username",username);
				        		i.putExtra("accno",accno);
				        		i.putExtra("status","");
				        		i.putExtra("statusw","");
				        		i.putExtra("statusfund","");
				        		startActivity(i);
							}
	        			}
	        		}
		    		catch(Exception e)
		    		{
		    			Toast.makeText(getApplicationContext(),"UserImageView Activity->"+e,Toast.LENGTH_LONG).show();
		    		}
				}
			});
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(),"UserImageView Activity->"+e,Toast.LENGTH_LONG).show();
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