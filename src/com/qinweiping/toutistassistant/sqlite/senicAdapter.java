package com.qinweiping.toutistassistant.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class senicAdapter {

	//Context���� 
	private Context context;
	//���ݿ����� 
	private String dbName;
	//������ 
	private String table;
	
	private SQLiteDatabase mSQLiteDatabase = null;
	
	private DatabaseHelper mDatabaseHelper = null;
	
	public senicAdapter(Context context)
	{
		this.context = context;
	}
}
