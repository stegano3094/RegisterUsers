package com.stegano.registerusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)  // 액션바 대신 툴바로 선언
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
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}