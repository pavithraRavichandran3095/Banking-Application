package com.covertatt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class UserBankWithSuccess extends Activity
{
	Button but;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userbankwithsuc);
        Bundle extras=this.getIntent().getExtras();
        final String username=extras.getString("username");
        final String accno=extras.getString("accno");
        but=(Button)findViewById(R.id.button1);
        but.setOnClickListener(new View.OnClickListener()
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