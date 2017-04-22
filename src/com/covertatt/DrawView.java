package com.covertatt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import org.apache.commons.lang.ArrayUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class DrawView extends View implements OnTouchListener
{
	Vector vecArra[]=new Vector[6];
    Vector vecTwinArra[]=new Vector[13];
    Vector vrow1=new Vector();
    Vector vrow2=new Vector();
    Vector vrow3=new Vector();
    Vector vcol1=new Vector();
    Vector vcol2=new Vector();
    Vector vcol3=new Vector();
    Vector vTwinFrow1=new Vector();
    Vector vTwinFrow2=new Vector();
    Vector vTwinFrow3=new Vector();
    Vector vTwinLrow1=new Vector();
    Vector vTwinLrow2=new Vector();
    Vector vTwinLrow3=new Vector();
    Vector vTwinFcol1=new Vector();
    Vector vTwinFcol2=new Vector();
    Vector vTwinFcol3=new Vector();
    Vector vTwinLcol1=new Vector();
    Vector vTwinLcol2=new Vector();
    Vector vTwinLcol3=new Vector();

    Vector vTwinMcol2=new Vector();
    Vector vw=new Vector();
    Vector vb=new Vector();
    TreeSet fullVec=new TreeSet();
    TreeMap map=new TreeMap();
    TreeMap TMANdroid=new TreeMap();
    Paint paint = new Paint();
    ArrayList<Rect> rectposList=new ArrayList<Rect>();
    Canvas canvas;
    int width,height;
    int cou=5,cou1=5;
    Context context;
    String username="",accno="";
    public DrawView(Context context,int w,int h,String use,String ac)
    {
    	super(context);
    	this.context=context;
    	width=w;
    	height=h;
    	username=use;
    	accno=ac;
    	numbersGen();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
    	try
    	{
    		this.canvas=canvas;
    		genKeys(cou,cou1);
		}
    	catch(Exception e)
    	{
    		Toast.makeText(getContext(),"Draw View "+e.toString(),Toast.LENGTH_SHORT).show();
		}
    }
    
    public void genKeys(int co,int co1)
    {
    	try
    	{
	    	FrstCal(co,co1);
	    	if(map.size()==1)
    		{
	    		String key=map.toString().replace("{","").replace("}","").trim().split("\\=")[0];
	        	PinActivity.v.add(key);
	    		Intent i=new Intent(context,PinActivity.class);
	    		i.putExtra("username",username);
	    		i.putExtra("accno",accno);
				super.getContext().startActivity(i);
    		}
	    	else
	    	{
		    	int lt=180;
		    	int tp=520;
		    	int rt=190;
		    	int bm=570;
		    	
		    	for(int i=0;i<PinActivity.v.size()+1;i++)
		    	{
		    		paint.setColor(Color.rgb(188,3,89));
			        paint.setStrokeWidth(3);
			        canvas.drawRect(lt,tp,rt,bm,paint);
			        lt=lt+30;
			        rt=rt+30;
		    	}
		    	
		    	TMANdroid.put(10,"Black");
		    	TMANdroid.put(11,"White");
		    	int left=30;
		    	int top=30;
		    	int right=80;
		    	int bottom=80;
		    	int count=0;
		    	for(int i=0;i<=3;i++)
		    	{
		    		left=60;
		    		right=120;
		    		
		    		for(int j=0;j<=2;j++)
		    		{
		    			Rect r=new Rect();
		    			r.set(left, top, right, bottom);
		    			
		    			count++;
		    			String color="";
		    			if(count<=9)
		    			{
			    			color=TMANdroid.get(count).toString();
			    			if(color.equalsIgnoreCase("White"))
			    			{
			    				paint.setColor(Color.WHITE);
			    			}
			    			else
			    			{
			    				paint.setColor(Color.DKGRAY);
			    			}
			    			
			    			paint.setStrokeWidth(4);
			    			canvas.drawRect(r, paint);
			    			if(color.equalsIgnoreCase("White"))
			    			{
			    				paint.setColor(Color.BLACK);
			    			}
			    			else
			    			{
			    				paint.setColor(Color.WHITE);
			    			}
		    			}
		    			paint.setTextSize(20);
		    			if(i==3)
		    			{
		    				if(j==0)
		    				{
		    					setFocusable(true);
		    			        setFocusableInTouchMode(true);
		    					paint.setColor(Color.DKGRAY);
		    					canvas.drawRect(r, paint);
		    					paint.setTextSize(14);
		    					paint.setColor(Color.WHITE);
								canvas.drawText("Black", left+7, top+30, paint);
								rectposList.add(r);
							}
		    			
		    				if(j==1)
		    				{
		    					color=TMANdroid.get(0).toString();
		    	    			if(color.equalsIgnoreCase("White"))
		    	    			{
		    	    				paint.setColor(Color.WHITE);
		    	    			}
		    	    			else
		    	    			{
		    	    				paint.setColor(Color.DKGRAY);
		    	    			}
		    	    			
		    					canvas.drawRect(r, paint);
		    					if(color.equalsIgnoreCase("White"))
		    	    			{
		    	    				paint.setColor(Color.BLACK);
		    	    			}
		    	    			else
		    	    			{
		    	    				paint.setColor(Color.WHITE);
		    	    			}
		    					paint.setTextSize(20);
		    					canvas.drawText("0", left+20, top+30, paint);
		    				}
		        			
		    				if(j==2)
		    				{
		    					setFocusable(true);
		    			        setFocusableInTouchMode(true);
		    					paint.setColor(Color.WHITE);
		    					canvas.drawRect(r, paint);
		    					paint.setTextSize(14);
		    					paint.setColor(Color.BLACK);
		    					paint.setTextSize(14);
		    					canvas.drawText("White", left+7, top+30, paint);
		    					rectposList.add(r);
							}
						}
		    			else
		    			{
		    				canvas.drawText(String.valueOf(count), left+20, top+30, paint);
		    			}
		    			left=left+140;
		    			right+=140;
		    		}
		    		bottom+=120;
		    		top+=120;
		    	}
	    	}	    	
	    	setOnTouchListener(this);
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(getContext(),"Exception "+e,Toast.LENGTH_SHORT).show();
		}
    }
    
    public boolean onTouch(View v, MotionEvent event)
    {
    	try
    	{
    		int touchX = (int)event.getX();
	    	int touchY = (int)event.getY();
	    	int action=event.getAction();
	    	switch(action)
			{
				case MotionEvent.ACTION_DOWN:
					int i=0;
					for(Rect rect : rectposList)
					{
						i++;
		                if(rect.contains(touchX,touchY))
		                {
		                	if(i==1)
		                	{
		                		rectposList=new ArrayList<Rect>();
		                		blackWhite("Black");
		                		invalidate();
		                	}
		                	else
		                	{
		                		rectposList=new ArrayList<Rect>();
		                		blackWhite("White");
		                		invalidate();
		                	}
		                }
					}
		            break;
		    }
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(getContext(),"Exception "+e,Toast.LENGTH_SHORT).show();
		}
	    return false;
    }
    
    public void FrstCal(int by,int by1) throws Exception
    {
        Vector vvec=new Vector();
        vvec.addAll(fullVec);
        Vector vvv=new Vector();
        Vector vvvv=new Vector();
        while(vvv.size()<by)
        {
            while(true)
            {
                int s=fullVec.size();
                int rand=new Random().nextInt(s);
                int f=(Integer)vvec.get(rand);
                if(!vvv.contains(f))
                {
                    vvv.add(f);
                    break;
                }
            }
        }

        while(vvvv.size()<by1)
        {
            int s=fullVec.size();
            int rand=new Random().nextInt(s);
            int f=(Integer)vvec.get(rand);
            if(!vvv.contains(f))
            {
                if(!vvvv.contains(f))
                {
                    vvvv.add(f);
                    map.put(f,"White");
                }
            }
        }

        for(int jj=0;jj<vvv.size();jj++)
        {
            map.put(vvv.get(jj),"Black");
        }
        MapForAndroid(map,vvv,vvvv);
    }
    
    public void MapForAndroid(TreeMap map,Vector BC,Vector WC)
    {
          TMANdroid=new TreeMap();
          Vector vAL=new Vector();
          vAL.add("White");
          vAL.add("Black");
          int j=0;
          
        while(BC.size()<5)
        {
            while(true)
            {
                int rand=new Random().nextInt(10);
                if(!BC.contains(rand))
                {
                	if(!WC.contains(rand))
                	{
                		BC.add(rand);
                		break;
                	}
                }
            }
        }

        while(WC.size()<5)
        {
            int rand=new Random().nextInt(10);
            if(!BC.contains(rand))
            {
                if(!WC.contains(rand))
                {
                    WC.add(rand);
                }
            }
        }
        
        for(int jj=0;jj<BC.size();jj++)
        {
            TMANdroid.put(BC.get(jj),"Black");
        }
        for(int jj=0;jj<WC.size();jj++)
        {
            TMANdroid.put(WC.get(jj),"White");
        }
    }
    
    public void vbvw()
    {
        Iterator it=map.keySet().iterator();
        while(it.hasNext())
        {
            int key=Integer.parseInt(it.next().toString());
            String value=map.get(key).toString();
            if(value.equalsIgnoreCase("White"))
            {
                vw.add(key);
            }
            else
            {
                vb.add(key);
            }
        }
    }
    
    public void blackWhite(String cond)
    {
        try
        {
            vbvw();
            if(map.size()<=3)
            {
                Nomatch(cond);
            }
            else
            {
                Vector ve;
                if(cond.equalsIgnoreCase("Black"))
                {
                    ve=vw;
                }
                else
                {
                    ve=vb;
                }
                map.clear();
                boolean j=true;
                int temp=0;
                Vector v = new Vector();
                for(int i=0;i<vecArra.length;i++)
                {
                    if(ve.containsAll(vecArra[i]))
                    {
                        for(int jj=0;jj<vecArra[i].size();jj++)
                        {
                            for(int jk=0;jk<vecArra.length;jk++)
                            {
                                if(jk!=i)
                                {
                                    if(vecArra[jk].contains(vecArra[i].get(jj)))
                                    {
                                        vecArra[jk].remove(vecArra[i].get(jj));
                                    }
                                }
                            }
                        }
                        v.add(vecArra[i]);
                        j=false;
                    }
            	}
                if(j)
                {
                    for(int i=0;i<vecTwinArra.length;i++)
                    {
                        if(ve.containsAll(vecTwinArra[i]))
                        {
                            for(int jj=0;jj<vecTwinArra[i].size();jj++)
                            {
                                for(int jk=0;jk<vecArra.length;jk++)
                                {
                                	if(vecArra[jk].contains(vecTwinArra[i].get(jj)))
                                    {
                                		vecArra[jk].remove(vecTwinArra[i].get(jj));
                                    }
                                }
                            }
                            v.add(vecTwinArra[i]);
                            j=false;
                        }
                     }
                }
                if(j)
                {
                    Nomatch(cond);
                }
                com(v);

                vw.clear();
                vb.clear();
            }
        }
        catch(Exception e)
        {
        	Toast.makeText(getContext(),"Exception "+e,Toast.LENGTH_SHORT).show();
        }
    }
    
    public void Nomatch(String cond) throws Exception
    {
    	Vector v=new Vector();
        if(cond.equalsIgnoreCase("White"))
        {
        	v.add(vb);
             com(v);
        }
        else
        {
        	v.add(vw);
            com(v);
        }
        vw.clear();
        vb.clear();
     }
    
    public void com(Vector vvv1) throws Exception
    {
        for(int i=0;i<vvv1.size();i++)
        {
            vecArra = (Vector[])ArrayUtils.removeElement(vecArra,vvv1.get(i));
            fullVec.removeAll((Vector)vvv1.get(i));
        }
        map.clear();
        Vector vvec=new Vector();
        vvec.addAll(fullVec);
        int si=fullVec.size();
        double d=Math.ceil((double)si/2);
        int by=Integer.parseInt(String.valueOf(d).split("\\.")[0]);
        int by1=0;
        if(fullVec.size()%2==0)
        {
            by1=by;
        }
        else
        {
            by1=by-1;
        }

        Vector vvv=new Vector();
        Vector vvvv=new Vector();
        cou=by;
        cou1=by1;
    }
    
    public void numbersGen()
    {
    	 try
         {
             vrow1.add(1);
             vrow1.add(2);
             vrow1.add(3);

             vrow2.add(4);
             vrow2.add(5);
             vrow2.add(6);

             vrow3.add(7);
             vrow3.add(8);
             vrow3.add(9);

             vcol1.add(1);
             vcol1.add(4);
             vcol1.add(7);

             vcol2.add(2);
             vcol2.add(5);
             vcol2.add(8);
             vcol2.add(0);

             vcol3.add(3);
             vcol3.add(6);
             vcol3.add(9);

             fullVec.addAll(vrow1);
             fullVec.addAll(vrow2);
             fullVec.addAll(vrow3);
             fullVec.addAll(vcol1);
             fullVec.addAll(vcol2);
             fullVec.addAll(vcol3);

             vecArra[0]=vrow1;
             vecArra[1]=vrow2;
             vecArra[2]=vrow3;
             vecArra[3]=vcol1;
             vecArra[4]=vcol2;
             vecArra[5]=vcol3;

             vTwinFrow1.add(1);
             vTwinFrow1.add(2);
             vTwinLrow1.add(2);
             vTwinLrow1.add(3);

             vTwinFrow2.add(4);
             vTwinFrow2.add(5);
             vTwinLrow2.add(5);
             vTwinLrow2.add(6);

             vTwinFrow3.add(7);
             vTwinFrow3.add(8);
             vTwinLrow3.add(8);
             vTwinLrow3.add(9);

             vTwinFcol1.add(1);
             vTwinFcol1.add(4);
             vTwinLcol1.add(4);
             vTwinLcol1.add(7);

             vTwinFcol2.add(2);
             vTwinFcol2.add(5);
             vTwinMcol2.add(5);
             vTwinMcol2.add(8);
             vTwinLcol2.add(8);
             vTwinLcol2.add(0);

             vTwinFcol3.add(3);
             vTwinFcol3.add(6);
             vTwinLcol3.add(6);
             vTwinLcol3.add(9);

             vecTwinArra[0]=vTwinFrow1;
             vecTwinArra[1]=vTwinFrow2;
             vecTwinArra[2]=vTwinFrow3;
             vecTwinArra[3]=vTwinLrow1;
             vecTwinArra[4]=vTwinLrow2;
             vecTwinArra[5]=vTwinLrow3;
             vecTwinArra[6]=vTwinFcol1;
             vecTwinArra[7]=vTwinFcol2;
             vecTwinArra[8]=vTwinFcol3;
             vecTwinArra[9]=vTwinLcol1;
             vecTwinArra[10]=vTwinMcol2;
             vecTwinArra[11]=vTwinLcol3;
             vecTwinArra[12]=vTwinLcol2;
         }
         catch(Exception e)
         {
        	 Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
         }
    }
}