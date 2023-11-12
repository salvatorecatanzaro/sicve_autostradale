package com.example.sicve

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class Admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        getSupportActionBar()?.hide()

        //var text = findViewById<TextView>(R.id.text_view)
        val intent = getIntent()
        var username = intent.getStringExtra("username")
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        toolbar.setTitle("username: $username")


        val firstFragment = Insert()
        val secondFragment = Modify()
        val thirdFragment = Informations()

        selectFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->selectFragment(firstFragment)
                R.id.person->selectFragment(secondFragment)
                R.id.settings->selectFragment(thirdFragment)

            }
            true
        }
    }


    fun selectFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.admin_fragment_id, fragment)
            commit()
        }
    }
}