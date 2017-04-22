package com.covertatt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class SessionKeyDrawView extends View implements OnTouchListener
{
	Paint paint = new Paint();
	Vector vecfirstsymb=new Vector();
	ArrayList<Rect> rectposList=new ArrayList<Rect>();
	Canvas canvas;
	SessionPinActivity sespin;
	String updownstat="";
	TextView text[]=new TextView[10];
	TextView textsym[]=new TextView[10];
	Vector vecpos=new Vector();
	Vector vecsymbols=new Vector();
	Context context;
	int width,height;
    public SessionKeyDrawView(Context context,SessionPinActivity sespin,int wi,int hei)
    {
    	super(context);
    	this.context=context;
    	this.sespin=sespin;
    	width=wi;
    	height=hei;
    }

    @Override
    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	this.canvas=canvas;
    	
    	setFocusable(true);
        setFocusableInTouchMode(true);
    	
        paint.setColor(Color.rgb(227,200,150));
        canvas.drawPaint(paint);
    	
        vecfirstsymb.add("@");
		vecfirstsymb.add("#");
		vecfirstsymb.add("$");
		vecfirstsymb.add("%");
		vecfirstsymb.add("&");
		vecfirstsymb.add("*");
		vecfirstsymb.add("O");
		vecfirstsymb.add("+");
		vecfirstsymb.add("=");
		vecfirstsymb.add("-");        
		
		Rect r = new Rect();
		r.set(width - 120, Math.round(height / 7), width,
				(int) Math.round(height / 4.7));
		paint.setColor(Color.RED);
		canvas.drawRect(r, paint);
		paint.setTextSize(36);
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		paint.setColor(Color.WHITE);
		canvas.drawText("Up", width - 60, Math.round(height / 6.3), paint);
		rectposList.add(r);

		r = new Rect();
		r.set(width - 120, (int) Math.round(height / 3.8), width,
				(int) Math.round(height / 2.5));
		paint.setColor(Color.RED);
		canvas.drawRect(r, paint);
		paint.setTextSize(36);
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		paint.setColor(Color.WHITE);
		canvas.drawText("Down", width - 60, Math.round(height / 3), paint);
		rectposList.add(r);
		int toppos=0;
		r = new Rect();
		int ss = (width / 2) - 35;
		r.set(0, (int) (height - Math.round(height / 4.5)), width, height
				- Math.round(height / 7));
		paint.setColor(Color.RED);
		canvas.drawRect(r, paint);
		paint.setTextSize(34);
		paint.setColor(Color.WHITE);
		canvas.drawText("Ok", ss, height - Math.round(height / 6), paint);
		rectposList.add(r);

		int lt = 370;
		int tp = height / 11;
		int rt = 383;
		int bm = height / 8;

		for (int i = 0; i <= SessionPinActivity.vecsym.size(); i++) {
			paint.setColor(Color.rgb(188, 3, 89));
			paint.setStrokeWidth(3);
			canvas.drawRect(lt, tp, rt, bm, paint);
			lt = lt + 22;
			rt = rt + 22;
		}

		paint.setTextSize(60);
		paint.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			toppos = toppos + (height / 13);
			text[i] = new TextView(getContext());
			text[i].setText("" + i);
			text[i].setTextSize(20);
			text[i].setTypeface(null, Typeface.BOLD);
			text[i].setMinLines(1);
			canvas.drawText(text[i].getText().toString(),
					Math.round(width / 4), toppos, paint);
		}
		
		if (sespin.stat==0) {
			sespin.stat++;
			toppos = 0;
			String str = "";
			Collections.shuffle(vecfirstsymb);
			for (int i = 0; i < 10; i++) {
				str = vecfirstsymb.get(i).toString();
				toppos = toppos + (height / 13);
				textsym[i] = new TextView(getContext());
				textsym[i].setText(str);
				textsym[i].setTextSize(25);
				textsym[i].setTypeface(null, Typeface.BOLD);
				textsym[i].setMinLines(1);
				vecpos.add(Math.round(width / 1.8) + "-" + toppos);
				vecsymbols.add(textsym[i].getText().toString());
				canvas.drawText(str, Math.round(width / 1.8), toppos, paint);
				// Toast.makeText(getContext(),"---" + str + "---"+
				// Math.round(width / 1.8) + "---"+ toppos,
				// Toast.LENGTH_SHORT).show();
			}
		} else {
			if (updownstat.equalsIgnoreCase("up")) {
				Vector vecSy = (Vector) vecsymbols.clone();
				for (int i = 9; i >= 0; i--) {
					if (i == 0) {
						String topos = vecpos.get(9).toString();
						String sp[] = topos.split("-");
						String cont = vecSy.get(i).toString();
						vecsymbols.set(9, cont);
						canvas.drawText(cont, Integer.parseInt(sp[0]),
								Integer.parseInt(sp[1]), paint);
					} else {
						String topos = vecpos.get(i - 1).toString();
						String sp[] = topos.split("-");
						String cont = vecSy.get(i).toString();
						vecsymbols.set(i - 1, cont);
						canvas.drawText(cont, Integer.parseInt(sp[0]),
								Integer.parseInt(sp[1]), paint);
					}
				}
			} else if (updownstat.equalsIgnoreCase("down")) {
				Vector vecSy = (Vector) vecsymbols.clone();
				for (int i = 0; i < 10; i++) {
					if (i == 9) {
						String topos = vecpos.get(0).toString();
						String sp[] = topos.split("-");
						String cont = vecSy.get(i).toString();
						vecsymbols.set(0, cont);
						canvas.drawText(cont, Integer.parseInt(sp[0]),
								Integer.parseInt(sp[1]), paint);
					} else {
						String topos = vecpos.get(i + 1).toString();
						String sp[] = topos.split("-");
						String cont = vecSy.get(i).toString();
						vecsymbols.set(i + 1, cont);
						canvas.drawText(cont, Integer.parseInt(sp[0]),
								Integer.parseInt(sp[1]), paint);
					}
				}
			}
		}
        setOnTouchListener(this);
    }
    
    public boolean onTouch(View vi, MotionEvent event)
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
								updownstat="up";
								invalidate();
		                	}
							else
							if(i==2)
							{
								rectposList=new ArrayList<Rect>();
								updownstat="down";
								invalidate();
							}
							else
							{
								sespin.vecsym.add(vecsymbols);
								Intent intt=new Intent(context,SessionPinActivity.class);
								intt.putExtra("username",sespin.username);
								intt.putExtra("accno",sespin.accno);
								super.getContext().startActivity(intt);
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
}