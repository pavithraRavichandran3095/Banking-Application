package com.covertatt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends Activity
{
	TextView logout;
	String username="",accno="";
	ImageButton bookserBut,bankserBut;
	RadioGroup radiotypes;
	String selectedRadioValue;
	String ucheck="";
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useractivity);
        Bundle extras=this.getIntent().getExtras();
        username=extras.getString("username");
        accno=extras.getString("accno");
        radiotypes=(RadioGroup)findViewById(R.id.radiogroupid);
        logout=(TextView)findViewById(R.id.useractlogout);
        bankserBut=(ImageButton)findViewById(R.id.bankserbut);        
        ucheck=CovertAttentionalActivity.db.getPin(username);
        bankserBut.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		selectedRadioValue=((RadioButton)findViewById(radiotypes.getCheckedRadioButtonId())).getText().toString();
        		
    			if(selectedRadioValue.equalsIgnoreCase("Improved IOC Method"))
        		{
        			Intent i=new Intent(getApplicationContext(),ProposePinActivity.class);
	        		i.putExtra("username",username);
	        		i.putExtra("accno",accno);
	        		i.putExtra("ucheck",ucheck);
	        		startActivity(i);
        		}
    			else if(selectedRadioValue.equalsIgnoreCase("StegnoPIN Method"))
        		{
        			Intent i=new Intent(getApplicationContext(),MainActivity.class);
	        		i.putExtra("username",username);
	        		i.putExtra("accno",accno);
	        		i.putExtra("ucheck",ucheck);
	        		startActivity(i);
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