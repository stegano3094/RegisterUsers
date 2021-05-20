package com.stegano.registerusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.stegano.registerusers.DB.DBHandler_Anko

class MainActivity : AppCompatActivity() {
    private var mAdapter: UserListAdapter? = null
    public var mDBHandler: DBHandler_Anko = DBHandler_Anko(this)
    companion object {
        val REQUEST_ADD_USER = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)  // 액션바 대신 툴바로 선언
        val newOne = mDBHandler.getUserAlLWithCursor()
        if(newOne?.count != 0) {
            mAdapter = UserListAdapter(this, newOne)
            val listView = findViewById<ListView>(R.id.user_list)
            listView.adapter = mAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            REQUEST_ADD_USER -> {
                val newOne = mDBHandler.getUserAlLWithCursor()

                if(mAdapter == null) {
                    mAdapter = UserListAdapter(applicationContext, newOne)
                    val listView = findViewById<ListView>(R.id.user_list)
                    listView.adapter = mAdapter
                }
                mAdapter?.changeCursor(newOne)
                mAdapter?.notifyDataSetInvalidated()
            }
        }
    }

    fun onClickDelete(view: View) {
        mDBHandler.deleteUser(view.tag as Long)
        val newOne = mDBHandler.getUserAlLWithCursor()
        mAdapter?.changeCursor(newOne)
    }

    override fun onDestroy() {
        super.onDestroy()

        mAdapter?.cursor?.close()
        mDBHandler.close()
    }

    // Menu를 만들 때 호출
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // Menu가 화면 갱신 시 호출 (onCreateOptionsMenu 호출된 후 호출됨)
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    // Menu가 선택되었을 때 호출 (선택된 메뉴에 대해서 동작이 필요할 때 사용)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {  // item의 id를 이용해서 메뉴를 구분할 수 있음
            R.id.add_user -> {
                val intent = Intent(this, SaveUserActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD_USER)
            }
            R.id.anko -> {
//                val layout = Intent(this, AnkoDSLActivitiy::class.java)
//                startActivity(layout)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}