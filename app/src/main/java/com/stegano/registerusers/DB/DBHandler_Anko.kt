package com.stegano.registerusers.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.PRIMARY_KEY
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

class DBHandler_Anko(context: Context) : SQLiteOpenHelper(context, DB_Name, null, DB_Version) {
    companion object {
        val DB_Name = "user.db"
        val DB_Version = 1
    }

    object UserTable {
        val TABLE_NAME = "user"
        val ID = "_id"
        val NAME = "name"
        val AGE = "age"
        val TELNUM = "telnum"
        val PIC_PATH = "pic_path"
    }

    fun getUserAlLWithCursor(): Cursor {
        return readableDatabase.query(UserTable.TABLE_NAME, arrayOf(
            UserTable.ID, UserTable.NAME, UserTable.AGE, UserTable.TELNUM, UserTable.PIC_PATH),
            null, null, null, null, null
        )
    }

    fun addUser(user: UserInfo) {
        val info = ContentValues()
        info.put(UserTable.NAME, user.name)
        info.put(UserTable.AGE, user.age)
        info.put(UserTable.PIC_PATH, user.pic_path)

        // Anko의 use 함수 -> DB를 사용하는 동안 블록을 걸어주고 함수가 끝나면 close해준다.
        writableDatabase.use {
            writableDatabase.insert(UserTable.TABLE_NAME, null, info)
        }
    }

    fun deleteUser(id: Long) {
        writableDatabase.use {
            writableDatabase.execSQL(
                "DELETE FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.ID} = ${id};"
            )
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Anko의 pair 함수 -> 인자 2개를 받아 String으로 변경해주는 역할을 함
        // 여기서 쿼리문을 String으로 쓰지 않고 Pair 클래스를 사용해서 쿼리가 만들어지도록 함
        db?.createTable(UserTable.TABLE_NAME, true,
            Pair(UserTable.ID, INTEGER+PRIMARY_KEY),
            Pair(UserTable.NAME, TEXT),
            Pair(UserTable.AGE, TEXT),
            Pair(UserTable.TELNUM, TEXT),
            Pair(UserTable.PIC_PATH, TEXT)
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}