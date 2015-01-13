package com.qinweiping.toutistassistant.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class senicAdapter {

	//Context对象 
	private Context context;
	//数据库名称 
	private String dbName;
	//表名称 
	private String table;
	
	private SQLiteDatabase mSQLiteDatabase = null;
	
	private DatabaseHelper mDatabaseHelper = null;
	
	public senicAdapter(Context context)
	{
		this.context = context;
	}
}
