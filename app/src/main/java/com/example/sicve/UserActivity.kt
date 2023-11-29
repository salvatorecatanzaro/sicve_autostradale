package com.example.sicve

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        getSupportActionBar()?.hide()
        val intent = getIntent()
        val username = intent.getStringExtra("username")
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        toolbar.setTitle("username: $username")


        val firstFragment = WalkHighwayFragment()
        val secondFragment = MessagesFragment()
        val thirdFragment = MyVehicleFragment()

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
            replace(R.id.user_fragment_id, fragment)
            commit()
        }
    }
}