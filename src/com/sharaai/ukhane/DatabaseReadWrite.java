package com.sharaai.ukhane;



import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class DatabaseReadWrite
{

	public static ArrayList<Integer> ShotMessageID = new ArrayList<Integer>();
	public static ArrayList<Integer> LongMessageID = new ArrayList<Integer>();
	public static ArrayList<Integer> CategoryID = new ArrayList<Integer>();
	public static ArrayList<Integer> OtherMessageID = new ArrayList<Integer>();
	public static ArrayList<Integer> CurrentStatus = new ArrayList<Integer>();
	public static ArrayList<Integer> CurrentUserId = new ArrayList<Integer>();
	

	public SQLiteAdapter mysqLiteAdapter;

	private Context context;
	public DatabaseReadWrite(Context c){
		context = c;
	}
	
	
	public void openToWrite(){
		mysqLiteAdapter = new SQLiteAdapter(context);
     mysqLiteAdapter.openToWrite();
	}
	
	
	public void openToWrite(int cat, int sMsg, int msg,int othermsg, int status, int user){
		mysqLiteAdapter = new SQLiteAdapter(context);
      mysqLiteAdapter.openToWrite();
		mysqLiteAdapter.insert(cat, sMsg, msg,othermsg, status, user);
	}
	
	public void openToUpdate(int status, int columnNo, int columeValue){
		mysqLiteAdapter = new SQLiteAdapter(context);
     mysqLiteAdapter.openToUpdate();
     mysqLiteAdapter.updateTable(status, columnNo, columeValue);
	}
	public void openToRead(){
		
        mysqLiteAdapter = new SQLiteAdapter(context);
        mysqLiteAdapter.openToRead();
        Cursor cursor = mysqLiteAdapter.queueAll();
        ShotMessageID = new ArrayList<Integer>();
    	 LongMessageID = new ArrayList<Integer>();
    	 CategoryID = new ArrayList<Integer>();
    	 OtherMessageID = new ArrayList<Integer>();
    	 CurrentStatus = new ArrayList<Integer>();
    	 CurrentUserId = new ArrayList<Integer>();
    	 
        while ( !cursor.isAfterLast() ) {
			System.out.println(" xxxxssssssssss  "+cursor.getInt(0)+" "+cursor.getInt(1)+" "+cursor.getInt(2)+" "+cursor.getInt(3)+" "+cursor.getInt(4)+" "+cursor.getInt(5));
			CategoryID.add(cursor.getInt(1));
			ShotMessageID.add(cursor.getInt(2));
			LongMessageID.add(cursor.getInt(3));
			OtherMessageID.add(cursor.getInt(4));
			CurrentStatus.add(cursor.getInt(5));
			CurrentUserId.add(cursor.getInt(6));
//			CurrentStatus.get(index);
			cursor.moveToNext();
	    }
        
        mysqLiteAdapter.close();
	}
}
