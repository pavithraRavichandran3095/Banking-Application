package com.covertatt;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.inputmethodservice.Keyboard.Key;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CirclesDrawingView extends View implements CollectionsDataInterface{
	private static final String TAG = "CirclesDrawingView";
	/** Main bitmap */
	private Bitmap mBitmap = null;
	Boolean animationbool=false;
	static Context conn=null;
	public MainActivity mainOBJ=new MainActivity();
	private Bitmap bm = null;
	private ImageView imageview = null;
//	 ImageView imageView = new ImageView(this);
	private Rect mMeasuredRect;
	private Rect mMeasuredRect1;
	static Boolean setdisplaystatus=false;
	public int pointercount = 0;
	String checkPINEntered="";
	private Paint textPaint;
	public int setimage = 0;
	public boolean status = false;
	static int ClickedButton=1;
	public static boolean circlestatus = false;
	static int r = 255, g = 255, b = 255;
	CircleArea touchedCircle;
	CircleArea buttontouchedCircle;
	SensorManager mySensorManager;
	Sensor myProximitySensor;
	Boolean bool = false;
	float w,textSize;
	static Context context;
	/** Stores data about single circle */
	private static class CircleArea {
		int radius;
		int centerX;
		int centerY;

		CircleArea(int centerX, int centerY, int radius) {
			this.radius = radius;
			this.centerX = centerX;
			this.centerY = centerY;
		}

		@Override
		public String toString() {
			return "Circle[" + centerX + ", " + centerY + ", " + radius + "]";
		}
	}

	/** Paint to draw circles */
	private Paint mCirclePaint,mpaint,paint2;
	private Paint mCirclePaintForInvisible;
	private Paint paint;
	// private final Random mRadiusGenerator = new Random();
	private final Integer mRadiusGenerator =40 ;
	// Radius limit in pixels
	private final static int RADIUS_LIMIT =70 ;
	private static final int CIRCLES_LIMIT = 4;
	private static final int BUTTON_LIMIT = 10;
	/** All available circles */
	private HashSet<CircleArea> mCircles = new HashSet<CircleArea>(
			CIRCLES_LIMIT);
	private Vector<CircleArea> mRect = new Vector<CircleArea>(BUTTON_LIMIT);
	private SparseArray<CircleArea> mCirclePointer = new SparseArray<CircleArea>(
			CIRCLES_LIMIT);
	private SparseArray<CircleArea> mCirclePointerButton = new SparseArray<CircleArea>(
			BUTTON_LIMIT);


	/**
	 * Default constructor
	 * 
	 * @param ct
	 *            {@link android.content.Context}
	 */
	public CirclesDrawingView(final Context ct) {
		super(ct);
		
		this.context=ct;
		init(ct);
	}

	public CirclesDrawingView(final Context ct, final AttributeSet attrs) {
		super(ct, attrs);
		this.context=ct;
		init(ct);
	}

	public CirclesDrawingView(final Context ct, final AttributeSet attrs,
			final int defStyle) {
		super(ct, attrs, defStyle);
		this.context=ct;
		init(ct);
	}

	private void init(final Context ct) {
		try
		{
		imageview = new ImageView(ct);
		conn=ct;
		
		// Generate bitmap used for background
//		mBitmap = BitmapFactory.decodeResource(ct.getResources(),
//				R.drawable.ic_launcher);
//		bm = BitmapFactory.decodeResource(ct.getResources(), R.drawable.keypad);
		mCirclePaint = new Paint();
		// mCirclePaint.setColor(Color.MAGENTA);
		// mCirclePaint.setStrokeWidth(10);
		// mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(Color.RED);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setStrokeWidth(4.5f);
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(45);

		mCirclePaintForInvisible = new Paint();
		mCirclePaintForInvisible.setAntiAlias(true);
		mCirclePaintForInvisible.setColor(Color.GREEN);
		mCirclePaintForInvisible.setStyle(Paint.Style.STROKE);
		mCirclePaintForInvisible.setStrokeWidth(4.5f);
		
		paint = new Paint();
        paint.setColor(Color.GREEN);
        
	        paint2= new Paint();
	        paint2.setColor(Color.BLACK);
	        paint2.setTextSize(35);  //set text size
//	        w = paint2.measureText("siva")/2;
//	        textSize = paint2.getTextSize();
	        
		touchedCircle = new CircleArea(110, 110, mRadiusGenerator + RADIUS_LIMIT);
		mCircles.add(touchedCircle);
		touchedCircle = new CircleArea(350, 110, mRadiusGenerator + RADIUS_LIMIT);
		mCircles.add(touchedCircle);
		touchedCircle = new CircleArea(110, 350, mRadiusGenerator + RADIUS_LIMIT);
		mCircles.add(touchedCircle);
		touchedCircle = new CircleArea(350, 350, mRadiusGenerator
				+ RADIUS_LIMIT);
		mCircles.add(touchedCircle);
		
		buttontouchedCircle = new CircleArea(140, 490, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(220, 490, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(300, 490, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(140, 570, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(220, 570, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(300, 570, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(140, 650, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(220, 650, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(300, 650, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(140, 730, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(220, 730, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		buttontouchedCircle = new CircleArea(300, 730, mRadiusGenerator);
		mRect.add(buttontouchedCircle);
		}
		catch(Exception e)
		{
			urlcall("initexception"+e);
		}
	
	}
  
	@Override
	public void onDraw(final Canvas canv) {
		try
		{
		if (status) {
			//canv.drawBitmap(bm, 0, 0, null);
			mainOBJ.b1.setText(" "+String.valueOf(checkNum.get(1))+" ");
			mainOBJ.b2.setText(" "+String.valueOf(checkNum.get(2))+" ");
			mainOBJ.b3.setText(" "+String.valueOf(checkNum.get(3))+" ");
			mainOBJ.b4.setText(" "+String.valueOf(checkNum.get(4))+" ");
			mainOBJ.b5.setText(" "+String.valueOf(checkNum.get(5))+" ");
			mainOBJ.b6.setText(" "+String.valueOf(checkNum.get(6))+" ");
			mainOBJ.b7.setText(" "+String.valueOf(checkNum.get(7))+" ");
			mainOBJ.b8.setText(" "+String.valueOf(checkNum.get(8))+" ");
			mainOBJ.b9.setText(" "+String.valueOf(checkNum.get(9))+" ");
			mainOBJ.b0.setText(" "+String.valueOf(checkNum.get(0))+" ");
			mainOBJ.b1.setVisibility(View.VISIBLE);
			mainOBJ.b2.setVisibility(View.VISIBLE);
			mainOBJ.b3.setVisibility(View.VISIBLE);
			mainOBJ.b4.setVisibility(View.VISIBLE);
			mainOBJ.b5.setVisibility(View.VISIBLE);
			mainOBJ.b6.setVisibility(View.VISIBLE);
			mainOBJ.b7.setVisibility(View.VISIBLE);
			mainOBJ.b8.setVisibility(View.VISIBLE);
			mainOBJ.b9.setVisibility(View.VISIBLE);
			mainOBJ.b0.setVisibility(View.VISIBLE);
			
						
		}
		
		if(animationbool)
		{
			CircleArea circle=mRect.get(ClickedButton-1);
			canv.drawCircle(circle.centerX,circle.centerY, mRadiusGenerator, paint);
		}
		
		canv.drawCircle(110, 110, 4, mCirclePaint);
		canv.drawCircle(350, 110, 4, mCirclePaint);
		canv.drawCircle(110, 350, 4, mCirclePaint);
		canv.drawCircle(350, 350, 4, mCirclePaint);
		int i=1;
		for (CircleArea circle : mRect) {

			canv.drawCircle(circle.centerX, circle.centerY, circle.radius,
					mCirclePaintForInvisible);
			if(i<=9)
			{
				canv.drawText(String.valueOf(i), circle.centerX,circle.centerY ,paint2);
			}
			else if(i==10)
			{
				canv.drawText("x", circle.centerX,circle.centerY ,mCirclePaint);
			}
			else if(i==11)
			{
				canv.drawText("0", circle.centerX,circle.centerY ,paint2);
			}
			else
			{
					canv.drawText("OK", circle.centerX,circle.centerY ,mCirclePaint);
			}
			i++;
		}
//		for (CircleArea circle : mCircles) {
//
//			canv.drawCircle(circle.centerX, circle.centerY, circle.radius,
//					mCirclePaintForInvisible);
//		}
		canv.drawText("Total pointers: " + mCirclePointer.size(), 10, 40,
				textPaint);
		}
		catch(Exception e)
		{
			urlcall("OnDrawException"+e);
		}
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();
		pointercount = event.getPointerCount();
		boolean handled = false;

		int xTouch;
		int yTouch;
		int pointerId;
		int actionIndex = event.getActionIndex();
		// get touch event coordinates and make transparent circle from it
		switch (event.getActionMasked()) {
		
		
		case MotionEvent.ACTION_DOWN:
			// it's the first pointer, so clear all existing pointers data
			clearCirclePointer();
			xTouch = (int) event.getX(0);
			yTouch = (int) event.getY(0);
			// check if we've touched inside some circle
			touchedCircle = obtainTouchedCircle(xTouch, yTouch);

//			touchedCircle.centerX = xTouch;
//			touchedCircle.centerY = yTouch;
			if (xTouch == touchedCircle.centerX
					&& yTouch == touchedCircle.centerY && circlestatus) {
				mCirclePointer.put(event.getPointerId(0), touchedCircle);
				
				
				Log.w(TAG, "theHASHSETValueIS:" + mCirclePointer.toString());
				// urlcall("theHASHSETValueIS:"+mCirclePointer);
			}
			else if(!circlestatus)
			{
				mCirclePointerButton.put(event.getPointerId(0), touchedCircle);
			}
			invalidate();
			handled = true;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.d(TAG, "Pointer down");
	
			// It secondary pointers, so obtain their ids and check circles
			pointerId = event.getPointerId(actionIndex);
			xTouch = (int) event.getX(actionIndex);
			yTouch = (int) event.getY(actionIndex);
			// check if we've touched inside some circle
			touchedCircle = obtainTouchedCircle(xTouch, yTouch);
			if (circlestatus) 
			{
				mCirclePointer.put(pointerId, touchedCircle);
			}
			else if(!circlestatus)
			{
				mCirclePointerButton.put(event.getPointerId(0), touchedCircle);
			}
			// touchedCircle.centerX = xTouch;
			// touchedCircle.centerY = yTouch;
			Log.d(TAG, " touchedCircle.centerX" + touchedCircle.centerX);
			Log.d(TAG, " touchedCircle.centerY" + touchedCircle.centerY);
			invalidate();
			handled = true;
			break;
		case MotionEvent.ACTION_MOVE:

			// final int pointerCount = event.getPointerCount();
			// int i=0;
			// Log.w(TAG, "Move");
			//
			// for (actionIndex = 0; actionIndex < pointerCount; actionIndex++)
			// {
			//
			//
			// // Some pointer has moved, search it by pointer id
			// pointerId = event.getPointerId(actionIndex);
			//
			// xTouch = (int) event.getX(actionIndex);
			// yTouch = (int) event.getY(actionIndex);
			//
			// touchedCircle = mCirclePointer.get(pointerId);
			//
			//
			// if (null != touchedCircle)
			// {
			//
			// touchedCircle.centerX = xTouch;
			// touchedCircle.centerY = yTouch;
			// }
			// }
			invalidate();
			handled = true;
			break;
		case MotionEvent.ACTION_UP:
			mainOBJ.b1.setVisibility(View.INVISIBLE);
			mainOBJ.b2.setVisibility(View.INVISIBLE);
			mainOBJ.b3.setVisibility(View.INVISIBLE);
			mainOBJ.b4.setVisibility(View.INVISIBLE);
			mainOBJ.b5.setVisibility(View.INVISIBLE);
			mainOBJ.b6.setVisibility(View.INVISIBLE);
			mainOBJ.b7.setVisibility(View.INVISIBLE);
			mainOBJ.b8.setVisibility(View.INVISIBLE);
			mainOBJ.b9.setVisibility(View.INVISIBLE);
			mainOBJ.b0.setVisibility(View.INVISIBLE);
			animationbool=false;
			clearCirclePointer();
			invalidate();
			handled = true;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			// not general pointer was up
			pointerId = event.getPointerId(actionIndex);
			mainOBJ.b1.setVisibility(View.INVISIBLE);
			mainOBJ.b2.setVisibility(View.INVISIBLE);
			mainOBJ.b3.setVisibility(View.INVISIBLE);
			mainOBJ.b4.setVisibility(View.INVISIBLE);
			mainOBJ.b5.setVisibility(View.INVISIBLE);
			mainOBJ.b6.setVisibility(View.INVISIBLE);
			mainOBJ.b7.setVisibility(View.INVISIBLE);
			mainOBJ.b8.setVisibility(View.INVISIBLE);
			mainOBJ.b9.setVisibility(View.INVISIBLE);
			mainOBJ.b0.setVisibility(View.INVISIBLE);
			status = false;
			mCirclePointer.remove(pointerId);
			animationbool=false;
			invalidate();
			handled = true;
			break;
		case MotionEvent.ACTION_CANCEL:
			handled = true;
			break;
		default:
			// do nothing
			break;
		}
		return super.onTouchEvent(event) || handled;
	}

	/**
	 * Clears all CircleArea - pointer id relations
	 */
	private void clearCirclePointer() {
		Log.w(TAG, "clearCirclePointer");
		mCirclePointer.clear();
	}

	/**
	 * Search and creates new (if needed) circle based on touch area
	 * 
	 * @param xTouch
	 *            int x of touch
	 * @param yTouch
	 *            int y of touch
	 * 
	 * @return obtained {@link CircleArea}
	 */
	public void proximity() {
		mySensorManager = (SensorManager) getContext().getSystemService(
				Context.SENSOR_SERVICE);
		myProximitySensor = mySensorManager
				.getDefaultSensor(Sensor.TYPE_PROXIMITY);

		if (myProximitySensor == null) {
		} else {
			mySensorManager.registerListener(proximitySensorEventListener,
					myProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	private CircleArea obtainTouchedCircle(final int xTouch, final int yTouch) {
		circlestatus = false;
		CircleArea touchedCircle = getTouchedCircle(xTouch, yTouch);
		Log.w(TAG, "touchedCIRCLEValueIs:" + touchedCircle);
		// urlcall("touchedCIRCLEValueIs:"+touchedCircle);
		proximity();
		if (mCirclePointer.size() >= 1 && touchedCircle != null && bool == true) 
		{
			try
			{
				status = true;
			}
			catch(Exception e)
			{
				urlcall("THEEXCEPTIONIS:"+e);
			}
		}
		if (null == touchedCircle) {
			status = false;
			// touchedCircle = new CircleArea(xTouch, yTouch,
			// mRadiusGenerator.nextInt(RADIUS_LIMIT) + RADIUS_LIMIT);
			touchedCircle = new CircleArea(xTouch, yTouch, mRadiusGenerator
					+ RADIUS_LIMIT);
			if (mCircles.size() == CIRCLES_LIMIT) {
				Log.w(TAG, "Clear all circles, size is " + mCircles.size());
			}
			if (mCircles.size() < 4) {
				mCircles.add(touchedCircle);
				// urlcall("newcirclecreatedsuccessfully");
				Log.w(TAG, "newcirclecreatedsuccessfully");

			}
			Log.w(TAG, "Added circle " + touchedCircle);
		}
		return touchedCircle;
	}

	/**
	 * Determines touched circle
	 * 
	 * @param xTouch
	 *            int x touch coordinate
	 * @param yTouch
	 *            int y touch coordinate
	 * 
	 * @return {@link CircleArea} touched circle or null if no circle has been
	 *         touched
	 */
	private CircleArea getTouchedCircle(final int xTouch, final int yTouch) {
		CircleArea touched = null;
		for (CircleArea circle : mCircles) {
			if ((circle.centerX - xTouch) * (circle.centerX - xTouch)
					+ (circle.centerY - yTouch) * (circle.centerY - yTouch) <= circle.radius
					* circle.radius) {
				circlestatus = true;
				touched = circle;
				Log.w(TAG, "the toched if executed====");
				break;
			}
		}
		if(!circlestatus)
		{
			ClickedButton=1;
			for (CircleArea circle : mRect) 
			{
				if ((circle.centerX - xTouch) * (circle.centerX - xTouch)
						+ (circle.centerY - yTouch) * (circle.centerY - yTouch) <= circle.radius
						* circle.radius) 
				{
					circlestatus = false;
					animationbool=true;
					touched = circle;
					if(ClickedButton<=9)
					{
						int OriginalPIN=checkNum.indexOf(ClickedButton);
						checkPINEntered=checkPINEntered+String.valueOf(OriginalPIN);
						//conformPIN.add(OriginalPIN);
						urlcall("clicked====>"+OriginalPIN+"newPIN"+ClickedButton);
						
					}
					else if(ClickedButton==10)
					{
//						conformPIN.remove(Integer.parseInt((String) conformPIN.lastElement()));
						if(!checkPINEntered.equalsIgnoreCase(""))
						{
							int temp=checkPINEntered.length();
							checkPINEntered=checkPINEntered.substring(0, temp-1);		
							urlcall("clicked====>"+ClickedButton+"deletedpin"+checkPINEntered);
						}
						
					}
					else if(ClickedButton==11)
					{
						int OriginalPIN=checkNum.indexOf(0);
					//	conformPIN.add(OriginalPIN);
						checkPINEntered=checkPINEntered+String.valueOf(OriginalPIN);
						urlcall("clicked====>"+OriginalPIN+"newPIN"+ClickedButton);
					}
					else
					{
						//Context ct;
						mainOBJ.PINChecking(checkPINEntered,super.getContext());			
					}
					
					urlcall("===iINCREMENTED==");
					break;
				}
				ClickedButton++;
			}
		}
		return touched;
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mMeasuredRect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
	}
	
	 public static void urlcall(String value)//deleted static type
	 {
		 try
		 {
			 value=value.replace(" ", "%20");
			 URL u=new
			 URL(FstIpAddress.ipaddstr+"Myservicecheck?value="+value);
			 BufferedReader br=new BufferedReader(new
			 InputStreamReader(u.openStream()));
			 String str=br.readLine();
		 }
		 catch(Exception e)
		 {
		 	e.printStackTrace();
		 }
	 }
	SensorEventListener proximitySensorEventListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}
int i=0;
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

				if (event.values[0]<1.0) {
					bool = true;
					if(i==0)
					{	
						checkNum.clear();
						mainOBJ.suffleKeypadGenereate();
						i=1;
						Log.w(TAG, "the toched if executed====");
					}
					
					
				} else {
					i=0;
					bool = false;
				}

			}
		}

	};
}

