package com.sharaai.ukhane;

import java.util.ArrayList;

import com.sharaai.ukhane.SQLiteAdapter.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;



public class SQLiteAdapter {

	public static final String MYDATABASE_NAME = "MyDataBase1";
	public static final String MYDATABASE_TABLE = "MyFav";
	public static final int MYDATABASE_VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String KEY_CONTENT ="Content";
	public static final String KEY_CAT_ID ="Catid";
	public static final String KEY_SHOT_MSG_ID ="Shotmsgid";
	public static final String KEY_MSG_ID ="Msgid";
	public static final String KEY_OTHER_MSG_ID ="Othermsgid";
	public static final String KEY_CURRENT_STATUS ="Statusid";
	public static final String KEY_CURRENT_USER ="Userid";
	//create table  MY_DATABASE(ID integer primary key,Context text not null);
//	private static final String SCRIPT_CREATE_DATABASE = 
//		"create table "+MYDATABASE_TABLE+" ("+KEY_ID
//		+" integer primary key autoincrement, "+
//		KEY_CONTENT+" text not null)";
	
	private static final String SCRIPT_CREATE_DATABASE = 
			"create table "+MYDATABASE_TABLE+" ("+KEY_ID
			+" integer primary key autoincrement, "+
			KEY_CAT_ID+" integer , "+KEY_SHOT_MSG_ID+" integer , "+
			KEY_MSG_ID+" integer , "+
			KEY_OTHER_MSG_ID+" integer , "+KEY_CURRENT_STATUS+" integer , "+KEY_CURRENT_USER+" integer)";
	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;
	public SQLiteAdapter(Context c){
		context = c;
			}
    public SQLiteAdapter openToRead()throws android.database.SQLException{
    	SQLiteHelper sqLiteHelper = new SQLiteHelper(context,MYDATABASE_NAME,null,MYDATABASE_VERSION);
    	sqLiteDatabase  = sqLiteHelper.getReadableDatabase();
    	return this;
    	//http://android-er.blogspot.in/2011/06/simple-example-using-androids-sqlite_02.html
    }
    
    public SQLiteAdapter openToWrite() throws android.database.SQLException {
    	sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
    	sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    	return this;
    	}

    public SQLiteAdapter openToUpdate() throws android.database.SQLException {
    	sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
    	sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    	return this;
    	}
    public void close(){
//    	sqLiteHelper.close();
//    	sqLiteHelper.getWritableDatabase().close();
    }
//    public long insert(String content){
//    	ContentValues contentValues = new ContentValues();
//    	contentValues.put(KEY_CONTENT, content);
//    	return sqLiteDatabase.insert(MYDATABASE_TABLE,null,contentValues);
//    }
    public long insert(int cat, int sMsg, int msg,int othermsg, int status, int userId){
    	ContentValues contentValues = new ContentValues();
//    	contentValues.put(KEY_CONTENT, content);
    	contentValues.put(KEY_CAT_ID, cat);
    	contentValues.put(KEY_SHOT_MSG_ID, sMsg);
    	contentValues.put(KEY_MSG_ID, msg);
    	contentValues.put(KEY_OTHER_MSG_ID, othermsg);
    	contentValues.put(KEY_CURRENT_STATUS, status);
    	contentValues.put(KEY_CURRENT_USER, userId);
    	return sqLiteDatabase.insert(MYDATABASE_TABLE,null,contentValues);
    }
	public int deleteAll(){
		return sqLiteDatabase.delete(MYDATABASE_TABLE,null,null);
	}
	public Cursor queueAll(){
//		String[] columns = new String[]{KEY_ID,KEY_CONTENT};
		String[] columns = new String[]{KEY_ID,KEY_CAT_ID,KEY_SHOT_MSG_ID,KEY_MSG_ID,KEY_OTHER_MSG_ID,KEY_CURRENT_STATUS,KEY_CURRENT_USER};
		
		Cursor  cursor = sqLiteDatabase.query(MYDATABASE_TABLE,columns,
		null,null,null,null,null);
//		final ArrayList MarkerElement > result = new ArrayList< MarkerElement >();
	    cursor.moveToFirst();

//		while ( !cursor.isAfterLast() ) {
////	        result.add( new MarkerElement(
////	                cursor.getString( COL_TITLE ),
////	                cursor.getString( COL_SNIPPET ),
////	                new LatLng(
////	                        cursor.getDouble( COL_LAT ),
////	                        cursor.getDouble( COL_LNG ) ),
////	                cursor.getString( COL_OTHER_USEFUL_DATA ) );
////			String ss = cursor.getString(0);
////			String ss1 = cursor.getString(1);
////			System.out.println(" xxxxsssssssssss  "+ss+ " "+ss1);
//			System.out.println(" xxxxssssssssss  "+cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4));
//	        cursor.moveToNext();
//	    }

		return cursor;
	}	
	
	public void updateTable(int status, int columnNo, int columeValue){ 
		try{
			ContentValues values = new ContentValues();

			values.put(KEY_CURRENT_STATUS, status);
			
			String updateString = "Msgid = "+columeValue;
			if(columnNo==3)
			{
				sqLiteDatabase.update(MYDATABASE_TABLE,values, /*"KEY_MSG_ID = 21"*/updateString,null);
			}else
			{
				String updateString1 = "Othermsgid = "+columeValue;
				sqLiteDatabase.update(MYDATABASE_TABLE,values, /*"KEY_OTHER_MSG_ID = 21"*/updateString1,null);
			}
			sqLiteDatabase.close();        
		}catch(Exception e){  
			System.out.println(" xxxx ssssssssssssss u3 "+e.getMessage());   
		}   
	} 
	public class SQLiteHelper extends SQLiteOpenHelper{
		
		public SQLiteHelper(Context context,String name,
				CursorFactory factory,int version){
		        super(context,name,factory,version);	
		}
		
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(SCRIPT_CREATE_DATABASE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db,int OldVersion,int newVersion){
		
	}
}
	
	
}
