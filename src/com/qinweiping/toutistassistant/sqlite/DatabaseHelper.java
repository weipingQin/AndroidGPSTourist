package com.qinweiping.toutistassistant.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	//1数据库表名称
	
	private static  String table;
	
	//2 数据库名称 

	private  final String dbName;
	
	/* 构造函数-创建一个数据库 */
	DatabaseHelper(Context context,String db_name,String tableStr)
	{
		//当调用getWritableDatabase() 
		//或 getReadableDatabase()方法时
		//则创建一个数据库
		super(context, db_name, null, DBInfo.DB_VERSION);
		this.table = tableStr;
		this.dbName = db_name;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	

}
