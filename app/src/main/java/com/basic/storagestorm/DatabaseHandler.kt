package com.basic.storagestorm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.basic.storagestorm.models.HistoryEntry

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_HISTORY ($COL_ENTRY VARCHAR, $COL_DATE INTEGER)"
        db!!.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    companion object {
        private val DATABASE_NAME = "storagestorm.db"
        private val DATABASE_VERSION = 1

        // table
        private val TABLE_HISTORY = "History"
        private val COL_ENTRY = "Entry"
        private val COL_DATE = "Date"
    }

    val history: List<HistoryEntry>
        get() {
            val list = ArrayList<HistoryEntry>()
            val selectQuery = "SELECT * FROM $TABLE_HISTORY"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val entry = HistoryEntry(
                        cursor.getString(cursor.getColumnIndex(COL_ENTRY)),
                        cursor.getLong(cursor.getColumnIndex(COL_DATE))
                    )

                    list.add(entry)
                } while (cursor.moveToNext())
            }
            db.close()
            return list
        }

    fun addEntryToHistory(entry: HistoryEntry) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ENTRY, entry.entry)
        values.put(COL_DATE, entry.date)

        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }

    fun clearHistory() {
        this.writableDatabase.execSQL("DELETE FROM $TABLE_HISTORY")
    }
}