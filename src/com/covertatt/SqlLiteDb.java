package com.covertatt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SqlLiteDb extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CovertDb";
    
    private static final String TABLE_COVERT = "covertpin";
    
    private static final String KEY_USERNAME="username";
    private static final String KEY_ACCTNO="acctno";
    private static final String KEY_PASSCODE="passcode";
    private static final String KEY_PASSCODEORI="passcodeori";
    
    private static final String[] COLUMNS = {KEY_USERNAME,KEY_ACCTNO,KEY_PASSCODE,KEY_PASSCODEORI};
    Context context;
    public SqlLiteDb(Context context)
    {
    	super(context,DATABASE_NAME, null, DATABASE_VERSION);
    	this.context=context;
	}
    
    @Override
	public void onCreate(SQLiteDatabase db)
    {
		String CREATE_BOOK_TABLE = "CREATE TABLE covertpin(username TEXT,acctno TEXT,passcode TEXT,passcodeori TEXT)";
		db.execSQL(CREATE_BOOK_TABLE);
	}
    
    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS covertpin");
        this.onCreate(db);
	}
    
    public void addTable(String user,String pin,String acctno,String pinori)
	{
    	SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_COVERT,COLUMNS,"username=?",new String[]{user},null,null,null,null);
        if(cursor!=null)
        {
        	if(cursor.getCount()==1)
        	{
        		String whereClause=KEY_USERNAME+"='"+user+"'";
        		db.delete(TABLE_COVERT,whereClause,null);
        		db = this.getWritableDatabase();
        		ContentValues values = new ContentValues();
                values.put(KEY_USERNAME,user);
                values.put(KEY_ACCTNO,acctno);
                values.put(KEY_PASSCODE,pin);
                values.put(KEY_PASSCODEORI,pinori);
                db.insert(TABLE_COVERT,null,values);
                db.close();
        	}
        	else
        	{
        		db = this.getWritableDatabase();
        		ContentValues values = new ContentValues();
                values.put(KEY_USERNAME,user);
                values.put(KEY_ACCTNO,acctno);
                values.put(KEY_PASSCODE,pin);
                values.put(KEY_PASSCODEORI,pinori);
                db.insert(TABLE_COVERT,null,values);
                db.close();
        	}
        }
	}
    
    public String getRecord(String user)
	{
    	String valu="";
    	SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_COVERT,COLUMNS,"username=?",new String[]{user},null,null,null,null);
        
        if (cursor != null)
        {
        	if(cursor.getCount()==1)
        	{
        		cursor.moveToFirst();
        		valu=cursor.getString(2);
        	}
        }
        return valu;
	}
    
    public String getPin(String user)
	{
    	String valu="";
    	SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_COVERT,COLUMNS,"username=?",new String[]{user},null,null,null,null);
        
        if (cursor != null)
        {
        	if(cursor.getCount()==1)
        	{
        		cursor.moveToFirst();
        		valu=cursor.getString(3);
        	}
        }
        return valu;
	}
}