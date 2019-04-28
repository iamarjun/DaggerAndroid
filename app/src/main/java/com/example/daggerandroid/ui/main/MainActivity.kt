package com.example.daggerandroid.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.daggerandroid.BaseActivity
import com.example.daggerandroid.R
import com.example.daggerandroid.ui.main.post.PostFragment

class MainActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testFragment()
    }

    private fun testFragment() {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, PostFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId) {

            R.id.logout -> sessionManager.logout()
        }


        return super.onOptionsItemSelected(item)
    }
}