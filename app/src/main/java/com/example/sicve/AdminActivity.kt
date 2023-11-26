package com.example.sicve

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        getSupportActionBar()?.hide()

        val intent = getIntent()
        val username = intent.getStringExtra("username")
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        toolbar.setTitle("username: $username")


        val firstFragment = InsertFragment()
        val secondFragment = ModifyFragment()
        val thirdFragment = InformationsFragment()

        selectFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.add->selectFragment(firstFragment)
                R.id.modify->selectFragment(secondFragment)
                R.id.info->selectFragment(thirdFragment)

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