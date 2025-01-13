package com.ifmg.testegringo.localdata

class DatabaseContract {

    //static
    companion object{
        val DATABASE_NAME:String = "diet.db"
        val DATABASE_VERSION = 1

        val SQL_CREATE_TABLES = "${FOOD.SQL_CREATE} ${USER.SQL_CREATE}"
        val SQL_DROP_TABLES = "${FOOD.SQL_DROP} ${USER.SQL_DROP}"
    }

    object FOOD{
        val TABLE_NAME = "food"

        val COLUMN_NAME_ID = "id"
        val COLUMN_NAME_NAME = "name"
        val COLUMN_NAME_CALORIES = "calories"
        val COLUMN_NAME_DAY = "day"

        val SQL_CREATE = "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (" +
                "${COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${COLUMN_NAME_NAME} TEXT," +
                "${COLUMN_NAME_CALORIES} REAL," +
                "${COLUMN_NAME_DAY} LONG);"

        val SQL_DROP = "DROP TABLE IF EXISTS ${TABLE_NAME};"
    }


    object USER{
        val TABLE_NAME = "user"

        val COLUMN_NAME_ID = "id"
        val COLUMN_NAME_NAME = "name"
        val COLUMN_NAME_EMAIL = "email"
        val COLUMN_NAME_PASSWORD = "password"

        val SQL_CREATE = "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (" +
                "${COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${COLUMN_NAME_NAME} TEXT," +
                "${COLUMN_NAME_EMAIL} TEXT," +
                "${COLUMN_NAME_PASSWORD} TEXT);"

        val SQL_DROP = "DROP TABLE IF EXISTS ${TABLE_NAME};"

    }



}