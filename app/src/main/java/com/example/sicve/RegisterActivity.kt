package com.example.sicve

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sicve.utils.DBHelper
import com.example.sicve.utils.Utils


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getSupportActionBar()?.hide()
        val view = this.findViewById<View>(android.R.id.content)
        val db = DBHelper(view.context)
        val dbw = db.writableDatabase
        Utils.generateRegisterForm(view, dbw)
    }


    fun selectFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.admin_fragment_id, fragment)
            commit()
        }
    }
}