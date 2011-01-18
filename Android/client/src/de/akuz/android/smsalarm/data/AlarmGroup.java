package de.akuz.android.smsalarm.data;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class represents an AlarmGroup. AlarmGroups define sender numbers and
 * keywords which are allowed to send an alarm to this device. Also AlarmGroups 
 * define which action should be taken if an alarm occurs.
 * @author Till Klocke
 *
 */
public class AlarmGroup {
	
	private int id;
	private SQLiteDatabase db;
	private AlarmDataAdapter parent;

	AlarmGroup(AlarmDataAdapter parent, int id){
		this.db = parent.getDatabase();
		this.parent = parent;
		this.id = id;
	}
	
	/**
	 * Signals that this alarm group won't be used any longer
	 */
	public void close(){
		parent.closeAlarmGroup(id);
	}
	
	/**
	 * Returns a user defined name of this alarm group
	 * @return the name
	 */
	public String getName(){
		Cursor mCursor = getAlarmTableCursor( new String[]{AlarmDataAdapter.ALARM_NAME});
		if(mCursor.getCount()>1 || mCursor.getCount()<1){
			throw new IllegalArgumentException("We have too many are too few name entries in our database for id: "+id);
		}
		mCursor.moveToFirst();
		int nameColumn = mCursor.getColumnIndex(AlarmDataAdapter.ALARM_NAME);
		String name = mCursor.getString(nameColumn);
		mCursor.close();
		return name;
	}
	
	/**
	 * Returns the URI to use as URI for the ringtone which be used as alarm
	 * @return an URI
	 */
	public String getRingtoneURI(){
		Cursor mCursor = getAlarmTableCursor(new String[]{AlarmDataAdapter.ALARM_RINGTONE});
		if(mCursor.getCount()!=1){
			throw new IllegalArgumentException("We seem to have none ore more than one ringtone uri for this alarm. id: "+id);
		}
		mCursor.moveToFirst();
		int ringtoneColumn = mCursor.getColumnIndex(AlarmDataAdapter.ALARM_RINGTONE);
		String ringtone = mCursor.getString(ringtoneColumn);
		mCursor.close();
		return ringtone;
	}
	
	/**
	 * Returns true if the phone should vibrate during the alarm
	 * @return
	 */
	public boolean vibrate(){
		Cursor mCursor = getAlarmTableCursor(new String[]{AlarmDataAdapter.ALARM_VIBRATE});
		boolean vibrate = false;
		if(mCursor.getCount()!=1){
			mCursor.close();
			return false;
		}
		mCursor.moveToFirst();
		int vibrateColumn = mCursor.getColumnIndex(AlarmDataAdapter.ALARM_VIBRATE);
		vibrate = mCursor.getInt(vibrateColumn)==1;
		mCursor.close();
		return vibrate;
	}
	
	/**
	 * Returns an integer representing the color in which LED should blink in case
	 * of an alarm. See the android reference for more details about this integer.
	 * @return
	 */
	public int getLEDColor(){	
		Cursor mCursor = getAlarmTableCursor(new String[]{AlarmDataAdapter.ALARM_LED});
		if(mCursor.getCount()!=1){
			throw new IllegalArgumentException("We seem to have none ore more than one led color for this alarm. id: "+id);
		}
		mCursor.moveToFirst();
		int ledColumn = mCursor.getColumnIndex(AlarmDataAdapter.ALARM_LED);
		int ledColor = mCursor.getInt(ledColumn);
		mCursor.close();
		return ledColor;
	}
	
	/**
	 * Returns the keyword with which an alarm SMS has to start
	 * @return a String representing the keyword
	 */
	public String getKeyword(){
		Cursor mCursor = getAlarmTableCursor(new String[]{AlarmDataAdapter.ALARM_KEYWORD});
		if(mCursor.getCount()!=1){
			throw new IllegalArgumentException("We seem to have none ore more than one entries for this alarm. id: "+id);
		}
		mCursor.moveToFirst();
		int alarmKeyword = mCursor.getColumnIndex(AlarmDataAdapter.ALARM_KEYWORD);
		String keyword = mCursor.getString(alarmKeyword);
		mCursor.close();
		return keyword;
	}
	
	/**
	 * Small helper method to get a cursor which queries the Alarm database
	 * @param columns
	 * @return
	 */
	private Cursor getAlarmTableCursor(String[] columns){
		return db.query(AlarmDataAdapter.ALARM_TABLE_NAME, 
				new String[]{AlarmDataAdapter.ALARM_NAME},
				AlarmDataAdapter.ALARM_ID+"=?", new String[]{String.valueOf(id)},
				null, null, null);
	}
	
	

}
