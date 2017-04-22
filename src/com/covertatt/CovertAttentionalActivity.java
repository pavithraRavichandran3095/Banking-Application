package com.covertatt;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class CovertAttentionalActivity extends Activity
{
    ImageView imgview;
    public static SqlLiteDb db;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imgview=(ImageView)findViewById(R.id.imageView1);
        db=new SqlLiteDb(this);
        imgview.setOnClickListener(new OnClickListener()
        {
			public void onClick(View arg0)
			{
				try
				{
					String dirname = Environment.getExternalStorageDirectory()+"/CovertFiles";
					File sddir = new File(dirname);
			        sddir.mkdir();
//			        Intent i=new Intent(getApplicationContext(),SessionPinActivity.class);
//	        		i.putExtra("username","");
//	        		i.putExtra("accno","");
//	        		startActivity(i);
					Intent i=new Intent(getApplicationContext(),LoginRegisActivity.class);
					i.putExtra("regis","");
					startActivity(i);
				}
				catch(Exception e)
	    		{
	    			Toast.makeText(getApplicationContext(),"Covert Activity->"+e,Toast.LENGTH_LONG).show();
	    		}
			}
		});
    }
}