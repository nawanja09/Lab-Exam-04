// MyDatabaseHelper.kt
package com.example.sqlitedatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "BookLibrary.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "my_library"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "book_title"
        private const val COLUMN_AUTHOR = "book_author"
        private const val COLUMN_PAGES = "book_pages"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_AUTHOR TEXT, " +
                "$COLUMN_PAGES INTEGER);"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addBook(title: String, author: String, pages: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_AUTHOR, author)
        cv.put(COLUMN_PAGES, pages)
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    fun readAllData(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }

    fun updateData(row_id: String, title: String, author: String, pages: String): Int {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_AUTHOR, author)
        cv.put(COLUMN_PAGES, pages)

        return db.update(TABLE_NAME, cv, "$COLUMN_ID=?", arrayOf(row_id))
    }

    fun deleteOneRow(row_id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(row_id))
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
}
