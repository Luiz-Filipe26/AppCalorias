package com.ifmg.testegringo.localdata

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseSQLite:SQLiteOpenHelper {

    constructor(context:Context):super(context,
        DatabaseContract.DATABASE_NAME,null,
        DatabaseContract.DATABASE_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DatabaseContract.SQL_CREATE_TABLES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DatabaseContract.SQL_DROP_TABLES)
        onCreate(db)
    }

}