package com.qinweiping.toutistassistant.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	//1���ݿ������
	
	private static  String table;
	
	//2 ���ݿ����� 

	private  final String dbName;
	
	/* ���캯��-����һ�����ݿ� */
	DatabaseHelper(Context context,String db_name,String tableStr)
	{
		//������getWritableDatabase() 
		//�� getReadableDatabase()����ʱ
		//�򴴽�һ�����ݿ�
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
